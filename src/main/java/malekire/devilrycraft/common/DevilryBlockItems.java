package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class DevilryBlockItems {
    public static ArrayList<ItemRegistryHelper> blockItems = new ArrayList<>();
    public static final FabricItemSettings DevilryDefaultItemSetting = new FabricItemSettings().group(Devilrycraft.ITEM_GROUP);
    static {
        add(new BlockItem(DevilryBlocks.MAGICAL_CAULDRON_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)), "magical_cauldron");
        add(new BlockItem(DevilryBlocks.BASIC_INFUSER, DevilryDefaultItemSetting), "basic_infuser");
    }

    public static void add(Item item2, String name) {
        blockItems.add(new ItemRegistryHelper(item2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
}
