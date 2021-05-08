package malekire.devilrycraft.objects.blockentities.blockentityrenderers;

import com.qouteall.immersive_portals.my_util.DQuaternion;
import malekire.devilrycraft.objects.blockentities.SealBlockEntity;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.DevilryProperties;
import malekire.devilrycraft.util.render.DRenderUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.datafixer.fix.ChunkPalettedStorageFix;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static malekire.devilrycraft.util.CrystalType.*;

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
    public static final Identifier TAINT_ID = new Identifier("devilry_craft:magic/seals/taint_seal");
    public static final Identifier NOTHING_ID = new Identifier("none");
    World world;
    BlockPos pos;
    SealBlockEntity sealBlockEntity;
    public SealBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(BlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        world = entity.getWorld();
        pos = entity.getPos();
        sealBlockEntity = (SealBlockEntity) entity;
        first_layer_identifier = getIdFromCrystalType(world.getBlockState(pos).get(DevilryProperties.FIRST_LAYER));
        System.out.println(first_layer_identifier);
        matrices.push();

        Quaternion quaternion = DQuaternion.rotationByDegrees(new Vec3d(0, 0, 1), (entity.getWorld().getTime() + tickDelta) * 4).toMcQuaternion();
        //doRotationAndTranslation(matrices, 0.5, 0.5, 0, quaternion);
        float time = (entity.getWorld().getTime() + tickDelta)*2;
        float x1 = 0, y1 = 0, x2 = 1, y2 = 1;
        //float x1 = getXFromDegree(0, 0.5F, 0, 0.5F, time);
        //float y1 = getYFromDegree(0, 0.5F, 0, 0.5F, time);
        //float x2 = getXFromDegree(1, 0.5F, 1, 0.5F, time);
        //float y2 = getYFromDegree(1, 0.5F, 1, 0.5F, time);
        doRotationBasedOnFacing(matrices, world.getBlockState(pos).get(Properties.FACING), time);
        Direction myFacing = world.getBlockState(pos).get(Properties.FACING);

        if (!first_layer_identifier.equals(NOTHING_ID)) {
            DRenderUtil.renderTexturedFace(myFacing, x1, y1, 0, x2, y2, 1, vertexConsumers, matrices, first_layer_identifier, light);
        }
        matrices.pop();
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
