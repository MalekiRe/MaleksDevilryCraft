package malekire.devilrycraft.util;

import malekire.devilrycraft.objects.blockentities.sealhelpers.SealHelperAbstractClass;
import malekire.devilrycraft.objects.blockentities.sealhelpers.SealPortalHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static malekire.devilrycraft.util.CrystalType.*;

public class SealCombinations {
    public static HashMap<String, SealHelperAbstractClass> sealCombinations = new HashMap<>();

    static {
       //add("attack_all_mob", FIRE_TYPE, FIRE_TYPE, FIRE_TYPE, FIRE_TYPE);
        //THIS WILL BE IMPLMENETED BY PLACEING A ITEM FRAME
        // NEXT TO THE BLOCK, AND PUTTING THE MOB DROP OF THAT MOB INTO THAT ITEM FRAME.
       //add("attack_specific_mob", FIRE_TYPE, WATER_TYPE, FIRE_TYPE, FIRE_TYPE);

        //PICKUPS ITEMS AND PUTS IT IN THE NEARBY INVENTORY, itemframe on chest filters so it only picksup those items to those chests.
        //add("pickup_items", AIR_TYPE, AIR_TYPE, AIR_TYPE, AIR_TYPE);

        //Creates a portal to another location, using the remaning two types as idenftifers for the portal.
        add(new SealPortalHelper("portal"));
    }


    public static void add(SealHelperAbstractClass seal) {
        sealCombinations.put(seal.id, seal);
    }
}
