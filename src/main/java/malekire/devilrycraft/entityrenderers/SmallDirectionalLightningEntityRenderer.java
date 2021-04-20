package malekire.devilrycraft.entityrenderers;

import com.qouteall.immersive_portals.my_util.DQuaternion;
import malekire.devilrycraft.entities.SmallDirectionalLightningEntity;
import malekire.devilrycraft.util.DevilryBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.Random;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;
@Environment(EnvType.CLIENT)
public class SmallDirectionalLightningEntityRenderer extends EntityRenderer<SmallDirectionalLightningEntity> {
    public SmallDirectionalLightningEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(SmallDirectionalLightningEntity lightningEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        float[] fs = new float[8];
        float[] gs = new float[8];
        float h = 0.0F;
        float j = 0.0F;
        Random random = new Random(lightningEntity.seed);

        for (int k = 7; k >= 0; --k) {
            fs[k] = h;
            gs[k] = j;
            h += (float) (random.nextInt(11) - 5);
            j += (float) (random.nextInt(11) - 5);
        }

        matrixStack.scale(.008F, 0.0006F, .008F);
        matrixStack.translate(random.nextDouble(), random.nextDouble() ,random.nextDouble());
        matrixStack.multiply(DQuaternion.rotationByDegrees(new Vec3d(0, 0, 1), random.nextInt(360)).toMcQuaternion());
        matrixStack.multiply(DQuaternion.rotationByDegrees(new Vec3d(1, 0, 0), random.nextInt(360)).toMcQuaternion());
        matrixStack.multiply(DQuaternion.rotationByDegrees(new Vec3d(0, 1, 0), random.nextInt(360)).toMcQuaternion());
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getLightning());
        Matrix4f matrix4f = matrixStack.peek().getModel();



        for (int l = 0; l < 4; ++l) {
            Random random2 = new Random(lightningEntity.seed);

            for (int m = 0; m < 3; ++m) {
                int n = 7;
                int o = 0;
                if (m > 0) {
                    n = 7 - m;
                }

                if (m > 0) {
                    o = n - 2;
                }

                float p = fs[n] - h;
                float q = gs[n] - j;

                for (int r = n; r >= o; --r) {
                    float s = p;
                    float t = q;
                    if (m == 0) {
                        p += (float) (random2.nextInt(11) - 5);
                        q += (float) (random2.nextInt(11) - 5);
                    } else {
                        p += (float) (random2.nextInt(31) - 15);
                        q += (float) (random2.nextInt(31) - 15);
                    }

                    float u = 0.5F;
                    float v = 0.45F;
                    float w = 0.45F;
                    float x = 0.5F;
                    float y = 0.1F + (float) l * 0.2F;
                    if (m == 0) {
                        y = (float) ((double) y * ((double) r * 0.1D + 1.0D));
                    }


                    float z = 0.1F + (float) l * 0.2F;
                    if (m == 0) {
                        z *= (float) (r - 1) * 0.1F + 1.0F;
                    }

                    //t = 10F; //T tapers
                    //y tapers
                    //z tapers

                    method_23183(matrix4f, vertexConsumer, p, q, r, s, t, 0.45F, 0.45F, 0.5F, y, z, false, false, true, false);
                    method_23183(matrix4f, vertexConsumer, p, q, r, s, t, 0.45F, 0.45F, 0.5F, y, z, true, false, true, true);
                    method_23183(matrix4f, vertexConsumer, p, q, r, s, t, 0.45F, 0.45F, 0.5F, y, z, true, true, false, true);
                    method_23183(matrix4f, vertexConsumer, p, q, r, s, t, 0.45F, 0.45F, 0.5F, y, z, false, true, false, false);


                }
            }
        }

    }

    private static void method_23183(Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, int i, float h, float j, float k, float l, float m, float n, float o, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
//        vertexConsumer.vertex(matrix4f, f + (bl ? o : -o), (float) (i *s 16), g + (bl2 ? o : -o)).color(k, l, m, 0.3F).next();
//        vertexConsumer.vertex(matrix4f, h + (bl ? n : -n), (float) ((i + 1) * 16), j + (bl2 ? n : -n)).color(k, l, m, 0.3F).next();
//        vertexConsumer.vertex(matrix4f, h + (bl3 ? n : -n), (float) ((i + 1) * 16), j + (bl4 ? n : -n)).color(k, l, m, 0.3F).next();
//        vertexConsumer.vertex(matrix4f, f + (bl3 ? o : -o), (float) (i * 16), g + (bl4 ? o : -o)).color(k, l, m, 0.3F).next();
       diff(matrix4f, vertexConsumer, f + (bl ? o : -o), (float) (i * 16), g + (bl2 ? o : -o), k, l, m);
        diff(matrix4f, vertexConsumer, h + (bl ? n : -n), (float) ((i + 1) * 16), j + (bl2 ? n : -n), k, l, m);
        diff(matrix4f, vertexConsumer, h + (bl3 ? n : -n), (float) ((i + 1) * 16), j + (bl4 ? n : -n), k, l, m);
        diff(matrix4f, vertexConsumer, f + (bl3 ? o : -o), (float) (i * 16), g + (bl4 ? o : -o), k, l, m);
    }
    private static void diff(Matrix4f matrix4f, VertexConsumer vertexConsumer, float x, float y, float z, float k, float l, float m)
    {

        vertexConsumer.vertex(matrix4f, z+30, x, y).color(k, l, m, 0.3F).next();
    }
    private static void renderLine(Vec3d start, Vec3d end)
    {
        GL11.glVertex3d(start.x, start.y, start.z);
        GL11.glVertex3d(end.x, end.y, end.z);
    }
    @Override
    public Identifier getTexture(SmallDirectionalLightningEntity lightningEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}