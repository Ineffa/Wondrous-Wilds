package com.ineffa.wondrouswilds.registry;

import com.google.common.collect.ImmutableList;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.mixin.FoliagePlacerTypeInvoker;
import com.ineffa.wondrouswilds.mixin.TreeConfiguredFeaturesInvoker;
import com.ineffa.wondrouswilds.mixin.TreeDecoratorTypeInvoker;
import com.ineffa.wondrouswilds.mixin.TrunkPlacerTypeInvoker;
import com.ineffa.wondrouswilds.world.features.FallenLogFeature;
import com.ineffa.wondrouswilds.world.features.VioletPatchFeature;
import com.ineffa.wondrouswilds.world.features.configs.FallenLogFeatureConfig;
import com.ineffa.wondrouswilds.world.features.configs.VioletPatchFeatureConfig;
import com.ineffa.wondrouswilds.world.features.trees.decorators.CobwebTreeDecorator;
import com.ineffa.wondrouswilds.world.features.trees.decorators.HangingBeeNestTreeDecorator;
import com.ineffa.wondrouswilds.world.features.trees.decorators.PolyporeTreeDecorator;
import com.ineffa.wondrouswilds.world.features.trees.foliage.FancyBirchFoliagePlacer;
import com.ineffa.wondrouswilds.world.features.trees.trunks.StraightBranchingTrunkPlacer;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;

public class WondrousWildsFeatures {

    public static final class Trees {

        public static final class TrunkPlacers {
            public static final TrunkPlacerType<StraightBranchingTrunkPlacer> STRAIGHT_BRANCHING_TRUNK = TrunkPlacerTypeInvoker.registerTrunkPlacer("straight_branching_trunk_placer", StraightBranchingTrunkPlacer.CODEC);

            public static void init() {}
        }

        public static final class FoliagePlacers {
            public static final FoliagePlacerType<FancyBirchFoliagePlacer> FANCY_BIRCH = FoliagePlacerTypeInvoker.registerFoliagePlacer("fancy_birch_foliage_placer", FancyBirchFoliagePlacer.CODEC);

            public static void init() {}
        }

        public static final class Decorators {
            public static final TreeDecoratorType<HangingBeeNestTreeDecorator> HANGING_BEE_NEST_TYPE = TreeDecoratorTypeInvoker.registerTreeDecorator("hanging_bee_nest", HangingBeeNestTreeDecorator.CODEC);
            public static final TreeDecoratorType<PolyporeTreeDecorator> POLYPORE_TYPE = TreeDecoratorTypeInvoker.registerTreeDecorator("polypores", PolyporeTreeDecorator.CODEC);
            public static final TreeDecoratorType<CobwebTreeDecorator> COBWEB_TYPE = TreeDecoratorTypeInvoker.registerTreeDecorator("cobwebs", CobwebTreeDecorator.CODEC);

            public static final TreeDecorator HANGING_BEE_NEST = HangingBeeNestTreeDecorator.INSTANCE;
            public static final TreeDecorator POLYPORES = PolyporeTreeDecorator.INSTANCE;
            public static final TreeDecorator COBWEBS = CobwebTreeDecorator.INSTANCE;

            public static void init() {}
        }

        public static final class Configs {
            private static TreeFeatureConfig.Builder fancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG),
                        new StraightBranchingTrunkPlacer(10, 10, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(Blocks.BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES, Decorators.COBWEBS)).ignoreVines();
            }

