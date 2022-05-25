package com.ineffa.thewildupgrade.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class FancyBirchFeatureConfig implements FeatureConfig {

    public final boolean hasBees;
    public final int minHeight;
    public final int maxHeight;

    public static final Codec<FancyBirchFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("has_bees").orElse(false).forGetter(config -> config.hasBees),
            Codec.INT.fieldOf("min_height").orElse(12).forGetter(config -> config.minHeight),
            Codec.INT.fieldOf("max_height").orElse(20).forGetter(config -> config.maxHeight)
    ).apply(instance, FancyBirchFeatureConfig::new));

    public FancyBirchFeatureConfig(boolean hasBees, int minHeight, int maxHeight) {
        this.hasBees = hasBees;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }
}
