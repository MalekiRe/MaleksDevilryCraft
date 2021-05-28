package malekire.devilrycraft.common.generation;

import malekire.devilrycraft.generation.VisCrystalGeode;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import static malekire.devilrycraft.Devilrycraft.DevilryID;

public class DevilryStructures {
    public static StructureFeature<DefaultFeatureConfig> VIS_CRYSTAL_GEODE = new VisCrystalGeode(DefaultFeatureConfig.CODEC);

    public static void setupAndRegisterStructureFeatures() {
        FabricStructureBuilder.create(DevilryID("vis_crystal_geode"), VIS_CRYSTAL_GEODE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(10,5,2141370128)).superflatFeature(VIS_CRYSTAL_GEODE.configure(FeatureConfig.DEFAULT)).adjustsSurface().register();//arg fuq this is fucking hard - gamma/null

    }
}
