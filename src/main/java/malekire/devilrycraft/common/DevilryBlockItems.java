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
    public static final BlockItem MAGICAL_CAULDRON_BLOCK_ITEM = new BlockItem(DevilryBlocks.MAGICAL_CAULDRON_BLOCK, DevilryDefaultItemSetting);
    public static final BlockItem BASIC_INFUSER_BLOCK_ITEM = new BlockItem(DevilryBlocks.BASIC_INFUSER, DevilryDefaultItemSetting);
    public static final BlockItem SILVERWOOD_LOG_BLOCK_ITEM = new BlockItem(DevilryBlocks.SILVERWOOD_LOG, DevilryDefaultItemSetting);
    public static final BlockItem SILVERWOOD_LEAVES_BLOCK_ITEM = new BlockItem(DevilryBlocks.SILVERWOOD_LEAVES, DevilryDefaultItemSetting);
    public static final BlockItem SILVERWOOD_PLANKS_BLOCK_ITEM = new BlockItem(DevilryBlocks.SILVERWOOD_PLANKS, DevilryDefaultItemSetting);
    public static final BlockItem VIS_PIPE_BLOCK_ITEM = new BlockItem(DevilryBlocks.VIS_PIPE, DevilryDefaultItemSetting);
    static {
        add(MAGICAL_CAULDRON_BLOCK_ITEM, "crucible");
        add(BASIC_INFUSER_BLOCK_ITEM, "basic_infuser");
        add(SILVERWOOD_LOG_BLOCK_ITEM, "silverwood_log");
        add(SILVERWOOD_LEAVES_BLOCK_ITEM, "silverwood_leaves");
        add(SILVERWOOD_PLANKS_BLOCK_ITEM, "silverwood_planks");
        add(VIS_PIPE_BLOCK_ITEM, "pipe/default");
    }

    public static void add(Item item2, String name) {
        blockItems.add(new ItemRegistryHelper(item2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
}
