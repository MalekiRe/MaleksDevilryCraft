package malekire.devilrycraft.common;

import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public class FeatureGroup {
    public ConfiguredFeature configuredFeature;
    public Feature feature;
    public Identifier name;
    public FeatureGroup(ConfiguredFeature configuredFeature, Feature feature, Identifier name)
    {
        this.configuredFeature = configuredFeature;
        this.feature = feature;
        this.name = name;
    }
}
