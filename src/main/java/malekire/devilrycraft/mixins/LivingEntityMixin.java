package malekire.devilrycraft.mixins;

import net.minecraft.block.Blocks;
import com.mojang.datafixers.types.templates.List;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import jdk.nashorn.internal.codegen.CompilerConstants;
import malekire.devilrycraft.common.DevilryArmorItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.fluid.Fluids.FLOWING_WATER;
import static net.minecraft.fluid.Fluids.WATER;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {


    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    @Shadow public abstract int getArmor();

    @Shadow @Final private DefaultedList<ItemStack> equippedArmor;

    @Shadow public abstract void setMovementSpeed(float movementSpeed);

    @Shadow public abstract float getAbsorptionAmount();

    @Shadow public float forwardSpeed;

    @Shadow public abstract BlockState getBlockState();

    public double posX =  getX();
    public double posY =  getY()-1;
    public double posZ =  getZ();



    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "jump()V", at = @At("TAIL"))
    private void injectJumpMethod(CallbackInfo info) {
        for(ItemStack item : this.getArmorItems())
        {
            if(item.getItem() == DevilryArmorItems.BOOTS_OF_STRIDING)
                setVelocity(getVelocity().add(0, 0.15, 0));
        }
    }
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
