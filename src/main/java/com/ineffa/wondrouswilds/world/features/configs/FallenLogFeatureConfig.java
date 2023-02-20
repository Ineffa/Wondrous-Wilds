package com.ineffa.wondrouswilds.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class FallenLogFeatureConfig implements FeatureConfig {

    public final BlockStateProvider logProvider, stumpProvider;
    public final int minLength, maxLength;

    public static final Codec<FallenLogFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.TYPE_CODEC.fieldOf("log_provider").forGetter(config -> config.logProvider),
            BlockStateProvider.TYPE_CODEC.fieldOf("stump_provider").forGetter(config -> config.stumpProvider),
            Codec.INT.fieldOf("min_length").orElse(3).forGetter(config -> config.minLength),
            Codec.INT.fieldOf("max_length").orElse(8).forGetter(config -> config.maxLength)
    ).apply(instance, FallenLogFeatureConfig::new));

    public FallenLogFeatureConfig(BlockStateProvider logProvider, BlockStateProvider stumpProvider, int minLength, int maxLength) {
        this.logProvider = logProvider;
        this.stumpProvider = stumpProvider;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }
}
