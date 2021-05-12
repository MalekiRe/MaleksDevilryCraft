package malekire.devilrycraft.objects.items;

import com.qouteall.immersive_portals.my_util.DQuaternion;
import com.qouteall.immersive_portals.portal.Portal;

import malekire.devilrycraft.objects.blockentities.PortableHoleBlockEntity;
import malekire.devilrycraft.common.DevilryBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

public class PortableHole extends Item {
    public static int BLOCK_RANGE = 300;
    public static int COOLDOWN_TICKS = 75;
    public PortableHole(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if(!context.getWorld().isClient()) {
            Direction playerDirection = Direction.getFacing(context.getPlayer().getRotationVector().x, context.getPlayer().getRotationVector().y, context.getPlayer().getRotationVector().z);
            BlockPos portalPosition = context.getBlockPos().offset(playerDirection.getOpposite(), 1);
            BlockPos blockPosOffset;
            switch(playerDirection)
            {
                case DOWN: blockPosOffset = new BlockPos(0, 0, 0); break;
                case UP: blockPosOffset = new BlockPos(0, 0, 0); break;
                default : blockPosOffset = new BlockPos(0, -1, 0);
            }

            if(context.getWorld().getBlockState(portalPosition.offset(playerDirection, 1).add(blockPosOffset)).getBlock() == Blocks.CAVE_AIR || context.getWorld().getBlockState(portalPosition.offset(playerDirection, 1).add(blockPosOffset)).getBlock() == Blocks.AIR)
            {
                //Play failure sound.
                return ActionResult.FAIL;
            }

            if(!(context.getWorld().getBlockState(portalPosition.add(blockPosOffset)).getBlock() == Blocks.AIR || context.getWorld().getBlockState(portalPosition.add(blockPosOffset)).getBlock() == Blocks.CAVE_AIR))
            {
                if(!(playerDirection == Direction.DOWN || playerDirection == Direction.UP)) {
                    portalPosition = portalPosition.up(1);
                }
            }


            BlockPos outputPos = null;
            boolean outputPosValid = false;
            if(context.getWorld().getBlockState(portalPosition.offset(playerDirection, 1)).getBlock() == Blocks.AIR || context.getWorld().getBlockState(portalPosition.offset(playerDirection, 1)).getBlock() == Blocks.CAVE_AIR)
            {
                return ActionResult.FAIL;
            }
            for(int i = 1; i < BLOCK_RANGE; i++)
            {
                outputPos = portalPosition.offset(playerDirection, i);
                if(context.getWorld().getBlockState(outputPos.add(blockPosOffset)).getBlock() == Blocks.AIR || context.getWorld().getBlockState(outputPos).getBlock() == Blocks.CAVE_AIR)
                {
                    outputPosValid = true;
                    outputPos = portalPosition.offset(playerDirection, i);
                    break;
                }
            }
            if(portalPosition.equals(context.getBlockPos().offset(playerDirection.getOpposite(), 1))) {
                if (!outputPos.isWithinDistance(portalPosition, 50D) && playerDirection != Direction.DOWN && playerDirection != Direction.UP) {
                    portalPosition = portalPosition.up();
                }
            }
            if(outputPosValid) {
                context.getWorld().setBlockState(portalPosition, DevilryBlocks.PORTABLE_HOLE_BLOCK.getDefaultState().with(Properties.FACING, playerDirection));

                Vec3d originPos = Vec3d.of(portalPosition);
                Vec3d destPos = Vec3d.of(outputPos);

                final float portalVisualOffset = 0.06F;

                switch(playerDirection){
                    case NORTH : originPos = originPos.add(0.5, 0, portalVisualOffset); destPos = destPos.add(0.5, 0, 1-portalVisualOffset); break;
                    case SOUTH : originPos = originPos.add(0.5, 0, -portalVisualOffset+1); destPos = destPos.add(0.5, 0, portalVisualOffset); break;
                    case WEST : originPos = originPos.add(portalVisualOffset, 0, 0.5); destPos = destPos.add(-portalVisualOffset+1, 0, 0.5); break;
                    case EAST : originPos = originPos.add(-portalVisualOffset+1, 0, 0.5); destPos = destPos.add(+portalVisualOffset, 0, 0.5); break;
                    case DOWN : originPos = originPos.add(0.5, portalVisualOffset, 1); destPos = destPos.add(0.5, -portalVisualOffset+1, 1); break;
                    case UP : originPos = originPos.add(0.5, -portalVisualOffset+1, 0); destPos = destPos.add(0.5, portalVisualOffset, 0.999); break;
                    default: break;
                }
                double rotation = 0;
                double degrees = 0;
                switch(playerDirection)
                {
                    case NORTH : rotation = 0; break;
                    case SOUTH : rotation = 180; break;
                    case EAST: rotation = 270; break;
                    case WEST: rotation = 90; break;
                    default: break;
                }
                Portal portal = Portal.entityType.create(context.getWorld());
                ((PortableHoleBlockEntity) context.getWorld().getBlockEntity(portalPosition)).thePortal = portal;
                ((PortableHoleBlockEntity) context.getWorld().getBlockEntity(portalPosition)).hasPortals = true;
                portal.setOriginPos(originPos);
                portal.setDestinationDimension(context.getWorld().getRegistryKey());
                portal.setDestination(destPos);



                portal.setRotationTransformation(DQuaternion.rotationByDegrees(new Vec3d(0, 1, 0), degrees).toMcQuaternion());
                float rotation2 = 90;
                switch (playerDirection)
                {
                    case UP : portal.setOrientationAndSize(
                            new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                            new Vec3d(0, 1, 0).rotateX((float)Math.toRadians(-rotation2)), // axisH
                            1, // width
                            2 // height
                    );break;
                    case DOWN : portal.setOrientationAndSize(
                            new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                            new Vec3d(0, 1, 0).rotateX((float)Math.toRadians(rotation2)), // axisH
                            1, // width
                            2 // height
                    ); break;
                    default:
                        portal.setOrientationAndSize(
                            new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                            new Vec3d(0, 1, 0), // axisH
                            1, // width
                            2 // height
                    ); break;
                }
                portal.width = 0.01;
                portal.height = 0.01;
                ((PortableHoleBlockEntity)context.getWorld().getBlockEntity(portalPosition)).resultPos = new BlockPos(destPos);
                context.getWorld().setBlockState(new BlockPos(destPos), DevilryBlocks.PORTABLE_HOLE_BLOCK.getDefaultState().with(Properties.FACING, playerDirection.getOpposite()));
                switch(playerDirection) {
                    case UP: break;
                    case DOWN : break;
                    default :
                    testFabricOfReality(outputPos.offset(context.getPlayer().getHorizontalFacing(), -1), context, portalPosition);
                    testFabricOfReality(portalPosition.offset(context.getPlayer().getHorizontalFacing(), 1), context, portalPosition);
                    testFabricOfReality(portalPosition.offset(context.getPlayer().getHorizontalFacing(), 1).down(1), context, portalPosition); break;
                }

                if (context.getPlayer() instanceof PlayerEntity) {
                    context.getPlayer().getItemCooldownManager().set(this, COOLDOWN_TICKS);
                }
                return ActionResult.PASS;




            }
            else
            {
                return ActionResult.FAIL;
            }

        }
        return ActionResult.FAIL;

    }
    public static void testFabricOfReality(BlockPos pos, ItemUsageContext context, BlockPos portalPos) {
        if(context.getWorld().getBlockState(pos).getBlock() == Blocks.CAVE_AIR || context.getWorld().getBlockState(pos).getBlock() == Blocks.AIR)
        {
            addFabricOfReality(pos, context, portalPos);
        }
    }
    public static void addFabricOfReality(BlockPos pos, ItemUsageContext context, BlockPos portalPos) {
        context.getWorld().setBlockState(pos, DevilryBlocks.FABRIC_OF_REALITY_BLOCK.getDefaultState());
        ((PortableHoleBlockEntity) context.getWorld().getBlockEntity(portalPos)).fabricsOfReality.add(pos);

    }

}
