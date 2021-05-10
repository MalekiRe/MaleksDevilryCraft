package malekire.devilrycraft.objects.blockentities.sealhelpers;

import jdk.nashorn.internal.ir.Block;
import malekire.devilrycraft.util.CrystalType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class SealHelperAbstractClass {

    public abstract void doHelperTick();
    public abstract void doHelperTick(SealBlockEntity blockEntity);

    public abstract void doHelperOneOffFunction();
    public abstract void doHelperOneOffFunction(SealBlockEntity blockEntity);
    public final String id;
    public final ArrayList<CrystalType> crystalCombination;
    public World world;
    public BlockPos pos;
    public SealBlockEntity blockEntity;
    public void setHelperFields(SealBlockEntity blockEntity)
    {
        world = blockEntity.getWorld();
        pos = blockEntity.getPos();
        this.blockEntity = blockEntity;
    }

    public SealHelperAbstractClass(String id, ArrayList<CrystalType> crystalCombination)
    {
        this.id = id;
        this.crystalCombination = crystalCombination;
    }
    public SealHelperAbstractClass(String id, CrystalType... crystalTypes)
    {
        this.id = id;
        crystalCombination = new ArrayList<>();
        for(CrystalType crystal : crystalTypes)
        {
            crystalCombination.add(crystal);
        }
    }
}
