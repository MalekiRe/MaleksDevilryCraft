package malekire.devilrycraft.objects.blocks;

import malekire.devilrycraft.objects.blockentities.BlackHoleBlockEntity;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class BlackHoleBlock extends BlockWithEntity {
    public BlackHoleBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new BlackHoleBlockEntity();
    }
}
