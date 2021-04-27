package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

public class BlockRegistryHelper {
    final public Identifier identifier;
    final public Block block;
    public BlockRegistryHelper(Block block, Identifier identifier) {
        this.identifier = identifier;
        this.block = block;
    }

}
