package malekire.devilrycraft.objects.blockentities.blockentityrenderers;

import com.qouteall.immersive_portals.my_util.DQuaternion;
import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.blockentities.sealhelpers.SealBlockEntity;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.DevilryProperties;
import malekire.devilrycraft.util.render.DRenderUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class SealBlockEntityRenderer extends BlockEntityRenderer {
    Identifier first_layer_identifier;
    Identifier second_layer_identifier;
    Identifier third_layer_identifier;
    Identifier fourth_layer_identifier;
    public static final Identifier AIR_ID = new Identifier("devilry_craft:magic/seals/air_seal");
    public static final Identifier WATER_ID = new Identifier("devilry_craft:magic/seals/water_seal");
    public static final Identifier EARTH_ID = new Identifier("devilry_craft:magic/seals/earth_seal");
    public static final Identifier FIRE_ID = new Identifier("devilry_craft:magic/seals/fire_seal");
    public static final Identifier VIS_ID = new Identifier("devilry_craft:magic/seals/vis_seal");
    public static final Identifier TAINT_ID = new Identifier("devilry_craft:magic/seals/tainted_seal");
    public static final Identifier NOTHING_ID = new Identifier("none");
    public static final Identifier DEFAULT = new Identifier("devilry_craft:magic/seals/seal_none");
    public static final Identifier DEFAULT2 = new Identifier("devilry_craft:magic/seals/seal_none_2");
    World world;
    BlockPos pos;
    SealBlockEntity sealBlockEntity;
    public SealBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }
    BlockPos renderingFacingPos;
    @Override
    public void render(BlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        world = entity.getWorld();
        pos = entity.getPos();
        sealBlockEntity = (SealBlockEntity) entity;
        first_layer_identifier = getIdFromCrystalType(world.getBlockState(pos).get(DevilryProperties.FIRST_LAYER));
        second_layer_identifier = getIdFromCrystalType(world.getBlockState(pos).get(DevilryProperties.SECOND_LAYER));
        third_layer_identifier = getIdFromCrystalType(world.getBlockState(pos).get(DevilryProperties.THIRD_LAYER));
        fourth_layer_identifier = getIdFromCrystalType(world.getBlockState(pos).get(DevilryProperties.FOURTH_LAYER));

        SealBlockEntity sealBlockEntity = (SealBlockEntity) entity;
        if(sealBlockEntity.getSealHelper() != null) {
            matrices.push();
            sealBlockEntity.getSealHelper().render(vertexConsumers, matrices, light, overlay);
            matrices.pop();
        }
        float time = (entity.getWorld().getTime() + tickDelta)*2;
        float x1 = 0, y1 = 0, x2 = 1, y2 = 1;
        Direction myFacing = world.getBlockState(pos).get(Properties.FACING);
        renderLayer(0, time, DEFAULT, vertexConsumers, matrices, light, myFacing);
        renderLayer(1, time, first_layer_identifier, vertexConsumers, matrices, light, myFacing);
        renderLayer(2, (float) (time+90), second_layer_identifier, vertexConsumers, matrices, light, myFacing);
        if(!third_layer_identifier.equals(NOTHING_ID))
            renderLayer(3, time, DEFAULT2, vertexConsumers, matrices, light, myFacing);
        renderLayer(4, (float) (time), third_layer_identifier, vertexConsumers, matrices, light, myFacing);
        renderLayer(5, (float) (time+90), fourth_layer_identifier, vertexConsumers, matrices, light, myFacing);

        if(!(entity instanceof SealBlockEntity)) {
            Devilrycraft.LOGGER.log(Level.ERROR, "block entity being rendered is not a SealBlockEntity");
            return;
        }



    }

    public void renderLayer(int layerNumber, float time, Identifier id, VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, int light, Direction facing)
    {
        matrixStack.push();
        if(layerNumber < 3)
            doRotationBasedOnFacing(matrixStack, facing, time);
        else
            doRotationBasedOnFacing(matrixStack, facing.getOpposite(), time);
        float x1 = 0;
        float y1 = 0;
        float z1 = 0;

        float x2 = 0, y2 = 0, z2 = 0;
        float shrinkSize = 0.0F;
        if(layerNumber >= 3)
        {
            shrinkSize = 0.2F;
        }
        float distanceSize = 0.001F;
        float nearBlock = 0.99F;
        switch (facing) {
            case NORTH : z1+=distanceSize; x2 = shrinkSize; y2 = shrinkSize; break;
            case SOUTH : z1-=distanceSize; x2 = shrinkSize; y2 = shrinkSize; break;
            case EAST : x1+=distanceSize; z2 = shrinkSize;  y2 = shrinkSize; break;
            case WEST: x1-=distanceSize; z2 = shrinkSize; y2 = shrinkSize; break;
            case UP : y1-=distanceSize; x2 = shrinkSize; z2 = shrinkSize; break;
            case DOWN: y1+=distanceSize; x2 = shrinkSize; z2 = shrinkSize; break;
        }

           x1 *= layerNumber; y1 *= layerNumber; z1 *= layerNumber;
           switch (facing) {
               case NORTH : z1 += nearBlock;break;
               case SOUTH : z1 -= nearBlock; break;
               case EAST: x1 -= nearBlock; break;
               case WEST: x1 += nearBlock; break;
               case UP : y1 -= nearBlock; break;
               case DOWN : y1 += nearBlock; break;
           }

        /*
        x2 *= layerNumber; x2 /= 1;
        y2 *= layerNumber; y2 /= 1;
        z2 *= layerNumber; z2 /= 1;*/
        if (!id.equals(NOTHING_ID)) {
            DRenderUtil.renderTexturedFace(facing, x2+x1, y2+y1, z2+z1, x1+1-x2, y1+1-y2, z1+1-z2, vertexConsumerProvider, matrixStack, id, light);
        }

        matrixStack.pop();
    }
    public void doRotationBasedOnFacing(MatrixStack matrixStack, Direction facing, float time) {
        switch(facing)
        {
            case SOUTH:
                doRotationAndTranslation(matrixStack, 0.5, 0.5, 0, DQuaternion.rotationByDegrees(new Vec3d(0, 0, 1), time).toMcQuaternion());
            break;
            case NORTH:
                doRotationAndTranslation(matrixStack, 0.5, 0.5, 0, DQuaternion.rotationByDegrees(new Vec3d(0, 0, -1), time).toMcQuaternion());
                break;
            case EAST:
                doRotationAndTranslation(matrixStack, 0, 0.5, 0.5, DQuaternion.rotationByDegrees(new Vec3d(1, 0, 0), time).toMcQuaternion());
                break;
            case WEST:
                doRotationAndTranslation(matrixStack, 0, 0.5, 0.5, DQuaternion.rotationByDegrees(new Vec3d(-1, 0, 0), time).toMcQuaternion());
                break;
            default: System.out.println("default");
        }
    }
    public void doRotationAndTranslation(MatrixStack matrixStack, double x, double y, double z, Quaternion quaternion)
    {
        matrixStack.translate(x, y, z);
        matrixStack.multiply(quaternion);
        matrixStack.translate(-x, -y, -z);
    }
    public float getXFromDegree(float x1, float x2, float y1, float y2, float time)
    {
        return (float) ((Math.cos(time)*(x1-x2)) - (Math.sin(time)*(y1-y2))+x2);
    }
    public float getYFromDegree(float x1, float x2, float y1, float y2, float time)
    {
        return (float) ((Math.sin(time)*(x1-x2)) + (Math.cos(time)*(y1-y2))+y2);
    }
    public static Identifier getIdFromCrystalType(CrystalType type)
    {
        Identifier identifier;
        switch (type) {
            case AIR_TYPE: identifier = AIR_ID; break;
            case WATER_TYPE: identifier = WATER_ID; break;
            case EARTH_TYPE: identifier = EARTH_ID; break;
            case FIRE_TYPE: identifier = FIRE_ID; break;
            case VIS_TYPE: identifier = VIS_ID; break;
            case TAINT_TYPE: identifier = TAINT_ID; break;
            case NONE: identifier = NOTHING_ID; break;
            default: identifier = null;
        }
        return identifier;
    }
}
