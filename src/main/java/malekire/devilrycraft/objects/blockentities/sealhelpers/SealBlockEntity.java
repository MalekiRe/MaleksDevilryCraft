package malekire.devilrycraft.objects.blockentities.sealhelpers;


import com.qouteall.immersive_portals.my_util.DQuaternion;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.SealCombinations;
import malekire.devilrycraft.util.math.beziercurves.BezierCurve;
import malekire.devilrycraft.util.math.beziercurves.Point;
import malekire.devilrycraft.util.portalutil.PortalFinderUtil;
import malekire.devilrycraft.util.portalutil.PortalFunctionUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

import static malekire.devilrycraft.common.DevilryBlocks.SEAL_BLOCK;
import static malekire.devilrycraft.util.CrystalType.NONE;
import static malekire.devilrycraft.util.DevilryProperties.*;

public class SealBlockEntity extends BlockEntity implements Tickable {
    public SealBlockEntity() {
        super(DevilryBlockEntities.SEAL_BLOCK_ENTITY);

    }
    public Direction facing;
    int tick = 0;
    public BlockPos offsetPos;
    public BlockState blockState;




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
    public SealHelperAbstractClass sealHelper;
    boolean doHelperFunctions = false;
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

            if(world.getBlockState(offsetPos).getBlock() == Blocks.AIR)
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
                                sealHelper = SealCombinations.sealCombinations.get(id);
                                if(sealHelper instanceof SealPortalHelper)
                                {
                                    ((SealPortalHelper)sealHelper).hasPortal = false;
                                }
                                sealHelper.doHelperOneOffFunction(this);
                                sealHelper.doHelperOneOffFunction();
                                doHelperFunctions = true;

                            }
                        }
                    }
                }
            }

            if(doHelperFunctions)
            {
                sealHelper.doHelperTick();
                sealHelper.doHelperTick(this);
            }

            //performPortalFunction();
        }
    }



}
