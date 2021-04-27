package malekire.devilrycraft.client;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.blockentityrenderers.BasicInfuserEntityRenderer;
import malekire.devilrycraft.entityrenderers.SmallDirectionalLightningEntityRenderer;
import malekire.devilrycraft.screens.BasicInfuserScreen;
import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.util.EntityPacketUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import java.util.UUID;


@Environment(EnvType.CLIENT)
public class DevilrycraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.MAGICAL_CAULDRON_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.PORTABLE_HOLE_CORRUPTION_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.PORTABLE_HOLE_BLOCK, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.BASIC_INFUSER, RenderLayer.getTranslucent());
        ScreenRegistry.register(Devilrycraft.BASIC_INFUSER_SCREEN_HANDLER, BasicInfuserScreen::new);
        EntityRendererRegistry.INSTANCE.register(Devilrycraft.SMALL_DIRECTIONAL_LIGHTNING_ENTITY, (dispatcher, context) -> {
            return new SmallDirectionalLightningEntityRenderer(dispatcher);
        });
        BlockEntityRendererRegistry.INSTANCE.register(DevilryBlockEntities.BASIC_INFUSER_BLOCK_ENTITY, BasicInfuserEntityRenderer::new);
        ClientSidePacketRegistry.INSTANCE.register(EntityPacketUtils.SPAWN_PACKET_ID, (context, byteBuf) ->
        {
            final EntityType<?> type = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            final UUID entityUUID = byteBuf.readUuid();
            final int entityID = byteBuf.readVarInt();
            final double x = byteBuf.readDouble();
            final double y = byteBuf.readDouble();
            final double z = byteBuf.readDouble();
            final float pitch = (byteBuf.readByte() * 360) / 256.0F;
            final float yaw = (byteBuf.readByte() * 360) / 256.0F;

            context.getTaskQueue().execute(() ->
            {
                final MinecraftClient client = MinecraftClient.getInstance();
                final ClientWorld world = client.world;
                final Entity entity = type.create(world);
                if (world != null && entity != null)
                {
                    entity.updatePosition(x, y, z);
                    entity.updateTrackedPosition(x, y, z);
                    entity.pitch = pitch;
                    entity.yaw = yaw;
                    entity.setEntityId(entityID);
                    entity.setUuid(entityUUID);
                    world.addEntity(entityID, entity);
                }
            });
        });
    }

}
