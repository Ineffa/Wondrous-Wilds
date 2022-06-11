package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.mixin.TreeConfiguredFeaturesInvoker;
import com.ineffa.wondrouswilds.world.features.FallenLogFeature;
import com.ineffa.wondrouswilds.world.features.FancyBirchFeature;
import com.ineffa.wondrouswilds.world.features.VioletPatchFeature;
import com.ineffa.wondrouswilds.world.features.configs.FallenLogFeatureConfig;
import com.ineffa.wondrouswilds.world.features.configs.FancyBirchFeatureConfig;
import com.ineffa.wondrouswilds.world.features.configs.VioletPatchFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class WondrousWildsFeatures {

    public static final Feature<FancyBirchFeatureConfig> FANCY_BIRCH = new FancyBirchFeature();
    public static final RegistryEntry<ConfiguredFeature<FancyBirchFeatureConfig, ?>> FANCY_BIRCH_CONFIGURED = registerConfigured("fancy_birch", FANCY_BIRCH, fancyBirchConfig());
    public static final RegistryEntry<PlacedFeature> FANCY_BIRCH_PLACED = registerPlaced("fancy_birch", FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
    public static final RegistryEntry<ConfiguredFeature<FancyBirchFeatureConfig, ?>> FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("fancy_birch_with_bees", FANCY_BIRCH, fancyBirchWithBeesConfig());
    public static final RegistryEntry<PlacedFeature> FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("fancy_birch_with_bees", FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<FancyBirchFeatureConfig, ?>> TALL_FANCY_BIRCH_CONFIGURED = registerConfigured("tall_fancy_birch", FANCY_BIRCH, tallFancyBirchConfig());
    public static final RegistryEntry<PlacedFeature> TALL_FANCY_BIRCH_PLACED = registerPlaced("tall_fancy_birch", TALL_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
    public static final RegistryEntry<ConfiguredFeature<FancyBirchFeatureConfig, ?>> TALL_FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("tall_fancy_birch_with_bees", FANCY_BIRCH, tallFancyBirchWithBeesConfig());
    public static final RegistryEntry<PlacedFeature> TALL_FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("tall_fancy_birch_with_bees", TALL_FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> TALL_BIRCH_CONFIGURED = registerConfigured("tall_birch", Feature.TREE, TreeConfiguredFeaturesInvoker.tallBirchConfig().build());
    public static final RegistryEntry<PlacedFeature> TALL_BIRCH_PLACED = registerPlaced("tall_birch", TALL_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> BIRCH_FOREST_TREES_CONFIGURED = registerConfigured("birch_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(TreePlacedFeatures.BIRCH_CHECKED, 0.15F), new RandomFeatureEntry(FANCY_BIRCH_WITH_BEES_PLACED, 0.015F)), FANCY_BIRCH_PLACED));
    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_TREES_PLACED = registerPlaced("birch_forest_trees", BIRCH_FOREST_TREES_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(5, 0.1F, 1), Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> OLD_GROWTH_BIRCH_FOREST_TREES_CONFIGURED = registerConfigured("old_growth_birch_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(TALL_BIRCH_PLACED, 0.15F), new RandomFeatureEntry(TALL_FANCY_BIRCH_WITH_BEES_PLACED, 0.015F)), TALL_FANCY_BIRCH_PLACED));
    public static final RegistryEntry<PlacedFeature> OLD_GROWTH_BIRCH_FOREST_TREES_PLACED = registerPlaced("old_growth_birch_forest_trees", OLD_GROWTH_BIRCH_FOREST_TREES_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(5, 0.1F, 1), Blocks.BIRCH_SAPLING));

    public static final Feature<FallenLogFeatureConfig> FALLEN_LOG = new FallenLogFeature();
    public static final RegistryEntry<ConfiguredFeature<FallenLogFeatureConfig, ?>> FALLEN_BIRCH_LOG_CONFIGURED = registerConfigured("fallen_birch_log", FALLEN_LOG, fallenBirchLogConfig());
    public static final RegistryEntry<PlacedFeature> FALLEN_BIRCH_LOG_PLACED = registerPlaced("fallen_birch_log", FALLEN_BIRCH_LOG_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(RarityFilterPlacementModifier.of(12), Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_FLOWERS = PlacedFeatures.register("birch_forest_flowers", VegetationConfiguredFeatures.FOREST_FLOWERS, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-3, 1), 0, 1)), BiomePlacementModifier.of());
    public static final Feature<VioletPatchFeatureConfig> VIOLET_PATCH = new VioletPatchFeature();
    public static final RegistryEntry<ConfiguredFeature<VioletPatchFeatureConfig, ?>> PURPLE_VIOLETS_CONFIGURED = registerConfigured("purple_violets", VIOLET_PATCH, purpleVioletPatchConfig());
    public static final RegistryEntry<ConfiguredFeature<VioletPatchFeatureConfig, ?>> PINK_VIOLETS_CONFIGURED = registerConfigured("pink_violets", VIOLET_PATCH, pinkVioletPatchConfig());
    public static final RegistryEntry<ConfiguredFeature<VioletPatchFeatureConfig, ?>> RED_VIOLETS_CONFIGURED = registerConfigured("red_violets", VIOLET_PATCH, redVioletPatchConfig());
    public static final RegistryEntry<ConfiguredFeature<VioletPatchFeatureConfig, ?>> WHITE_VIOLETS_CONFIGURED = registerConfigured("white_violets", VIOLET_PATCH, whiteVioletPatchConfig());
    public static final RegistryEntry<PlacedFeature> PURPLE_VIOLETS_PLACED = registerPlaced("purple_violets", PURPLE_VIOLETS_CONFIGURED, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> PINK_VIOLETS_PLACED = registerPlaced("pink_violets", PINK_VIOLETS_CONFIGURED, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> RED_VIOLETS_PLACED = registerPlaced("red_violets", RED_VIOLETS_CONFIGURED, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> WHITE_VIOLETS_PLACED = registerPlaced("white_violets", WHITE_VIOLETS_CONFIGURED, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_ROCK = registerPlaced("birch_forest_rock", MiscConfiguredFeatures.FOREST_ROCK, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    private static FancyBirchFeatureConfig fancyBirchConfig() {
        return new FancyBirchFeatureConfig(false, 10, 20);
    }

    private static FancyBirchFeatureConfig fancyBirchWithBeesConfig() {
        return new FancyBirchFeatureConfig(true, 10, 20);
    }

    private static FancyBirchFeatureConfig tallFancyBirchConfig() {
        return new FancyBirchFeatureConfig(false, 10, 26);
    }

    private static FancyBirchFeatureConfig tallFancyBirchWithBeesConfig() {
        return new FancyBirchFeatureConfig(true, 10, 26);
    }

    private static FallenLogFeatureConfig fallenBirchLogConfig() {
        return new FallenLogFeatureConfig(BlockStateProvider.of(WondrousWildsBlocks.HOLLOW_BIRCH_LOG), 3, 8);
    }

    private static VioletPatchFeatureConfig purpleVioletPatchConfig() {
        return new VioletPatchFeatureConfig(BlockStateProvider.of(WondrousWildsBlocks.PURPLE_VIOLET));
    }

    private static VioletPatchFeatureConfig pinkVioletPatchConfig() {
        return new VioletPatchFeatureConfig(BlockStateProvider.of(WondrousWildsBlocks.PINK_VIOLET));
    }

    private static VioletPatchFeatureConfig redVioletPatchConfig() {
        return new VioletPatchFeatureConfig(BlockStateProvider.of(WondrousWildsBlocks.RED_VIOLET));
    }

    private static VioletPatchFeatureConfig whiteVioletPatchConfig() {
        return new VioletPatchFeatureConfig(BlockStateProvider.of(WondrousWildsBlocks.WHITE_VIOLET));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> registerConfigured(String name, F feature, FC config) {
        return BuiltinRegistries.addCasted(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(WondrousWilds.MOD_ID, name).toString(), new ConfiguredFeature<FC, F>(feature, config));
    }

    private static RegistryEntry<PlacedFeature> registerPlaced(String name, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, PlacementModifier... modifiers) {
        return registerPlaced(name, registryEntry, List.of(modifiers));
    }

    private static RegistryEntry<PlacedFeature> registerPlaced(String name, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, List<PlacementModifier> modifiers) {
        String id = new Identifier(WondrousWilds.MOD_ID, name).toString();
        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, id, new PlacedFeature(RegistryEntry.upcast(registryEntry), List.copyOf(modifiers)));
    }

    public static void initialize() {
        Registry.register(Registry.FEATURE, new Identifier(WondrousWilds.MOD_ID, "fancy_birch"), FANCY_BIRCH);

        Registry.register(Registry.FEATURE, new Identifier(WondrousWilds.MOD_ID, "fallen_log"), FALLEN_LOG);

        Registry.register(Registry.FEATURE, new Identifier(WondrousWilds.MOD_ID, "violet_patch"), VIOLET_PATCH);
    }
}
