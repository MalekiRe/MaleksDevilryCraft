package malekire.devilrycraft.objects.blockentities;

import com.tfc.minecraft_effekseer_implementation.meifabric.NetworkingFabric;
import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.common.DevilryBlockItems;
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
    @Override
    public void tick() {

        if(!getWorld().isClient()) {
            if(ticks == 100)
            {
                ticks = 0;
                //NetworkingFabric.sendStartEffekPacket(Objects.requireNonNull(this.getWorld()), new Identifier("devilry_craft:energy_bolt"), new Identifier("devilry_craft:effeks"), 200, new Vector3d(getPos().getX(), getPos().getY(), getPos().getZ()));
            }
            if(currentDistance == null) {
                currentFacing = world.getBlockState(getPos()).get(Properties.FACING);
                //NetworkingFabric.sendStartEffekPacket(Objects.requireNonNull(this.getWorld()), new Identifier("devilry_craft:energy_bolt"), new Identifier("devilry_craft:effeks"), 0, new Vector3d(getPos().getX(), getPos().getY(), getPos().getZ()));
                currentDistance = getPos().add(0, 0, 0).offset(currentFacing.rotateYClockwise(), 2).offset(currentFacing.rotateYCounterclockwise(), 2);
            }
            if(currentMining == getMiningSpeed()) {
                System.out.println("TRYING MINING");
                tryMining();
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
        System.out.println("Tried Mining Block Of " + world.getBlockState(pos).getBlock());
        LootContext.Builder builder = (new LootContext.Builder((ServerWorld) getWorld())).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).parameter(LootContextParameters.BLOCK_STATE, world.getBlockState(pos)).parameter(LootContextParameters.TOOL, new ItemStack(DevilryBlockItems.BORE_BLOCK_ITEM));
        List<ItemStack> stacks = world.getBlockState(pos).getDroppedStacks(builder);
        System.out.println("stacks are : " + stacks);
        if(stacks.size() > 0)
            world.spawnEntity(new ItemEntity(world, getPos().getX(), getPos().getY(), getPos().getZ(), new ItemStack(stacks.get(0).getItem(), stacks.size())));
        world.removeBlock(pos, false);
    }
    public int getMiningSpeed(){return 20;}

    public int getMaxWidth(){return 3;}
    public int getMaxHeight(){return 3;}
    public int getMaxDistance(){return 300;}
}
