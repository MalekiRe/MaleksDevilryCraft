package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.util.CrystalType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public abstract class AbstractPositionedSealHelper extends AbstractSealHelper{
    public AbstractPositionedSealHelper(Identifier id, ArrayList<CrystalType> crystalCombination) {
        super(id, crystalCombination);
    }

    public AbstractPositionedSealHelper(Identifier id, CrystalType... crystalTypes) {
        super(id, crystalTypes);
    }

    @Override
    public AbstractSealHelper getNewInstance(BlockPos pos) {
        sealPositions.add(pos);
        return getNewInstance();
    }
}
