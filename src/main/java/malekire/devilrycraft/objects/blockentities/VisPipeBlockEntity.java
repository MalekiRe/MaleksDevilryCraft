package malekire.devilrycraft.objects.blockentities;

import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.vis_system.Vis;
import malekire.devilrycraft.vis_system.VisTaint;
import malekire.devilrycraft.vis_system.VisType;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class VisPipeBlockEntity extends VisBlockEntity implements BlockEntityClientSerializable {
    public ArrayList<BlockPos> nearVisContainers = new ArrayList<>();
    double oldVis = 0;
    public VisPipeBlockEntity() {
        super(DevilryBlockEntities.PIPE_BLOCK_ENTITY);
        maxVisTaint = new VisTaint(80, 80);
        extractionRate = 20;
    }

    @Override
    public boolean CanContainVisType(VisType type) {
        return true;
    }
    @Override
    public void markDirty() {
        super.markDirty();
        isDirty = true;
    }
    @Override
    public void tick() {
        if(!getWorld().isClient())
        {
            for(BlockPos conPos : nearVisContainers)
            {
                Vis nearbyPipe = (VisBlockEntity) world.getBlockEntity(conPos);
                for(VisType type : VisType.values())
                if(nearbyPipe.GetLevel(type) > this.GetLevel(type))
                {

                    double extraction = Math.min(Math.ceil(nearbyPipe.GetLevel(type)-this.GetLevel(type))/2, this.extractionRate);
                    //extraction = extractionRate;
                    if(nearbyPipe.Extract(type, extraction, this)) {
                        isDirty = true;
                    }
                }
            }
            if(oldVis != visTaint.visLevel)
            {
                isDirty = true;
                System.out.println(visTaint.visLevel + "at" + this.getPos());
                oldVis = visTaint.visLevel;
            }
        }
        if(!world.isClient() && isDirty) {
            //Syncing visual animations
            isDirty = false;
            this.sync();
        }

    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        visTaint.visLevel = tag.getDouble("vis_level");
        visTaint.taintLevel = tag.getDouble("taint_level");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putDouble("vis_level", visTaint.visLevel);
        tag.putDouble("taint_level", visTaint.taintLevel);
        return tag;
    }
}
