package malekire.devilrycraft.objects.blocks;

import malekire.devilrycraft.objects.blockentities.SealBlockEntity;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.DevilryProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import static malekire.devilrycraft.util.DevilryProperties.*;

public class SealBlock extends BlockWithEntity implements Tickable {
    public SealBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState()
                .with(FIRST_LAYER, CrystalType.NONE)
                .with(SECOND_LAYER, CrystalType.NONE)
                .with(THIRD_LAYER, CrystalType.NONE)
                .with(FOURTH_LAYER, CrystalType.NONE)
                .with(Properties.FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new SealBlockEntity();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FIRST_LAYER);
        stateManager.add(SECOND_LAYER);
        stateManager.add(THIRD_LAYER);
        stateManager.add(FOURTH_LAYER);
        stateManager.add(Properties.FACING);
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(Properties.FACING, ctx.getPlayerFacing());
    }

    @Override
    public void tick() {

    }
}
