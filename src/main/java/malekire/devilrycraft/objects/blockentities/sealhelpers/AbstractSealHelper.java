package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.SealCombinations;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static malekire.devilrycraft.objects.blockentities.sealhelpers.SealUtilities.crystalBlockStatesMatch;
import static malekire.devilrycraft.objects.blockentities.sealhelpers.SealUtilities.potentialSealMates;

public abstract class AbstractSealHelper {
    public final Identifier id;
    public final ArrayList<CrystalType> crystalCombination;
    public World world;
    public BlockPos pos;
    public SealBlockEntity blockEntity;
    public boolean isMateable;
    public AbstractSealHelper mate;
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
     * @param blockEntity The posistion in the world of the Seal.
     * @return a new instance of this sealHelper.
     */
    public AbstractSealHelper getNewInstance(SealBlockEntity blockEntity) {
        AbstractSealHelper returnSeal = getNewInstance();
        returnSeal.setFields(blockEntity);
        if(returnSeal.isMateable) {
            if(!returnSeal.tryForMate())
            {
                potentialSealMates.add(returnSeal);
            } else
            {
                potentialSealMates.remove(returnSeal.mate);
            }
        }
     return returnSeal;
    }

    public boolean tryForMate() {
        for(AbstractSealHelper potentialMate : potentialSealMates) {
            if(potentialMate.id.equals(this.id)) {
                Devilrycraft.LOGGER.log(Level.INFO, "ID's Match");
                if(crystalBlockStatesMatch(potentialMate.blockEntity.blockState, this.blockEntity.blockState)) {
                    Devilrycraft.LOGGER.log(Level.INFO, "sucessfully matched full seals");
                    this.mate = potentialMate;
                    potentialMate.mate = this;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The function you override with a new instance of your class.
     * @return A new instance of your class
     */
    protected abstract AbstractSealHelper getNewInstance();



    public AbstractSealHelper(Identifier id, ArrayList<CrystalType> crystalCombination)
    {
        this.id = id;
        this.crystalCombination = crystalCombination;
        SealCombinations.add(this);
        isMateable = false;
    }
    public AbstractSealHelper(Identifier id, CrystalType... crystalTypes)
    {
        this.id = id;
        crystalCombination = new ArrayList<>();
        crystalCombination.addAll(Arrays.asList(crystalTypes));
        SealCombinations.add(this);
        isMateable = false;
    }

}
