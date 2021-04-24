package malekire.devilrycraft.blockentities;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.inventory.BasicInfuserInventory;
import malekire.devilrycraft.magic.Vis;
import malekire.devilrycraft.magic.VisType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class BasicInfuserBlockEntity extends BlockEntity implements Tickable, Vis, NamedScreenHandlerFactory, BasicInfuserInventory {
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
        return null;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }
}
