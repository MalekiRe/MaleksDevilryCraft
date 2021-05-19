package malekire.devilrycraft.objects.blockentities.seals;

import malekire.devilrycraft.util.CrystalType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class SealIdentifer extends Identifier {
    ArrayList<CrystalType> sealCombinations;
    public SealIdentifer(String namespace, String path, CrystalType... crystalTypes) {
        super(namespace, path);
        setSealCombinations(crystalTypes);
    }
    public SealIdentifer(String namespace, String path, ArrayList<CrystalType> crystalTypes) {
        super(namespace, path);
        sealCombinations = crystalTypes;
    }
    public SealIdentifer(String id, CrystalType... crystalTypes) {
        super(id);
        setSealCombinations(crystalTypes);
    }
    private void setSealCombinations(CrystalType... crystalTypes) {
        sealCombinations = new ArrayList<>();
        for(CrystalType crystalType : crystalTypes) {
            sealCombinations.add(crystalType);
        }
    }
    public SealIdentifer appendSealType(CrystalType type) {
        ArrayList<CrystalType> tempArrayList = (ArrayList<CrystalType>) sealCombinations.clone();
        tempArrayList.add(type);
        return new SealIdentifer(this.namespace, this.path, tempArrayList);
    }
}
