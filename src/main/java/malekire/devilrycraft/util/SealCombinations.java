package malekire.devilrycraft.util;

import malekire.devilrycraft.objects.blockentities.sealhelpers.AbstractSealHelperClass;
import malekire.devilrycraft.objects.blockentities.sealhelpers.SealPortalHelper;
import malekire.devilrycraft.objects.blockentities.sealhelpers.SuctionSealHelper;

import java.util.HashMap;

public class SealCombinations {
    public static HashMap<String, AbstractSealHelperClass> sealCombinations = new HashMap<>();

    static {
       //add("attack_all_mob", FIRE_TYPE, FIRE_TYPE, FIRE_TYPE, FIRE_TYPE);
        //THIS WILL BE IMPLMENETED BY PLACEING A ITEM FRAME
        // NEXT TO THE BLOCK, AND PUTTING THE MOB DROP OF THAT MOB INTO THAT ITEM FRAME.
       //add("attack_specific_mob", FIRE_TYPE, WATER_TYPE, FIRE_TYPE, FIRE_TYPE);

        //PICKUPS ITEMS AND PUTS IT IN THE NEARBY INVENTORY, itemframe on chest filters so it only picksup those items to those chests.
        //add("pickup_items", AIR_TYPE, AIR_TYPE, AIR_TYPE, AIR_TYPE);

        //Creates a portal to another location, using the remaning two types as idenftifers for the portal.
        add(new SealPortalHelper("portal"));
        add(new SuctionSealHelper("suction"));
    }


    public static void add(AbstractSealHelperClass seal) {
        sealCombinations.put(seal.id, seal);
        System.out.println("ADDING SEAL");
    }
}
