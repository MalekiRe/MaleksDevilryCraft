package malekire.devilrycraft.fluid_api;

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


    static {
        VIS_TO_FLOWING_WATER = new FluidSetBlockInteraction(DevilryFluidRegistry.FLOWING_VIS, Fluids.WATER, new Identifier(MOD_ID, "vis_to_flowing_water"), Blocks.SAND.getDefaultState());
        fluidRegistry.put(DevilryFluidRegistry.FLOWING_VIS, VIS_TO_FLOWING_WATER);
    }
    protected void visFluidAndWaterInteraction() {

    }
}
