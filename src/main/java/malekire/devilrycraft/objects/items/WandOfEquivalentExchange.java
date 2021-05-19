package malekire.devilrycraft.objects.items;

import malekire.devilrycraft.objects.blockentities.seals.SealBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

public class WandOfEquivalentExchange extends Item {
    public WandOfEquivalentExchange(Settings settings) {
        super(settings);
    }
    public boolean isValid(Object world, Object blockPos, Block block) {
        //System.out.println(((World)world).getBlockState((BlockPos)blockPos).getBlock());
        if(((World)world).getBlockState((BlockPos)blockPos).getBlock().equals(block))
            return ((SealBlockEntity) ((World) world).getBlockEntity((BlockPos) blockPos)).getSeal() != null;
        return false;
    }
    public void whatToDo(World world, BlockPos blockPos, Block block, HashSet<BlockPos> blocks) {
        if(isValid(world, blockPos, block)) {
            System.out.println(blockPos);
            blocks.add(blockPos);
        }
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()) {
            //System.out.println("not client");
            Block block = context.getWorld().getBlockState(context.getBlockPos()).getBlock();
            //System.out.println(block);
            HashSet<BlockPos> blocks = new HashSet<>();
            BlockPos.iterateOutwards(context.getBlockPos(), 5, 5, 5).forEach((blockPos -> whatToDo(context.getWorld(), blockPos, block, blocks)));
            int stacks = 0;
            int extra = 0;

            for(int i = 0; i < blocks.size() - context.getPlayer().inventory.count(Items.COBBLESTONE); i++) {
                blocks.remove(blocks.size()-1);
            }
            System.out.println(blocks.size());
            for(BlockPos pos : blocks) {
                //System.out.println("setitng" + pos + "to cobbelstone");
                context.getWorld().setBlockState(pos, Blocks.COBBLESTONE.getDefaultState(), 3);
            }
            if (blocks.size() > 64) {
                stacks = (blocks.size() / 64);
            }
            extra = blocks.size() % 64;
            context.getPlayer().inventory.insertStack(new ItemStack(block.asItem(), extra));
            for (int i = 0; i < stacks; i++) {
                context.getPlayer().inventory.insertStack(new ItemStack(block.asItem(), 64));
            }
            System.out.println(stacks);
            System.out.println(extra);
        }

        return ActionResult.FAIL;
    }
}
