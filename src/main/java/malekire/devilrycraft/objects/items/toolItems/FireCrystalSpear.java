package malekire.devilrycraft.objects.items.toolItems;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import malekire.devilrycraft.objects.items.toolItems.DevilryCrystalSpearGroup;

public class FireCrystalSpear extends SwordItem implements Vanishable {
    public FireCrystalSpear(DevilryCrystalSpearGroup instance, int i, float v, Settings settings) {
        super(new DevilryCrystalSpearGroup(), 8, -1.4F, settings);
    }
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
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
            if (i >= 25 && !(i >= 85)) {

                //this is basically ripped from the ghast's and trident's code... lol

                float f = playerEntity.yaw;
                float g = playerEntity.pitch;
                float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                float k = -MathHelper.sin(g * 0.017453292F);
                float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                float m = MathHelper.sqrt(h * h + k * k + l * l);
                float n = 3.0F * (1.0F / 4.0F);
                h *= n / m;
                k *= n / m;
                l *= n / m;
                double posiX = playerEntity.getX();
                double posiY = playerEntity.getY();
                double posiZ = playerEntity.getZ();

                FireballEntity fireballEntity = new FireballEntity(world, playerEntity, h, k, l);
                fireballEntity.explosionPower = 5;
                Vec3d vec3d = new Vec3d(h, k, l);
                fireballEntity.updatePosition(posiX + vec3d.x * 4.0D, posiY+1, fireballEntity.getZ() + vec3d.z * 4.0D);
                world.spawnEntity(fireballEntity);


            }
        }
    }


}
