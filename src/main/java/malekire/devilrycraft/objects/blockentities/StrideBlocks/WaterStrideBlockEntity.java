package malekire.devilrycraft.objects.blockentities.StrideBlocks;

import malekire.devilrycraft.common.DevilryBlockEntities;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.util.Tickable;

public class WaterStrideBlockEntity extends BlockEntity implements Tickable {

    public WaterStrideBlockEntity() {
        super(DevilryBlockEntities.WATER_STRIDE_BLOCK_ENTITY);
    }



    int waterTicksSinceCreation = 0;

    public void tick() {
        if(waterTicksSinceCreation>20){
            this.world.setBlockState(getPos(), Blocks.WATER.getDefaultState(), 2);
        }
        waterTicksSinceCreation++;
    }

}
