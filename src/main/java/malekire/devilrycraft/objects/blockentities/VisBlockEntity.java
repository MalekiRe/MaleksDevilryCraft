package malekire.devilrycraft.objects.blockentities;

import malekire.devilrycraft.vis_system.Vis;
import malekire.devilrycraft.vis_system.VisTaint;
import malekire.devilrycraft.vis_system.VisType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;

import static malekire.devilrycraft.vis_system.VisType.TAINT;
import static malekire.devilrycraft.vis_system.VisType.VIS;

public abstract class VisBlockEntity extends BlockEntity implements Tickable, Vis {
    public VisTaint maxVisTaint;
    public VisTaint visTaint;
    public double extractionRate = 0;
    public double insertionRate = 0;
    public boolean isDirty = false;
    public VisBlockEntity(BlockEntityType<?> type) {
        super(type);
        visTaint = new VisTaint(0, 0);
        maxVisTaint = new VisTaint(0, 0);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putDouble("vis_level", visTaint.visLevel);
        tag.putDouble("taint_level", visTaint.taintLevel);
        tag.putDouble("extraction_rate", extractionRate);
        tag.putDouble("insertion_rate", insertionRate);
        tag.putDouble("max_vis", maxVisTaint.visLevel);
        tag.putDouble("max_taint", maxVisTaint.taintLevel);
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        visTaint = new VisTaint(tag.getDouble("vis_level"), tag.getDouble("taint_level"));
        maxVisTaint = new VisTaint(tag.getDouble("max_vis"), tag.getDouble("max_taint"));
        extractionRate = tag.getDouble("extraction_rate");
        insertionRate = tag.getDouble("insertion_rate");
    }

    @Override
    public boolean CanInsert(VisType type, double amount) {
            return !(GetLevel(type) + amount > MaxLevel(type));
    }

    @Override
    public double GetLevel(VisType type) {
        return type == VisType.VIS ? visTaint.visLevel : visTaint.taintLevel;
    }


    @Override
    public boolean Insert(VisType type, double amount, Vis vis) {
        return false;
    }

    @Override
    public boolean Extract(VisType type, double amount, Vis vis) {
        if(!vis.CanInsert(type, amount))
            return false;
        double leftover = Extract(type, amount);
        if(leftover == -1)
            return false;
        vis.Insert(type, amount);
        return true;

    }
    @Override
    public double Insert(VisType type, double amount) {
        if(IsFull(type))
            return -1;
        if(type==VisType.VIS){
            if(amount+visTaint.visLevel>maxVisTaint.visLevel) {
                visTaint.visLevel = maxVisTaint.visLevel;
                return (amount+visTaint.visLevel-maxVisTaint.visLevel);
            }

            visTaint.visLevel = amount+ visTaint.visLevel;
            return 0;
        }

        if (amount + visTaint.taintLevel > maxVisTaint.taintLevel) {
            visTaint.taintLevel = maxVisTaint.taintLevel;
            return (amount + visTaint.taintLevel - maxVisTaint.taintLevel);
        }
        visTaint.taintLevel = amount + visTaint.taintLevel;
        return 0;


    }

    @Override
    public double Extract(VisType type, double amount) {
        if(IsEmpty(type))
            return -1;
        if(type==VisType.VIS){
            if(visTaint.visLevel-amount < 0) {
                visTaint.visLevel = 0;
                return -1*visTaint.visLevel-amount;
            }
            visTaint.visLevel = visTaint.visLevel-amount;
            return 0;
        }
        if(visTaint.taintLevel-amount < 0) {
            visTaint.taintLevel = 0;
            return -1*visTaint.taintLevel-amount;
        }
        visTaint.taintLevel = visTaint.taintLevel-amount;
        return 0;


    }
    @Override
    public boolean IsEmpty(VisType type) {
        return type==VIS ? GetLevel(VIS)==0 : GetLevel(TAINT)==0;
    }

    @Override
    public boolean IsFull(VisType type) {
        return GetLevel(type) == MaxLevel(type);
    }


    @Override
    public double ExtractionRate(VisType type) {
        return extractionRate;
    }

    @Override
    public double InsertionRate(VisType type) {
        return insertionRate;
    }

    @Override
    public boolean IsFull() {
        return IsFull(VIS) && IsFull(TAINT);
    }

    @Override
    public double MaxLevel(VisType type) {
        if(type == VIS)
            return maxVisTaint.visLevel;
        return maxVisTaint.taintLevel;
    }

}
