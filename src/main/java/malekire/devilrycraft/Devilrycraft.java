package malekire.devilrycraft;

import com.google.common.collect.ImmutableList;
import malekire.devilrycraft.common.generation.DevilryOreGeneration;
import malekire.devilrycraft.common.generation.DevilryTreeGeneration;
import malekire.devilrycraft.common.*;

import malekire.devilrycraft.generation.tree_generation.SilverwoodTreeGeneration;
import malekire.devilrycraft.objects.blocks.SilverwoodSaplingGenerator;
import malekire.devilrycraft.objects.entities.SlimeZombieEntity;
import malekire.devilrycraft.common.DevilryFluidRegistry;
import malekire.devilrycraft.objects.particles.JavaCup;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.NetherBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DepthAverageDecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static malekire.devilrycraft.common.generation.DevilryTreeGeneration.*;
import static malekire.devilrycraft.util.render.DRenderUtil.interpolatePositionsThroughTime;



public class Devilrycraft implements ModInitializer, ClientModInitializer {
    public static final int mossSpreadRate = 4;
    public static final String MOD_ID = "devilry_craft";
    public static final Logger LOGGER = LogManager.getLogger("Devilrycraft");
    public static DefaultParticleType JAVA_CUP;
    public static Identifier DevilryID(String path) {
        return new Identifier(MOD_ID, path);
    }
    public static final RegistryKey<Biome> SILVERLAND_KEY = RegistryKey.of(Registry.BIOME_KEY, DevilryID("silver_land"));
    protected static final BlockState ANCIENT_DEBRIS = Blocks.ANCIENT_DEBRIS.getDefaultState();;

//    public static ConfiguredFeature<?, ?> SILVERWOOD_FOREST_CONFIGURED;
//    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
//        return (ConfiguredFeature)Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
//    }
    public static Feature<TreeFeatureConfig> SILVER_TREE = new SilverwoodTreeGeneration(TreeFeatureConfig.CODEC);
    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TRE_CONFIGURED = createConfiguredFeature("silverwood_tree_default", Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new JungleFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new ForkingTrunkPlacer(5, 2, 2), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build()));
    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TREE_0002_CONFIGURED = createConfiguredFeature("silverwood_tree_mega_jungle", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new JungleFoliagePlacer(UniformIntDistribution.of(3), UniformIntDistribution.of(0), 2), new MegaJungleTrunkPlacer(10, 2, 19), new TwoLayersFeatureSize(1, 2, 2))).ignoreVines().build()));
    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TREE_0003_CONFIGURED = createConfiguredFeature("silverwood_tree_mega_dark_oak", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(UniformIntDistribution.of(3), UniformIntDistribution.of(0)), new DarkOakTrunkPlacer(10, 2, 19), new TwoLayersFeatureSize(1, 2, 2))).ignoreVines().build()));
    public static final ConfiguredFeature<OreFeatureConfig, ?> DEVILRY_ORE_DEBRIS_LARGE = createConfiguredFeature("devilry_ore_debris_large", (ConfiguredFeature) Feature.NO_SURFACE_ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, ANCIENT_DEBRIS, 3)).decorate(Decorator.DEPTH_AVERAGE.configure(new DepthAverageDecoratorConfig(16, 8))).spreadHorizontally());
    public static final ConfiguredFeature<OreFeatureConfig, ?> DEVILRY_ORE_DEBRIS_SMALL = createConfiguredFeature("devilry_ore_debris_small", (ConfiguredFeature) Feature.NO_SURFACE_ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, ANCIENT_DEBRIS, 2)).decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(8, 16, 128))).spreadHorizontally());

    public static final ConfiguredFeature<?, ?> SILVERWOOD_FOREST_CONFIGURED = createConfiguredFeature("silver_forest", Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(SILVERWOOD_TREE_0002_CONFIGURED.withChance(0.5F), SILVERWOOD_TREE_0003_CONFIGURED.withChance(0.1F)), SILVERWOOD_TRE_CONFIGURED)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(50, 0.1F, 1))));

    //Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build());

    public static <FC extends FeatureConfig, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF createConfiguredFeature(String id, CF configuredFeature) {


        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);//please do something better later, gamma. - null | fuck you null. you are so fucking stupid that I cant even. spent so fucking much time im not fucking doing shit to this fucking function if it fucking works
        return configuredFeature;
    }


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
//        DevilryParticles.registerParticles();


        testPosEquation(new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), 0.5F, new Vec3d(0.5, 0, 0));
        testPosEquation(new Vec3d(0, 0, 0), new Vec3d(1, 1, 1), 0.5F, new Vec3d(0.5, 0.5, 0.5));
        FabricDefaultAttributeRegistry.register(DevilryEntities.SLIME_ZOMBIE_ENTITY_TYPE, SlimeZombieEntity.createZombieAttributes());


//        SILVERWOOD_FOREST_CONFIGURED = register("silver_forest", Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(SILVERWOOD_TREE_0002_CONFIGURED.withChance(0.5F), SILVERWOOD_TREE_0003_CONFIGURED.withChance(0.1F)), SILVERWOOD_TRE_CONFIGURED)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))));
        Registry.register(Registry.FEATURE, "silver_treee", SILVER_TREE);
        OverworldBiomes.addContinentalBiome(SILVERLAND_KEY, OverworldClimate.TEMPERATE, 2D);
        OverworldBiomes.addContinentalBiome(SILVERLAND_KEY, OverworldClimate.COOL, 2D);
        NetherBiomes.canGenerateInNether(SILVERLAND_KEY);
        NetherBiomes.addNetherBiome(SILVERLAND_KEY,  new Biome.MixedNoisePoint(2.0F, 2.0F, 1.0F, 1.0F, 25));
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
    public void onInitializeClient(){
        JAVA_CUP = ParticleRegistryUtils.registerParticles("java_cup");
        ParticleFactoryRegistry.getInstance().register(JAVA_CUP, JavaCup.DefaultFactory::new);
    }






}
