package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.items.PortableHole;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class DevilryItems {
    public static final FabricItemSettings DevilryDefaultItemSetting = new FabricItemSettings().group(
            DevilryItemGroups.itemGroups.get("general"));

    public static ArrayList<ItemRegistryHelper> items = new ArrayList<>();
    //public static final BlockItem VIS_CRYSTAL_BLOCK_ITEM = new BlockItem(VIS_CRYSTAL_BLOCK, DevilryDefaultItemSetting);
    public static final Item PORTABLE_HOLE = new PortableHole(DevilryDefaultItemSetting);
    public static final Item VIS_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item TAINTED_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item EARTH_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item FIRE_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item WATER_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item AIR_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item NECRONOMICON = new Item(DevilryDefaultItemSetting);
    static {
        //add(VIS_CRYSTAL_BLOCK_ITEM, "vis_crystal_block");

        add(PORTABLE_HOLE, "portable_hole");
        add(VIS_CRYSTAL, "crystals/vis_crystal");
        add(TAINTED_CRYSTAL, "crystals/tainted_crystal");
        add(EARTH_CRYSTAL, "crystals/earth_crystal");
        add(FIRE_CRYSTAL, "crystals/fire_crystal");
        add(WATER_CRYSTAL, "crystals/water_crystal");
        add(AIR_CRYSTAL, "crystals/air_crystal");
        add(NECRONOMICON, "necronomicon");

    }
    public static void add(Item item2, String name) {
        items.add(new ItemRegistryHelper(item2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
    public static void registerItems() {
        for(ItemRegistryHelper item : DevilryItems.items)
            Registry.register(Registry.ITEM, item.identifier, item.item);
    }


}
class ItemRegistryHelper {
    public final Item item;
    public final Identifier identifier;
    public ItemRegistryHelper(Item item, Identifier identifier) {
        this.item = item;
        this.identifier = identifier;
    }

}
