package malekire.devilrycraft.util;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.blocks.MagicalCauldronBlock;
import malekire.devilrycraft.blocks.NoBoundingBoxBlock;
import malekire.devilrycraft.blocks.PortableHoleBlock;
import malekire.devilrycraft.blocks.crystals.VisCrystalBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class DevilryBlocks {
    public static ArrayList<BlockRegistryHelper> blocks = new ArrayList<>();
    public static final VisCrystalBlock VIS_CRYSTAL_BLOCK = new VisCrystalBlock(FabricBlockSettings.of(Material.GLASS).strength(4.0f).breakByHand(true).breakInstantly().noCollision().sounds(BlockSoundGroup.GLASS));
    public static final Block MAGICAL_CAULDRON_BLOCK = new MagicalCauldronBlock(FabricBlockSettings.of(Material.METAL).breakByHand(true).nonOpaque());
    public static final Block PORTABLE_HOLE_BLOCK = new PortableHoleBlock(FabricBlockSettings.of(Material.AIR).nonOpaque().luminance(10));
    public static final Block PORTABLE_HOLE_CORRUPTION_BLOCK = new NoBoundingBoxBlock(FabricBlockSettings.of(Material.AIR).nonOpaque());
    public static final Block FABRIC_OF_REALITY_BLOCK = new NoBoundingBoxBlock(FabricBlockSettings.of(Material.AIR));
    static {
        add(VIS_CRYSTAL_BLOCK, "vis_crystal_block");
        add(MAGICAL_CAULDRON_BLOCK, "magical_cauldron");
        add(PORTABLE_HOLE_BLOCK, "portable_hole_block");
        add(PORTABLE_HOLE_CORRUPTION_BLOCK, "portable_hole_corruption_block");
        add(FABRIC_OF_REALITY_BLOCK, "fabric_of_reality_block");
    }
    public static void add(Block block2, String name) {
        blocks.add(new BlockRegistryHelper(block2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
}
