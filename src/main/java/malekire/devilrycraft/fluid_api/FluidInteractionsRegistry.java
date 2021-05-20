package malekire.devilrycraft.fluid_api;

import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.common.DevilryFluidRegistry;
import malekire.devilrycraft.objects.fluids.BaseAbstractFluid;
import malekire.devilrycraft.objects.fluids.VisFluid;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class FluidInteractionsRegistry {
    public static final Map<Fluid, FluidInteractionGroup> fluidRegistry = new HashMap<>();

    public static final FluidInteractionGroup VIS_TO_FLOWING_WATER;
    public static final FluidInteractionGroup VIS_TO_TAINT;
    public static final FluidInteractionGroup TAINT_TO_VIS;
    static {
        VIS_TO_FLOWING_WATER = new FluidSetBlockInteraction(DevilryFluidRegistry.FLOWING_VIS, Fluids.FLOWING_LAVA, new Identifier(MOD_ID, "vis_to_flowing_water"), DevilryBlocks.VISTONE.getDefaultState());

        VIS_TO_TAINT = new FluidOreGenInteraction(DevilryFluidRegistry.FLOWING_VIS, DevilryFluidRegistry.FLOWING_TAINT, new Identifier(MOD_ID, "vis_to_taint"));
        TAINT_TO_VIS = new FluidOreGenInteraction(DevilryFluidRegistry.FLOWING_TAINT, DevilryFluidRegistry.FLOWING_TAINT, new Identifier(MOD_ID, "taint_to_vis"));
        fluidRegistry.put(DevilryFluidRegistry.FLOWING_VIS, VIS_TO_FLOWING_WATER);
        fluidRegistry.put(DevilryFluidRegistry.FLOWING_VIS, VIS_TO_TAINT);
        fluidRegistry.put(DevilryFluidRegistry.FLOWING_TAINT, TAINT_TO_VIS);
    }
    protected void visFluidAndWaterInteraction() {

    }
}
