package malekire.devilrycraft.blockentities;

import malekire.devilrycraft.magic.Vis;
import malekire.devilrycraft.magic.VisType;
import malekire.devilrycraft.common.DevilryBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;


public class MagicalCauldronBlockEntity extends BlockEntity implements Tickable, Vis {
    double visLevel = 0;
    double taintLevel = 0;
    double insertionRate = 10;
    double extractionRate = insertionRate;
    double maxVisCapacity = 1000;
    double maxTaintCapacity = 1000;
    int visualLevel = 0; //Set up as a value from 0 to 4.
    boolean isVisOnTopVisual = true;
    public MagicalCauldronBlockEntity() {
        super(DevilryBlockEntities.MAGICAL_CAULDRON_BLOCK_ENTITY);
    }
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putDouble("taintLevel", taintLevel);
        tag.putDouble("visLevel", visLevel);
        tag.putDouble("maxVisCapacity", maxVisCapacity);
        tag.putDouble("maxTaintCapacity", maxTaintCapacity);
        tag.putDouble("insertionRate", insertionRate);
        tag.putInt("visualLevel", visualLevel);
        tag.putBoolean("isVisOnTopVisual", isVisOnTopVisual);
        return tag;
    }
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        visLevel = tag.getDouble("visLevel");
        taintLevel = tag.getDouble("taintLevel");
        maxVisCapacity = tag.getDouble("maxVisCapacity");
        maxTaintCapacity = tag.getDouble("maxTaintCapacity");
        insertionRate = tag.getDouble("insertionRate");
        extractionRate = insertionRate;
        visualLevel = tag.getInt("visualLevel");
        isVisOnTopVisual = tag.getBoolean("isVisOnTopVisual");
    }

    @Override
    public void tick() {
        if(!this.world.isClient()) {
            isVisOnTopVisual = visLevel >= taintLevel;
            if (isVisOnTopVisual) {
                visualLevel = (int) Math.ceil(visLevel / maxVisCapacity * 5);
            } else {
                visualLevel = (int) Math.ceil(taintLevel / maxTaintCapacity * 5);
            }

            this.getWorld().setBlockState(this.getPos(),
                    this.getWorld().getBlockState(this.getPos())
                            .with(Properties.HONEY_LEVEL, visualLevel)
                            .with(Properties.INVERTED, !isVisOnTopVisual));

        }

    }

    public boolean consumeItemStack(ItemStack itemStack)
    {
        if(!IsFull(VisType.VIS) && !IsFull(VisType.TAINT))
        {
            Insert(VisType.VIS, 10);
            Insert(VisType.TAINT, 10);
            System.out.println("Vis : " + this.visLevel);
            if(!this.getWorld().isClient()) {
                System.out.println(this.getWorld().getBlockState(pos).getProperties());
            }
            return true;
        }
        return false;
    }

    @Override
    public double GetLevel(VisType type) {
        if(type==VisType.VIS)
            return visLevel;
        return taintLevel;
    }

    @Override
    public boolean Insert(VisType type, double amount) {
        if(IsFull(type))
            return false;
        if(type==VisType.VIS){
            if(amount+visLevel>maxVisCapacity)
                visLevel = maxVisCapacity;
            else
                visLevel = amount+visLevel;
        }
        else {
            if (amount + taintLevel > maxTaintCapacity)
                taintLevel = maxTaintCapacity;
            else
                taintLevel = amount + taintLevel;
        }
        return true;
    }

    @Override
    public boolean Extract(VisType type, double amount) {
        if(IsEmpty(type))
            return false;
        if(type==VisType.VIS){
            if(visLevel-amount < 0)
                visLevel = 0;
            else
                visLevel = visLevel-amount;
        }
        else {
            if(taintLevel-amount < 0)
                taintLevel = 0;
            else
                taintLevel = taintLevel-amount;
        }
        return true;
    }

    @Override
    public boolean IsEmpty(VisType type) {
        if(type==VisType.VIS)
            return visLevel == 0;
        return taintLevel == 0;
    }

    @Override
    public boolean IsFull(VisType type) {
        if(type==VisType.VIS)
            return visLevel==maxVisCapacity;
        return taintLevel==maxTaintCapacity;
    }

    @Override
    public boolean CanContainVisType(VisType type) {
        return true;
    }

    @Override
    public double ExtractionRate(VisType type) {
        return extractionRate;
    }

    @Override
    public double InsertionRate(VisType type) {
        return insertionRate;
    }
}
