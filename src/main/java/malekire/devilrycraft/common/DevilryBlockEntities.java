package malekire.devilrycraft.common;

import malekire.devilrycraft.objects.blockentities.*;
import malekire.devilrycraft.objects.blockentities.StrideBlocks.LavaStrideBlockEntity;
import malekire.devilrycraft.objects.blockentities.StrideBlocks.WaterStrideBlockEntity;
import malekire.devilrycraft.objects.blockentities.sealhelpers.SealBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class DevilryBlockEntities {


    public static BlockEntityType<MagicalCauldronBlockEntity> MAGICAL_CAULDRON_BLOCK_ENTITY;
    public static BlockEntityType<PortableHoleBlockEntity> PORTABLE_HOLE_BLOCK_ENTITY;
    public static BlockEntityType<BasicInfuserBlockEntity> BASIC_INFUSER_BLOCK_ENTITY;
    public static BlockEntityType<VisPipeBlockEntity> PIPE_BLOCK_ENTITY;
    public static BlockEntityType<BoreBlockEntity> BORE_BLOCK_ENTITY;
    public static BlockEntityType<SealBlockEntity> SEAL_BLOCK_ENTITY;
    public static BlockEntityType<WaterStrideBlockEntity> WATER_STRIDE_BLOCK_ENTITY;
    public static BlockEntityType<LavaStrideBlockEntity> LAVA_STRIDE_BLOCK_ENTITY;
    public static BlockEntityType<BlackHoleBlockEntity> BLACK_HOLE_BLOCK_ENTITY;

    public static void registerBlockEntities()
    {
        MAGICAL_CAULDRON_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:magical_cauldron",
                BlockEntityType.Builder.create(MagicalCauldronBlockEntity::new, DevilryBlocks.MAGICAL_CAULDRON_BLOCK).build(null));
        PORTABLE_HOLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:portable_hole",
                BlockEntityType.Builder.create(PortableHoleBlockEntity::new, DevilryBlocks.PORTABLE_HOLE_BLOCK).build(null));
        BASIC_INFUSER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:basic_infuser",
                BlockEntityType.Builder.create(BasicInfuserBlockEntity::new, DevilryBlocks.BASIC_INFUSER).build(null));
        PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:pipe/default",
                BlockEntityType.Builder.create(VisPipeBlockEntity::new, DevilryBlocks.VIS_PIPE).build(null));
        BORE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:bore",
                BlockEntityType.Builder.create(BoreBlockEntity::new, DevilryBlocks.BORE_BLOCK).build(null));
        SEAL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:seal",
                BlockEntityType.Builder.create(SealBlockEntity::new, DevilryBlocks.SEAL_BLOCK).build(null));
        WATER_STRIDE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:water_stride",
                BlockEntityType.Builder.create(WaterStrideBlockEntity::new, DevilryBlocks.WATER_STRIDE).build(null));
        LAVA_STRIDE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:lava_stride",
                BlockEntityType.Builder.create(LavaStrideBlockEntity::new, DevilryBlocks.LAVA_STRIDE).build(null));
        BLACK_HOLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "devilry_craft:black_hole",
                BlockEntityType.Builder.create(BlackHoleBlockEntity::new, DevilryBlocks.BLACK_HOLE).build(null));
    }
}

