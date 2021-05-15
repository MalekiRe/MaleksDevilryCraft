package malekire.devilrycraft.mixins;

import malekire.devilrycraft.common.DevilryArmorItems;
import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.common.DevilryWeaponItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin<feetEquipmentSlot> extends Entity {
    @Shadow public abstract Iterable<ItemStack> getArmorItems();
    @Shadow public abstract int getArmor();
    @Shadow @Final private DefaultedList<ItemStack> equippedArmor;
    public Boolean isItemEquipped = false;
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract DamageTracker getDamageTracker();

    @Shadow protected abstract float applyArmorToDamage(DamageSource source, float amount);

    @Shadow public abstract ItemStack getActiveItem();

    @Shadow public abstract ItemStack getMainHandStack();

    @Shadow protected int riptideTicks;

    @Shadow public abstract BlockState getBlockState();

    @Shadow protected abstract boolean blockedByShield(DamageSource source);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Inject(method = "jump()V", at = @At("TAIL"))
    private void injectJumpMethod(CallbackInfo info) {
        for(ItemStack item : this.getArmorItems())
        {
            if(item.getItem() == DevilryArmorItems.BOOTS_OF_STRIDING) {
                setVelocity(getVelocity().add(0, 0.25F, 0));
            }else{
                setVelocity(getVelocity().add(0,0,0));
            }
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
                this.world.setBlockState(this.getBlockPos().down(1), DevilryBlocks.WATER_STRIDE.getDefaultState(), 2);


            }else if(item.getItem() == DevilryArmorItems.BOOTS_OF_STRIDING && this.world.getBlockState(this.getBlockPos().down(1)).getBlock() == Blocks.LAVA)
            {
                this.world.setBlockState(this.getBlockPos().down(1), DevilryBlocks.LAVA_STRIDE.getDefaultState(), 2);


            }

        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTickMethod(CallbackInfo info) {
        //I hate java sometimes, and this is one of them. took an hour to figure that out.
         if(getEquippedStack(EquipmentSlot.FEET).getItem() == DevilryArmorItems.BOOTS_OF_STRIDING){
            this.stepHeight = 1.6F;
         }else {
            this.stepHeight = 0.6F;
         }

         if(this.getMainHandStack().getItem() == DevilryWeaponItems.WATER_CRYSTAL_SPEAR && this.world.getBlockState(this.getBlockPos().down(1)).getBlock() != Blocks.AIR && this.fallDistance >= 3){

            this.fallDistance = 0;

         }



    }






//    this.stepHeight = 1.6F;
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
