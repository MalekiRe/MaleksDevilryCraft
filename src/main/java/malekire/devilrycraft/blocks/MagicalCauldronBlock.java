package malekire.devilrycraft.blocks;

import malekire.devilrycraft.blockentities.MagicalCauldronBlockEntity;
import malekire.devilrycraft.util.SpecialBlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MagicalCauldronBlock extends Block implements BlockEntityProvider {
    public MagicalCauldronBlock(Settings settings) {

        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.HONEY_LEVEL, 0).with(Properties.INVERTED, false));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HONEY_LEVEL);
        stateManager.add(Properties.INVERTED);
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

}
