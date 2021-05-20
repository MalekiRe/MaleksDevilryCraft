package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.items.BaseAbstractSealItem;
import malekire.devilrycraft.objects.items.PortableHole;
import malekire.devilrycraft.objects.items.SealWrangler;
import malekire.devilrycraft.objects.items.WandOfEquivalentExchange;
import malekire.devilrycraft.objects.items.armoritems.DevilryCustomArmorStrider;
import malekire.devilrycraft.util.CrystalType;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

public class DevilryItems {
    public static final FabricItemSettings DevilryDefaultItemSetting = new FabricItemSettings().group(DevilryItemGroups.itemGroups.get("general"));

    public static ArrayList<ItemRegistryHelper> items = new ArrayList<>();
    //public static final BlockItem VIS_CRYSTAL_BLOCK_ITEM = new BlockItem(VIS_CRYSTAL_BLOCK, DevilryDefaultItemSetting);
//    public static final ArmorMaterial DevilryArmor = new DevilryCustomArmorStrider(); how long has this unused bit of code been here? - gamma/null

    public static final Item PORTABLE_HOLE = new PortableHole(DevilryDefaultItemSetting);

    public static final Item VIS_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item TAINTED_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item EARTH_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item FIRE_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item WATER_CRYSTAL = new Item(DevilryDefaultItemSetting);
    public static final Item AIR_CRYSTAL = new Item(DevilryDefaultItemSetting);

    public static final Item NECRONOMICON = new Item(DevilryDefaultItemSetting);
    public static final Item VIS_FLASK = new Item(DevilryDefaultItemSetting);

    public static final Item AIR_CHALK = new BaseAbstractSealItem(DevilryDefaultItemSetting, CrystalType.AIR_TYPE);
    public static final Item WATER_CHALK = new BaseAbstractSealItem(DevilryDefaultItemSetting, CrystalType.WATER_TYPE);
    public static final Item EARTH_CHALK = new BaseAbstractSealItem(DevilryDefaultItemSetting, CrystalType.EARTH_TYPE);
    public static final Item FIRE_CHALK = new BaseAbstractSealItem(DevilryDefaultItemSetting, CrystalType.FIRE_TYPE);
    public static final Item VIS_CHALK = new BaseAbstractSealItem(DevilryDefaultItemSetting, CrystalType.VIS_TYPE);
    public static final Item TAINTED_CHALK = new BaseAbstractSealItem(DevilryDefaultItemSetting, CrystalType.TAINT_TYPE);

    public static final Item WAND_OF_EQUIVALENT_EXCHANGE = new WandOfEquivalentExchange(DevilryDefaultItemSetting);

    public static final Item SEAL_WRANGLER = new SealWrangler(DevilryDefaultItemSetting);

    static {
        //add(VIS_CRYSTAL_BLOCK_ITEM, "vis_crystal_block");

        add(PORTABLE_HOLE, "portable_hole");
        add(VIS_CRYSTAL, "crystals/vis_crystal");
        add(TAINTED_CRYSTAL, "crystals/tainted_crystal");
        add(EARTH_CRYSTAL, "crystals/earth_crystal");
        add(FIRE_CRYSTAL, "crystals/fire_crystal");
        add(WATER_CRYSTAL, "crystals/water_crystal");
        add(AIR_CRYSTAL, "crystals/air_crystal");
        add(NECRONOMICON, "necronomicon");
        add(VIS_FLASK, "vis_flask");

        add(AIR_CHALK, "chalk/air_chalk");
        add(WATER_CHALK, "chalk/water_chalk");
        add(EARTH_CHALK, "chalk/earth_chalk");
        add(FIRE_CHALK, "chalk/fire_chalk");
        add(VIS_CHALK, "chalk/vis_chalk");
        add(TAINTED_CHALK, "chalk/tainted_chalk");

        add(WAND_OF_EQUIVALENT_EXCHANGE, "wand_of_equivalent_exchange");

        add(SEAL_WRANGLER, "seal_wrangler");


    }
    public static void add(Item item2, String name) {
        items.add(new ItemRegistryHelper(item2, new Identifier(Devilrycraft.MOD_ID, name)));
    }
    public static void registerItems() {
        for(ItemRegistryHelper item : DevilryItems.items)
            Registry.register(Registry.ITEM, item.identifier, item.item);
    }


}
class ItemRegistryHelper {
    public final Item item;
    public final Identifier identifier;
    public ItemRegistryHelper(Item item, Identifier identifier) {
        this.item = item;
        this.identifier = identifier;
    }

}
