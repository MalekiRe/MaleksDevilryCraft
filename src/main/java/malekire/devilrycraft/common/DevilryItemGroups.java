package malekire.devilrycraft.common;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class DevilryItemGroups {
    public static HashMap<String, ItemGroup> itemGroups = new HashMap<>();
    public static ArrayList<ItemGroupHelper> itemGroupHelpers = new ArrayList<>();
    public static final ItemGroup GENERAL = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(DevilryItems.VIS_FLASK));
    public static void add(Item item, String name)
    {
        itemGroupHelpers.add(new ItemGroupHelper(item, name));
    }

}
class ItemGroupHelper {
    public final Item ITEM;
    public final String NAME;
    public ItemGroupHelper(Item item, String name) {
        ITEM = item;
        NAME = name;
    }
}
