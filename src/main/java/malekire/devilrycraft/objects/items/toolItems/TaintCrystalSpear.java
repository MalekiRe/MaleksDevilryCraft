package malekire.devilrycraft.objects.items.toolItems;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import javax.swing.*;

public class TaintCrystalSpear extends SwordItem implements Vanishable {
    public TaintCrystalSpear(DevilryCrystalSpearGroup instance, int i, float v, Settings settings) {
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
            if (i >= 10 && !(i >= 80)) {



                world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));

                if (!world.isClient) {
                    EnderPearlEntity enderPearlEntity = new EnderPearlEntity(world, user);
                    enderPearlEntity.setItem(user.getStackInHand(Hand.MAIN_HAND));
                    enderPearlEntity.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
                    world.spawnEntity(enderPearlEntity);
                }
            }
        }
    }



}