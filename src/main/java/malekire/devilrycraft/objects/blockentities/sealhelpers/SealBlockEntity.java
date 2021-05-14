package malekire.devilrycraft.objects.blockentities.sealhelpers;


import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.SealCombinations;
import malekire.devilrycraft.util.portalutil.PortalFinderUtil;
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

import java.util.List;

import java.util.ArrayList;

import static malekire.devilrycraft.common.DevilryBlocks.SEAL_BLOCK;
import static malekire.devilrycraft.util.CrystalType.NONE;
import static malekire.devilrycraft.util.DevilryProperties.*;

public class SealBlockEntity extends BlockEntity implements Tickable{
    public SealBlockEntity() {
        super(DevilryBlockEntities.SEAL_BLOCK_ENTITY);

    }
    public Direction facing;
    int tick = 0;
    public BlockPos offsetPos;
    public BlockState blockState;
    private CompoundTag sealHelperTag;

    private AbstractSealHelper sealHelper;
    boolean doHelperFunctions = false;
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


    @Override
    public void tick() {


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

            if(world.getBlockState(offsetPos).getBlock() == Blocks.AIR || world.getBlockState(offsetPos).getBlock() == SEAL_BLOCK)
            {
                PortalFinderUtil.sealBlockEntities.remove(this);
                world.breakBlock(pos, false);
            }
            if(blockState != world.getBlockState(pos))
            {

                    blockState = world.getBlockState(pos);
                if(blockState.getBlock() == SEAL_BLOCK) {
                    if (blockState.get(FOURTH_LAYER) != NONE) {
                        for (String id : SealCombinations.sealCombinations.keySet()) {
                            if (matchBlockState(SealCombinations.sealCombinations.get(id).crystalCombination, blockState)) {
                                if(sealHelper != null)
                                    continue;
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