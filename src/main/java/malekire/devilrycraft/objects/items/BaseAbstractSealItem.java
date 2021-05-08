package malekire.devilrycraft.objects.items;

import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.objects.blockentities.SealBlockEntity;
import malekire.devilrycraft.util.CrystalType;
import net.minecraft.block.Block;
import net.minecraft.block.DeadBushBlock;
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

        if(world.getBlockState(pos).getBlock() != DevilryBlocks.SEAL_BLOCK)
        {
            System.out.println("NOT SEAL BLOCK");

            world.setBlockState(pos.offset(context.getPlayerFacing().getOpposite()),
                    DevilryBlocks.SEAL_BLOCK.getDefaultState().with(FIRST_LAYER, crystalType).with(Properties.FACING, context.getPlayerFacing().getOpposite()), 2);
        }
        else
        {
            if(world.getBlockState(pos).get(SECOND_LAYER) == CrystalType.NONE)
            {
                world.setBlockState(pos,
                        world.getBlockState(pos).with(SECOND_LAYER, crystalType), 2);
                return ActionResult.PASS;
            }
            if(world.getBlockState(pos).get(THIRD_LAYER) == CrystalType.NONE)
            {
                world.setBlockState(pos,
                        world.getBlockState(pos).with(THIRD_LAYER, crystalType), 2);
                return ActionResult.PASS;
            }
            if(world.getBlockState(pos).get(FOURTH_LAYER) == CrystalType.NONE)
            {
                world.setBlockState(pos,
                        world.getBlockState(pos).with(FOURTH_LAYER, crystalType), 2);
                return ActionResult.PASS;
            }
        }


        return ActionResult.PASS;
    }

}
