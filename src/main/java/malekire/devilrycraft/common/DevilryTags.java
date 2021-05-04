package malekire.devilrycraft.common;


import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class DevilryTags {
    public static final Tag VIS_CONTAINER = blockTag("vis_container");


    private static Tag<Block> blockTag(String name) {
        return TagRegistry.block(new Identifier("c", name));
    }
}
