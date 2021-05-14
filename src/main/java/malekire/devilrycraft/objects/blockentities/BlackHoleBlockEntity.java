package malekire.devilrycraft.objects.blockentities;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.common.DevilryBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.Level;

import java.util.Random;

import static malekire.devilrycraft.common.DevilryBlockEntities.BLACK_HOLE_BLOCK_ENTITY;
import static net.minecraft.block.Blocks.AIR;

public class BlackHoleBlockEntity extends BlockEntity implements Tickable {
    public BlackHoleBlockEntity() {
        super(BLACK_HOLE_BLOCK_ENTITY);
    }
    Random random;
    int tick = 0;
    int x = 0;
    int y = 0;
    int z = 0;
    int prevX = 0;
    int prevY = 0;
    int prevZ = 0;
    @Override
    public void tick() {
        if(!getWorld().isClient()) {
            if(tick == 0) {
                random = new Random(pos.getX()*pos.getZ() - pos.getX()/pos.getY());
                x = getPos().getX();
                y = getPos().getY();
                z = getPos().getZ();
                prevX = x;
                prevY = y;
                prevZ = z;
            }
            tick++;
            if (tick == 1000) {
                this.world.setBlockState(pos, AIR.getDefaultState(), 3);
            } /*
            if(x != prevX) {
                if(y != prevY) {
                    if(z != prevZ) {
                        prevX = x;
                        prevY = y;
                        prevZ = z;
                    } else {
                        z++;
                    }
                } else {
                    y++;
                }
            } else
            {
                x++;
            }*/
            x += random.nextInt(8) - 4;
            y += random.nextInt(8) - 4;
            z += random.nextInt(8) - 4;
            BlockPos ourPos = new BlockPos(x, y, z);
            if(world.getBlockState(ourPos).getBlock() == DevilryBlocks.BLACK_HOLE)
                return;
            FallingBlockEntity fallingEntity = new FallingBlockEntity(world, x, y+3, z, world.getBlockState(ourPos));
            world.spawnEntity(fallingEntity);
            fallingEntity.setVelocity(0, 0.01, 0);
            fallingEntity.velocityDirty = true;
            world.setBlockState(ourPos, AIR.getDefaultState(), 3);
            x = getPos().getX();
            y = getPos().getY();
            z = getPos().getZ();
        }
    }
}
