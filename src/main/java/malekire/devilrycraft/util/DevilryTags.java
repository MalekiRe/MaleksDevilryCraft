package malekire.devilrycraft.util;

import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.block.Block;

public class DevilryTags {
    public final Tag.TagType<Block> connectableTag;

    public DevilryTags(Tag.TagType<Block> connectableTag) {
        this.connectableTag = connectableTag;
    }
}
