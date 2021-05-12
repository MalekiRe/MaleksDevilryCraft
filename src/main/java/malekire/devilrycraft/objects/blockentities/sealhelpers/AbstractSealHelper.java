package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.SealCombinations;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class AbstractSealHelper {
    public final Identifier id;
    public final ArrayList<CrystalType> crystalCombination;
    public World world;
    public BlockPos pos;
    public SealBlockEntity blockEntity;
    public HashSet<BlockPos> sealPositions = new HashSet<>();
    public boolean hasDestSealHelper = false;
    public AbstractSealHelper destSealHelper;
    /**
     * Override and implement any functions you want to run every tick.
     * Is called after oneOffTick()
     */
    public abstract void tick();
    /**
     * Is not mean to be overriden, but called in the {@link SealBlockEntity}, and sets the fields for stuff like world, pos, and block entity using the
     * setFields method.
     * @param blockEntity How it gets the fields to use
     */
    public void tick(SealBlockEntity blockEntity) {
        setFields(blockEntity);
        tick();
    }

    /**
     * Override and implement any functions you want to run once, when a seal is finished/created in {@link SealBlockEntity}.
     */
    public abstract void oneOffTick();

    /**
     * Is not mean to be overriden, but called in the {@link SealBlockEntity}, and sets the fields for stuff like world, pos, and block entity using the
     * setFields method.
     * @param blockEntity How it gets the fields to use
     */
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

    /**
     * Called when a new sealHelper is created to do any extra functionality like save its position to a Set.
     * @param pos The posistion in the world of the Seal.
     * @return a new instance of this sealHelper.
     */
    public AbstractSealHelper getNewInstance(BlockPos pos) {
     return getNewInstance();
    }

    /**
     * The function you override with a new instance of your class.
     * @return A new instance of your class
     */
    protected abstract AbstractSealHelper getNewInstance();

    /**
     * sets the boolean for hasDestSealHelper, and sets hasDestSealHelper for both the destSealHelper and for this sealHelper.
     * @param sealHelper
     */
    public void setDestSealHelper(AbstractSealHelper sealHelper) {
        this.destSealHelper = sealHelper;
        this.hasDestSealHelper = true;
        this.destSealHelper.setDestSealHelper(this);
    }

    public AbstractSealHelper(Identifier id, ArrayList<CrystalType> crystalCombination)
    {
        this.id = id;
        this.crystalCombination = crystalCombination;
        SealCombinations.add(this);
    }
    public AbstractSealHelper(Identifier id, CrystalType... crystalTypes)
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
