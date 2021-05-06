package malekire.devilrycraft.objects.blocks;

import malekire.devilrycraft.objects.blockentities.MagicalCauldronBlockEntity;
import malekire.devilrycraft.util.DevilryProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static malekire.devilrycraft.common.DevilryBlockItems.MAGICAL_CAULDRON_BLOCK_ITEM;

public class MagicalCauldronBlock extends Block implements BlockEntityProvider {

    public MagicalCauldronBlock(Settings settings) {

        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.HONEY_LEVEL, 0).with(Properties.INVERTED, false).with(DevilryProperties.TAINTED_PERCENT, 1));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HONEY_LEVEL);
        stateManager.add(Properties.INVERTED);
        stateManager.add(DevilryProperties.TAINTED_PERCENT);
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MagicalCauldronBlockEntity();
    }
    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        dropStack(world, pos, new ItemStack(MAGICAL_CAULDRON_BLOCK_ITEM, 1));
    }

}
