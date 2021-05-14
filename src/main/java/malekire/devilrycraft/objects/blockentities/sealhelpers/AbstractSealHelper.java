package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.components.SealMateWorldComponent;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.SealCombinations;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.Inventories;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractSealHelper implements BlockEntityClientSerializable {
    public final Identifier id;
    public final ArrayList<CrystalType> crystalCombination;
    public SealBlockEntity blockEntity;
    public boolean isMateable;
    public BlockPos matePos;
    public boolean hasMate = false;
    private BlockPos mateBlockPosForDeseralization;
    public CompoundTag toTag(CompoundTag tag) {
        tag.putBoolean("is_mateable", isMateable);
        if(this.hasMate) {
            tag.put("mate_pos", BlockPos.CODEC.encode(matePos, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).getOrThrow(false, (string) -> {}));

        }


        return tag;
    }

    public void fromClientTag(CompoundTag tag) {

    }


    public CompoundTag toClientTag(CompoundTag tag) {
        return tag;
    }
    public AbstractSealHelper getMate() {
        if(!hasMate) {
            Devilrycraft.LOGGER.log(Level.ERROR, "Tried to get mate, but didn't have mate");
            return null;
        }
        getWorld().getChunk(matePos);
        return ((SealBlockEntity)getWorld().getBlockEntity(matePos)).getSealHelper();
    }
    public void fromTag(BlockState state, CompoundTag tag) {
        isMateable = tag.getBoolean("is_mateable");

        hasMate = tag.contains("mate_pos");
        if(hasMate) {
            matePos = BlockPos.CODEC.decode(NbtOps.INSTANCE, tag.get("mate_pos")).getOrThrow(false, (string) -> {}).getFirst();
        }
    }

    /**
     * Override and implement any custom rendering for the seal.
     * @param vertexConsumerProvider
     * @param matrixStack
     * @param light
     */
    public abstract void render(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, int light, int overlay);

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
        this.blockEntity = blockEntity;
    }

    public BlockPos getPos() {return this.blockEntity.getPos();}
    public World getWorld() {return this.blockEntity.getWorld();}

    /**
     * Called when a new sealHelper is created to do any extra functionality like save its position to a Set.
     * @param blockEntity The entity in the world of the Seal.
     * @return a new instance of this sealHelper.
     */
    public AbstractSealHelper getNewInstance(SealBlockEntity blockEntity) {
        AbstractSealHelper returnSeal = getNewInstance();
        returnSeal.setFields(blockEntity);
        if(returnSeal.isMateable) {
            if(!returnSeal.tryForMate())
            {
                SealMateWorldComponent.get(returnSeal.getWorld()).potentialSealMates.put(SealTarget.of(returnSeal), returnSeal.getPos());
            } else
            {
                returnSeal.hasMate = true;
                returnSeal.getMate().hasMate = true;
                SealMateWorldComponent.get(returnSeal.getWorld()).potentialSealMates.remove(SealTarget.of(returnSeal.getMate()));
            }
        }
     return returnSeal;
    }

    public boolean tryForMate() {
        if(!this.hasMate){
            AbstractSealHelper possibleMate = SealMateWorldComponent.get(getWorld()).findMate(this);
            if (possibleMate != null) {
                Devilrycraft.LOGGER.log(Level.INFO, "sucessfully matched full seals");
                this.matePos = possibleMate.getPos();
                possibleMate.matePos = this.getPos();
                return true;
            }
            Devilrycraft.LOGGER.log(Level.INFO, "did not sucessfully match seals");
            return false;
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
        //SealCombinations.add(this);
        isMateable = false;
    }
    public AbstractSealHelper(Identifier id, CrystalType... crystalTypes)
    {
        this.id = id;
        crystalCombination = new ArrayList<>();
        crystalCombination.addAll(Arrays.asList(crystalTypes));
        //SealCombinations.add(this);
        isMateable = false;
    }

}
