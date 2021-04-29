package malekire.devilrycraft.blockentities;

import malekire.devilrycraft.magic.Vis;
import malekire.devilrycraft.magic.VisTaint;
import malekire.devilrycraft.magic.VisType;
import malekire.devilrycraft.common.DevilryBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;


public class MagicalCauldronBlockEntity extends VisBlockEntity {
    int visualLevel = 0; //Set up as a value from 0 to 4.
    boolean isVisOnTopVisual = true;
    public MagicalCauldronBlockEntity() {
        super(DevilryBlockEntities.MAGICAL_CAULDRON_BLOCK_ENTITY);
        maxVisTaint = new VisTaint(1000, 1000);

    }
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putInt("visualLevel", visualLevel);
        tag.putBoolean("isVisOnTopVisual", isVisOnTopVisual);
        return tag;
    }
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        visualLevel = tag.getInt("visualLevel");
        isVisOnTopVisual = tag.getBoolean("isVisOnTopVisual");
    }

    @Override
    public void tick() {
        if(!this.world.isClient()) {
            isVisOnTopVisual = visTaint.visLevel >= visTaint.taintLevel;
            if (isVisOnTopVisual) {
                visualLevel = (int) Math.ceil(visTaint.visLevel / maxVisTaint.visLevel * 5);
            } else {
                visualLevel = (int) Math.ceil(visTaint.taintLevel / maxVisTaint.taintLevel * 5);
            }

            this.getWorld().setBlockState(this.getPos(),
                    this.getWorld().getBlockState(this.getPos())
                            .with(Properties.HONEY_LEVEL, visualLevel)
                            .with(Properties.INVERTED, !isVisOnTopVisual));

        }

    }
    //ERROR, FOR SOME REASON THIS RUNS TWICE IDK WHY, WILL FIX LATER
    public boolean consumeItemStack(ItemStack itemStack)
    {
        if(this.getWorld().isClient())
            return false;
        if(!IsFull(VisType.VIS) && !IsFull(VisType.TAINT))
        {
            Insert(VisType.VIS, 10);
            Insert(VisType.TAINT, 10);
            System.out.println("Vis : " + this.visTaint.visLevel);
            if(!this.getWorld().isClient()) {
                System.out.println(this.getWorld().getBlockState(pos).getProperties());
            }
            return true;
        }
        return false;
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
