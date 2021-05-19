package malekire.devilrycraft.mixins;


import malekire.devilrycraft.common.DevilryBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class FeatureMixin {
    @Inject(method = "isSoil(Lnet/minecraft/block/Block;)Z", at = @At("HEAD"), cancellable = true)
    private static void isSoilMixin(Block block, CallbackInfoReturnable<Boolean> cir){
        if(block == DevilryBlocks.SILVER_MOSS){
            cir.setReturnValue(true);
        }
    }


}
