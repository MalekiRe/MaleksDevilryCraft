package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SaddleItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

public class DevilryBlockItems {
    public static ArrayList<BlockItemRegistryHelper> blockItems = new ArrayList<>();
    public static final FabricItemSettings DevilryDefaultItemSetting = new FabricItemSettings().group(DevilryItemGroups.itemGroups.get("general"));
    public static final BlockItem MAGICAL_CAULDRON_BLOCK_ITEM = new BlockItem(DevilryBlocks.MAGICAL_CAULDRON_BLOCK, DevilryDefaultItemSetting);
    public static final BlockItem BASIC_INFUSER_BLOCK_ITEM = new BlockItem(DevilryBlocks.BASIC_INFUSER, DevilryDefaultItemSetting);
    public static final BlockItem SILVERWOOD_LOG_BLOCK_ITEM = new BlockItem(DevilryBlocks.SILVERWOOD_LOG, DevilryDefaultItemSetting);
    public static final BlockItem SILVERWOOD_LEAVES_BLOCK_ITEM = new BlockItem(DevilryBlocks.SILVERWOOD_LEAVES, DevilryDefaultItemSetting);
    public static final BlockItem SILVERWOOD_PLANKS_BLOCK_ITEM = new BlockItem(DevilryBlocks.SILVERWOOD_PLANKS, DevilryDefaultItemSetting);
    public static final BlockItem SILVERWOOD_SAPLING_BLOCK_ITEM = new BlockItem(DevilryBlocks.SILVERWOOD_SAPLING, DevilryDefaultItemSetting);
    public static final BlockItem VIS_PIPE_BLOCK_ITEM = new BlockItem(DevilryBlocks.VIS_PIPE, DevilryDefaultItemSetting);
    public static final BlockItem BORE_BLOCK_ITEM = new BlockItem(DevilryBlocks.BORE_BLOCK, DevilryDefaultItemSetting);
    public static final BlockItem WATER_STRIDE_ITEM = new BlockItem(DevilryBlocks.WATER_STRIDE, DevilryDefaultItemSetting);
    public static final BlockItem LAVA_STRIDE_ITEM = new BlockItem(DevilryBlocks.LAVA_STRIDE, DevilryDefaultItemSetting);
    public static final BlockItem BLACK_HOLE_ITEM = new BlockItem(DevilryBlocks.BLACK_HOLE, DevilryDefaultItemSetting);

    static {
        add(MAGICAL_CAULDRON_BLOCK_ITEM, "crucible");
        add(BASIC_INFUSER_BLOCK_ITEM, "basic_infuser");
        add(SILVERWOOD_LOG_BLOCK_ITEM, "silverwood_log");
        add(SILVERWOOD_LEAVES_BLOCK_ITEM, "silverwood_leaves");
        add(SILVERWOOD_PLANKS_BLOCK_ITEM, "silverwood_planks");
        add(SILVERWOOD_SAPLING_BLOCK_ITEM, "silverwood_sapling");
        add(VIS_PIPE_BLOCK_ITEM, "pipe/default");
        add(BORE_BLOCK_ITEM, "bore");
        add(WATER_STRIDE_ITEM, "water_stride");
        add(LAVA_STRIDE_ITEM, "lava_stride");
        add(BLACK_HOLE_ITEM, "black_hole");
    }

    public static void add(Item item2, String name) {
        blockItems.add(new BlockItemRegistryHelper(item2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
    public static void registerBlockItems() {
        for(BlockItemRegistryHelper blockItem : DevilryBlockItems.blockItems)
            Registry.register(Registry.ITEM, blockItem.identifier, blockItem.item);
    }
}
class BlockItemRegistryHelper {
    public final Item item;
    public final Identifier identifier;
    public BlockItemRegistryHelper(Item item, Identifier identifier) {
        this.item = item;
        this.identifier = identifier;
    }

}
