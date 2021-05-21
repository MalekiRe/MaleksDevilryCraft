package malekire.devilrycraft.fluid_api;

import malekire.devilrycraft.util.world.WorldUtil;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.function.Consumer;

public abstract class FluidInteractionGroup {
    final Fluid mainFluid;
    final Fluid secondaryFluid;
    final Identifier fluidInteractionId;
    public FluidInteractionGroup(Fluid mainFluid, Fluid secondaryFluid, Identifier fluidInteractionId) {
        this.mainFluid = mainFluid;
        this.secondaryFluid = secondaryFluid;
        this.fluidInteractionId = fluidInteractionId;
    }
    public abstract void doFluidCollisionFunction(World world, BlockPos pos);
    public Identifier getId() {
        return fluidInteractionId;
    }
}
