package com.ineffa.thewildupgrade.registry;

import com.ineffa.thewildupgrade.TheWildUpgrade;
import com.ineffa.thewildupgrade.world.features.FancyBirchFeature;
import com.ineffa.thewildupgrade.world.features.configs.FancyBirchFeatureConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;

public class TheWildUpgradeFeatures {

    public static final Feature<FancyBirchFeatureConfig> FANCY_BIRCH = new FancyBirchFeature();

    public static void initialize() {
        Registry.register(Registry.FEATURE, new Identifier(TheWildUpgrade.MOD_ID, "fancy_birch"), FANCY_BIRCH);
    }
}
