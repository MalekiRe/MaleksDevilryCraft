package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.blocks.*;
import malekire.devilrycraft.blocks.crystals.BaseCrystalBlock;
import malekire.devilrycraft.blocks.silverwood.SilverwoodLog;
import malekire.devilrycraft.mixins.BlockInvoker;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Shadow;

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


    public static final Block SILVERWOOD_LEAVES = new SilverwoodLeaves(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    //new SeeThroughBlock(FabricBlockSettings.of(Material.LEAVES).sounds(BlockSoundGroup.GRASS));
    public static final Block SILVERWOOD_LOG = new SilverwoodLog(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).nonOpaque());

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

        add(SILVERWOOD_LOG, "silverwood_log");
        add(SILVERWOOD_LEAVES, "silverwood_leaves_fixed");
    }
    public static void add(Block block2, String name) {
        blocks.add(new BlockRegistryHelper(block2, new Identifier(Devilrycraft.MOD_ID, name)));
    }

}
