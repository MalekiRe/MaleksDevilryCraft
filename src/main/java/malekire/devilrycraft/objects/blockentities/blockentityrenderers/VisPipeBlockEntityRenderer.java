package malekire.devilrycraft.objects.blockentities.blockentityrenderers;

import malekire.devilrycraft.objects.blockentities.VisPipeBlockEntity;
import malekire.devilrycraft.util.render.DRenderUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;

import static malekire.devilrycraft.util.DevilryProperties.*;

public class VisPipeBlockEntityRenderer extends BlockEntityRenderer<VisPipeBlockEntity> {
    float top;
    float bottom;
    float north;
    float south;
    float east;
    float west;
    float max = 0.999f;
    float min = 0.001f;
    boolean isConnectedToSomething;
    float multiplier = 1;
    public VisPipeBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(VisPipeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        multiplier = 1;
        isConnectedToSomething = false;
        /*
        for (BooleanProperty connectedDirection : CONNECTED_DIRECTIONS) {
            if (entity.getWorld().getBlockState(entity.getPos()).get(connectedDirection)) {
                isConnectedToSomething = true;
                BlockPos pos = new BlockPos(0, 0, 0);
                try {
                    pos = pos.offset(getConnectedDirection(connectedDirection));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                float offset = 0.4F;
                if (connectedDirection.getName() == "north_connected" || connectedDirection.getName() == "up_connected" ||
                connectedDirection.getName() == "west_connected")
                {
                    DRenderUtil.renderCube(new Identifier("devilry_craft:magic/extra/vis_liquid_still_1"), 255, 255, 255, 200, 0.2F+offset, 0.2F+offset, 0.2F+offset,pos.getX()+offset, pos.getY()+offset, pos.getZ()+offset,  matrices, vertexConsumers, light);
                }
                else
                {
                    //DRenderUtil.renderCube(new Identifier("devilry_craft:magic/extra/vis_liquid_still_1"), 255, 255, 255, 200, north, top, west, pos.getX(), pos.getY(), pos.getZ(), matrices, vertexConsumers, light);
                }
            }
        }

         */
        if(entity.visTaint.visLevel > entity.visTaint.taintLevel)
        {
            multiplier = (float) (entity.visTaint.visLevel/entity.maxVisTaint.visLevel);
        }
        else
        {
            multiplier = (float) (entity.visTaint.taintLevel/entity.maxVisTaint.taintLevel);
        }
        if(multiplier == 0)
            return;
        multiplier = 1;

        //System.out.println(entity.GetLevel(VIS));
        top = 0.4F + multiplier*0.2F;
        bottom = 0.6F - multiplier*0.2F;
        north = 0.6F - multiplier*0.2F;
        south = 0.4F + multiplier*0.2F;
        east = 0.4F + multiplier*0.2F;
        west = 0.6F - multiplier*0.2F;
        max = 0.999f;
        min = 0.001f;
        int color = (int) (200 * (entity.visTaint.visLevel/entity.visTaint.taintLevel)) + 55;
        if(connected(entity, UP_CONNECTED))
        {
            DRenderUtil.renderCube(new Identifier("devilry_craft:magic/extra/vis_liquid_still_1"), color, color, color, 200, north, 0.99F, east,south, 0.6001F, west,  matrices, vertexConsumers, light);
        }
        if(connected(entity, DOWN_CONNECTED))
        {
            DRenderUtil.renderCube(new Identifier("devilry_craft:magic/extra/vis_liquid_still_1"), color, color, color, 200, north, 0.399F, east,south, 0.01F, west,  matrices, vertexConsumers, light);
        }
        if(connected(entity, EAST_CONNECTED))
        {
            DRenderUtil.renderCube(new Identifier("devilry_craft:magic/extra/vis_liquid_still_1"), color, color, color, 200,
                    max, top, north,west+0.2f, bottom, south,
                    matrices, vertexConsumers, light);
        }
        if(connected(entity, WEST_CONNECTED))
        {
            DRenderUtil.renderCube(new Identifier("devilry_craft:magic/extra/vis_liquid_still_1"), color, color, color, 200,
                    0.399F, top, north, min, bottom, south,
                    matrices, vertexConsumers, light);
        }
        if(connected(entity, NORTH_CONNECTED))
        {
            DRenderUtil.renderCube(new Identifier("devilry_craft:magic/extra/vis_liquid_still_1"), color, color, color, 200, east, top, 0.001F ,west, bottom, south-0.2F,  matrices, vertexConsumers, light);
        }
        if(connected(entity, SOUTH_CONNECTED))
        {
            DRenderUtil.renderCube(new Identifier("devilry_craft:magic/extra/vis_liquid_still_1"), color, color, color, 200, east, top, north+0.2F,west, bottom, max,  matrices, vertexConsumers, light);
        }
            DRenderUtil.renderCube(new Identifier("devilry_craft:magic/extra/vis_liquid_still_1"), color, color, color, 200, east, top, north, west, bottom, south, matrices, vertexConsumers, light);
    }

    public static boolean connected(BlockEntity entity, BooleanProperty property)
    {
        return entity.getWorld().getBlockState(entity.getPos()).get(property);
    }


}
