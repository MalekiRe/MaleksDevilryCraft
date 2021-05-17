package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.inventory.TransferSealInventory;
import malekire.devilrycraft.util.item_interaction.InventoryUtil;
import malekire.devilrycraft.util.render.DRenderUtil;
import malekire.devilrycraft.util.world.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Optional;

import static com.qouteall.immersive_portals.render.context_management.RenderStates.tickDelta;
import static malekire.devilrycraft.objects.blockentities.sealhelpers.SealUtilities.*;

public class SealItemTransferer extends AbstractSeal implements TransferSealInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    boolean isDirty = false;
    Box box;
    public boolean isReceiver = false;
    public static final int RANGE = 4;
    public SealItemTransferer() {
        super(ItemTransferSealID, ItemTransferSealID.sealCombinations);
        this.isMateable = true;
    }

    public boolean isOriginMate() {
        return matePos != null;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, this.inventory);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, this.inventory);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.fromTag(getWorld().getBlockState(getPos()), tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        this.toTag(tag);
        return tag;
    }
    ItemStack renderStack = new ItemStack(Items.LAPIS_BLOCK, 1);
    BlockPos offsetPos = new BlockPos(3, 3, 3);
    Vec3d origin = new Vec3d(0, 0, 0);
    public float getTime() {
        float time = (this.blockEntity.getWorld().getTime() + tickDelta);
        time = (time % 100) / 100;
        return time;
    }
    @Override
    public void render(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, int light, int overlay) {
        if (!isOriginMate()) {
            return;
        }
        float time = getTime();
        //System.out.println((time%100)/100);
        /*
        if(this.getMate() != null) {
            Devilrycraft.LOGGER.log(Level.INFO, "rendering seal itemstack");
            Vec3d myPos = DRenderUtil.interpolatePositionsThroughTime(Vec3d.of(this.getPos()), Vec3d.of(getMate().getPos()), time);
            matrixStack.push();
            matrixStack.translate(myPos.x, myPos.y, myPos.z);
            MinecraftClient.getInstance().getItemRenderer().renderItem(renderStack, ModelTransformation.Mode.GROUND, light, 1, matrixStack, vertexConsumerProvider);
            matrixStack.pop();
        }
         */
        //Devilrycraft.LOGGER.log(Level.INFO, "rendering seal itemstack");
        //System.out.println(getPos());
        //System.out.println(matePos);
        SealBlockEntity clientBlockEntity = (SealBlockEntity)MinecraftClient.getInstance().world.getBlockEntity(getPos());
        offsetPos = (matePos.subtract(getPos()));
        Vec3d myPos = DRenderUtil.interpolatePositionsThroughTime(origin, Vec3d.of((offsetPos)), time);
        System.out.println(((SealItemTransferer)clientBlockEntity.getSeal()).getStack(0));
        //System.out.println(myPos);
        matrixStack.push();
        matrixStack.translate(myPos.x, myPos.y, myPos.z);
        MinecraftClient.getInstance().getItemRenderer().renderItem(((SealItemTransferer)clientBlockEntity.getSeal()).getStack(0), ModelTransformation.Mode.GROUND, light, overlay, matrixStack, vertexConsumerProvider);
        matrixStack.pop();
    }
    List<ItemEntity> itemEntities;
    @Override
    public boolean getIsReceiver() {
        return this.isReceiver;
    }
    BlockPos externalInventoryPos;
    boolean hasFoundExternalInventory = false;

    public boolean isBlockWeWant(World world, BlockPos pos) {
        if(world.getBlockState(pos).getBlock().equals(Blocks.CHEST)) {
            return true;
        }
        return false;
    }
    public Inventory getBlockInventory(World world, BlockPos pos) {
        return (Inventory)world.getBlockEntity(pos);
    }
    Vec3d range = new Vec3d(2, 2, 2);
    @Override
    public void tick() {
        if(!getWorld().isClient) {
            if(!hasFoundExternalInventory) {
                Optional<BlockPos> posOptional = WorldUtil.findFirstBlockInRange(getWorld(), getPos(), range, (world, pos) -> ((World)world).getBlockState((BlockPos)pos).getBlock().equals(Blocks.CHEST));
                if(posOptional.isPresent()) {
                    hasFoundExternalInventory = true;
                    externalInventoryPos = posOptional.get();
                    Devilrycraft.LOGGER.log(Level.INFO, "Transfer Seal Found Nearby Chest");
                }
            }
            if(hasFoundExternalInventory && getTime() < 0.1 && getItems().get(0).isEmpty() && hasMate && !isReceiver) {
                if(InventoryUtil.tryInsert(getBlockInventory(getWorld(), externalInventoryPos), this, 0, 1)) {
                    markDirty();
                }
            }
            if(!getItems().get(0).isEmpty() && getTime() > 0.9 && hasMate && !isReceiver && ((SealItemTransferer)getMate()).hasFoundExternalInventory) {
                if(InventoryUtil.tryInsert(this, (Inventory)getMate(), 0, 1)) {
                    markDirty();
                    ((Inventory) getMate()).markDirty();
                }
            }
            if(!getItems().get(0).isEmpty() && isReceiver && hasFoundExternalInventory) {
                if(InventoryUtil.tryInsert(this, getBlockInventory(getWorld(), externalInventoryPos), 0, 1)) {
                    markDirty();
                }
            }
            if(!this.isReceiver) {
                if(this.hasMate) {
                    if(!this.getMate().getIsReceiver()) {
                        this.isReceiver = true;
                    }
                }
            }
        }

    }

    /**
     * finds and attaches the first itemTransferable in the sealCombinations list that has the same sealSignature.
     */
    @Override
    public void oneOffTick() {

        box = new Box(-RANGE, -RANGE, -RANGE, RANGE, RANGE, RANGE);

    }
    @Override
    public void markDirty() {
        this.blockEntity.markDirty();
    }

    @Override
    public AbstractSeal getNewInstance() {
        return new SealItemTransferer();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
}
