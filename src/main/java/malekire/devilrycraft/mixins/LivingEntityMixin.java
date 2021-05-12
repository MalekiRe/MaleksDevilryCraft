package malekire.devilrycraft.mixins;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract Iterable<ItemStack> getArmorItems();
    @Shadow public abstract int getArmor();
    @Shadow @Final private DefaultedList<ItemStack> equippedArmor;
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Inject(method = "jump()V", at = @At("TAIL"))
    private void injectJumpMethod(CallbackInfo info) {
        for(ItemStack item : this.getArmorItems())
        {
            if(item.getItem() == Items.IRON_BOOTS)
                setVelocity(getVelocity().add(0, 10, 0));
        }

    }
    
    
    
    
    
        
        
        
        
        
    public double posX =  getX();
    public double posY =  getY()-1;
    public double posZ =  getZ();
    
    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void injectTickMovementMethod(CallbackInfo info) {

        for(ItemStack item : this.getArmorItems())
        {

            if(item.getItem() == DevilryArmorItems.BOOTS_OF_STRIDING && this.world.getBlockState(this.getBlockPos().down(1)).getBlock() == Blocks.WATER)
            {
                this.world.setBlockState(this.getBlockPos().down(1), Blocks.FROSTED_ICE.getDefaultState(), 2);


            }

        }
    }




//    @Inject(method = "canWalkOnFluid", at = @At("HEAD"))
//    private boolean injectFluidWalkMethod(Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
//        for(ItemStack item : this.getArmorItems())
//        {
//            if(item.getItem() == DevilryArmorItems.BOOTS_OF_STRIDING) {
//                return true;
//            }
//        }
//
//        return false;
//    }
}
