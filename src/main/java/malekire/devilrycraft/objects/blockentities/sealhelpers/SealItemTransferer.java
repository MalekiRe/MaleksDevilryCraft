package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.Devilrycraft;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.Level;

import static malekire.devilrycraft.objects.blockentities.sealhelpers.SealUtilities.*;

public class SealItemTransferer extends AbstractSealHelper {
    public SealItemTransferer() {
        super(ItemTransferSealID, ItemTransferSealID.sealCombinations);
        this.isMateable = true;
    }
    @Override
    public void tick() {
        Devilrycraft.LOGGER.log(Level.INFO, this);
        Devilrycraft.LOGGER.log(Level.INFO, this.mate);
        if(this.mate != null) {
            Devilrycraft.LOGGER.log(Level.INFO, this.mate.mate);
        }
        Devilrycraft.LOGGER.log(Level.INFO, "NOTHING");
    }

    /**
     * finds and attaches the first itemTransferable in the sealCombinations list that has the same sealSignature.
     */
    @Override
    public void oneOffTick() {


    }



    @Override
    public AbstractSealHelper getNewInstance() {
        return new SealItemTransferer();
    }
}
