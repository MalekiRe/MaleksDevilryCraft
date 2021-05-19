package malekire.devilrycraft.objects.blockentities.seals;

import malekire.devilrycraft.util.CrystalType;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;
import static malekire.devilrycraft.util.CrystalType.*;
import static malekire.devilrycraft.util.DevilryProperties.*;

public class SealUtilities {
    public static HashMap<String, AbstractSeal> sealCombinations = new HashMap<>();

    public static SealIdentifer FireSealID = new SealIdentifer(MOD_ID, "attack_mobs_with_fire", FIRE_TYPE, FIRE_TYPE, FIRE_TYPE, FIRE_TYPE);
    public static SealIdentifer PortalSealID = new SealIdentifer(MOD_ID, "portal_seal", VIS_TYPE, AIR_TYPE);
    public static SealIdentifer SuctionSealID = new SealIdentifer(MOD_ID, "suction_seal", AIR_TYPE, AIR_TYPE, AIR_TYPE, AIR_TYPE);
    public static SealIdentifer ItemTransferSealID = new SealIdentifer(MOD_ID, "item_transfer_seal", WATER_TYPE);
    public static SealIdentifer DestroySealID = new SealIdentifer(MOD_ID, "destroy_seal", EARTH_TYPE, FIRE_TYPE, EARTH_TYPE, FIRE_TYPE);
    public static SealIdentifer PlaceSealID = new SealIdentifer(MOD_ID, "place_seal", EARTH_TYPE, AIR_TYPE, EARTH_TYPE, EARTH_TYPE);


    static {
        add(new SealPortal());
        add(new SuctionSeal());
        add(new SealFire());
        add(new SealItemTransferer());
        add(new DestroySeal());
        add(new PlaceSeal());
    }
    public static void add(AbstractSeal seal) {
        sealCombinations.put(seal.id.toString(), seal);
    }
    public static AbstractSeal getSealFromWorldAndPos(World world, BlockPos pos) {
        return ((SealBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).getSeal();
    }
    public static boolean crystalBlockStatesMatch(BlockState state1, BlockState state2) {
        if(!doLayersMatch(state1, state2, FIRST_LAYER))
            return false;
        if(!doLayersMatch(state1, state2, SECOND_LAYER))
            return false;
        if(!doLayersMatch(state1, state2, THIRD_LAYER))
            return false;
        if(!doLayersMatch(state1, state2, FOURTH_LAYER))
            return false;
        return true;
    }
    public static boolean doLayersMatch(BlockState state1, BlockState state2, Property<CrystalType> property) {
        return state1.get(property) == state2.get(property);
    }
    /**
     * checks if the two sealCombinations are the same, if one is smaller than the other, just checks up to the smallest that they are identical.
     * @param sealCombo1
     * @param sealCombo2
     * @return if the two sealCombinations are the same, if one is smaller than the other, just checks up to the smallest that they are identical.
     */
    public static boolean sealSignaturesMatch(ArrayList<CrystalType> sealCombo1, ArrayList<CrystalType> sealCombo2) {
        if(sealCombo1.size() == sealCombo2.size()) {
            for (int i = 0; i < sealCombo1.size(); i++)
                if (sealCombo1.get(i) != sealCombo2.get(i))
                    return false;
        }
        else if(sealCombo1.size() < sealCombo2.size()) {
            for (int i = 0; i < sealCombo1.size(); i++)
                if (sealCombo1.get(i) != sealCombo2.get(i))
                    return false;
        }
        else {
            for (int i = 0; i < sealCombo2.size(); i++)
                if (sealCombo1.get(i) != sealCombo2.get(i))
                    return false;
        }
        return true;
    }
}
