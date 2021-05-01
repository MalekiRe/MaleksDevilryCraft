package malekire.devilrycraft.blocks;

import malekire.devilrycraft.blockentities.BasicInfuserBlockEntity;
import malekire.devilrycraft.vis_system.Vis;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BasicInfuser extends BlockWithEntity {
    public BasicInfuser(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new BasicInfuserBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            //This will call the createScreenHandlerFactory method from BlockWithEntity, which will return our blockEntity casted to
            //a namedScreenHandlerFactory. If your block class does not extend BlockWithEntity, it needs to implement createScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }


    //This method will drop all items onto the ground when the block is broken
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BasicInfuserBlockEntity) {
                ItemScatterer.spawn(world, pos, (BasicInfuserBlockEntity)blockEntity);
                // update comparators
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        //With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {

        if(world.isClient())
            return;
        for(Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.offset(direction);
            if(world.getBlockEntity(offsetPos) instanceof Vis)
            {
                if(!((BasicInfuserBlockEntity)world.getBlockEntity(pos)).neighborVisBlocks.contains(offsetPos)) {
                    ((BasicInfuserBlockEntity) world.getBlockEntity(pos)).neighborVisBlocks.add(offsetPos);
                    System.out.println("ADDING");
                    System.out.println((((BasicInfuserBlockEntity) world.getBlockEntity(pos)).neighborVisBlocks));
                }

            }
            else {

                if(((BasicInfuserBlockEntity)world.getBlockEntity(pos)).neighborVisBlocks.contains(offsetPos)) {
                    System.out.println("removing");
                    ((BasicInfuserBlockEntity) world.getBlockEntity(pos)).neighborVisBlocks.remove(offsetPos);
                }
            }
        }

    }

}
