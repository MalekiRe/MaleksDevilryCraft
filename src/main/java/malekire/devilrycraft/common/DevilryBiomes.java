package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;

import malekire.devilrycraft.common.generation.DevilryTreeGeneration;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decoratable;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;


import java.util.ArrayList;

import static malekire.devilrycraft.Devilrycraft.*;
import static malekire.devilrycraft.common.generation.DevilryOreGeneration.*;

public class DevilryBiomes {
    public static ArrayList<BiomeRegistryHelper> biomes = new ArrayList<>();
    public static void addSilverwoodTrees(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, Devilrycraft.SILVERWOOD_TRE_CONFIGURED);
    }
    public static void addSilverForest(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, SILVERWOOD_FOREST_CONFIGURED);
    }
    public static void addCrystalGeneration(GenerationSettings.Builder builder){
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, WATER_CRYSTAL_CONFIGURED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, TAINT_CRYSTAL_CONFIGURED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, VIS_CRYSTAL_CONFIGURED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, EARTH_CRYSTAL_CONFIGURED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, FIRE_CRYSTAL_CONFIGURED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, AIR_CRYSTAL_CONFIGURED);
        System.out.println("crystals being generated");
    }
    public static void addOverworldDebris(GenerationSettings.Builder builder){
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, DEVILRY_ORE_DEBRIS_LARGE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, DEVILRY_ORE_DEBRIS_SMALL);
    }
    public static final RegistryKey<Biome> SILVERLAND_KEY = RegistryKey.of(Registry.BIOME_KEY, DevilryID("silver_land"));
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> SILVER_FOREST_SURFACE_BUILDER = SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(DevilryBlocks.SILVER_MOSS.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()));
    public static final Biome SILVER_FOREST = createSilverland();

    public static Biome createSilverland() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addMonsters(spawnSettings, 95, 100, 60);

        GenerationSettings.Builder genSettings = new GenerationSettings.Builder();
        genSettings.surfaceBuilder(SILVER_FOREST_SURFACE_BUILDER);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(genSettings);
        DefaultBiomeFeatures.addLandCarvers(genSettings);
        DefaultBiomeFeatures.addDefaultLakes(genSettings);
        DefaultBiomeFeatures.addDungeons(genSettings);
        DefaultBiomeFeatures.addMineables(genSettings);
        DefaultBiomeFeatures.addDefaultOres(genSettings);
        DefaultBiomeFeatures.addDefaultDisks(genSettings);
        DefaultBiomeFeatures.addSprings(genSettings);
        addOverworldDebris(genSettings);
        DefaultBiomeFeatures.addEmeraldOre(genSettings);
        DefaultBiomeFeatures.addExtraGoldOre(genSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(genSettings);
        DefaultBiomeFeatures.addMossyRocks(genSettings);
//        addSilverwoodTrees(genSettings);
//        DefaultBiomeFeatures.addSavannaTrees(genSettings);
        addSilverForest(genSettings);
        addCrystalGeneration(genSettings);

//




        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.NONE)
                .depth(0.125F)
                .scale(0.05F)
                .temperature(0.8F)
                .downfall(0.0F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x9c9c9c)
                        .waterFogColor(0x5e5e5e)
                        .fogColor(0x242424)
                        .skyColor(0x788394)
                        .grassColor(0x999999)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(genSettings.build())
                .build();
    }
    static {
        add(SILVER_FOREST, "silver_land");
    }
    public static void add(Biome biome2, String name) {
        biomes.add(new BiomeRegistryHelper(biome2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
    public static void registerBiomes() {
        for(BiomeRegistryHelper biome1 : DevilryBiomes.biomes)
            Registry.register(BuiltinRegistries.BIOME, biome1.identifier, biome1.biome);


    }


}
class BiomeRegistryHelper {
    public final Biome biome;
    public final Identifier identifier;
    public BiomeRegistryHelper(Biome biome, Identifier identifier) {
        this.biome = biome;
        this.identifier = identifier;
    }

}
