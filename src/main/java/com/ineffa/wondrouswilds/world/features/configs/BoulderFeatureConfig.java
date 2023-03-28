package com.ineffa.wondrouswilds.world.features.configs;

import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record BoulderFeatureConfig(BlockStateProvider blockProvider, BlockPredicate replaceBlockPredicate, IntProvider startingRadius, IntProvider extensions) implements FeatureConfig {

    public static final BlockPredicate DEFAULT_BOULDER_REPLACE_PREDICATE = BlockPredicate.not(BlockPredicate.matchingBlockTag(WondrousWildsTags.BlockTags.BOULDERS_CANNOT_REPLACE));

    public static final Codec<BoulderFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.TYPE_CODEC.fieldOf("block_provider").forGetter(BoulderFeatureConfig::blockProvider),
            BlockPredicate.BASE_CODEC.fieldOf("replace_block_predicate").orElse(DEFAULT_BOULDER_REPLACE_PREDICATE).forGetter(BoulderFeatureConfig::replaceBlockPredicate),
            IntProvider.POSITIVE_CODEC.fieldOf("starting_radius").forGetter(BoulderFeatureConfig::startingRadius),
            IntProvider.NON_NEGATIVE_CODEC.fieldOf("extensions").forGetter(BoulderFeatureConfig::extensions)
    ).apply(instance, BoulderFeatureConfig::new));
}
