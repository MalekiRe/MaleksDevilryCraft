package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.SealCombinations;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class AbstractSealHelperClass {
    public final String id;
    public final ArrayList<CrystalType> crystalCombination;
    public World world;
    public BlockPos pos;
    public SealBlockEntity blockEntity;

    public abstract void tick();
    public void tick(SealBlockEntity blockEntity) {
        setFields(blockEntity);
        tick();
    }

    public abstract void oneOffTick();
    public void oneOffTick(SealBlockEntity blockEntity) {
        setFields(blockEntity);
        oneOffTick();
    }

    private void setFields(SealBlockEntity blockEntity)
    {
        world = blockEntity.getWorld();
        pos = blockEntity.getPos();
        this.blockEntity = blockEntity;
    }
    public abstract AbstractSealHelperClass getNewInstance();

    public AbstractSealHelperClass(String id, ArrayList<CrystalType> crystalCombination)
    {
        this.id = id;
        this.crystalCombination = crystalCombination;
        SealCombinations.add(this);
    }
    public AbstractSealHelperClass(String id, CrystalType... crystalTypes)
    {
        this.id = id;
        crystalCombination = new ArrayList<>();
        for(CrystalType crystal : crystalTypes)
        {
            crystalCombination.add(crystal);
        }
        SealCombinations.add(this);
    }

}
