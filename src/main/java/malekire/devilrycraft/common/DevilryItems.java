package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.items.PortableHole;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

import static malekire.devilrycraft.common.DevilryBlocks.VIS_CRYSTAL_BLOCK;

public class DevilryItems {
    public static final FabricItemSettings DevilryDefaultItemSetting = new FabricItemSettings().group(Devilrycraft.ITEM_GROUP);
    public static ArrayList<ItemRegistryHelper> items = new ArrayList<>();
    //public static final BlockItem VIS_CRYSTAL_BLOCK_ITEM = new BlockItem(VIS_CRYSTAL_BLOCK, DevilryDefaultItemSetting);
    public static final Item PORTABLE_HOLE = new PortableHole(DevilryDefaultItemSetting);
    public static final Item VIS_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item TAINTED_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item EARTH_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item FIRE_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item WATER_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item AIR_CRYSTAL = new Item(DevilryDefaultItemSetting);
    static {
        //add(VIS_CRYSTAL_BLOCK_ITEM, "vis_crystal_block");

        add(PORTABLE_HOLE, "portable_hole");
        add(VIS_CRYSTAL, "crystals/vis_crystal");
        add(TAINTED_CRYSTAL, "crystals/tainted_crystal");
        add(EARTH_CRYSTAL, "crystals/earth_crystal");
        add(FIRE_CRYSTAL, "crystals/fire_crystal");
        add(WATER_CRYSTAL, "crystals/water_crystal");
        add(AIR_CRYSTAL, "crystals/air_crystal");

    }
    public static void add(Item item2, String name) {
        items.add(new ItemRegistryHelper(item2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
}
