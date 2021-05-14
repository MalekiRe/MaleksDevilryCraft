package malekire.devilrycraft.objects.blocks;

import malekire.devilrycraft.util.DevilryProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;

import static net.minecraft.block.Blocks.AIR;

public class SealRenderingHackBlock extends Block {
    public SealRenderingHackBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(DevilryProperties.HACKY_RENDERING_CHEAT, 0));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(DevilryProperties.HACKY_RENDERING_CHEAT);
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return AIR.getDefaultState();
    }

}
