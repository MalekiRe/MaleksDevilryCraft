package malekire.devilrycraft.util.render;



import malekire.devilrycraft.objects.blockentities.blockentityrenderers.AutomotionRenderLayers;
import malekire.devilrycraft.vis_system.VisTaint;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class DRenderUtil {
    public static Vec3d interpolatePositionsThroughTime(Vec3d originPos, Vec3d destPos, float timeValue) {
        return originPos.add(destPos.multiply(timeValue));
    }
    public static void renderTexturedFace(Direction direction, float x1, float y1, float z1, float x2, float y2, float z2, VertexConsumerProvider vertexConsumerProvider, MatrixStack matrices, Identifier textureID, int light) {
        VertexConsumer consumer = vertexConsumerProvider.getBuffer(AutomotionRenderLayers.TRANSLUCENT_TEXTURED);
        Matrix4f matrix4f = matrices.peek().getModel();
        SpriteIdentifier id = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, textureID);
        Sprite icon = id.getSprite();
        // MinecraftClient.getInstance().getTextureManager().bindTexture(id.getTextureId());
        //Sprite icon = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(new Identifier("dirt"));
        float size = 1F;

        int c = 30;
        //noinspection UnnecessaryLocalVariable
        int r = c, g = c, b = c;
        int a = 225;
        int NSD = 0;
        int EWD = 0;
        int BD = 0;
        float x = 0, y = 0, z = 0;
        //System.out.println(icon);

        float minX = x1, minY = y1, minZ = z1;
        float maxX = x2, maxY = y2, maxZ = z2;
        float minU = icon.getMinU();
        float maxU = icon.getMaxU();
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();

        matrices.push();
        switch (direction) {
            case DOWN:
                consumer.vertex(matrix4f, maxX, minY, maxZ).color(r + BD, g + BD, b + BD, a).light(light).texture(minU, maxV).normal(0, 0, 0).next();
                consumer.vertex(matrix4f, minX, minY, maxZ).color(r + BD, g + BD, b + BD, a).light(light).texture(maxU, maxV).normal(0, 0, 0).next();
                consumer.vertex(matrix4f, minX, minY, minZ).color(r + BD, g + BD, b + BD, a).light(light).texture(maxU, minV).normal(0, 0, 0).next();
                consumer.vertex(matrix4f, maxX, minY, minZ).color(r + BD, g + BD, b + BD, a).light(light).texture(minU, minV).normal(0, 0, 0).next();
                break;

            case UP:
                // Top
                consumer.vertex(matrix4f, minX, maxY, maxZ).color(c, c, c, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, maxX, maxY, maxZ).color(c, c, c, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, maxX, maxY, minZ).color(c, c, c, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, minX, maxY, minZ).color(c, c, c, a).light(light).texture(minU, minV).next();
                break;

            case NORTH:
                // North
                consumer.vertex(matrix4f, maxX, minY, minZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, minX, minY, minZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, minX, maxY, minZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, maxX, maxY, minZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(minU, minV).next();
                break;

            case SOUTH:
                // South
                consumer.vertex(matrix4f, minX, minY, maxZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, maxX, minY, maxZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, maxX, maxY, maxZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, minX, maxY, maxZ).color(c + NSD, c + NSD, c + NSD, a).light(light).texture(minU, minV).next();
                break;

            case EAST:
                // East
                consumer.vertex(matrix4f, maxX, minY, maxZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, maxX, minY, minZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, maxX, maxY, minZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, maxX, maxY, maxZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(minU, minV).next();
                break;

            case WEST:
                // West
                consumer.vertex(matrix4f, minX, minY, minZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, minX, minY, maxZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, minX, maxY, maxZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, minX, maxY, minZ).color(c + EWD, c + EWD, c + EWD, a).light(light).texture(minU, minV).next();
                break;


        }

        matrices.pop();
    }

    public static void renderColoredTexturedFace(Direction direction, int r, int g, int b, int a, float x1, float y1, float z1, float x2, float y2, float z2, VertexConsumerProvider vertexConsumerProvider, MatrixStack matrices, Identifier textureID, int light) {
        VertexConsumer consumer = vertexConsumerProvider.getBuffer(AutomotionRenderLayers.TRANSLUCENT_TEXTURED_COLORED);
        Matrix4f matrix4f = matrices.peek().getModel();
        SpriteIdentifier id = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, textureID);
        Sprite icon = id.getSprite();
        // MinecraftClient.getInstance().getTextureManager().bindTexture(id.getTextureId());
        //Sprite icon = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(new Identifier("dirt"));
        float size = 1F;

        int c = 30;
        //noinspection UnnecessaryLocalVariable
        int NSD = 0;
        int EWD = 0;
        int BD = 0;
        float x = 0, y = 0, z = 0;
        //System.out.println(icon);

        float minX = x1, minY = y1, minZ = z1;
        float maxX = x2, maxY = y2, maxZ = z2;
        float minU = icon.getMinU();
        float maxU = icon.getMaxU();
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();

        matrices.push();
        switch (direction) {
            case DOWN:
                consumer.vertex(matrix4f, maxX, minY, maxZ).color(r + BD, g + BD, b + BD, a).light(light).texture(minU, maxV).normal(0, 0, 0).next();
                consumer.vertex(matrix4f, minX, minY, maxZ).color(r + BD, g + BD, b + BD, a).light(light).texture(maxU, maxV).normal(0, 0, 0).next();
                consumer.vertex(matrix4f, minX, minY, minZ).color(r + BD, g + BD, b + BD, a).light(light).texture(maxU, minV).normal(0, 0, 0).next();
                consumer.vertex(matrix4f, maxX, minY, minZ).color(r + BD, g + BD, b + BD, a).light(light).texture(minU, minV).normal(0, 0, 0).next();
                break;

            case UP:
                // Top
                consumer.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a).light(light).texture(minU, minV).next();
                break;

            case NORTH:
                // North
                consumer.vertex(matrix4f, maxX, minY, minZ).color(r, g + NSD, b + NSD, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, minX, minY, minZ).color(r, g + NSD, b + NSD, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, minX, maxY, minZ).color(r, g + NSD, b + NSD, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, maxX, maxY, minZ).color(r, g + NSD, b + NSD, a).light(light).texture(minU, minV).next();
                break;

            case SOUTH:
                // South
                consumer.vertex(matrix4f, minX, minY, maxZ).color(r + NSD, g + NSD, b + NSD, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, maxX, minY, maxZ).color(r + NSD, g + NSD, b + NSD, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, maxX, maxY, maxZ).color(r + NSD, g + NSD, b + NSD, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, minX, maxY, maxZ).color(r + NSD, g + NSD, b + NSD, a).light(light).texture(minU, minV).next();
                break;

            case EAST:
                // East
                consumer.vertex(matrix4f, maxX, minY, maxZ).color(r + EWD, g + EWD, b + EWD, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, maxX, minY, minZ).color(r + EWD, g + EWD, b + EWD, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, maxX, maxY, minZ).color(r + EWD, g + EWD, b + EWD, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, maxX, maxY, maxZ).color(r + EWD, g + EWD, b + EWD, a).light(light).texture(minU, minV).next();
                break;

            case WEST:
                // West
                consumer.vertex(matrix4f, minX, minY, minZ).color(r + EWD, g + EWD, b + EWD, a).light(light).texture(minU, maxV).next();
                consumer.vertex(matrix4f, minX, minY, maxZ).color(r + EWD, g + EWD, b + EWD, a).light(light).texture(maxU, maxV).next();
                consumer.vertex(matrix4f, minX, maxY, maxZ).color(r + EWD, g + EWD, b + EWD, a).light(light).texture(maxU, minV).next();
                consumer.vertex(matrix4f, minX, maxY, minZ).color(r + EWD, g + EWD, b + EWD, a).light(light).texture(minU, minV).next();
                break;


        }

        matrices.pop();
    }

    public static void renderLine(Direction direction, int r, int g, int b, int a, float x1, float y1, float z1, float x2, float y2, float z2, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
        matrices.push();
        VertexConsumer consumer = vertexConsumerProvider.getBuffer(AutomotionRenderLayers.TRANSLUCENT);
        Matrix4f matrix4f = matrices.peek().getModel();
        switch (direction) {
            case DOWN:
                consumer.vertex(matrix4f, x2, y1, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x1, y1, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x1, y1, z1).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x2, y1, z1).color(r, g, b, a).light(light).next();
                break;

            case UP:
                // Top
                consumer.vertex(matrix4f, x1, y2, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x2, y2, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x2, y2, z1).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x1, y2, z1).color(r, g, b, a).light(light).next();
                break;

            case NORTH:
                // North
                consumer.vertex(matrix4f, x2, y1, z1).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x1, y1, z1).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x1, y2, z1).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x2, y2, z1).color(r, g, b, a).light(light).next();
                break;

            case SOUTH:
                // South
                consumer.vertex(matrix4f, x1, y1, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x2, y1, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x2, y2, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x1, y2, z2).color(r, g, b, a).light(light).next();
                break;

            case EAST:
                // East
                consumer.vertex(matrix4f, x2, y1, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x2, y1, z1).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x2, y2, z1).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x2, y2, z2).color(r, g, b, a).light(light).next();
                break;

            case WEST:
                // West
                consumer.vertex(matrix4f, x1, y1, z1).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x1, y1, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x1, y2, z2).color(r, g, b, a).light(light).next();
                consumer.vertex(matrix4f, x1, y2, z1).color(r, g, b, a).light(light).next();
                break;


        }

        matrices.pop();
    }

    public static void renderCube(int r, int g, int b, int a, float x1, float y1, float z1, float x2, float y2, float z2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        for (Direction direction : Direction.values()) {
            renderLine(direction, r, g, b, a, x1, y1, z1, x2, y2, z2, matrixStack, vertexConsumerProvider, light);
        }
    }

    public static void renderCube(Identifier identifier, float x1, float y1, float z1, float x2, float y2, float z2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        for (Direction direction : Direction.values()) {
            renderTexturedFace(direction, x1, y1, z1, x2, y2, z2, vertexConsumerProvider, matrixStack, identifier, light);
        }
    }

    public static void renderCube(Identifier identifier, int r, int g, int b, int a, float x1, float y1, float z1, float x2, float y2, float z2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        for (Direction direction : Direction.values()) {
            renderColoredTexturedFace(direction, r, g, b, a, x1, y1, z1, x2, y2, z2, vertexConsumerProvider, matrixStack, identifier, light);
        }
    }
}

