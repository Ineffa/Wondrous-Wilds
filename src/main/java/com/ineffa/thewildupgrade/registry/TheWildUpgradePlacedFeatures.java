package com.ineffa.thewildupgrade.registry;

import com.ineffa.thewildupgrade.TheWildUpgrade;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class TheWildUpgradePlacedFeatures {

    public static final RegistryEntry<PlacedFeature> FANCY_BIRCH_CHECKED = register("fancy_birch_checked", TheWildUpgradeConfiguredFeatures.FANCY_BIRCH, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
    public static final RegistryEntry<PlacedFeature> FANCY_BIRCH_WITH_BEES_CHECKED = register("fancy_birch_with_bees_checked", TheWildUpgradeConfiguredFeatures.FANCY_BIRCH_WITH_BEES, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

    private static RegistryEntry<PlacedFeature> register(String name, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, PlacementModifier... modifiers) {
        return register(name, registryEntry, List.of(modifiers));
    }

    private static RegistryEntry<PlacedFeature> register(String name, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, List<PlacementModifier> modifiers) {
        String id = new Identifier(TheWildUpgrade.MOD_ID, name).toString();
        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, id, new PlacedFeature(RegistryEntry.upcast(registryEntry), List.copyOf(modifiers)));
    }

    public static void initialize() {}
}
