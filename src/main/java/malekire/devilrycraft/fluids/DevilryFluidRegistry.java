package malekire.devilrycraft.fluids;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class DevilryFluidRegistry {
    public static FlowableFluid STILL_VIS;
    public static FlowableFluid FLOWING_VIS;
    public static Item VIS_BUCKET;
    public static Block VIS_FLUID_BLOCK;

    public static void RegisterFluids() {


        STILL_VIS = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "vis"), new VisFluid.Still());
        FLOWING_VIS = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "flowing_vis"), new VisFluid.Flowing());
        VIS_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "vis_bucket"), new BucketItem(STILL_VIS, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        VIS_FLUID_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "vis"), new FluidBlock(STILL_VIS, FabricBlockSettings.copy(Blocks.WATER)){});
    }
}
