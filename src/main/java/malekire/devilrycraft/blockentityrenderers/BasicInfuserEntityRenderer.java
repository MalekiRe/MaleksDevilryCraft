package malekire.devilrycraft.blockentityrenderers;

import com.mojang.blaze3d.platform.GlStateManager;
import malekire.devilrycraft.blockentities.BasicInfuserBlockEntity;
import malekire.devilrycraft.fluids.DevilryFluidRegistry;
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
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;

import java.awt.*;
import java.util.ArrayList;

import static net.minecraft.fluid.Fluids.LAVA;


public class BasicInfuserEntityRenderer extends BlockEntityRenderer<BasicInfuserBlockEntity> {
    // A jukebox itemstack
    private static ArrayList<Vec3d> renderPositions= new ArrayList<>();
    private static float renderScale = 0.25F;
    static double xF = 0.25;
    static double yF = 0.9;
    static double zF = -0.05;
    CustomFluidRenderer renderer;
    FluidRenderer fluidRenderer;
    BlockRenderManager blockRenderManager;
    public BasicInfuserEntityRenderer(BlockEntityRenderDispatcher dispatcher) {

        super(dispatcher);
        renderer = new CustomFluidRenderer();
        fluidRenderer = new FluidRenderer();
        renderer.onResourceReload();
        blockRenderManager = new BlockRenderManager(null, null);
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


        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        Matrix4f matrix4f = matrices.peek().getModel();
        //blockRenderManager.renderFluid(new BlockPos(1, 1, 1), blockEntity.getWorld(), consumer, LAVA.getDefaultState());
        matrices.push();


        //System.out.println(matrix4f.toString());

        double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
        // Move the item

        matrices.scale(renderScale, renderScale, renderScale);
        matrices.translate(.75, 2.7, -0.15);
        // Rotate the item
        //blockEntity.setStack(1, stack);
        //matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
        BlockPos pos = blockEntity.getPos();
        //System.out.println(pos);

        BasicInfuserBlockEntity clientBlockEntity = (BasicInfuserBlockEntity)MinecraftClient.getInstance().world.getBlockEntity(pos);
        //ItemStack stack2 = clientBlockEntity.getStack(0);
        //System.out.println(((Inventory)clientBlockEntity).getStack(0));

        for(int i = 0; i <= 12; i++) {
            if(i > 0)
            {
                matrices.translate(-renderPositions.get(i-1).x, -renderPositions.get(i-1).y, -renderPositions.get(i-1).z);
            }
            matrices.translate(renderPositions.get(i).x, renderPositions.get(i).y, renderPositions.get(i).z);
            MinecraftClient.getInstance().getItemRenderer().renderItem(clientBlockEntity.getStack(i), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);

        }
        // Mandatory call after GL calls
        matrices.pop();
         }
    private void drawLine(Vec3d blockA, Vec3d blockB) {
        Vec3d start = new Vec3d(blockA.getX(), blockA.getY(), blockA.getZ());
        Vec3d end = start.add(0, 10, 0);
        Vec3d posDiff = end.subtract(start);
        GlStateManager.pushMatrix();
        GlStateManager.lineWidth(30F);
        //GlStateManager.disableTexture();
        BufferBuilder bb = Tessellator.getInstance().getBuffer();
        bb.begin(GL11.GL_LINES, VertexFormats.POSITION_COLOR);
        bb.vertex(end.x, end.y, end.z).color(1, 1, 1, 1F).next();
        bb.vertex(posDiff.x, posDiff.y, posDiff.z).color(1, 1, 1, 1F).next();
        Tessellator.getInstance().draw();
        //GlStateManager.enableTexture();
        GlStateManager.popMatrix();
    }
    public static void drawBox(BlockPos block, double x, double y, double z) {

    }
    public static final double d = 1 / 16d;
    public static final Box BOUNDING_BOX = new Box(2 * d, 0, 2 * d, 14 * d, 1d, 14 * d);
    public static final VoxelShape TANK_SHAPE = VoxelShapes.cuboid(BOUNDING_BOX);
}