package com.ineffa.wondrouswilds.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class FallenLogFeatureConfig implements FeatureConfig {

    public final BlockStateProvider logProvider, stumpProvider;
    public final int minLength, maxLength, maxStumpHeight;

    public static final Codec<FallenLogFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.TYPE_CODEC.fieldOf("log_provider").forGetter(config -> config.logProvider),
            BlockStateProvider.TYPE_CODEC.fieldOf("stump_provider").forGetter(config -> config.stumpProvider),
            Codecs.POSITIVE_INT.fieldOf("min_length").forGetter(config -> config.minLength),
            Codecs.POSITIVE_INT.fieldOf("max_length").forGetter(config -> config.maxLength),
            Codecs.NONNEGATIVE_INT.fieldOf("max_stump_height").forGetter(config -> config.maxStumpHeight)
    ).apply(instance, FallenLogFeatureConfig::new));

    public FallenLogFeatureConfig(BlockStateProvider logProvider, BlockStateProvider stumpProvider, int minLength, int maxLength, int maxStumpHeight) {
        this.logProvider = logProvider;
        this.stumpProvider = stumpProvider;

        this.minLength = minLength;
        this.maxLength = maxLength;
        this.maxStumpHeight = maxStumpHeight;
    }
}
