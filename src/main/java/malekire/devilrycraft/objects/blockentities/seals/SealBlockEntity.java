package malekire.devilrycraft.objects.blockentities.seals;


import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.objects.components.SealMateWorldComponent;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.SealCombinations;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

import static malekire.devilrycraft.common.DevilryBlocks.SEAL_BLOCK;
import static malekire.devilrycraft.objects.blockentities.seals.SealUtilities.sealCombinations;
import static malekire.devilrycraft.util.CrystalType.NONE;
import static malekire.devilrycraft.util.DevilryProperties.*;

public class SealBlockEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable {
    public SealBlockEntity() {
        super(DevilryBlockEntities.SEAL_BLOCK_ENTITY);
    }
    private Direction facing;
    private BlockPos offsetPos;
    public BlockState blockState;
    //A tag to allow lazy loading of a seal helper in getSealHelper() function.
    private CompoundTag sealTag;
    //Boolean that is set and called in client to sync the seal and this to client.
    private boolean isDirty = false;
    //The instance of the seal connected to this seal block entity.
    private AbstractSeal seal;
    //Boolean which is only set after all the seals are placed down, in a correct seal combination.
    boolean doSealFunction = false;
    boolean isFirstTick = true;
    private boolean destroyOnNextTick = false;
    public Direction getFacing() {
        if(facing == null) {
            facing = world.getBlockState(getPos()).get(Properties.FACING);
        }
        return facing;
    }
    public void markDirty() {
        super.markDirty();
        this.isDirty = true;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if(this.seal != null) {
            tag.put("seal_helper", this.seal.toTag(new CompoundTag()));
        }
        tag.putBoolean("do_helper_functions", doSealFunction);
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        doSealFunction = tag.getBoolean("do_helper_functions");
        if(tag.contains("seal_helper")) {
            for (String id : sealCombinations.keySet()) {
                if (matchBlockState(sealCombinations.get(id).crystalCombination, state)) {
                    seal = sealCombinations.get(id).getNewInstance();
                    sealTag = tag.getCompound("seal_helper");
                }
            }
        }
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.fromTag(world.getBlockState(getPos()), tag);
        if(this.seal != null) {
            this.getSeal().fromClientTag(tag);
        }
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        this.toTag(tag);
        if(this.seal != null) {
            this.getSeal().toClientTag(tag);
        } else {
            Devilrycraft.LOGGER.log(Level.INFO, "seal is null in to client tag");
        }
        return tag;
    }

    public AbstractSeal getSeal() {
        if (sealTag != null) {
            seal.blockEntity = this;
            seal.fromTag(this.blockState, sealTag);
            sealTag = null;
        }
        return seal;
    }

    public boolean matchBlockState(ArrayList<CrystalType> types, BlockState state)
    {
        if(state.getBlock() != SEAL_BLOCK)
            return false;
        if (state.get(FIRST_LAYER) != types.get(0))
            return false;
        if(types.size() > 1 && state.get(SECOND_LAYER) != types.get(1))
            return false;

        if(types.size() > 2 && state.get(THIRD_LAYER) != types.get(2))
            return false;

        if(types.size() > 3 && state.get(FOURTH_LAYER) != types.get(3))
            return false;

        return true;
    }
    public BlockState getBlockState() {
        return world.getBlockState(getPos());
    }
    public boolean hasFinalLayerFilled() {
        return getBlockState().get(FOURTH_LAYER) != NONE;
    }

    public void markForDestroy() {
        destroyOnNextTick = true;
    }
    public void destroy() {
        if(seal != null && hasFinalLayerFilled()) {
            SealMateWorldComponent.get(getWorld()).potentialSealMates.remove(SealTarget.of(getSeal()));
        }
        world.breakBlock(getPos(), false);
        if(seal.hasMate) {
            ((SealBlockEntity)world.getBlockEntity(seal.matePos)).markForDestroy();
            seal.getMate().hasMate = false;
            seal.getMate().matePos = null;
            seal.matePos = null;
            seal.hasMate = false;
        }
        seal = null;
        doSealFunction = false;
    }
    public BlockPos getOffsetPos() {
        if(offsetPos == null) {
            offsetPos = getPos().offset(getFacing().getOpposite());
        }
        return offsetPos;
    }
    @Override
    public void tick() {

        if(getWorld().isClient)
            return;

        //Syncs the client and server side, in case the seal has some client side rendering that depends on server side stuff.
        if(isDirty) {
            isDirty = false;
            this.sync();
        }
        //This exists because of some weird errors if we try to run the code below on the first tick.
        if(isFirstTick) {
            isFirstTick = false;
            return;
        }

        if(destroyOnNextTick)
            destroy();
        //Destroys the seal if the block behind it has been destroyed, aka is air, or if somehow it was placed in front of another seal.
        //NO SEAL STACKING ALLOWED ITS INHUMANE!
        if(world.getBlockState(getOffsetPos()).getBlock() == Blocks.AIR || world.getBlockState(getOffsetPos()).getBlock() == SEAL_BLOCK) {
            markForDestroy();
        }

        //Runs the seal function if we already set the seal.
        if(doSealFunction) {
            seal.tick(this);
        }

        //This runs when the blockstate changes, so when u add seals to the sealblock.
        if(blockState != getBlockState())
        {
            blockState = getBlockState();
            if(blockState.getBlock() == SEAL_BLOCK) {
                if (hasFinalLayerFilled()) {
                    for (String id : sealCombinations.keySet()) {
                        if (matchBlockState(sealCombinations.get(id).crystalCombination, blockState)) {
                            System.out.println("CREATED SEAL");
                            if(seal != null) {
                                continue;
                            }
                            seal = sealCombinations.get(id).getNewInstance(this);
                            //Idk why but this code needed to run at somepoint, dunno if it is still needed now.
                            if(seal instanceof SealPortal) {
                                ((SealPortal) seal).hasPortal = false;
                            }
                            seal.oneOffTick(this);
                            //Now that we set the seal, we can run the function every tick.
                            doSealFunction = true;
                        }
                    }
                }
            }
        }

    }



}
// TODO: remember to implement seal easter egg!!!!!! - nul