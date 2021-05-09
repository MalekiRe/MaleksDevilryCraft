package malekire.devilrycraft.objects.blockentities;


import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.util.CrystalType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

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
        if (state.get(FIRST_LAYER) != types.get(0)) {
            return false;
        }
        if(types.size() > 1 && state.get(SECOND_LAYER) != types.get(1)) {
            return false;
        }
        if(types.size() > 2 && state.get(THIRD_LAYER) != types.get(2)) {
            return false;
        }
        if(types.size() > 3 && state.get(FOURTH_LAYER) != types.get(3)) {
            return false;
        }
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
            tick = 0;
        }
        tick++;
        if(!world.isClient)
        {
            if(world.getBlockState(offsetPos).getBlock() == Blocks.AIR)
            {
                world.breakBlock(pos, false);
            }
            if(blockState != world.getBlockState(pos))
            {
                blockState = world.getBlockState(pos);
                if(blockState.get(FIRST_LAYER) ==CrystalType.TAINT_TYPE)
                {

                }
            }
        }
    }
}
