package malekire.devilrycraft.blockentityrenderers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.qouteall.immersive_portals.my_util.DQuaternion;
import com.sun.javafx.geom.Vec3f;
import malekire.devilrycraft.blockentities.BasicInfuserBlockEntity;
import malekire.devilrycraft.fluids.DevilryFluidRegistry;
import malekire.devilrycraft.util.DevilryProperties;
import malekire.devilrycraft.util.RenderUtil;
import malekire.devilrycraft.util.render.DRenderUtil;
import malekire.devilrycraft.vis_system.VisTaint;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;
import static net.minecraft.fluid.Fluids.LAVA;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_LINES;
import static org.lwjgl.opengl.GL11C.glLineWidth;


public class BasicInfuserEntityRenderer extends BlockEntityRenderer<BasicInfuserBlockEntity> {
    // A jukebox itemstack
    private static ArrayList<Vec3d> renderPositions= new ArrayList<>();
    private static float renderScale = 0.25F;
    static double xF = 0.25;
    static double yF = 0.9;
    static double zF = -0.05;
    public static ArrayList<Vec3f> lightningPos = new ArrayList<>();
    CustomFluidRenderer renderer;
    FluidRenderer fluidRenderer;
    BlockRenderManager blockRenderManager;

    Random random = new Random();
    public BasicInfuserEntityRenderer(BlockEntityRenderDispatcher dispatcher) {

        super(dispatcher);
        renderer = new CustomFluidRenderer();
        fluidRenderer = new FluidRenderer();
        renderer.onResourceReload();
        blockRenderManager = new BlockRenderManager(null, null);
        MatrixStack stack = null;
        VertexConsumerProvider v = null;
        DRenderUtil.getLightningPositions(0F, 0F, 0F, 20F, 2F, 20F, 2F, 0F, stack, v, 1, new VisTaint(9, 9), lightningPos);
    }
    static {
        renderPositions.add(new Vec3d(0.25, 0.9, -0.05));
        renderPositions.add(new Vec3d(2.0+renderPositions.get(0).x, 0.9, -0.05));
        renderPositions.add(new Vec3d(3.25+renderPositions.get(0).x, renderPositions.get(0).y, 1.2+renderPositions.get(0).z));
        renderPositions.add(new Vec3d(3.25+renderPositions.get(0).x, renderPositions.get(0).y, 3.2+renderPositions.get(0).z));
        renderPositions.add(new Vec3d(1.8+xF, 0+yF, 4.5+zF));
        renderPositions.add(new Vec3d(xF, yF, zF+4.5));
        renderPositions.add(new Vec3d(xF-1.25, yF, zF+3.2));
        renderPositions.add(new Vec3d(xF-1.25, yF, zF+1.2));


        renderPositions.add(new Vec3d(0.25+xF, yF, zF+1.5));
        renderPositions.add(new Vec3d(1.8+xF, yF, zF+1.5));
        renderPositions.add(new Vec3d(1.8+xF, yF, 3+zF));
        renderPositions.add(new Vec3d(0.25+xF, yF, 3+zF));

        renderPositions.add(new Vec3d(1+xF, yF, 2.25+zF));
        //renderPositions.add(new Vec3d(0, 0, 0));
    }
    @Override
    public void render(BasicInfuserBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        //drawLine(new Vec3d(0, 0, 0), new Vec3d(2, 2, 2));
        matrices.push();
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        //DRenderUtil.renderGuiTank(DevilryFluidRegistry.FLOWING_VIS, 1, 1, 0, 1, 1);
        //DRenderUtil.renderGuiTank(DevilryFluidRegistry.STILL_VIS, 0, 0, 0, 64, 644);
        //this.renderFunction(blockEntity, 0, 0, matrices, vertexConsumers, light);
        matrices.pop();
        //renderFunction(blockEntity, 0, 0, matrices, vertexConsumers, light);
        //blockRenderManager.renderFluid(new BlockPos(1, 1, 1), blockEntity.getWorld(), consumer, LAVA.getDefaultState());



        //System.out.println(matrix4f.toString());

        double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
        // Move the item


        // Rotate the item
        //blockEntity.setStack(1, stack);
        //matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
        BlockPos pos = blockEntity.getPos();
        //System.out.println(pos);
        //DRenderUtil.RenderFace(Direction.UP, 0, 0, vertexConsumers, matrices, new Identifier(MOD_ID, "silverwood_log.png"), light);
        //DRenderUtil.renderLine(Direction.UP, 100, 100, 100, 200, 0, 0, 0, 0.5F, 0.5F, 0.5F, matrices, vertexConsumers, light);
        BasicInfuserBlockEntity clientBlockEntity = (BasicInfuserBlockEntity)MinecraftClient.getInstance().world.getBlockEntity(pos);
        //ItemStack stack2 = clientBlockEntity.getStack(0);
        //System.out.println(((Inventory)clientBlockEntity).getStack(0));
        //DRenderUtil.renderCube(255, 0, 40, 200, 0, 0, 0, 10, 10, 10, matrices, vertexConsumers, light);
        for(int i = 0; i <= 12; i++) {
            matrices.push();
            matrices.scale(renderScale, renderScale, renderScale);
            matrices.translate(.75, 2.7, -0.15);
            matrices.translate(renderPositions.get(i).x, renderPositions.get(i).y, renderPositions.get(i).z);
            MinecraftClient.getInstance().getItemRenderer().renderItem(clientBlockEntity.getStack(i), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);

            matrices.pop();
            matrices.push();
            /*
            for(int i2 = 0; i2 < lightningPos.size(); i2 = i2+2)
            {
                DRenderUtil.renderCube(i*5, 20, 80, 255, lightningPos.get(i2).x, lightningPos.get(i2).y, lightningPos.get(i2).z, lightningPos.get(i2+1).x, lightningPos.get(i2+1).y, lightningPos.get(i2+1).z, matrices, vertexConsumers, light);
            }
            /*
            if(!clientBlockEntity.getStack(i).isEmpty() && (((int)blockEntity.getWorld().getTime()) % 20 == 0))
            {
                lightningPos.clear();
                //matrices.multiply(DQuaternion.rotationByDegrees(new Vec3d(0, 1, 0), 180).toMcQuaternion());
                DRenderUtil.getLightningPositions(
                        (float) renderPositions.get(i).x,(float) renderPositions.get(i).y,(float) renderPositions.get(i).z,
                        (float) renderPositions.get(12).x, (float)renderPositions.get(12).y,(float) renderPositions.get(12).z, 2, 0,
                        matrices, vertexConsumers, light, new VisTaint(0, 0), lightningPos
                );
            }

             */
            matrices.pop();
        }
        // Mandatory call after GL calls




        //drawLine(blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ(), blockEntity.getPos().getX()+10, blockEntity.getPos().getY()+10, blockEntity.getPos().getZ()+10, matrices, vertexConsumers);
         }
    private void drawLine(float x1, float y1, float z1, float x2, float y2, float z2, MatrixStack matrices, VertexConsumerProvider consumerProvider) {

        GlStateManager.pushMatrix();
        GlStateManager.lineWidth(30F);
        //GlStateManager.disableTexture();
        BufferBuilder bb = Tessellator.getInstance().getBuffer();
        bb.begin(GL11.GL_LINES, VertexFormats.POSITION_COLOR);
        bb.vertex(x1, y1, z1).color(1, 1, 1, 1F).next();
        bb.vertex(x2, y2, z2).color(1, 1, 1, 1F).next();
        Tessellator.getInstance().draw();
        //GlStateManager.enableTexture();
        GlStateManager.popMatrix();
    }
    public static void drawBox(BlockPos block, double x, double y, double z) {

    }
    public static final double d = 1 / 16d;
    public static final Box BOUNDING_BOX = new Box(2 * d, 0, 2 * d, 14 * d, 1d, 14 * d);
    public static final VoxelShape TANK_SHAPE = VoxelShapes.cuboid(BOUNDING_BOX);

