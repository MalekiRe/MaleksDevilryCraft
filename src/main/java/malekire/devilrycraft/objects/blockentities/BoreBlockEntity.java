package malekire.devilrycraft.objects.blockentities;

import com.tfc.minecraft_effekseer_implementation.meifabric.NetworkingFabric;
import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.common.DevilryBlockItems;
import malekire.devilrycraft.common.DevilrySounds;
import malekire.devilrycraft.vis_system.VisType;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.datafixer.fix.ChunkPalettedStorageFix;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BoreBlockEntity extends VisBlockEntity{
    public BoreBlockEntity() {
        super(DevilryBlockEntities.BORE_BLOCK_ENTITY);
    }

    @Override
    public boolean CanContainVisType(VisType type) {
        return false;
    }
    public BlockPos currentDistance = null;
    public int currentMining = 0;
    public int ticks = 0;
    Direction currentFacing;
    BlockPos outputPos;
    public BlockPos offsetDouble(Direction direction, BlockPos pos1, double i) {
            return i == 0 ? pos1 : new BlockPos(pos1.getX() + direction.getOffsetX() * i, pos1.getY() + direction.getOffsetY() * i, pos1.getZ() + direction.getOffsetZ() * i);
    }
    public void makeEffect() {
        BlockPos blockPos = getPos().add(0, 1.5, 0);
        Vector3d vec = new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        switch(currentFacing) {
            case NORTH : vec = new Vector3d(vec.x+.5, vec.y+.4, vec.z-1.65); break;
            case SOUTH : vec = new Vector3d(vec.x+.5, vec.y+.4, vec.z+2.65); break;
            case EAST :  vec = new Vector3d(vec.x+4.3, vec.y, vec.z);
            case WEST : vec = new Vector3d(vec.x-1.65, vec.y+.4, vec.z+.5); break;
        }
        NetworkingFabric.sendStartEffekPacket(
                Objects.requireNonNull(this.getWorld()), getEffekEmmiterName(),
                getEffekName(), 0, vec);

    }
    public void destroyEffect() {
        NetworkingFabric.sendEndEffekPacket(this.getWorld(), getEffekEmmiterName(), getEffekName(), true);
    }
    public Identifier getEffekEmmiterName() {
        switch(currentFacing)
        {
            case NORTH : return new Identifier("devilry_craft:energy_bolt_180");
            case SOUTH : return new Identifier("devilry_craft:energy_bolt_0");
            case EAST : return new Identifier("devilry_craft:energy_bolt_90");
            case WEST : return new Identifier("devilry_craft:energy_bolt_270");
        }
        return null;
    }
    public Identifier getEffekName() {
        return new Identifier("devilry_craft:energy_bolt" + getPos().getX() + getPos().getY() + getPos().getZ());
    }
    @Override
    public void tick() {

        if(!getWorld().isClient()) {
            if(ticks == 250)
            {
                ticks = 0;
                world.playSound(
                        null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                        this.getPos(), // The position of where the sound will come from
                        DevilrySounds.BORE, // The sound that will play
                        SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                        1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                        1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                );
                //NetworkingFabric.sendStartEffekPacket(Objects.requireNonNull(this.getWorld()), new Identifier("devilry_craft:energy_bolt"), new Identifier("devilry_craft:effeks"), 200, new Vector3d(getPos().getX(), getPos().getY(), getPos().getZ()));
            }
            if(currentDistance == null) {
                world.playSound(
                        null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                        this.getPos(), // The position of where the sound will come from
                        DevilrySounds.BORE, // The sound that will play
                        SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                        1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                        1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                );
                currentFacing = world.getBlockState(getPos()).get(Properties.FACING);
                makeEffect();


               currentDistance = getPos().add(0, 0, 0).offset(currentFacing.rotateYClockwise(), 2).offset(currentFacing.rotateYCounterclockwise(), 2);
                outputPos = getPos().add(0, 1, 0).offset(currentFacing.getOpposite(), 2);
            }
            if(currentMining == getMiningSpeed()) {
                //System.out.println("TRYING MINING");
                tryMining();
                currentMining = 0;
            }
            currentMining++;
            ticks++;
        }
    }
    public void tryMining() {
        BlockPos tryPos = null;
        //tryPos = currentDistance.add(0, 0, 0);
        //System.out.println(tryPos + " and block is " + world.getBlockState(tryPos).getBlock());

        for (int w = 0; w < getMaxWidth(); w++) {
            for (int h = 0; h < getMaxHeight(); h++) {
                tryPos = currentDistance.add(0, h, 0);
                if(w > 0) {
                    tryPos = currentDistance.add(0, h, 0).offset(currentFacing.rotateYClockwise(), w-1).offset(currentFacing, 2);
                }
                else
                {
                    tryPos = currentDistance.add(0, h, 0).offset(currentFacing.rotateYClockwise().getOpposite()).offset(currentFacing, 2);
                }

                //System.out.println("width : " + w + ", height : " + h);
                //System.out.println(world.getBlockState(tryPos));
                if(world.getBlockState(tryPos).getBlock() != Blocks.AIR) {
                        mineBlock(tryPos);
                        return;
                }
            }
        }

        currentDistance = currentDistance.offset(currentFacing);
    }
    public void mineBlock(BlockPos pos) {
        //System.out.println("Tried Mining Block Of " + world.getBlockState(pos).getBlock());
        LootContext.Builder builder = (new LootContext.Builder((ServerWorld) getWorld())).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).parameter(LootContextParameters.BLOCK_STATE, world.getBlockState(pos)).parameter(LootContextParameters.TOOL, new ItemStack(DevilryBlockItems.BORE_BLOCK_ITEM));
        List<ItemStack> stacks = world.getBlockState(pos).getDroppedStacks(builder);
        //System.out.println("stacks are : " + stacks);
        if(stacks.size() > 0)
            world.spawnEntity(new ItemEntity(world, outputPos.getX(), outputPos.getY(), outputPos.getZ(), new ItemStack(stacks.get(0).getItem(), stacks.size())));
        world.removeBlock(pos, false);
    }
    public int getMiningSpeed(){return 20;}

    public int getMaxWidth(){return 3;}
    public int getMaxHeight(){return 3;}
    public int getMaxDistance(){return 300;}
}
