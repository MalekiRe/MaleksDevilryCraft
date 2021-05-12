package malekire.devilrycraft.mixins;

import malekire.devilrycraft.objects.blockentities.MagicalCauldronBlockEntity;
import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.common.DevilryItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity{

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }



    @Shadow
    public abstract ItemStack getStack();
    @Inject(method = "tick()V", at = @At("RETURN"))
    private void tickMethod(CallbackInfo info) {
        if (!this.world.isClient && this.world.getBlockState(this.getBlockPos().down(1)).getBlock() == DevilryBlocks.MAGICAL_CAULDRON_BLOCK)
        {
            if(((MagicalCauldronBlockEntity)this.world.getBlockEntity(this.getBlockPos().down(1))).consumeItemStack(this.getStack()))
            {
                this.getStack().decrement(1);

            }
        }
    }
}
