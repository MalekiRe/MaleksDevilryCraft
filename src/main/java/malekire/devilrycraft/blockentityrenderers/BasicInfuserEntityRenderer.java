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

public class BasicInfuserEntityRenderer extends BlockEntityRenderer<BasicInfuserBlockEntity> {
    // A jukebox itemstack
    private static ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

    public BasicInfuserEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(BasicInfuserBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
        // Move the item
        matrices.translate(0.5, 1.25 + offset, 0.5);

        // Rotate the item
        //blockEntity.setStack(1, stack);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
        BlockPos pos = blockEntity.getPos();
        //System.out.println(pos);

        BasicInfuserBlockEntity clientBlockEntity = (BasicInfuserBlockEntity)MinecraftClient.getInstance().world.getBlockEntity(pos);
        ItemStack stack2 = clientBlockEntity.getStack(0);
        //System.out.println(((Inventory)clientBlockEntity).getStack(0));
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack2, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);

        // Mandatory call after GL calls
        matrices.pop();
    }
}