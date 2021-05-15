package malekire.devilrycraft.objects.blockentities.sealhelpers;


import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.objects.components.SealMateWorldComponent;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.DevilryProperties;
import malekire.devilrycraft.util.SealCombinations;
import malekire.devilrycraft.util.portalutil.PortalFinderUtil;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.Level;

import java.util.List;

import java.util.ArrayList;

import static malekire.devilrycraft.common.DevilryBlocks.SEAL_BLOCK;
import static malekire.devilrycraft.util.CrystalType.NONE;
import static malekire.devilrycraft.util.DevilryProperties.*;

public class SealBlockEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable {
    public SealBlockEntity() {
        super(DevilryBlockEntities.SEAL_BLOCK_ENTITY);

    }
    public Direction facing;
    int tick = 0;
    public BlockPos offsetPos;
    public BlockState blockState;
    //A tag to allow lazy loading of a seal helper in getSealHelper() function.
    private CompoundTag sealHelperTag;
    //Boolean that is set and called in client to sync the seal and this to client.
    private boolean isDirty = false;
    //The instance of the seal connected to this seal block entity.
    private AbstractSealHelper sealHelper;
    //Boolean which is only set after all the seals are placed down, in a correct seal combination.
    boolean doHelperFunctions = false;
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
        if(this.sealHelper != null) {
            tag.put("seal_helper", this.sealHelper.toTag(new CompoundTag()));
        }
        tag.putBoolean("do_helper_functions", doHelperFunctions);
        return tag;
    }
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        doHelperFunctions = tag.getBoolean("do_helper_functions");
        if(tag.contains("seal_helper")) {
            for (String id : SealCombinations.sealCombinations.keySet()) {
                if (matchBlockState(SealCombinations.sealCombinations.get(id).crystalCombination, state)) {
                    sealHelper = SealCombinations.sealCombinations.get(id).getNewInstance();
                    sealHelperTag = tag.getCompound("seal_helper");
                }
            }
        }
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.fromTag(world.getBlockState(getPos()), tag);
        if(this.sealHelper != null) {
            this.getSealHelper().fromClientTag(tag);
        }
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        this.toTag(tag);
        if(this.sealHelper != null) {
            this.getSealHelper().toClientTag(tag);
        } else {
            Devilrycraft.LOGGER.log(Level.INFO, "seal is null in to client tag");
        }
        return tag;
    }

    public AbstractSealHelper getSealHelper() {
        if (sealHelperTag != null) {
            sealHelper.blockEntity = this;
            sealHelper.fromTag(this.blockState, sealHelperTag);
            sealHelperTag = null;
        }
        return sealHelper;
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
    private boolean destroyOnNextTick = false;

    public void markForDestroy() {
        destroyOnNextTick = true;
    }
    public void destroy() {
        if(sealHelper != null && hasFinalLayerFilled()) {
            SealMateWorldComponent.get(getWorld()).potentialSealMates.remove(SealTarget.of(getSealHelper()));
        }
        world.breakBlock(pos, false);
        if(sealHelper.hasMate) {
            ((SealBlockEntity)world.getBlockEntity(sealHelper.matePos)).markForDestroy();
            sealHelper.getMate().hasMate = false;
            sealHelper.getMate().matePos = null;
            sealHelper.matePos = null;
            sealHelper.hasMate = false;
        }
        sealHelper = null;
        doHelperFunctions = false;
    }
    @Override
    public void tick() {

        if(!getWorld().isClient() && isDirty) {
            isDirty = false;
            this.sync();
        }

        if(tick == 0)
        {
            facing = world.getBlockState(pos).get(Properties.FACING);
            offsetPos = pos.offset(facing.getOpposite());
        }
        if(tick > 1000)
        {
            tick = 1;
        }
        tick++;

        if(!world.isClient && tick > 0)
        {
            if(destroyOnNextTick) {
                destroy();
            }
            if(world.getBlockState(offsetPos).getBlock() == Blocks.AIR || world.getBlockState(offsetPos).getBlock() == SEAL_BLOCK)
            {
                markForDestroy();
            }
            if(blockState != getBlockState())
            {

                    blockState = getBlockState();
                if(blockState.getBlock() == SEAL_BLOCK) {
                    if (hasFinalLayerFilled()) {
                        for (String id : SealCombinations.sealCombinations.keySet()) {
                            if (matchBlockState(SealCombinations.sealCombinations.get(id).crystalCombination, blockState)) {
                                if(sealHelper != null) {
                                    continue;
                                }
                                sealHelper = SealCombinations.sealCombinations.get(id).getNewInstance(this);
                                if(sealHelper instanceof SealPortalHelper)
                                {
                                    ((SealPortalHelper)sealHelper).hasPortal = false;
                                }
                                sealHelper.oneOffTick(this);
                                doHelperFunctions = true;
                            }
                        }
                    }
                }
            }

            if(doHelperFunctions)
            {
                sealHelper.tick(this);
            }

            //performPortalFunction();
        }
    }



}
// TODO: remember to implement seal easter egg!!!!!! - nul