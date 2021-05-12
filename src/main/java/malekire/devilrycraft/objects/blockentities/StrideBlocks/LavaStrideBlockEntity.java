package malekire.devilrycraft.objects.blockentities.StrideBlocks;

import malekire.devilrycraft.common.DevilryBlockEntities;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.util.Tickable;

public class LavaStrideBlockEntity extends BlockEntity implements Tickable {

    public LavaStrideBlockEntity() {
        super(DevilryBlockEntities.LAVA_STRIDE_BLOCK_ENTITY);
    }



    int lavaTicksSinceCreation = 0;

    public void tick() {
        if(lavaTicksSinceCreation>20){
            this.world.setBlockState(getPos(), Blocks.LAVA.getDefaultState(), 2);
        }
        lavaTicksSinceCreation++;
    }

}