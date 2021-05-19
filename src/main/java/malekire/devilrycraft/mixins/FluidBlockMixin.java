package malekire.devilrycraft.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FluidBlock.class)
public interface FluidBlockMixin {
    @Invoker("receiveNeighborFluids") boolean receiveNeighborFluids(World world, BlockPos pos, BlockState state);
}
