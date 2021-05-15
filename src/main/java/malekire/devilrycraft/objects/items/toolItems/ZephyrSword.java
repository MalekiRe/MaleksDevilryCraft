package malekire.devilrycraft.objects.items.toolItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ZephyrSword extends SwordItem implements Vanishable {
    public ZephyrSword(DevilryWindSwordGroup instance, int i, float v, Settings settings) {
        super(new DevilryWindSwordGroup(), 8, -1.4F, settings);
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
        return UseAction.BLOCK;
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
            if (i >= 5 ) {
                double posX = playerEntity.getX();
                double posY = playerEntity.getY();
                double posZ = playerEntity.getZ();
                Box airBox = new Box(posX-5, posY-5, posZ-5, posX+5, posY+5, posZ+5);


                List<Entity> entitiesToFlame = world.getOtherEntities(null, airBox, (entity) -> !(entity instanceof PlayerEntity) && entity.isPushable() && !(entity instanceof ItemEntity));
                for (Entity entity : entitiesToFlame) {
                    entity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, playerEntity.getPos());

                    float f = entity.yaw;
                    float g = entity.pitch;
                    float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                    float k = -MathHelper.sin(g * 0.017453292F);
                    float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                    float m = MathHelper.sqrt(h * h + k * k + l * l);
                    float n = 3.0F * ((1.0F + (float) ((float)i*0.1) / 4.0F));
                    h *= n / m;
                    k *= n / m;
                    l *= n / m;
                    entity.addVelocity(-((double)h), -((double)k), -((double)l));


                }
            }
        }
    }

}
