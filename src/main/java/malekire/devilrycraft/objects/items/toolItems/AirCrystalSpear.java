package malekire.devilrycraft.objects.items.toolItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.util.math.Box;

import javax.swing.*;
import java.util.List;

public class AirCrystalSpear extends SwordItem implements Vanishable {
    public AirCrystalSpear(DevilryCrystalSpearGroup instance, int i, float v, Settings settings){
        super(new DevilryCrystalSpearGroup(), 8, -1.4F, settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }
    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return true;
    }


    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            if (i >= 20 && !(i >= 80)) {
                double posX = playerEntity.getX();
                double posY = playerEntity.getY();
                double posZ = playerEntity.getZ();
                Box airBox = new Box(posX-5, posY-5, posZ-5, posX+5, posY+5, posZ+5);

                List<Entity> entitiesToFlame = world.getOtherEntities(null, airBox, (entity) -> !(entity instanceof PlayerEntity) && entity.isPushable() && !(entity instanceof ItemEntity));
                for(int flameEntityTick=0;flameEntityTick<entitiesToFlame.size();flameEntityTick++){
                    entitiesToFlame.get(flameEntityTick).damage(DamageSource.MAGIC.setProjectile(), 15F);
                }

            }
        }
    }
}