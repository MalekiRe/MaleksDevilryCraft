package malekire.devilrycraft.common;


import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class DevilryTags {
    public static final Tag IS_VIS_INSERT_CAPABLE = blockTag("vis_insert_capable");
    public static final Tag IS_VIS_EXTRACT_CAPABLE = blockTag("vis_extract_capable");

    private static Tag<Block> blockTag(String name) {
        return TagRegistry.block(new Identifier("c", name));
    }

    public static boolean isVisInsertCapable(Block block){return IS_VIS_INSERT_CAPABLE.contains(block);}
    public static boolean isVisExtractCapable(Block block){return IS_VIS_EXTRACT_CAPABLE.contains(block);}
}
