package malekire.devilrycraft.fluid_api;

import net.minecraft.fluid.Fluid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FluidInteractionsRegistry {
    public static final Map<Fluid, Set<FluidInteractionGroup>> fluidRegistry = new HashMap<>();
    public static void addInteraction(Fluid fluid, FluidInteractionGroup fluidInteractionGroup) {
        if(!fluidRegistry.containsKey(fluid) || fluidRegistry.get(fluid) == null) {
            fluidRegistry.put(fluid, new HashSet<>());
        }

        fluidRegistry.get(fluid).add(fluidInteractionGroup);
    }
}
