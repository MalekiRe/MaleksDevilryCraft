package malekire.devilrycraft.client;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.blockentityrenderers.BasicInfuserEntityRenderer;
import malekire.devilrycraft.blockentityrenderers.VisPipeBlockEntityRenderer;
import malekire.devilrycraft.entityrenderers.SmallDirectionalLightningEntityRenderer;
import malekire.devilrycraft.fluids.DevilryFluidRegistry;
import malekire.devilrycraft.screen_stuff.screens.BasicInfuserScreen;
import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.util.EntityPacketUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.util.UUID;
import java.util.function.Function;


@Environment(EnvType.CLIENT)
public class DevilrycraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.MAGICAL_CAULDRON_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.PORTABLE_HOLE_CORRUPTION_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.PORTABLE_HOLE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.BASIC_INFUSER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.SILVERWOOD_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DevilryBlocks.VIS_PIPE, RenderLayer.getTranslucent());

        setupFluidRendering(DevilryFluidRegistry.STILL_VIS, DevilryFluidRegistry.FLOWING_VIS, new Identifier("devilry_craft", "magic/vis_liquid"), 0xde34eb);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), DevilryFluidRegistry.STILL_VIS, DevilryFluidRegistry.FLOWING_VIS);




        ScreenRegistry.register(Devilrycraft.BASIC_INFUSER_SCREEN_HANDLER, BasicInfuserScreen::new);
        EntityRendererRegistry.INSTANCE.register(Devilrycraft.SMALL_DIRECTIONAL_LIGHTNING_ENTITY, (dispatcher, context) -> {
            return new SmallDirectionalLightningEntityRenderer(dispatcher);
        });


        BlockEntityRendererRegistry.INSTANCE.register(DevilryBlockEntities.BASIC_INFUSER_BLOCK_ENTITY, BasicInfuserEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(DevilryBlockEntities.PIPE_BLOCK_ENTITY, VisPipeBlockEntityRenderer::new);

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

    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), textureFluidId.getPath() + "_still");
        final Identifier flowingSpriteId = new Identifier(textureFluidId.getNamespace(), textureFluidId.getPath() + "_flow");

        // If they're not already present, add the sprites to the block atlas
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final Identifier fluidId = Registry.FLUID.getId(still);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final Sprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return listenerId;
            }

            /**
             * Get the sprites from the block atlas when resources are reloaded
             */
            @Override
            public void apply(ResourceManager resourceManager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }
        });

        // The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
        final FluidRenderHandler renderHandler = new FluidRenderHandler()
        {
            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
    }



}