            private static TreeFeatureConfig.Builder fancyBirchWithBeesConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG),
                        new StraightBranchingTrunkPlacer(10, 10, 0, 1, 3, 1, 1),
                        BlockStateProvider.of(Blocks.BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.HANGING_BEE_NEST, Decorators.POLYPORES, Decorators.COBWEBS)).ignoreVines();
            }

            private static TreeFeatureConfig.Builder yellowFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG),
                        new StraightBranchingTrunkPlacer(10, 10, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES, Decorators.COBWEBS)).ignoreVines();
            }

            private static TreeFeatureConfig.Builder yellowFancyBirchWithBeesConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG),
                        new StraightBranchingTrunkPlacer(10, 10, 0, 1, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.HANGING_BEE_NEST, Decorators.POLYPORES, Decorators.COBWEBS)).ignoreVines();
            }

            private static TreeFeatureConfig.Builder orangeFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG),
                        new StraightBranchingTrunkPlacer(10, 10, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES, Decorators.COBWEBS)).ignoreVines();
            }

            private static TreeFeatureConfig.Builder orangeFancyBirchWithBeesConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG),
                        new StraightBranchingTrunkPlacer(10, 10, 0, 1, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.HANGING_BEE_NEST, Decorators.POLYPORES, Decorators.COBWEBS)).ignoreVines();
            }

            private static TreeFeatureConfig.Builder redFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG),
                        new StraightBranchingTrunkPlacer(10, 10, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.RED_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES, Decorators.COBWEBS)).ignoreVines();
            }

            private static TreeFeatureConfig.Builder redFancyBirchWithBeesConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG),
                        new StraightBranchingTrunkPlacer(10, 10, 0, 1, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.RED_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.HANGING_BEE_NEST, Decorators.POLYPORES, Decorators.COBWEBS)).ignoreVines();
            }

            public static void init() {}
        }

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FANCY_BIRCH_CONFIGURED = registerConfigured("fancy_birch", Feature.TREE, Configs.fancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> FANCY_BIRCH_PLACED = registerPlaced("fancy_birch", FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("fancy_birch_with_bees", Feature.TREE, Configs.fancyBirchWithBeesConfig().build());
        public static final RegistryEntry<PlacedFeature> FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("fancy_birch_with_bees", FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> YELLOW_FANCY_BIRCH_CONFIGURED = registerConfigured("yellow_fancy_birch", Feature.TREE, Configs.yellowFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> YELLOW_FANCY_BIRCH_PLACED = registerPlaced("yellow_fancy_birch", YELLOW_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> YELLOW_FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("yellow_fancy_birch_with_bees", Feature.TREE, Configs.yellowFancyBirchWithBeesConfig().build());
        public static final RegistryEntry<PlacedFeature> YELLOW_FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("yellow_fancy_birch_with_bees", YELLOW_FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> ORANGE_FANCY_BIRCH_CONFIGURED = registerConfigured("orange_fancy_birch", Feature.TREE, Configs.orangeFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> ORANGE_FANCY_BIRCH_PLACED = registerPlaced("orange_fancy_birch", ORANGE_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> ORANGE_FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("orange_fancy_birch_with_bees", Feature.TREE, Configs.orangeFancyBirchWithBeesConfig().build());
        public static final RegistryEntry<PlacedFeature> ORANGE_FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("orange_fancy_birch_with_bees", ORANGE_FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> RED_FANCY_BIRCH_CONFIGURED = registerConfigured("red_fancy_birch", Feature.TREE, Configs.redFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> RED_FANCY_BIRCH_PLACED = registerPlaced("red_fancy_birch", RED_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> RED_FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("red_fancy_birch_with_bees", Feature.TREE, Configs.redFancyBirchWithBeesConfig().build());
        public static final RegistryEntry<PlacedFeature> RED_FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("red_fancy_birch_with_bees", RED_FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> TALL_BIRCH_CONFIGURED = registerConfigured("tall_birch", Feature.TREE, TreeConfiguredFeaturesInvoker.tallBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> TALL_BIRCH_PLACED = registerPlaced("tall_birch", TALL_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> BIRCH_FOREST_TREES_CONFIGURED = registerConfigured("birch_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(
                new RandomFeatureEntry(TreePlacedFeatures.BIRCH_CHECKED, 0.15F),
                new RandomFeatureEntry(FANCY_BIRCH_WITH_BEES_PLACED, 0.015F)
        ), FANCY_BIRCH_PLACED));
        public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_TREES_PLACED = registerPlaced("birch_forest_trees", BIRCH_FOREST_TREES_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(5, 0.1F, 1), Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> OLD_GROWTH_BIRCH_FOREST_TREES_CONFIGURED = registerConfigured("old_growth_birch_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(
                new RandomFeatureEntry(FANCY_BIRCH_PLACED, 0.5F),
                new RandomFeatureEntry(YELLOW_FANCY_BIRCH_PLACED, 0.5F),
                new RandomFeatureEntry(ORANGE_FANCY_BIRCH_PLACED, 0.5F),
                new RandomFeatureEntry(RED_FANCY_BIRCH_PLACED, 0.5F),
                new RandomFeatureEntry(YELLOW_FANCY_BIRCH_WITH_BEES_PLACED, 0.03F),
                new RandomFeatureEntry(ORANGE_FANCY_BIRCH_WITH_BEES_PLACED, 0.03F),
                new RandomFeatureEntry(RED_FANCY_BIRCH_WITH_BEES_PLACED, 0.03F),
                new RandomFeatureEntry(FANCY_BIRCH_WITH_BEES_PLACED, 0.03F)
        ), TALL_BIRCH_PLACED));
        public static final RegistryEntry<PlacedFeature> OLD_GROWTH_BIRCH_FOREST_TREES_PLACED = registerPlaced("old_growth_birch_forest_trees", OLD_GROWTH_BIRCH_FOREST_TREES_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1), Blocks.BIRCH_SAPLING));

        public static void init() {}
    }

    public static final Feature<FallenLogFeatureConfig> FALLEN_LOG = new FallenLogFeature();
    public static final RegistryEntry<ConfiguredFeature<FallenLogFeatureConfig, ?>> FALLEN_BIRCH_LOG_CONFIGURED = registerConfigured("fallen_birch_log", FALLEN_LOG, fallenBirchLogConfig());
    public static final RegistryEntry<PlacedFeature> FALLEN_BIRCH_LOG_PLACED = registerPlaced("fallen_birch_log", FALLEN_BIRCH_LOG_CONFIGURED, VegetationPlacedFeatures.modifiersWithWouldSurvive(RarityFilterPlacementModifier.of(12), Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> LILY_OF_THE_VALLEY_PATCH_CONFIGURED = ConfiguredFeatures.register("lily_of_the_valley_patch", Feature.FLOWER, new RandomPatchFeatureConfig(64, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.LILY_OF_THE_VALLEY)))));
    public static final RegistryEntry<PlacedFeature> LILY_OF_THE_VALLEY_PATCH_PLACED = registerPlaced("lily_of_the_valley_patch", LILY_OF_THE_VALLEY_PATCH_CONFIGURED, RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_TALL_FLOWERS_PLACED = registerPlaced("birch_forest_tall_flowers", VegetationConfiguredFeatures.FOREST_FLOWERS, RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-3, 1), 0, 1)), BiomePlacementModifier.of());

    public static final Feature<VioletPatchFeatureConfig> VIOLET_PATCH = new VioletPatchFeature();
    public static final RegistryEntry<ConfiguredFeature<VioletPatchFeatureConfig, ?>> PURPLE_VIOLETS_CONFIGURED = registerConfigured("purple_violets", VIOLET_PATCH, purpleVioletPatchConfig());
    public static final RegistryEntry<ConfiguredFeature<VioletPatchFeatureConfig, ?>> PINK_VIOLETS_CONFIGURED = registerConfigured("pink_violets", VIOLET_PATCH, pinkVioletPatchConfig());
    public static final RegistryEntry<ConfiguredFeature<VioletPatchFeatureConfig, ?>> RED_VIOLETS_CONFIGURED = registerConfigured("red_violets", VIOLET_PATCH, redVioletPatchConfig());
    public static final RegistryEntry<ConfiguredFeature<VioletPatchFeatureConfig, ?>> WHITE_VIOLETS_CONFIGURED = registerConfigured("white_violets", VIOLET_PATCH, whiteVioletPatchConfig());
    public static final RegistryEntry<PlacedFeature> PURPLE_VIOLETS_PLACED = registerPlaced("purple_violets", PURPLE_VIOLETS_CONFIGURED, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> PINK_VIOLETS_PLACED = registerPlaced("pink_violets", PINK_VIOLETS_CONFIGURED, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> RED_VIOLETS_PLACED = registerPlaced("red_violets", RED_VIOLETS_CONFIGURED, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> WHITE_VIOLETS_PLACED = registerPlaced("white_violets", WHITE_VIOLETS_CONFIGURED, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_GRASS_PATCH_PLACED = registerPlaced("birch_forest_grass_patch", VegetationConfiguredFeatures.PATCH_GRASS, VegetationPlacedFeatures.modifiers(6));
    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_TALL_GRASS_PATCH_PLACED = PlacedFeatures.register("birch_forest_tall_grass_patch", VegetationConfiguredFeatures.PATCH_TALL_GRASS, RarityFilterPlacementModifier.of(20), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_ROCK_PLACED = registerPlaced("birch_forest_rock", MiscConfiguredFeatures.FOREST_ROCK, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    private static FallenLogFeatureConfig fallenBirchLogConfig() {
        return new FallenLogFeatureConfig(BlockStateProvider.of(WondrousWildsBlocks.HOLLOW_DEAD_BIRCH_LOG), 3, 8);
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
        Trees.TrunkPlacers.init();
        Trees.FoliagePlacers.init();
        Trees.Decorators.init();
        Trees.Configs.init();
        Trees.init();

        Registry.register(Registry.FEATURE, new Identifier(WondrousWilds.MOD_ID, "fallen_log"), FALLEN_LOG);

        Registry.register(Registry.FEATURE, new Identifier(WondrousWilds.MOD_ID, "violet_patch"), VIOLET_PATCH);
    }
}
