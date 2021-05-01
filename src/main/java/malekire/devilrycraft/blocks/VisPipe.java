package malekire.devilrycraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static malekire.devilrycraft.common.DevilryBlockItems.MAGICAL_CAULDRON_BLOCK_ITEM;
import static malekire.devilrycraft.common.DevilryBlockItems.VIS_PIPE_BLOCK_ITEM;

public class VisPipe extends Block {
    public VisPipe(Settings settings) {
        super(settings);
    }
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        dropStack(world, pos, new ItemStack(VIS_PIPE_BLOCK_ITEM, 1));
    }


}
