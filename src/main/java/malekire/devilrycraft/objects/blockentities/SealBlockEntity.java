package malekire.devilrycraft.objects.blockentities;


import malekire.devilrycraft.common.DevilryBlockEntities;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class SealBlockEntity extends BlockEntity implements Tickable {
    public SealBlockEntity() {
        super(DevilryBlockEntities.SEAL_BLOCK_ENTITY);
    }
    public Direction facing;
    int tick = 0;
    public BlockPos offsetPos;
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
        }
    }
}
