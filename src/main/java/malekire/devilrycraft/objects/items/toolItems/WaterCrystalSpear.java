package malekire.devilrycraft.objects.items.toolItems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.swing.*;

public class WaterCrystalSpear extends SwordItem implements Vanishable {
    public WaterCrystalSpear(DevilryCrystalSpearGroup instance, int i, float v, Settings settings) {
        super(new DevilryCrystalSpearGroup(), 8, -1.4F, settings);
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
            PlayerEntity playerEntity = (PlayerEntity)user;
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            if (i >= 0) {
                int j = EnchantmentHelper.getRiptide(stack);
                if (playerEntity.isTouchingWaterOrRain()) {


                    float f = playerEntity.yaw;
                    float g = playerEntity.pitch;
                    float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                    float k = -MathHelper.sin(g * 0.017453292F);
                    float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                    float m = MathHelper.sqrt(h * h + k * k + l * l);
                    float n = 20.0F * ((1.0F + (float)5) / 4.0F);
                    h *= n / m;
                    k *= n / m;
                    l *= n / m;
                    playerEntity.addVelocity((double)h, (double)k, (double)l);
                    playerEntity.setRiptideTicks(20);
                    if (playerEntity.isOnGround()) {
                        float o = 1.1999999F;
                        playerEntity.move(MovementType.SELF, new Vec3d(0.0D, 1.1999999284744263D, 0.0D));
                    }

                }
            }
        }
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else if (EnchantmentHelper.getRiptide(itemStack) > 0 && !user.isTouchingWaterOrRain()) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}
