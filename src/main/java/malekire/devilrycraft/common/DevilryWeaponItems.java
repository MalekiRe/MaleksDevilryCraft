package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.items.toolItems.DevilryCrystalSpearGroup;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class DevilryWeaponItems {

    public static ArrayList<WeaponItemsRegistryHelper> weaponItems = new ArrayList<>();
    public static final ToolMaterial crystalSpear = new DevilryCrystalSpearGroup();
    public static final Item FIRE_CRYSTAL_SPEAR = new SwordItem(DevilryCrystalSpearGroup.INSTANCE, 8, -1.4F, new Item.Settings().group(DevilryItemGroups.itemGroups.get("general")));

    static {
        add(FIRE_CRYSTAL_SPEAR, "fire_crystal_spear");
    }
    public static void add(Item item2, String name) {
        weaponItems.add(new WeaponItemsRegistryHelper(item2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
    public static void registerWeaponItems() {
        for(WeaponItemsRegistryHelper weaponItem : DevilryWeaponItems.weaponItems)
            Registry.register(Registry.ITEM, weaponItem.identifier, weaponItem.item);
    }
}
class WeaponItemsRegistryHelper {
    public final Item item;
    public final Identifier identifier;
    public WeaponItemsRegistryHelper(Item item, Identifier identifier) {
        this.item = item;
        this.identifier = identifier;
    }

}

