package malekire.devilrycraft.mixins;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlockInvoker {
    @Invoker("createLeavesBlock")
    static LeavesBlock invokeCreateLeaves(){
        throw new AssertionError();
    }
    @Invoker("createLogBlock")
    static PillarBlock createLogBlock(MaterialColor topMaterialColor, MaterialColor sideMaterialColor) throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}
