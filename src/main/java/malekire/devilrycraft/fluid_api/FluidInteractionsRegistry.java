package malekire.devilrycraft.fluid_api;

import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.common.DevilryFluidRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;
import static malekire.devilrycraft.common.DevilryFluidRegistry.FLOWING_TAINT;
import static malekire.devilrycraft.common.DevilryFluidRegistry.FLOWING_VIS;

public class FluidInteractionsRegistry {
    public static final Map<Fluid, Set<FluidInteractionGroup>> fluidRegistry = new HashMap<>();
    public static final Set<FluidInteractionGroup> VIS_INTERACTIONS = new HashSet<>();
    public static final Set<FluidInteractionGroup> TAINT_INTERACTIONS = new HashSet<>();

    public static final FluidInteractionGroup VISTONE_GENERATION_1;

    public static final FluidInteractionGroup VIS_TO_TAINT;
    public static final FluidInteractionGroup TAINT_TO_VIS;
    static {
        VISTONE_GENERATION_1 = new FluidSetBlockInteraction(FLOWING_VIS, Fluids.FLOWING_LAVA, new Identifier(MOD_ID, "vis_to_flowing_water"), DevilryBlocks.VISTONE.getDefaultState());


        VIS_TO_TAINT = new FluidOreGenInteraction(FLOWING_VIS, DevilryFluidRegistry.FLOWING_TAINT, new Identifier(MOD_ID, "vis_to_taint"));
        TAINT_TO_VIS = new FluidOreGenInteraction(DevilryFluidRegistry.FLOWING_TAINT, FLOWING_VIS, new Identifier(MOD_ID, "taint_to_vis"));

        VIS_INTERACTIONS.add(VISTONE_GENERATION_1);
        VIS_INTERACTIONS.add(VIS_TO_TAINT);

        TAINT_INTERACTIONS.add(TAINT_TO_VIS);

        fluidRegistry.put(FLOWING_VIS, VIS_INTERACTIONS);
        fluidRegistry.put(FLOWING_TAINT, TAINT_INTERACTIONS);
    }
    protected void visFluidAndWaterInteraction() {

    }
}
