package malekire.devilrycraft.blockentityrenderers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
@Environment(EnvType.CLIENT)
public class CustomFluidRenderer {
    private final Sprite[] lavaSprites = new Sprite[2];

    private final Sprite[] waterSprites = new Sprite[2];
    private Sprite waterOverlaySprite;

    protected void onResourceReload() {
        this.lavaSprites[0] = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.LAVA.getDefaultState()).getSprite();
        this.lavaSprites[1] = ModelLoader.LAVA_FLOW.getSprite();
        this.waterSprites[0] = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.WATER.getDefaultState()).getSprite();
        this.waterSprites[1] = ModelLoader.WATER_FLOW.getSprite();
        this.waterOverlaySprite = ModelLoader.WATER_OVERLAY.getSprite();
    }

    private static boolean isSameFluid(BlockView world, BlockPos pos, Direction side, FluidState state) {
        BlockPos blockPos = pos.offset(side);
        FluidState fluidState = world.getFluidState(blockPos);
        return fluidState.getFluid().matchesType(state.getFluid());
    }

    private static boolean method_29710(BlockView blockView, Direction direction, float f, BlockPos blockPos, BlockState blockState) {
        if (blockState.isOpaque()) {
            VoxelShape voxelShape = VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, (double) f, 1.0D);
            VoxelShape voxelShape2 = blockState.getCullingShape(blockView, blockPos);
            return VoxelShapes.isSideCovered(voxelShape, voxelShape2, direction);
        } else {
            return false;
        }
    }

    private static boolean isSideCovered(BlockView world, BlockPos pos, Direction direction, float maxDeviation) {
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        return method_29710(world, direction, maxDeviation, blockPos, blockState);
    }

    private static boolean method_29709(BlockView blockView, BlockPos blockPos, BlockState blockState, Direction direction) {
        return method_29710(blockView, direction.getOpposite(), 1.0F, blockPos, blockState);
    }

    public static boolean method_29708(BlockRenderView blockRenderView, BlockPos blockPos, FluidState fluidState, BlockState blockState, Direction direction) {
        return !method_29709(blockRenderView, blockPos, blockState, direction) && !isSameFluid(blockRenderView, blockPos, direction, fluidState);
    }

    public boolean render(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, FluidState state) {
        boolean bl = state.isIn(FluidTags.LAVA);
        Sprite[] sprites = bl ? this.lavaSprites : this.waterSprites;
        BlockState blockState = world.getBlockState(pos);
        int i = bl ? 16777215 : BiomeColors.getWaterColor(world, pos);
        float f = (float)(i >> 16 & 255) / 255.0F;
        float g = (float)(i >> 8 & 255) / 255.0F;
        float h = (float)(i & 255) / 255.0F;
        boolean bl2 = !isSameFluid(world, pos, Direction.UP, state);
        boolean bl3 = method_29708(world, pos, state, blockState, Direction.DOWN) && !isSideCovered(world, pos, Direction.DOWN, 0.8888889F);
        boolean bl4 = method_29708(world, pos, state, blockState, Direction.NORTH);
        boolean bl5 = method_29708(world, pos, state, blockState, Direction.SOUTH);
        boolean bl6 = method_29708(world, pos, state, blockState, Direction.WEST);
        boolean bl7 = method_29708(world, pos, state, blockState, Direction.EAST);
        if (false) {
            return false;
        } else {
            boolean bl8 = false;
            float j = world.getBrightness(Direction.DOWN, true);
            float k = world.getBrightness(Direction.UP, true);
            float l = world.getBrightness(Direction.NORTH, true);
            float m = world.getBrightness(Direction.WEST, true);


            float n = this.getNorthWestCornerFluidHeight(world, pos, state.getFluid());
            float o = this.getNorthWestCornerFluidHeight(world, pos.south(), state.getFluid());
            float p = this.getNorthWestCornerFluidHeight(world, pos.east().south(), state.getFluid());
            float q = this.getNorthWestCornerFluidHeight(world, pos.east(), state.getFluid());

            System.out.println(n);

            double d = (double)(pos.getX() & 15);
            double e = (double)(pos.getY() & 15);
            double r = (double)(pos.getZ() & 15);
            float s = 0.001F;
            float t = bl3 ? 0.001F : 0.0F;
            float ag;
            float bz;
            float ca;
            float am;
            float aj;
            float al;
            float an;
            float cf;
            float cg;
            float ch;
            if (bl2 && !isSideCovered(world, pos, Direction.UP, Math.min(Math.min(n, o), Math.min(p, q)))) {
                bl8 = true;
                n -= 0.001F;
                o -= 0.001F;
                p -= 0.001F;
                q -= 0.001F;
                Vec3d vec3d = state.getVelocity(world, pos);
                float ah;
                Sprite sprite2;
                float ap;
                float aq;
                float ar;
                float as;
                if (vec3d.x == 0.0D && vec3d.z == 0.0D) {
                    sprite2 = sprites[0];
                    ag = sprite2.getFrameU(0.0D);
                    ah = sprite2.getFrameV(0.0D);
                    bz = ag;
                    aj = sprite2.getFrameV(16.0D);
                    ca = sprite2.getFrameU(16.0D);
                    al = aj;
                    am = ca;
                    an = ah;
                } else {
                    sprite2 = sprites[1];
                    ap = (float)MathHelper.atan2(vec3d.z, vec3d.x) - 1.5707964F;
                    aq = MathHelper.sin(ap) * 0.25F;
                    ar = MathHelper.cos(ap) * 0.25F;
                    as = 8.0F;
                    ag = sprite2.getFrameU((double)(8.0F + (-ar - aq) * 16.0F));
                    ah = sprite2.getFrameV((double)(8.0F + (-ar + aq) * 16.0F));
                    bz = sprite2.getFrameU((double)(8.0F + (-ar + aq) * 16.0F));
                    aj = sprite2.getFrameV((double)(8.0F + (ar + aq) * 16.0F));
                    ca = sprite2.getFrameU((double)(8.0F + (ar + aq) * 16.0F));
                    al = sprite2.getFrameV((double)(8.0F + (ar - aq) * 16.0F));
                    am = sprite2.getFrameU((double)(8.0F + (ar - aq) * 16.0F));
                    an = sprite2.getFrameV((double)(8.0F + (-ar - aq) * 16.0F));
                }

                float ao = (ag + bz + ca + am) / 4.0F;
                ap = (ah + aj + al + an) / 4.0F;
                aq = (float)sprites[0].getWidth() / (sprites[0].getMaxU() - sprites[0].getMinU());
                ar = (float)sprites[0].getHeight() / (sprites[0].getMaxV() - sprites[0].getMinV());
                as = 4.0F / Math.max(ar, aq);
                ag = MathHelper.lerp(as, ag, ao);
                bz = MathHelper.lerp(as, bz, ao);
                ca = MathHelper.lerp(as, ca, ao);
                am = MathHelper.lerp(as, am, ao);
                ah = MathHelper.lerp(as, ah, ap);
                aj = MathHelper.lerp(as, aj, ap);
                al = MathHelper.lerp(as, al, ap);
                an = MathHelper.lerp(as, an, ap);
                int at = this.getLight(world, pos);
                cf = k * f;
                cg = k * g;
                ch = k * h;
                this.vertex(vertexConsumer, d + 0.0D, e + (double)n, r + 0.0D, cf, cg, ch, ag, ah, at);
                this.vertex(vertexConsumer, d + 0.0D, e + (double)o, r + 1.0D, cf, cg, ch, bz, aj, at);
                this.vertex(vertexConsumer, d + 1.0D, e + (double)p, r + 1.0D, cf, cg, ch, ca, al, at);
                this.vertex(vertexConsumer, d + 1.0D, e + (double)q, r + 0.0D, cf, cg, ch, am, an, at);
                if (state.method_15756(world, pos.up())) {
                    this.vertex(vertexConsumer, d + 0.0D, e + (double)n, r + 0.0D, cf, cg, ch, ag, ah, at);
                    this.vertex(vertexConsumer, d + 1.0D, e + (double)q, r + 0.0D, cf, cg, ch, am, an, at);
                    this.vertex(vertexConsumer, d + 1.0D, e + (double)p, r + 1.0D, cf, cg, ch, ca, al, at);
                    this.vertex(vertexConsumer, d + 0.0D, e + (double)o, r + 1.0D, cf, cg, ch, bz, aj, at);
                }
            }

            if (bl3) {
                ag = sprites[0].getMinU();
                bz = sprites[0].getMaxU();
                ca = sprites[0].getMinV();
                am = sprites[0].getMaxV();
                int bb = this.getLight(world, pos.down());
                aj = j * f;
                al = j * g;
                an = j * h;
                this.vertex(vertexConsumer, d, e + (double)t, r + 1.0D, aj, al, an, ag, am, bb);
                this.vertex(vertexConsumer, d, e + (double)t, r, aj, al, an, ag, ca, bb);
                this.vertex(vertexConsumer, d + 1.0D, e + (double)t, r, aj, al, an, bz, ca, bb);
                this.vertex(vertexConsumer, d + 1.0D, e + (double)t, r + 1.0D, aj, al, an, bz, am, bb);
                bl8 = true;
            }

            for(int bf = 0; bf < 4; ++bf) {
                double cb;
                double cd;
                double cc;
                double ce;
                Direction direction3;
                boolean bl12;
                if (bf == 0) {
                    bz = n;
                    ca = q;
                    cb = d;
                    cc = d + 1.0D;
                    cd = r + 0.0010000000474974513D;
                    ce = r + 0.0010000000474974513D;
                    direction3 = Direction.NORTH;
                    bl12 = bl4;
                } else if (bf == 1) {
                    bz = p;
                    ca = o;
                    cb = d + 1.0D;
                    cc = d;
                    cd = r + 1.0D - 0.0010000000474974513D;
                    ce = r + 1.0D - 0.0010000000474974513D;
                    direction3 = Direction.SOUTH;
                    bl12 = bl5;
                } else if (bf == 2) {
                    bz = o;
                    ca = n;
                    cb = d + 0.0010000000474974513D;
                    cc = d + 0.0010000000474974513D;
                    cd = r + 1.0D;
                    ce = r;
                    direction3 = Direction.WEST;
                    bl12 = bl6;
                } else {
                    bz = q;
                    ca = p;
                    cb = d + 1.0D - 0.0010000000474974513D;
                    cc = d + 1.0D - 0.0010000000474974513D;
                    cd = r;
                    ce = r + 1.0D;
                    direction3 = Direction.EAST;
                    bl12 = bl7;
                }

                if (bl12 && !isSideCovered(world, pos, direction3, Math.max(bz, ca))) {
                    bl8 = true;
                    BlockPos blockPos = pos.offset(direction3);
                    Sprite sprite3 = sprites[1];
                    if (!bl) {
                        Block block = world.getBlockState(blockPos).getBlock();
                        if (block instanceof TransparentBlock || block instanceof LeavesBlock) {
                            sprite3 = this.waterOverlaySprite;
                        }
                    }

                    cf = sprite3.getFrameU(0.0D);
                    cg = sprite3.getFrameU(8.0D);
                    ch = sprite3.getFrameV((double)((1.0F - bz) * 16.0F * 0.5F));
                    float ci = sprite3.getFrameV((double)((1.0F - ca) * 16.0F * 0.5F));
                    float cj = sprite3.getFrameV(8.0D);
                    int ck = this.getLight(world, blockPos);
                    float cl = bf < 2 ? l : m;
                    float cm = k * cl * f;
                    float cn = k * cl * g;
                    float co = k * cl * h;
                    this.vertex(vertexConsumer, cb, e + (double)bz, cd, cm, cn, co, cf, ch, ck);
                    this.vertex(vertexConsumer, cc, e + (double)ca, ce, cm, cn, co, cg, ci, ck);
                    this.vertex(vertexConsumer, cc, e + (double)t, ce, cm, cn, co, cg, cj, ck);
                    this.vertex(vertexConsumer, cb, e + (double)t, cd, cm, cn, co, cf, cj, ck);
                    if (sprite3 != this.waterOverlaySprite) {
                        this.vertex(vertexConsumer, cb, e + (double)t, cd, cm, cn, co, cf, cj, ck);
                        this.vertex(vertexConsumer, cc, e + (double)t, ce, cm, cn, co, cg, cj, ck);
                        this.vertex(vertexConsumer, cc, e + (double)ca, ce, cm, cn, co, cg, ci, ck);
                        this.vertex(vertexConsumer, cb, e + (double)bz, cd, cm, cn, co, cf, ch, ck);
                    }
                }
            }

            return bl8;
        }

    }

    public void vertex(VertexConsumer vertexConsumer, double x, double y, double z, float red, float green, float blue, float u, float v, int light) {
        vertexConsumer.vertex(x+2F, y+2F, z+2F).color(red, green, blue, 1.0F).texture(u, v).light(light).normal(0.0F, 1.0F, 0.0F).next();
        //System.out.println("doing vertex render");
    }

    private int getLight(BlockRenderView world, BlockPos pos) {
        int i = WorldRenderer.getLightmapCoordinates(world, pos);
        int j = WorldRenderer.getLightmapCoordinates(world, pos.up());
        int k = i & 255;
        int l = j & 255;
        int m = i >> 16 & 255;
        int n = j >> 16 & 255;
        return (k > l ? k : l) | (m > n ? m : n) << 16;
    }

    private float getNorthWestCornerFluidHeight(BlockView world, BlockPos pos, Fluid fluid) {
        int i = 0;
        float f = 0.0F;

        for (int j = 0; j < 4; ++j) {
            BlockPos blockPos = pos.add(-(j & 1), 0, -(j >> 1 & 1));
            if (world.getFluidState(blockPos.up()).getFluid().matchesType(fluid)) {
                return 1.0F;
            }

            FluidState fluidState = world.getFluidState(blockPos);
            if (fluidState.getFluid().matchesType(fluid)) {
                float g = fluidState.getHeight(world, blockPos);
                if (g >= 0.8F) {
                    f += g * 10.0F;
                    i += 10;
                } else {
                    f += g;
                    ++i;
                }
            } else if (!world.getBlockState(blockPos).getMaterial().isSolid()) {
                ++i;
            }
        }

        return f / (float) i;
    }
}