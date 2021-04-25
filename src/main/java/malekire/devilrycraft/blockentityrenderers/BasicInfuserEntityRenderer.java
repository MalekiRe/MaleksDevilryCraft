package malekire.devilrycraft.blockentityrenderers;

import malekire.devilrycraft.blockentities.BasicInfuserBlockEntity;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class BasicInfuserEntityRenderer extends BlockEntityRenderer<BasicInfuserBlockEntity> {
    // A jukebox itemstack
    private static ArrayList<Vec3d> renderPositions= new ArrayList<>();
    private static float renderScale = 0.25F;
    static double xF = 0.25;
    static double yF = 0.9;
    static double zF = -0.05;
    public BasicInfuserEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
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
        matrices.push();
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
}