//    @Nullable
//    public static void getLightningPositions(float x1, float y1, float z1, float x2, float y2, float z2, float displace, float curDetail, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, VisTaint dummy, ArrayList<Vec3f> array)
//    {
//        if(displace >= curDetail){
//            float mid_x = (x2+x1)/2;
//            float mid_y = (y2+y1);
//            float mid_z = (z2+z1)/2;
//            mid_x += (Math.random()-.5)*0.3;
//            // mid_y += (Math.random()-.5)*0.001;
//            mid_z += (Math.random()-.5)*0.3;
//            getLightningPositions(x1,y1, z1, mid_x, mid_y, mid_z,displace/2, curDetail+1, matrixStack, vertexConsumerProvider, light, dummy, array);
//            getLightningPositions(x2,y2, z2, mid_x,mid_y, mid_z,displace/2, curDetail+1, matrixStack, vertexConsumerProvider, light, dummy, array);
//        } else
//        {
//            array.add(new Vec3f(x1, y1, z1));
//            array.add(new Vec3f(x2, y2, z1));
//            /*
//            renderCube((int) (dummy.visLevel*3), 40, 255, 255, x1, y1, z1, x2, y2, z2, matrixStack, vertexConsumerProvider, light);
//            dummy.visLevel = dummy.visLevel+20;
//             */
//        }
//
//    }
//
//}