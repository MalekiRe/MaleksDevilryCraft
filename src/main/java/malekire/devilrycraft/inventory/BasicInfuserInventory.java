package malekire.devilrycraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface BasicInfuserInventory extends Inventory {

    /**
     * Retrieves the item list of this inventory.
     * Must return the same instance every time it's called.
     */
    DefaultedList<ItemStack> getItems();


    default double getVis() {
        return 0;
    }
    default double getTaint() {
        return 0;
    }
    default void removeVis(double amount) {

    }
    default void removeTaint(double amount) {

    }
    /**
     * Creates an inventory from the item list.
     */
    static BasicInfuserInventory of(DefaultedList<ItemStack> items) {
        return () -> items;
    }

    /**
     * Creates a new inventory with the specified size.
     */
    static BasicInfuserInventory ofSize(int size) {
        return of(DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

    /**
     * Returns the inventory size.
     */
    @Override
    default int size() {
        return getItems().size();
    }

    /**
     * Checks if the inventory is empty.
     * @return true if this inventory has only empty stacks, false otherwise.
     */
    @Override
    default boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the item in the slot.
     */
    @Override
    default ItemStack getStack(int slot) {
        return getItems().get(slot);
    }

    /**
     * Removes items from an inventory slot.
     * @param slot  The slot to remove from.
     * @param count How many items to remove. If there are less items in the slot than what are requested,
     *              takes all items in that slot.
     */
    @Override
    default ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        /*
        if (!result.isEmpty()) {
            markDirty();
        }*/
        markDirty();
        return result;
    }

    /**
     * Removes all items from an inventory slot.
     * @param slot The slot to remove from.
     */
    @Override
    default ItemStack removeStack(int slot) {
        ItemStack stack = Inventories.removeStack(getItems(), slot);
        markDirty();
        return stack;
    }

    /**
     * Replaces the current stack in an inventory slot with the provided stack.
     * @param slot  The inventory slot of which to replace the itemstack.
     * @param stack The replacing itemstack. If the stack is too big for
     *              this inventory ({@link Inventory#getMaxCountPerStack()}),
     *              it gets resized to this inventory's maximum amount.
     */
    @Override
    default void setStack(int slot, ItemStack stack) {

        getItems().set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();


    }

    /**
     * Clears the inventory.
     */
    @Override
    default void clear() {

        getItems().clear();
        markDirty();
    }

    /**
     * Marks the state as dirty.
     * Must be called after changes in the inventory, so that the game can properly save
     * the inventory contents and notify neighboring blocks of inventory changes.
     */
    @Override
    default void markDirty() {

    }


    /**
     * @return true if the player can use the inventory, false otherwise.
     */
    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}