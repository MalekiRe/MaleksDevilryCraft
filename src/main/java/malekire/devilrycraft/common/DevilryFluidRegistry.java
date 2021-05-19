package malekire.devilrycraft.common;

import malekire.devilrycraft.objects.fluids.VisFluid;
import malekire.devilrycraft.objects.fluids.fluid_blocks.MaleksFluidInteractoinFluidBlock;
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
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class DevilryFluidRegistry {
    public static ArrayList<FluidHelper1> fluids = new ArrayList<>();
    public static FlowableFluid STILL_VIS;
    public static FlowableFluid FLOWING_VIS;
    public static Item VIS_BUCKET;
    public static Block VIS_FLUID_BLOCK;
    //This cleanup is nowhere NEAR completed
    static {

    }
    public static void add(FlowableFluid STILL_FLUID, FlowableFluid FLOWING_FLUID, Block FLUID_BLOCK, FlowableFluid flowableFluid, String name) {
        fluids.add(new FluidHelper1(STILL_FLUID, FLOWING_FLUID, FLUID_BLOCK, flowableFluid, name));
    }
    public static void add(FlowableFluid STILL_FLUID, FlowableFluid FLOWING_FLUID, Block FLUID_BLOCK, Item FLUID_BUCKET, FlowableFluid flowableFluid, String name) {
        fluids.add(new FluidHelper2(STILL_FLUID, FLOWING_FLUID, FLUID_BLOCK, FLUID_BUCKET, flowableFluid, name));
    }

    public static void registerFluids() {


        STILL_VIS = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "vis"), new VisFluid.Still());
        FLOWING_VIS = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "flowing_vis"), new VisFluid.Flowing());
        VIS_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "vis_bucket"), new BucketItem(STILL_VIS, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        VIS_FLUID_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "vis"), new MaleksFluidInteractoinFluidBlock(STILL_VIS, FabricBlockSettings.copy(Blocks.WATER)){});

    }


}
class FluidHelper1 {
    public final FlowableFluid STILL_FLUID;
    public final FlowableFluid FLOWING_FLUID;
    public final Block FLUID_BLOCK;
    public final String name;
    public FlowableFluid flowableFluid;
    FluidHelper1(FlowableFluid still_fluid, FlowableFluid flowing_fluid, Block fluid_block, FlowableFluid flowableFluid, String name) {
        STILL_FLUID = still_fluid;
        FLOWING_FLUID = flowing_fluid;
        FLUID_BLOCK = fluid_block;
        this.flowableFluid = flowableFluid;
        this.name = name;
    }
}
class FluidHelper2 extends FluidHelper1{
    public final Item FLUID_BUCKET;
    FluidHelper2(FlowableFluid still_fluid,  FlowableFluid flowing_fluid, Block fluid_block, Item fluid_bucket, FlowableFluid flowableFluid, String name) {
        super(still_fluid, flowing_fluid, fluid_block, flowableFluid, name);
        this.FLUID_BUCKET = fluid_bucket;
    }
}