    public void renderFunction(BlockEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        //random.setSeed(entity.getUuid().getMostSignificantBits());
        double temperature = 100F;
        double age = 1;
        float size = (float) ((float) MathHelper.clamp(temperature / 100F, 0F, 1F) - (Math.sin((age + random.nextDouble() * 10) / 7.5F) + 1) / 7.5F) - 0.01F;
        size = (float) MathHelper.clamp(temperature / 100F, 0F, 1F);
        int c = (int) (235 - 20 * random.nextDouble());
        c = 30;
        //noinspection UnnecessaryLocalVariable
        int r = c, g = c, b = c;
        int a = 225;

        float x = 0, y = 0, z = 0;

        float minX = x - size / 2, minY = y - size / 2, minZ = z - size / 2;
        float maxX = x + size / 2, maxY = y + size / 2, maxZ = z + size / 2;

        VertexConsumer consumer = vertexConsumers.getBuffer(AutomotionRenderLayers.TRANSLUCENT_TEXTURED);
        Matrix4f matrix4f = matrices.peek().getModel();

        int NSD = -15;
        int EWD = -7;
        int BD = -30;

        float aUV[] = {0.3F, 0.1F};
        float bUV[] = {0.3F, 0.0F};
        float cUV[] = {0.2F, 0.0F};
        float dUV[] = {0.2F, 0.1F};

        Sprite icon = RenderUtil.getSprite(new Identifier("minecraft", "dirt"));
        icon = RenderUtil.getStillTexture(DevilryFluidRegistry.STILL_VIS);
        float minU = icon.getMinU();
        float maxU = icon.getMaxU();
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();
        //System.out.println(minU);
        int overlay = 16;


        // Bottom

        consumer.vertex(matrix4f, maxX, minY, maxZ).color(r + BD, g + BD, b + BD, a).light(light).texture(minU, maxV).normal(0, 0, 0).next();
        consumer.vertex(matrix4f, minX, minY, maxZ).color(r + BD, g + BD, b + BD, a).light(light).texture(maxU, maxV).normal(0, 0, 0).next();
        consumer.vertex(matrix4f, minX, minY, minZ).color(r + BD, g + BD, b + BD, a).light(light).texture(maxU, minV).normal(0, 0, 0).next();
        consumer.vertex(matrix4f, maxX, minY, minZ).color(r + BD, g + BD, b + BD, a).light(light).texture(minU, minV).normal(0, 0, 0).next();


        // Top
        consumer.vertex(matrix4f, minX, maxY, maxZ).color(c, c, c, a).light(light).texture(minU, maxV).next();
        consumer.vertex(matrix4f, maxX, maxY, maxZ).color(c, c, c, a).light(light).texture(maxU, maxV).next();
        consumer.vertex(matrix4f, maxX, maxY, minZ).color(c, c, c, a).light(light).texture(maxU, minV).next();
        consumer.vertex(matrix4f, minX, maxY, minZ).color(c, c, c, a).light(light).texture(minU, minV).next();

        // North
        consumer.vertex(matrix4f, maxX, minY, minZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(minU, maxV).next();
        consumer.vertex(matrix4f, minX, minY, minZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(maxU, maxV).next();
        consumer.vertex(matrix4f, minX, maxY, minZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(maxU, minV).next();
        consumer.vertex(matrix4f, maxX, maxY, minZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(minU, minV).next();

        // South
        consumer.vertex(matrix4f, minX, minY, maxZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(minU, maxV).next();
        consumer.vertex(matrix4f, maxX, minY, maxZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(maxU, maxV).next();
        consumer.vertex(matrix4f, maxX, maxY, maxZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(maxU, minV).next();
        consumer.vertex(matrix4f, minX, maxY, maxZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(minU, minV).next();

        // East
        consumer.vertex(matrix4f, maxX, minY, maxZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(minU, maxV).next();
        consumer.vertex(matrix4f, maxX, minY, minZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(maxU, maxV).next();
        consumer.vertex(matrix4f, maxX, maxY, minZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(maxU, minV).next();
        consumer.vertex(matrix4f, maxX, maxY, maxZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(minU, minV).next();

        // West
        consumer.vertex(matrix4f, minX, minY, minZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(minU, maxV).next();
        consumer.vertex(matrix4f, minX, minY, maxZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(maxU, maxV).next();
        consumer.vertex(matrix4f, minX, maxY, maxZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(maxU, minV).next();
        consumer.vertex(matrix4f, minX, maxY, minZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(minU, minV).next();

        matrices.pop();
    }

}