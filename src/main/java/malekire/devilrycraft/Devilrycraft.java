package malekire.devilrycraft;

import malekire.devilrycraft.blockentities.MagicalCauldronBlockEntity;
import malekire.devilrycraft.blockentities.PortableHoleBlockEntity;
import malekire.devilrycraft.entities.SmallDirectionalLightningEntity;
import malekire.devilrycraft.generation.crystal_generation.CrystalGenerationFeature;
import malekire.devilrycraft.util.BlockRegistryHelper;
import malekire.devilrycraft.util.DevilryBlocks;
import malekire.devilrycraft.util.DevilryItems;
import malekire.devilrycraft.util.ItemRegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;

public class Devilrycraft implements ModInitializer {
    public static final String MOD_ID = "devilry_craft";
    DevilryBlocks devilryBlocks = new DevilryBlocks();
    DevilryItems devilryItems = new DevilryItems();


    private static final Feature<OreFeatureConfig> STONE_SPIRAL = new CrystalGenerationFeature(OreFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> STONE_SPIRAL_CONFIGURED = STONE_SPIRAL.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
            Blocks.WHITE_WOOL.getDefaultState(),
            9))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(
            0,
            0,
            64)))
            .spreadHorizontally()
            .repeat(50); // number of veins per chunk


    public static BlockEntityType<MagicalCauldronBlockEntity> MAGICAL_CAULDRON_BLOCK_ENTITY;
    public static BlockEntityType<PortableHoleBlockEntity> PORTABLE_HOLE_BLOCK_ENTITY;
    public static Identifier CHAOS_PORTAL_ID = new Identifier(MOD_ID, "chaos_portal");
    public static SoundEvent CHAOS_PORTAL = new SoundEvent(CHAOS_PORTAL_ID);
    public static final EntityType<SmallDirectionalLightningEntity> SMALL_DIRECTIONAL_LIGHTNING_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("devilry_craft", "small_directional_lightning"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SmallDirectionalLightningEntity::new).dimensions(EntityDimensions.fixed(0f, 0f)).trackRangeBlocks(16).trackedUpdateRate(Integer.MAX_VALUE).build()
    );
    @Override
    public void onInitialize() {
        RegistryKey<ConfiguredFeature<?, ?>> stoneSpiral = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
                new Identifier("devilry_craft", "stone_spiral"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, stoneSpiral.getValue(), STONE_SPIRAL_CONFIGURED);
        Registry.register(Registry.FEATURE, new Identifier("devilry_craft", "stone_spiral"), STONE_SPIRAL);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.RAW_GENERATION, stoneSpiral);
        Registry.register(Registry.SOUND_EVENT, CHAOS_PORTAL_ID, CHAOS_PORTAL);


    RegisterBlocks();
    RegisterItems();

    MAGICAL_CAULDRON_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:magical_cauldron",
            BlockEntityType.Builder.create(MagicalCauldronBlockEntity::new, DevilryBlocks.MAGICAL_CAULDRON_BLOCK).build(null));
        PORTABLE_HOLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:portable_hole",
                BlockEntityType.Builder.create(PortableHoleBlockEntity::new, DevilryBlocks.PORTABLE_HOLE_BLOCK).build(null));

    }



    public static void RegisterBlocks() {
        for(BlockRegistryHelper block : DevilryBlocks.blocks)
            Registry.register(Registry.BLOCK, block.identifier, block.block);
    }
    public static void RegisterItems() {
        for(ItemRegistryHelper item : DevilryItems.items)
            Registry.register(Registry.ITEM, item.identifier, item.item);
    }
}
