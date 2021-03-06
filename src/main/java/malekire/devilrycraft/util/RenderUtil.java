package malekire.devilrycraft.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static final Identifier BLOCK_TEX = SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;

    public static TextureManager engine() {
        return MinecraftClient.getInstance().getTextureManager();
    }

    public static void bindBlockTexture() {
        engine().bindTexture(BLOCK_TEX);
    }

    /*
    public static Sprite getStillTexture(FluidInstance fluid) {
        if (fluid == null || fluid.getFluid() == null) {
            return null;
        }
        return getStillTexture(fluid.getFluid());
    }*/

    public static Sprite getSprite(Identifier identifier) {
        return MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(identifier);
    }

    public static Sprite getStillTexture(Fluid fluid) {
        FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);
        if (fluidRenderHandler != null) {
            return fluidRenderHandler.getFluidSprites(MinecraftClient.getInstance().world, BlockPos.ORIGIN, fluid.getDefaultState())[0];
        }
        return null;
    }

    /*
    public static void renderGuiTank(Tank tank, double x, double y, double zLevel, double width, double height) {
        renderGuiTank(tank.getFluidInstance(), tank.getCapacity(), tank.getFluidAmount(), x, y, zLevel, width, height);
    }

     */

    public static void renderGuiTank(Fluid fluid, double x, double y, double zLevel,
                                     double width, double height) {


        Sprite icon = getStillTexture(fluid);
        if (icon == null) {
            return;
        }

        int renderAmount = (int) Math.max(Math.min(height, 10 * height / 15), 1);
        int posY = (int) (y + height - renderAmount);

        RenderUtil.bindBlockTexture();
        int color = 0;
        GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));

        RenderSystem.enableBlend();
        for (int i = 0; i < width; i += 16) {
            for (int j = 0; j < renderAmount; j += 16) {
                int drawWidth = (int) Math.min(width - i, 16);
                int drawHeight = Math.min(renderAmount - j, 16);

                int drawX = (int) (x + i);
                int drawY = posY + j;

                float minU = icon.getMinU();
                float maxU = icon.getMaxU();
                float minV = icon.getMinV();
                float maxV = icon.getMaxV();

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder tes = tessellator.getBuffer();
                tes.begin(7, VertexFormats.POSITION_TEXTURE);
                tes.vertex(drawX, drawY + drawHeight, 0).texture(minU, minV + (maxV - minV) * drawHeight / 16F).next();
                tes.vertex(drawX + drawWidth, drawY + drawHeight, 0)
                        .texture(minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F)
                        .next();
                tes.vertex(drawX + drawWidth, drawY, 0).texture(minU + (maxU - minU) * drawWidth / 16F, minV).next();
                tes.vertex(drawX, drawY, 0).texture(minU, minV).next();
                tessellator.draw();
            }
        }
        RenderSystem.disableBlend();
    }

    public static void drawGradientRect(int zLevel, int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, VertexFormats.POSITION_COLOR);
        vertexbuffer.vertex(right, top, 0).color(f1, f2, f3, f).next();
        vertexbuffer.vertex(left, top, 0).color(f1, f2, f3, f).next();
        vertexbuffer.vertex(left, bottom, 0).color(f5, f6, f7, f4).next();
        vertexbuffer.vertex(right, bottom, 0).color(f5, f6, f7, f4).next();
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }
}
