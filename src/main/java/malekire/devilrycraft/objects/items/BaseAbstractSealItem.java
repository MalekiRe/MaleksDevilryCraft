package malekire.devilrycraft.objects.items;

import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.util.CrystalType;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static malekire.devilrycraft.util.DevilryProperties.*;

public class BaseAbstractSealItem extends Item {
    public final CrystalType crystalType;
    public BaseAbstractSealItem(Settings settings, CrystalType crystalType) {
        super(settings);
        this.crystalType = crystalType;

    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockPos offsetPos = pos.offset(context.getPlayerFacing().getOpposite());
        if(world.getBlockState(offsetPos).getBlock() != Blocks.AIR && world.getBlockState(offsetPos).getBlock() != DevilryBlocks.SEAL_BLOCK)
        {
            return ActionResult.FAIL;
        }
        if(world.getBlockState(offsetPos).getBlock() != DevilryBlocks.SEAL_BLOCK)
        {
            world.setBlockState(offsetPos,
                    DevilryBlocks.SEAL_BLOCK.getDefaultState().with(FIRST_LAYER, crystalType).with(Properties.FACING, context.getPlayerFacing().getOpposite()), 2);
        }
        else
        {
            if(world.getBlockState(offsetPos).get(SECOND_LAYER) == CrystalType.NONE)
            {
                world.setBlockState(offsetPos,
                        world.getBlockState(offsetPos).with(SECOND_LAYER, crystalType), 2);
                return ActionResult.PASS;
            }
            else if(world.getBlockState(offsetPos).get(THIRD_LAYER) == CrystalType.NONE)
            {
                world.setBlockState(offsetPos,
                        world.getBlockState(offsetPos).with(THIRD_LAYER, crystalType), 2);
                return ActionResult.PASS;
            }
            else if(world.getBlockState(offsetPos).get(FOURTH_LAYER) == CrystalType.NONE)
            {
                world.setBlockState(offsetPos,
                        world.getBlockState(offsetPos).with(FOURTH_LAYER, crystalType), 2);
                return ActionResult.PASS;
            }
        }


        return ActionResult.PASS;
    }

}
