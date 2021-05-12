package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.Devilrycraft;
import net.minecraft.util.math.BlockPos;

import static malekire.devilrycraft.objects.blockentities.sealhelpers.SealUtilities.*;

public class SealItemTransferer extends AbstractSealHelper {
    public SealItemTransferer() {
        super(ItemTransferSealID, ItemTransferSealID.sealCombinations);
    }
    @Override
    public void tick() {

    }

    /**
     * finds and attaches the first itemTransferable in the sealCombinations list that has the same sealSignature.
     */
    @Override
    public void oneOffTick() {
        for(BlockPos pos : sealPositions)
        {
            AbstractSealHelper sealHelper = getSealHelperFromPos(world, pos);
            if(SealUtilities.sealSignaturesMatch(sealHelper.crystalCombination, this.crystalCombination)) {
                Devilrycraft.LOGGER.debug("found and attacheed SealItemTransferable to another SealItemTransferable");
                this.setDestSealHelper(sealHelper);
                break;
            }
        }

    }



    @Override
    public AbstractSealHelper getNewInstance(BlockPos pos) {
        sealPositions.add(pos);
        return getNewInstance();
    }

    @Override
    public AbstractSealHelper getNewInstance() {
        return new SealItemTransferer();
    }
}
