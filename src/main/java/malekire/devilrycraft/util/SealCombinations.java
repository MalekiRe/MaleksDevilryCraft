package malekire.devilrycraft.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static malekire.devilrycraft.util.CrystalType.*;

public class SealCombinations {
    public static HashMap<String, ArrayList<CrystalType>> sealCombinations = new HashMap<>();

    static {
       add("attack_all_mob", FIRE_TYPE, FIRE_TYPE, FIRE_TYPE, FIRE_TYPE);
        //THIS WILL BE IMPLMENETED BY PLACEING A ITEM FRAME
        // NEXT TO THE BLOCK, AND PUTTING THE MOB DROP OF THAT MOB INTO THAT ITEM FRAME.
       add("attack_specific_mob", FIRE_TYPE, WATER_TYPE, FIRE_TYPE, FIRE_TYPE);

        //PICKUPS ITEMS AND PUTS IT IN THE NEARBY INVENTORY, itemframe on chest filters so it only picksup those items to those chests.
        add("pickup_items", AIR_TYPE, AIR_TYPE, AIR_TYPE, AIR_TYPE);

        //Creates a portal to another location, using the remaning two types as idenftifers for the portal.
        add("portal", VIS_TYPE, AIR_TYPE);
    }


    public static void add(String name, CrystalType... crystalTypes) {
        ArrayList<CrystalType> crystalTypes1 = new ArrayList<>();
        for(CrystalType crystal : crystalTypes)
            crystalTypes1.add(crystal);
        sealCombinations.put(name, crystalTypes1);
    }
}
