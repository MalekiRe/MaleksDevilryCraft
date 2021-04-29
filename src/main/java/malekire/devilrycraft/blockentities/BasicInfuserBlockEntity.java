package malekire.devilrycraft.blockentities;

import malekire.devilrycraft.inventory.BasicInfuserInventory;
import malekire.devilrycraft.magic.Vis;
import malekire.devilrycraft.magic.VisType;
import malekire.devilrycraft.recipies.BasicInfuserRecipe;
import malekire.devilrycraft.recipies.Type;
import malekire.devilrycraft.screenhandlers.BasicInfuserScreenHandler;
import malekire.devilrycraft.common.DevilryBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BasicInfuserBlockEntity extends BlockEntity implements Tickable, Vis, NamedScreenHandlerFactory, BasicInfuserInventory, BlockEntityClientSerializable {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(13, ItemStack.EMPTY);
    public BasicInfuserBlockEntity() {
        super(DevilryBlockEntities.BASIC_INFUSER_BLOCK_ENTITY);
    }
    ItemStack clientStack = null;
    ItemStack serverStack = null;
    ItemStack NOTHING_STACK = new ItemStack(Items.AIR, 2);
    public int currentCraftingTicks = 0;
    private boolean isDirty = false;

    @Override
    public double GetLevel(VisType type) {
        return type == VisType.VIS ? getVis() : getTaint();
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
        if(!world.isClient() && isDirty) {
            isDirty = false;
            this.sync();
            
            List<BasicInfuserRecipe> match = world.getRecipeManager()
                    .getAllMatches(Type.INSTANCE, this, world);

            for(int i = 0; i < match.size(); i++)
            {
                if(match.get(i).matches(this, world))
                {
                    currentCraftingTicks++;
                    if(currentCraftingTicks >= match.get(i).TICKS)
                    {
                        currentCraftingTicks = 0;
                        doCraft(match.get(i));
                    }
                }
            }

        }




    }

    public void doCraft(BasicInfuserRecipe recipe) {
        if(this.getItems().get(12).getCount() == 1) {
            for (int i2 = 0; i2 < this.size(); i2++) {
                if (!this.getItems().get(i2).isEmpty()) {
                    this.getItems().get(i2).decrement(1);
                }
            }
            this.getItems().set(12, recipe.craft(this));

        }

    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public int size() {
        return BasicInfuserInventory.super.size();
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

        Inventories.toTag(tag, this.inventory);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        clear();

        Inventories.fromTag(tag, getItems());

    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        super.toTag(tag);
        toTag(tag);
        return tag;
    }
    @Override
    public void markDirty() {
        super.markDirty();
        isDirty = true;
    }




}
