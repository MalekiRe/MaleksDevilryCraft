package malekire.devilrycraft.fluid_api;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class FluidInteractionGroup {
    final Fluid mixtureFluid;
    public FluidInteractionGroup(Fluid mixtureFluid) {
        this.mixtureFluid = mixtureFluid;
    }
    public abstract void doFluidCollisionFunction(World world, BlockPos pos);

}
