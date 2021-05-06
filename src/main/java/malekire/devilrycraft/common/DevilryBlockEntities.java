package malekire.devilrycraft.common;

import malekire.devilrycraft.objects.blockentities.BasicInfuserBlockEntity;
import malekire.devilrycraft.objects.blockentities.MagicalCauldronBlockEntity;
import malekire.devilrycraft.objects.blockentities.PortableHoleBlockEntity;
import malekire.devilrycraft.objects.blockentities.VisPipeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class DevilryBlockEntities {

    public static BlockEntityType<MagicalCauldronBlockEntity> MAGICAL_CAULDRON_BLOCK_ENTITY;
    public static BlockEntityType<PortableHoleBlockEntity> PORTABLE_HOLE_BLOCK_ENTITY;
    public static BlockEntityType<BasicInfuserBlockEntity> BASIC_INFUSER_BLOCK_ENTITY;
    public static BlockEntityType<VisPipeBlockEntity> PIPE_BLOCK_ENTITY;

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
    }
}

