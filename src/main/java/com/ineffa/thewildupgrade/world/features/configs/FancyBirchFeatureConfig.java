package com.ineffa.thewildupgrade.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class FancyBirchFeatureConfig implements FeatureConfig {

    public final boolean hasBees;

    public static final Codec<FancyBirchFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Codec.BOOL.fieldOf("has_bees").orElse(false).forGetter((config) -> config.hasBees)).apply(instance, FancyBirchFeatureConfig::new));

    public FancyBirchFeatureConfig(boolean hasBees) {
        this.hasBees = hasBees;
    }
}
