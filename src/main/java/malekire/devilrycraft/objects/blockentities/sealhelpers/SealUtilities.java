package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.util.CrystalType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;
import static malekire.devilrycraft.common.DevilryBlocks.SEAL_BLOCK;
import static malekire.devilrycraft.util.CrystalType.*;

public class SealUtilities {
    public static SealIdentifer FireSealID = new SealIdentifer(MOD_ID, "attack_mobs_with_fire", FIRE_TYPE, FIRE_TYPE, FIRE_TYPE, FIRE_TYPE);
    public static SealIdentifer PortalSealID = new SealIdentifer(MOD_ID, "portal_seal", VIS_TYPE, AIR_TYPE);
    public static SealIdentifer SuctionSealID = new SealIdentifer(MOD_ID, "suction_seal", AIR_TYPE, AIR_TYPE, AIR_TYPE, AIR_TYPE);
    public static SealIdentifer ItemTransferSealID = new SealIdentifer(MOD_ID, "item_transfer_seal", WATER_TYPE);

    /**
     *
     * @param world
     * @param pos
     * @return SealHelper at blockpos, logs errors and returns null if there is no sealHelper, or no SealBlock at the position.
     */
    public static AbstractSealHelper getSealHelperFromPos(World world, BlockPos pos) {
        if(world.getBlockState(pos).getBlock() != SEAL_BLOCK) {
            Devilrycraft.LOGGER.debug("Warning! Expected Seal Block at " + pos.toString(), ", instead recived block " + world.getBlockState(pos).getBlock());
            return null;
        }
        SealBlockEntity sealBlockEntity = (SealBlockEntity) world.getBlockEntity(pos);
        if(sealBlockEntity.sealHelper == null) {
            Devilrycraft.LOGGER.debug("Warning! Expected SealHelper at " + pos + "to have sealHelper, no seal helper found!");
            return null;
        }
        return sealBlockEntity.sealHelper;
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
