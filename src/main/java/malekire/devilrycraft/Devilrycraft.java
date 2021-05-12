package malekire.devilrycraft;

import malekire.devilrycraft.common.generation.DevilryOreGeneration;
import malekire.devilrycraft.common.generation.DevilryTreeGeneration;
import malekire.devilrycraft.common.*;
import malekire.devilrycraft.objects.blockentities.blockentityrenderers.SealBlockEntityRenderer;
import malekire.devilrycraft.objects.entities.SmallDirectionalLightningEntity;
import malekire.devilrycraft.common.DevilryFluidRegistry;
import malekire.devilrycraft.screen_stuff.screen_handlers.BasicInfuserScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.CallbackI;

import static malekire.devilrycraft.util.render.DRenderUtil.interpolatePositionsThroughTime;

public class Devilrycraft implements ModInitializer {
    public static final String MOD_ID = "devilry_craft";
    public static final Logger LOGGER = LogManager.getLogger("Devilrycraft");



    @Override
    public void onInitialize() {
        DevilryItemGroups.registerItemGroups();
        DevilryOreGeneration.RegisterFeatures();
        DevilryTreeGeneration.RegisterFeatures();
        DevilryRecipes.registerRecipies();
        DevilryFluidRegistry.registerFluids();
        DevilryBlockEntities.registerBlockEntities();
        DevilrySounds.registerSounds();
        DevilryBlocks.registerBlocks();
        DevilryItems.registerItems();
        DevilryBlockItems.registerBlockItems();
        DevilryArmorItems.registerArmorItems();
        testPosEquation(new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), 0.5F, new Vec3d(0.5, 0, 0));
        testPosEquation(new Vec3d(0, 0, 0), new Vec3d(1, 1, 1), 0.5F, new Vec3d(0.5, 0.5, 0.5));
    }
    public static void testPosEquation(Vec3d originPos, Vec3d destPos, float timeValue, Vec3d expectedValue)
    {
        System.out.println("Origin Pos : " + originPos);
        System.out.println("Dest Pos : " + destPos);
        System.out.println("Output Pos : " + interpolatePositionsThroughTime(originPos, destPos, timeValue));
        System.out.println("Expected Value : " + expectedValue);
        if(!expectedValue.equals(interpolatePositionsThroughTime(originPos, destPos, timeValue)))
        {
            for(int i = 0; i < 5; i++)
            {
                System.out.println("Expected Value not the same as Actual Value");
            }
        }
    }






}
