package malekire.devilrycraft.common;

import malekire.devilrycraft.fluid_api.FluidInteractionGroup;
import malekire.devilrycraft.fluid_api.FluidInteractionsRegistry;
import malekire.devilrycraft.fluid_api.FluidOreGenInteraction;
import malekire.devilrycraft.fluid_api.FluidSetBlockInteraction;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

import static malekire.devilrycraft.common.DevilryFluids.FLOWING_TAINT;
import static malekire.devilrycraft.common.DevilryFluids.FLOWING_VIS;

public class DevilryFluidInteractions {

    public static void registerFluidInteractions() {
        add(FLOWING_VIS, new FluidSetBlockInteraction(Fluids.FLOWING_LAVA, DevilryBlocks.VISTONE.getDefaultState()));
        add(FLOWING_TAINT, new FluidSetBlockInteraction(Fluids.FLOWING_LAVA, DevilryBlocks.VISTONE.getDefaultState()));
        add(FLOWING_VIS, new FluidOreGenInteraction(FLOWING_TAINT));
        add(FLOWING_TAINT, new FluidOreGenInteraction(FLOWING_VIS));
    }
    public static void add(Fluid fluid, FluidInteractionGroup fluidInteractionGroup) {
        FluidInteractionsRegistry.addInteraction(fluid, fluidInteractionGroup);
    }
}
