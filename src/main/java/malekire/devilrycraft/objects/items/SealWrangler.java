package malekire.devilrycraft.objects.items;

import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.objects.blockentities.seals.AbstractSeal;
import malekire.devilrycraft.objects.blockentities.seals.SealBlockEntity;
import malekire.devilrycraft.util.world.WorldUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class SealWrangler extends Item {
    public static final String CHEST_MODE = "chest_mode";
    public static final String ACTIVE = "active";


    public SealWrangler(Settings settings) {
        super(settings);
    }

    public static Optional<BlockPos> tryGetValidBlock(World world, BlockPos pos) {
        return WorldUtil.findFirstBlockInRange(world, pos, new Vec3d(2, 2, 2), (pos1) -> blockTry(world, (BlockPos) pos1));
    }

    public static boolean blockTry(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().equals(DevilryBlocks.SEAL_BLOCK);
    }

    public static boolean hasSealPos(ItemStack stack) {
        if (stack.getTag() == null)
            return false;
        return stack.getTag().contains("seal_pos");
    }

    public static boolean hasBlockSelected(ItemStack stack) {
        if (stack.getTag() == null)
            return false;
        return stack.getTag().contains("selected_block_pos");
    }

    public static AbstractSeal getSeal(World world, BlockPos pos) {
        return ((SealBlockEntity) world.getBlockEntity(pos)).getSeal();
    }

    public void toggleChestMode(ItemUsageContext context) {
        //Checks if there is a tag that contains chest mode, if there isn't, creates one.
        if (!context.getStack().getOrCreateTag().contains(CHEST_MODE))
            context.getStack().getOrCreateTag().putBoolean(CHEST_MODE, false);
        //Sets the chest mode to the oppisite of whatever it was
        context.getStack().getOrCreateTag().putBoolean(CHEST_MODE, !context.getStack().getOrCreateTag().getBoolean(CHEST_MODE));
    }
    public void callFunctionOnSeal(World world, BlockPos pos, ItemStack itemStack) {
        if(!itemStack.getOrCreateTag().contains(CHEST_MODE) &&
                !itemStack.getOrCreateTag().getBoolean(CHEST_MODE)) {
            getSeal(world, pos).normalModeSealWranglerFunction(itemStack);
            return;
        }
        getSeal(world, pos).chestModeSealWranglerFunction(itemStack);
    }
    public void setActive(ItemStack itemStack, boolean bool) {
        itemStack.getOrCreateTag().putBoolean(ACTIVE, bool);
    }
    public boolean isActive(ItemStack itemStack) {
        if(!itemStack.getOrCreateTag().contains(ACTIVE)) {
            return false;
        }
        return itemStack.getOrCreateTag().getBoolean(ACTIVE);
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) {
            return ActionResult.PASS;
        }
        if (context.getPlayer().isSneaky()) {
            toggleChestMode(context);
        }
        if (isActive(context.getStack())) {
            addPosToItemStack(context.getStack(), context.getBlockPos(), "selected_block_pos");
            callFunctionOnSeal(context.getWorld(), getPosFromItemStack(context.getStack(), "seal_pos"), context.getStack());
            setActive(context.getStack(), false);
            return ActionResult.PASS;
        }
        Optional<BlockPos> possibleSealPos = tryGetValidBlock(context.getWorld(), context.getBlockPos());
        possibleSealPos.ifPresent(blockPos -> {
            addPosToItemStack(context.getStack(), possibleSealPos.get(), "seal_pos");
            setActive(context.getStack(), true);
        });
        return ActionResult.PASS;
    }

    public static void addPosToItemStack(ItemStack itemStack, BlockPos pos, String key) {
        itemStack.getOrCreateTag().put(key, BlockPos.CODEC.encode(pos, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).getOrThrow(false, (string) -> {
        }));
    }

    public static BlockPos getPosFromItemStack(ItemStack itemStack, String key) {
        return BlockPos.CODEC.decode(NbtOps.INSTANCE, itemStack.getTag().get(key)).getOrThrow(false, (string) -> {
        }).getFirst();
    }
}
