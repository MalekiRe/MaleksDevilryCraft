package malekire.devilrycraft.blockentities;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.inventory.BasicInfuserInventory;
import malekire.devilrycraft.magic.Vis;
import malekire.devilrycraft.magic.VisType;
import malekire.devilrycraft.screenhandlers.BasicInfuserScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class BasicInfuserBlockEntity extends BlockEntity implements Tickable, Vis, NamedScreenHandlerFactory, BasicInfuserInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(13, ItemStack.EMPTY);
    public BasicInfuserBlockEntity() {
        super(Devilrycraft.BASIC_INFUSER_BLOCK_ENTITY);
    }

    @Override
    public double GetLevel(VisType type) {
        return 0;
    }

    @Override
    public boolean Insert(VisType type, double amount) {
        return false;
    }

    @Override
    public boolean Extract(VisType type, double amount) {
        return false;
    }

    @Override
    public boolean IsEmpty(VisType type) {
        return false;
    }

    @Override
    public boolean IsFull(VisType type) {
        return false;
    }

    @Override
    public boolean CanContainVisType(VisType type) {
        return false;
    }

    @Override
    public double ExtractionRate(VisType type) {
        return 0;
    }

    @Override
    public double InsertionRate(VisType type) {
        return 0;
    }

    @Override
    public void tick() {

    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("Infuser");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BasicInfuserScreenHandler(syncId, inv, this);
    }
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, this.inventory);
        return tag;
    }
}
