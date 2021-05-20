package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.blocks.*;
import malekire.devilrycraft.objects.blocks.StrideBlocks.LavaStride;
import malekire.devilrycraft.objects.blocks.StrideBlocks.WaterStride;
import malekire.devilrycraft.objects.blocks.crystals.BaseCrystalBlock;
import malekire.devilrycraft.objects.blocks.silverwood.SilverwoodLog;
import malekire.devilrycraft.objects.blocks.silverwood.SilverwoodPlanks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class DevilryBlocks {
    public static ArrayList<BlockRegistryHelper> blocks = new ArrayList<>();
    public static final FabricBlockSettings CRYSTAL_SETTINGS = FabricBlockSettings.of(Material.GLASS).strength(4.0f).breakByHand(true).breakInstantly().noCollision().sounds(BlockSoundGroup.GLASS);
    public static final BaseCrystalBlock VIS_CRYSTAL_BLOCK = new BaseCrystalBlock(CRYSTAL_SETTINGS, DevilryItems.VIS_CRYSTAL);
    public static final BaseCrystalBlock TAINT_CRYSTAL_BLOCK = new BaseCrystalBlock(CRYSTAL_SETTINGS, DevilryItems.TAINTED_CRYSTAL);
    public static final BaseCrystalBlock EARTH_CRYSTAL_BLOCK = new BaseCrystalBlock(CRYSTAL_SETTINGS, DevilryItems.EARTH_CRYSTAL);
    public static final BaseCrystalBlock FIRE_CRYSTAL_BLOCK = new BaseCrystalBlock(CRYSTAL_SETTINGS, DevilryItems.FIRE_CRYSTAL);
    public static final BaseCrystalBlock WATER_CRYSTAL_BLOCK = new BaseCrystalBlock(CRYSTAL_SETTINGS, DevilryItems.WATER_CRYSTAL);
    public static final BaseCrystalBlock AIR_CRYSTAL_BLOCK = new BaseCrystalBlock(CRYSTAL_SETTINGS, DevilryItems.AIR_CRYSTAL);

    public static final Block MAGICAL_CAULDRON_BLOCK = new MagicalCauldronBlock(FabricBlockSettings.of(Material.METAL).breakByHand(true).nonOpaque());
    public static final Block PORTABLE_HOLE_BLOCK = new PortableHoleBlock(FabricBlockSettings.of(Material.AIR).nonOpaque().luminance(10));
    public static final Block PORTABLE_HOLE_CORRUPTION_BLOCK = new NoBoundingBoxBlock(FabricBlockSettings.of(Material.AIR).nonOpaque());
    public static final Block FABRIC_OF_REALITY_BLOCK = new NoBoundingBoxBlock(FabricBlockSettings.of(Material.AIR));
    public static final Block BASIC_INFUSER = new BasicInfuser(FabricBlockSettings.of(Material.STONE).nonOpaque());
    public static final Block VIS_PIPE = new VisPipe(FabricBlockSettings.of(Material.TNT).breakByHand(true).nonOpaque());

    public static final Block BORE_BLOCK = new BoreBlock(FabricBlockSettings.of(Material.METAL).strength(1F, 1F).breakByHand(true).nonOpaque());

    public static final Block SEAL_BLOCK = new SealBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().breakByHand(true).strength(1F, 1F));

    public static final Block SILVERWOOD_LEAVES = new SilverwoodLeaves(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block SILVERWOOD_LOG = new SilverwoodLog(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).nonOpaque().strength(2.0F, 3.0F));
    public static final Block SILVERWOOD_PLANKS = new SilverwoodPlanks(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0F, 3.0F));
    public static final Block SILVER_MOSS = new DevilryMoss(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).ticksRandomly().sounds(BlockSoundGroup.GRASS).strength(0.6F, 0.5F));
    public static final Block SILVERWOOD_SAPLING = new SaplingBlock(new SilverwoodSaplingGenerator(), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)){};

//    public static final Block STRIDE_WATER = new (FabricBlockSettings.of(Material.DENSE_ICE).sounds(BlockSoundGroup.GLASS).strength(0.1F, 0.1F));

    public static final Block WATER_STRIDE = new WaterStride(FabricBlockSettings.of(Material.ICE).luminance(15).sounds(BlockSoundGroup.GLASS).strength(0.1F, 0.1F));
    public static final Block LAVA_STRIDE = new LavaStride(FabricBlockSettings.of(Material.ICE).luminance(15).sounds(BlockSoundGroup.STONE).strength(0.1F, 0.1F));

    public static final Block BLACK_HOLE = new BlackHoleBlock(FabricBlockSettings.of(Material.GLASS));

    public static final Block VISTONE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block TAINTSTONE = new Block(FabricBlockSettings.of(Material.STONE));

    static {
        add(VIS_CRYSTAL_BLOCK, "crystal/vis");
        add(TAINT_CRYSTAL_BLOCK, "crystal/taint");
        add(EARTH_CRYSTAL_BLOCK, "crystal/earth");
        add(FIRE_CRYSTAL_BLOCK, "crystal/fire");
        add(WATER_CRYSTAL_BLOCK, "crystal/water");
        add(AIR_CRYSTAL_BLOCK, "crystal/air");

        add(MAGICAL_CAULDRON_BLOCK, "crucible");
        add(PORTABLE_HOLE_BLOCK, "portable_hole_block");
        add(PORTABLE_HOLE_CORRUPTION_BLOCK, "portable_hole_corruption_block");
        add(FABRIC_OF_REALITY_BLOCK, "fabric_of_reality_block");
        add(BASIC_INFUSER, "basic_infuser");
        add(VIS_PIPE, "pipe/default");


        add(SILVERWOOD_LOG, "silverwood_log");
        add(SILVERWOOD_LEAVES, "silverwood_leaves_fixed");
        add(SILVERWOOD_PLANKS, "silverwood_planks");
        add(SILVER_MOSS, "silverwood_moss");
        add(SILVERWOOD_SAPLING, "silverwood_sapling");

        add(BORE_BLOCK, "bore");

        add(SEAL_BLOCK, "seal_block");
        add(new SealRenderingHackBlock(FabricBlockSettings.of(Material.AIR)), "dummy/dummy_seal");

        add(WATER_STRIDE, "water_stride");
        add(LAVA_STRIDE, "lava_stride");

        add(BLACK_HOLE, "black_hole");

        add(VISTONE, "vistone");
        add(TAINTSTONE, "taintstone");

    }

    public static void add(Block block2, String name) {
        blocks.add(new BlockRegistryHelper(block2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
    public static void registerBlocks() {
        for(BlockRegistryHelper block : DevilryBlocks.blocks)
            Registry.register(Registry.BLOCK, block.identifier, block.block);
    }


}
class BlockRegistryHelper {
    final public Identifier identifier;
    final public Block block;

    public BlockRegistryHelper(Block block, Identifier identifier) {
        this.identifier = identifier;
        this.block = block;
    }

}

