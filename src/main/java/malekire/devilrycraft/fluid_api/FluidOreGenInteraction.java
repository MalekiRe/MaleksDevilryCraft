package malekire.devilrycraft.fluid_api;

import malekire.devilrycraft.Devilrycraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FluidOreGenInteraction extends FluidInteractionGroup{
    public FluidOreGenInteraction(Fluid mainFluid, Fluid secondaryFluid, Identifier fluidInteractionId) {
        super(mainFluid, secondaryFluid, fluidInteractionId);
    }
    //TODO: Fix this shit, just generates ore blocks around the fluid.
    @Override
    public void doFluidCollisionFunction(World world, BlockPos pos) {
        Direction[] var5 = Direction.values();
        int var6 = var5.length;
        for (int var7 = 0; var7 < var6; ++var7) {
            Direction direction = var5[var7];
            if (direction == Direction.DOWN) {
                continue;
            }
            BlockPos blockPos = pos.offset(direction);
            if (this.secondaryFluid.getBucketItem() != world.getFluidState(blockPos).getFluid().getBucketItem()) {
                Devilrycraft.LOGGER.log(Level.INFO, "fluid interaction bucket item was : " + this.secondaryFluid);
                Devilrycraft.LOGGER.log(Level.INFO, "world bucket item was : " + world.getFluidState(blockPos).getFluid());
                continue;
            }
            world.setBlockState(pos, getRandomOreBlock(), 3);
            Devilrycraft.LOGGER.log(Level.INFO, "doing fluid interaction");
        }

    }
    public static final Map<Block, Integer> oreBlocks = new HashMap<>();
    static {
        oreBlocks.put(Blocks.COAL_ORE, 3);
        oreBlocks.put(Blocks.DIAMOND_ORE, 20);
        oreBlocks.put(Blocks.IRON_ORE, 5);
    }
    private Random randomOreBlock = new Random();
    public BlockState getRandomOreBlock() {
        for(Block block : oreBlocks.keySet())
            if (randomOreBlock.nextInt(oreBlocks.get(block)) == 0)
                return block.getDefaultState();
        return Blocks.STONE.getDefaultState();

    }

}