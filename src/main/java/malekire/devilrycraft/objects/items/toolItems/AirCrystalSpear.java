package malekire.devilrycraft.objects.items.toolItems;

import net.minecraft.item.SwordItem;
import net.minecraft.item.Vanishable;

import javax.swing.*;

public class AirCrystalSpear extends SwordItem implements Vanishable {
    public AirCrystalSpear(DevilryCrystalSpearGroup instance, int i, float v, Settings settings){
        super(new DevilryCrystalSpearGroup(), 8, -1.4F, settings);
    }
}