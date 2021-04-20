package malekire.devilrycraft.util;

import jdk.nashorn.internal.ir.Block;
import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.items.PortableHole;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import malekire.devilrycraft.util.DevilryBlocks.*;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

import static malekire.devilrycraft.util.DevilryBlocks.VIS_CRYSTAL_BLOCK;

public class DevilryItems {
    public static final FabricItemSettings DevilryDefaultItemSetting = new FabricItemSettings().group(ItemGroup.MISC);
    public static ArrayList<ItemRegistryHelper> items = new ArrayList<>();
    public static final BlockItem VIS_CRYSTAL_BLOCK_ITEM = new BlockItem(VIS_CRYSTAL_BLOCK, DevilryDefaultItemSetting);
    public static final Item PORTABLE_HOLE = new BlockItem(DevilryBlocks.PORTABLE_HOLE_BLOCK, DevilryDefaultItemSetting);
    static {
        add(VIS_CRYSTAL_BLOCK_ITEM, "vis_crystal_block");
        add(new BlockItem(DevilryBlocks.MAGICAL_CAULDRON_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)), "magical_cauldron");
        add(PORTABLE_HOLE, "portable_hole");
    }
    public static void add(Item item2, String name) {
        items.add(new ItemRegistryHelper(item2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
}
