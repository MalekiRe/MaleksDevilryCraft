package malekire.devilrycraft.mixins;


import malekire.devilrycraft.common.DevilryArmorItems;
import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.common.DevilryWeaponItems;
import malekire.devilrycraft.common.generation.DevilryTreeGeneration;
import malekire.devilrycraft.common.generation.GenerationAbstractBase;
import malekire.devilrycraft.generation.tree_generation.SilverwoodTreeGeneration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.Set;

@Mixin(Feature.class)
public class FeatureMixin {
//    @Unique
//    private static boolean isSilverwood = false;
//
//    @Inject(method = "generate(Lnet/minecraft/world/ModifiableTestableWorld;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Ljava/util/Set;Ljava/util/Set;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Z",
//            at = @At("HEAD"))
//    private void setIsSilverwood(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BlockBox box, TreeFeatureConfig config, CallbackInfoReturnable<Boolean> cir) {
//        isSilverwood = config.trunkProvider instanceof SilverwoodTreeGeneration;
//    }
//    @Inject(method = "isDirtOrGrass", at = @At("HEAD"), cancellable = true)
//    private static void IsSilverwoodTreeGen(TestableWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
//
//    }
    @Inject(method = "isSoil(Lnet/minecraft/block/Block;)Z", at = @At("TAIL"), cancellable = true)
    private static void isSoilMixin(BlockState blockState, World world, BlockPos position, CallbackInfoReturnable<Boolean> cir) {
        if (blockState == DevilryBlocks.SILVER_MOSS.getDefaultState()) {
            cir.setReturnValue(true);
        }
    }
}
