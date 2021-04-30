package malekire.devilrycraft.blocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
@Mixin(Blocks.class)
public interface BlocksMixin {
    @Invoker("createLogBlock")
    static PillarBlock createLogBlock(MaterialColor topMaterialColor, MaterialColor sideMaterialColor) throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}