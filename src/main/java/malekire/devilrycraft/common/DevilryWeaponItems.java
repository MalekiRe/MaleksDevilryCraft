package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.items.toolItems.*;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class DevilryWeaponItems {

    public static ArrayList<WeaponItemsRegistryHelper> weaponItems = new ArrayList<>();
    public static final ToolMaterial crystalSpear = new DevilryCrystalSpearGroup();
    public static final SwordItem FIRE_CRYSTAL_SPEAR = new FireCrystalSpear(DevilryCrystalSpearGroup.INSTANCE, 8, -1.4F, new Item.Settings().group(DevilryItemGroups.itemGroups.get("general")));
    public static final SwordItem WATER_CRYSTAL_SPEAR = new WaterCrystalSpear(DevilryCrystalSpearGroup.INSTANCE, 8, -1.4F, new Item.Settings().group(DevilryItemGroups.itemGroups.get("general")));
    public static final SwordItem TAINT_CRYSTAL_SPEAR = new TaintCrystalSpear(DevilryCrystalSpearGroup.INSTANCE, 8, -1.4F, new Item.Settings().group(DevilryItemGroups.itemGroups.get("general")));
    public static final SwordItem AIR_CRYSTAL_SPEAR = new AirCrystalSpear(DevilryCrystalSpearGroup.INSTANCE, 8, -1.4F, new Item.Settings().group(DevilryItemGroups.itemGroups.get("general")));
    public static final SwordItem VIS_CRYSTAL_SPEAR = new VisCrystalSpear(DevilryCrystalSpearGroup.INSTANCE, 8, -1.4F, new Item.Settings().group(DevilryItemGroups.itemGroups.get("general")));
    public static final SwordItem EARTH_CRYSTAL_SPEAR = new EarthCrystalSpear(DevilryCrystalSpearGroup.INSTANCE, 8, -1.4F, new Item.Settings().group(DevilryItemGroups.itemGroups.get("general")));
    public static final SwordItem ZEPHYR_SWORD = new ZephyrSword(DevilryWindSwordGroup.INSTANCE, 10, -1F, new Item.Settings().group(DevilryItemGroups.itemGroups.get("general")));




    static {
        add(FIRE_CRYSTAL_SPEAR, "fire_crystal_spear");
        add(WATER_CRYSTAL_SPEAR, "water_crystal_spear");
        add(TAINT_CRYSTAL_SPEAR, "taint_crystal_spear");
        add(AIR_CRYSTAL_SPEAR, "air_crystal_spear");
        add(VIS_CRYSTAL_SPEAR, "vis_crystal_spear");
        add(EARTH_CRYSTAL_SPEAR, "earth_crystal_spear");
        add(ZEPHYR_SWORD, "sword_of_zephyr");

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

