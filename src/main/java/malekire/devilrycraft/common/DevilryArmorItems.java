package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.items.armoritems.DevilryCustomArmorStrider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class DevilryArmorItems {
    public static ArrayList<ArmorItemsRegistryHelper> armorItems = new ArrayList<>();
    public static final ArmorMaterial striderCustomArmor = new DevilryCustomArmorStrider();
    public static final Item BOOTS_OF_STRIDING = new ArmorItem(striderCustomArmor, EquipmentSlot.FEET, new Item.Settings().group(DevilryItemGroups.itemGroups.get(0)));

    static {
        add(BOOTS_OF_STRIDING, "armor_boots_of_striding");
    }
    public static void add(Item item2, String name) {
        armorItems.add(new ArmorItemsRegistryHelper(item2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
    public static void registerArmorItems() {
        for(ArmorItemsRegistryHelper armorItem : DevilryArmorItems.armorItems)
            Registry.register(Registry.ITEM, armorItem.identifier, armorItem.item);
    }
}
class ArmorItemsRegistryHelper {
    public final Item item;
    public final Identifier identifier;
    public ArmorItemsRegistryHelper(Item item, Identifier identifier) {
        this.item = item;
        this.identifier = identifier;
    }

}
