package malekire.devilrycraft;

import malekire.devilrycraft.common.generation.DevilryOreGeneration;
import malekire.devilrycraft.common.generation.DevilryTreeGeneration;
import malekire.devilrycraft.common.*;
import malekire.devilrycraft.objects.blockentities.blockentityrenderers.SealBlockEntityRenderer;
import malekire.devilrycraft.objects.entities.SlimeZombieEntity;
import malekire.devilrycraft.objects.entities.SmallDirectionalLightningEntity;
import malekire.devilrycraft.common.DevilryFluidRegistry;
import malekire.devilrycraft.screen_stuff.screen_handlers.BasicInfuserScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
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
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.CallbackI;

import static malekire.devilrycraft.common.generation.DevilryTreeGeneration.SILVERWOOD_TREE;
import static malekire.devilrycraft.util.render.DRenderUtil.interpolatePositionsThroughTime;


public class Devilrycraft implements ModInitializer {
    public static final String MOD_ID = "devilry_craft";
    public static final Logger LOGGER = LogManager.getLogger("Devilrycraft");
    public static Identifier ID(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static final RegistryKey<Biome> SILVERLAND_KEY = RegistryKey.of(Registry.BIOME_KEY, ID("silver_land"));


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
        DevilryWeaponItems.registerWeaponItems();
        DevilryBiomes.registerBiomes();
        Registry.register(Registry.FEATURE, ID("silverwood_tree_generation"), SILVERWOOD_TREE);
        testPosEquation(new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), 0.5F, new Vec3d(0.5, 0, 0));
        testPosEquation(new Vec3d(0, 0, 0), new Vec3d(1, 1, 1), 0.5F, new Vec3d(0.5, 0.5, 0.5));
        FabricDefaultAttributeRegistry.register(DevilryEntities.SLIME_ZOMBIE_ENTITY_TYPE, SlimeZombieEntity.createZombieAttributes());



        OverworldBiomes.addContinentalBiome(SILVERLAND_KEY, OverworldClimate.TEMPERATE, 2D);
        OverworldBiomes.addContinentalBiome(SILVERLAND_KEY, OverworldClimate.COOL, 2D);
//        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier("devilry_craft", "silver_land"), DevilryBiomes.SILVERLAND_SURFACE_BUILDER);
//        Registry.register(BuiltinRegistries.BIOME, SILVERLAND_KEY.getValue(), DevilryBiomes.SILVER_LAND);
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
