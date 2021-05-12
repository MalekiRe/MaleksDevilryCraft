package malekire.devilrycraft.objects.blocks.StrideBlocks;

import malekire.devilrycraft.objects.blockentities.StrideBlocks.WaterStrideBlockEntity;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class WaterStride extends BlockWithEntity {
    public WaterStride(Settings settings) {
        super(settings);

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new WaterStrideBlockEntity();
    }
}