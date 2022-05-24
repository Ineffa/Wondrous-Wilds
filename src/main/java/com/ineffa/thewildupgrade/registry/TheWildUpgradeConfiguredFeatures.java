package com.ineffa.thewildupgrade.registry;

import com.ineffa.thewildupgrade.TheWildUpgrade;
import com.ineffa.thewildupgrade.mixin.TreeConfiguredFeaturesInvoker;
import com.ineffa.thewildupgrade.world.features.configs.FancyBirchFeatureConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

public class TheWildUpgradeConfiguredFeatures {

    public static final RegistryEntry<ConfiguredFeature<FancyBirchFeatureConfig, ?>> FANCY_BIRCH = register("fancy_birch", TheWildUpgradeFeatures.FANCY_BIRCH, new FancyBirchFeatureConfig(false));
    public static final RegistryEntry<ConfiguredFeature<FancyBirchFeatureConfig, ?>> FANCY_BIRCH_WITH_BEES = register("fancy_birch_with_bees", TheWildUpgradeFeatures.FANCY_BIRCH, new FancyBirchFeatureConfig(true));
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> TALL_BIRCH = register("tall_birch", Feature.TREE, TreeConfiguredFeaturesInvoker.getTallBirch().build());

    private static RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> registerWithoutConfig(String name, Feature<DefaultFeatureConfig> feature) {
        return TheWildUpgradeConfiguredFeatures.register(name, feature, FeatureConfig.DEFAULT);
    }

    protected static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> register(String name, F feature, FC config) {
        return BuiltinRegistries.addCasted(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(TheWildUpgrade.MOD_ID, name).toString(), new ConfiguredFeature<FC, F>(feature, config));
    }

    public static void initialize() {}
}
