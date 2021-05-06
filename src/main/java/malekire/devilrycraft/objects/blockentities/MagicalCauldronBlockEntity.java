package malekire.devilrycraft.objects.blockentities;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.common.DevilrySounds;
import malekire.devilrycraft.util.DevilryProperties;
import malekire.devilrycraft.vis_system.VisTaint;
import malekire.devilrycraft.vis_system.VisType;
import malekire.devilrycraft.common.DevilryBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;


public class MagicalCauldronBlockEntity extends VisBlockEntity {
    int visualLevel = 0; //Set up as a value from 0 to 4.
    boolean isVisOnTopVisual = true;
    int soundTicks = 0;
    int level;
    int oldLevel;
    int taintedPercent;
    int oldTaintedPercent;
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
            if (this.getWorld().getBlockState(this.getPos()).get(Properties.HONEY_LEVEL) > 0) {
                taintedPercent = (int)(4 * this.visTaint.taintLevel / (visTaint.taintLevel + visTaint.visLevel)) + 1;
                //System.out.println("tainted percent " + taintedPercent);
                if(taintedPercent != oldTaintedPercent)
                {
                    System.out.println("changed tainted percent too : " + taintedPercent);
                    oldTaintedPercent = taintedPercent;
                    this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).with(DevilryProperties.TAINTED_PERCENT, taintedPercent), 2);
                }
                level = this.getWorld().getBlockState(this.getPos()).get(Properties.HONEY_LEVEL);
                soundTicks--;
                if (soundTicks <= 1 || oldLevel != level) {
                    oldLevel = level;
                    soundTicks = 450;
                    System.out.println("playing");
                    //world.addParticle(ParticleTypes.CRIMSON_SPORE.);
                    world.playSound(
                            null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                            this.getPos(), // The position of where the sound will come from
                            DevilrySounds.CAULDRON_BUBBLING, // The sound that will play
                            SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                            (float) (((double)level)/6.0), //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                            1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                    );
                }

            }
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
            System.out.println("Taint : " + this.visTaint.taintLevel);
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
