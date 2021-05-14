package malekire.devilrycraft.objects.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.util.math.BlockPos;

public interface BlockPosComponent extends ComponentV3 {
    BlockPos getBlockPos();
    void setBlockPos(BlockPos blockPos);
    BlockPos getMateBlockPos();
    void setMateBlockPos(BlockPos blockPos);
}
