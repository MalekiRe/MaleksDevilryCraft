package malekire.devilrycraft.objects.blocks.StrideBlocks;

import malekire.devilrycraft.objects.blockentities.StrideBlocks.LavaStrideBlockEntity;
import malekire.devilrycraft.objects.blockentities.StrideBlocks.WaterStrideBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class LavaStride extends BlockWithEntity {

    public static final BooleanProperty PERSISTENT = BooleanProperty.of("persistent");

    public LavaStride(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(PERSISTENT, false));


    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PERSISTENT);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(PERSISTENT, ctx.getPlayer() != null);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new LavaStrideBlockEntity();
    }
}