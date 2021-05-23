package malekire.devilrycraft.objects.blockentities.seals;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.items.SealWrangler;
import malekire.devilrycraft.util.item_interaction.InventoryUtil;
import malekire.devilrycraft.util.world.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.Optional;

import static malekire.devilrycraft.objects.blockentities.seals.SealUtilities.DestroySealID;
import static malekire.devilrycraft.objects.blockentities.seals.SealUtilities.PlaceSealID;
import static net.minecraft.block.Blocks.AIR;
import static net.minecraft.block.Blocks.CAVE_AIR;

public class PlaceSeal extends AbstractSeal {
    public PlaceSeal() {
        super(PlaceSealID, PlaceSealID.sealCombinations);
    }


    /**
     * Override and implement any custom rendering for the seal.
     *
     * @param vertexConsumerProvider
     * @param matrixStack
     * @param light
     * @param overlay
     */
    @Override
    public void render(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, int light, int overlay) {

    }

    private BlockState placement;
    private BlockPos chestPos;

    public static boolean isBlockChestType(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.CHEST;
    }

    public Optional<BlockPos> getChestPos() {
        if (chestPos == null) {
            Optional<BlockPos> posOptional = WorldUtil.findFirstBlockInRange(getWorld(), getPos(), new Vec3d(2, 2, 2), (pos1) -> isBlockChestType(getWorld(), (BlockPos) pos1));
            posOptional.ifPresent(blockPos -> chestPos = blockPos);
            return posOptional;
        }
        return Optional.of(chestPos);
    }

    public Optional<Inventory> getChestInventory() {
        if (getChestPos().isPresent()) {
            if(getWorld().getBlockState(getChestPos().get()).getBlock() == Blocks.CHEST) {
                return Optional.of((Inventory) getWorld().getBlockEntity(getChestPos().get()));
            }
            chestPos = null;
            placement = null;
        }
        return Optional.empty();
    }

    public Optional<BlockState> getPlacement() {

        if (!getChestInventory().isPresent()) {
            return Optional.empty();
        }
        if(getWorld().getBlockState(getChestPos().get()).getBlock() != Blocks.CHEST) {
            chestPos = null;
            placement = null;
            return Optional.empty();
        }
        int indexOfItem = InventoryUtil.getFirstIndexOfItemMatchingPredicate(getChestInventory().get(), (itemstack) -> ((ItemStack) itemstack).getItem() instanceof BlockItem);
        Devilrycraft.LOGGER.log(Level.INFO, indexOfItem);
        if (indexOfItem == -1)
            return Optional.empty();
        BlockItem blockItem = (BlockItem) getChestInventory().get().getStack(indexOfItem).getItem();
        getChestInventory().get().removeStack(indexOfItem, 1);
        placement = blockItem.getBlock().getDefaultState();

        return Optional.of(placement);

    }

    public static int DESTROY_TICKS = 20;
    public int ticks = 0;
    /**
     * Override and implement any functions you want to run every tick.
     * Is called after oneOffTick()
     */
    public BlockPos targetBlock;

    public void normalModeSealWranglerFunction(ItemStack itemStack) {
        targetBlock = SealWrangler.getPosFromItemStack(itemStack, "selected_block_pos");
    }

    public void PlaceBlock() {
        if (targetBlock == null)
            return;
        if(getWorld().getBlockState(targetBlock).getBlock() != AIR && getWorld().getBlockState(targetBlock).getBlock() != CAVE_AIR)
            return;
        Optional<BlockState> myPlacement = getPlacement();
        if(!myPlacement.isPresent()) {
            return;
        }
        getWorld().setBlockState(targetBlock, myPlacement.get());
    }

    @Override
    public void tick() {
        if (ticks == DESTROY_TICKS) {
            PlaceBlock();
            ticks = 0;
        }
        ticks++;
    }

    /**
     * Override and implement any functions you want to run once, when a seal is finished/created in {@link SealBlockEntity}.
     */
    @Override
    public void oneOffTick() {

    }

    /**
     * The function you override with a new instance of your class.
     *
     * @return A new instance of your class
     */
    @Override
    protected AbstractSeal getNewInstance() {
        return new PlaceSeal();
    }
}
