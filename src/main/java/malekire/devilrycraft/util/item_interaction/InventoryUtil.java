package malekire.devilrycraft.util.item_interaction;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class InventoryUtil {
    public static boolean tryInsert(Inventory sender, Inventory receiver, int index, int amount) {
        ItemStack attemptedImport;
        if(sender.getStack(index).getCount() < amount) {
             attemptedImport = new ItemStack(sender.getStack(index).getItem(), sender.getStack(index).getCount());
        } else {
            attemptedImport = new ItemStack(sender.getStack(index).getItem(), amount);
        }
        int indexOfReceiver = getItemOfSameType(receiver, attemptedImport, false);
        if(indexOfReceiver == -1) {
            //The receving inventory did not have an itemstack of the same item type.
            indexOfReceiver = getEmptySlot(receiver);
            if(indexOfReceiver == -1) {
                //The reciving inventory didn't have an empty slot, aka was full.
                return false;
            }
        }
        //Checks if the amount of items in attempted import would overfill the amount of items already in the reciever.
        if(receiver.getStack(indexOfReceiver).getCount() + attemptedImport.getCount() > 64) {
            //Set attempted import to an item stack with the amount of items being exactly what will fill up reciver to 64.
            attemptedImport = new ItemStack(attemptedImport.getItem(), receiver.getStack(indexOfReceiver).getCount()-64);
        }
        //Sets the items and amounts for both inventories.
        receiver.setStack(indexOfReceiver, attemptedImport);
        sender.setStack(index, new ItemStack(attemptedImport.getItem(), sender.getStack(index).getCount() - attemptedImport.getCount()));
        if(sender.getStack(index).getCount() == 0) {
            sender.setStack(index, ItemStack.EMPTY);
        }
        return true;
    }
    public static int getItemOfSameType(Inventory inventory, ItemStack itemStack, boolean ignoreDamage) {
        if(ignoreDamage) {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.getStack(i).isItemEqualIgnoreDamage(itemStack)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.getStack(i).isItemEqual(itemStack)) {
                    return i;
                }
            }
        }
        return -1;
    }
    public static boolean containsItemOfSameType(Inventory inventory, ItemStack itemStack, boolean ignoreDamage) {
        if(ignoreDamage) {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.getStack(i).isItemEqualIgnoreDamage(itemStack)) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.getStack(i).isItemEqual(itemStack)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean hasEmptySlot(Inventory inventory) {
        for(int i = 0; i < inventory.size(); i++)  {
            if(inventory.getStack(i).isEmpty())
                return true;
        }
        return false;
    }
    public static int getEmptySlot(Inventory inventory) {
        for(int i = 0; i < inventory.size(); i++)  {
            if(inventory.getStack(i).isEmpty())
                return i;
        }
        return -1;
    }
}
