package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.common.generation.DevilryTreeGeneration;
import malekire.devilrycraft.common.generation.GenerationAbstractBase;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import org.graalvm.compiler.lir.LIRInstruction;

import java.util.ArrayList;

import static malekire.devilrycraft.Devilrycraft.ID;

public class DevilryBiomes {
    public static ArrayList<BiomeRegistryHelper> biomes = new ArrayList<>();
    public static void addSilverwoodTrees(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, DevilryTreeGeneration.SILVERWOOD_TREE_CONFIGURED);
    }
    public static final RegistryKey<Biome> SILVERLAND_KEY = RegistryKey.of(Registry.BIOME_KEY, ID("silver_land"));
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> SILVERLAND_SURFACE_BUILDER = SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(DevilryBlocks.SILVER_MOSS.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState()));
    public static final Biome SILVER_LAND = createSilverland();

    public static Biome createSilverland() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addMonsters(spawnSettings, 95, 100, 60);

        GenerationSettings.Builder genSettings = new GenerationSettings.Builder();
        genSettings.surfaceBuilder(SILVERLAND_SURFACE_BUILDER);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(genSettings);
        DefaultBiomeFeatures.addLandCarvers(genSettings);
        DefaultBiomeFeatures.addDefaultLakes(genSettings);
        DefaultBiomeFeatures.addDungeons(genSettings);
        DefaultBiomeFeatures.addMineables(genSettings);
        DefaultBiomeFeatures.addDefaultOres(genSettings);
        DefaultBiomeFeatures.addDefaultDisks(genSettings);
        DefaultBiomeFeatures.addSprings(genSettings);
        DefaultBiomeFeatures.addAncientDebris(genSettings);
        DefaultBiomeFeatures.addEmeraldOre(genSettings);
        DefaultBiomeFeatures.addExtraGoldOre(genSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(genSettings);
        DefaultBiomeFeatures.addMossyRocks(genSettings);
        addSilverwoodTrees(genSettings);
        DefaultBiomeFeatures.addForestTrees(genSettings);

        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.NONE)
                .depth(0.125F)
                .scale(0.05F)
                .temperature(0.8F)
                .downfall(0.0F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x3f76e4)
                        .waterFogColor(0x050533)
                        .fogColor(0xc0d8ff)
                        .skyColor(0x77adff)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(genSettings.build())
                .build();
    }
    static {
        add(SILVER_LAND, "silver_land");
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
