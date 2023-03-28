package com.ineffa.wondrouswilds.registry;

import com.google.common.collect.ImmutableList;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.mixin.FoliagePlacerTypeInvoker;
import com.ineffa.wondrouswilds.mixin.TreeDecoratorTypeInvoker;
import com.ineffa.wondrouswilds.mixin.TrunkPlacerTypeInvoker;
import com.ineffa.wondrouswilds.world.features.BoulderFeature;
import com.ineffa.wondrouswilds.world.features.FallenLogFeature;
import com.ineffa.wondrouswilds.world.features.TerrainSplotchFeature;
import com.ineffa.wondrouswilds.world.features.VioletPatchFeature;
import com.ineffa.wondrouswilds.world.features.configs.BoulderFeatureConfig;
import com.ineffa.wondrouswilds.world.features.configs.FallenLogFeatureConfig;
import com.ineffa.wondrouswilds.world.features.configs.TerrainSplotchFeatureConfig;
import com.ineffa.wondrouswilds.world.features.configs.VioletPatchFeatureConfig;
import com.ineffa.wondrouswilds.world.features.trees.decorators.*;
import com.ineffa.wondrouswilds.world.features.trees.foliage.BirchFoliagePlacer;
import com.ineffa.wondrouswilds.world.features.trees.foliage.FancyBirchFoliagePlacer;
import com.ineffa.wondrouswilds.world.features.trees.trunks.StraightBranchingTrunkPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WondrousWildsFeatures {

    public static final class Trees {

        public static final class TrunkPlacers {
            public static final TrunkPlacerType<StraightBranchingTrunkPlacer> STRAIGHT_BRANCHING_TRUNK = TrunkPlacerTypeInvoker.registerTrunkPlacer("straight_branching_trunk_placer", StraightBranchingTrunkPlacer.CODEC);

            public static void init() {}
        }

        public static final class FoliagePlacers {
            public static final FoliagePlacerType<BirchFoliagePlacer> BIRCH = FoliagePlacerTypeInvoker.registerFoliagePlacer("birch_foliage_placer", BirchFoliagePlacer.CODEC);
            public static final FoliagePlacerType<FancyBirchFoliagePlacer> FANCY_BIRCH = FoliagePlacerTypeInvoker.registerFoliagePlacer("fancy_birch_foliage_placer", FancyBirchFoliagePlacer.CODEC);

            public static void init() {}
        }

        public static final class Decorators {
            public static final TreeDecoratorType<TreeHollowTreeDecorator> TREE_HOLLOW_TYPE = TreeDecoratorTypeInvoker.registerTreeDecorator("tree_hollow", TreeHollowTreeDecorator.CODEC);
            public static final TreeDecoratorType<HangingBeeNestTreeDecorator> HANGING_BEE_NEST_TYPE = TreeDecoratorTypeInvoker.registerTreeDecorator("hanging_bee_nest", HangingBeeNestTreeDecorator.CODEC);
            public static final TreeDecoratorType<PolyporeTreeDecorator> POLYPORE_TYPE = TreeDecoratorTypeInvoker.registerTreeDecorator("polypores", PolyporeTreeDecorator.CODEC);
            public static final TreeDecoratorType<CobwebTreeDecorator> COBWEB_TYPE = TreeDecoratorTypeInvoker.registerTreeDecorator("cobwebs", CobwebTreeDecorator.CODEC);
            public static final TreeDecoratorType<LeafDecayTreeDecorator> LEAF_DECAY_TYPE = TreeDecoratorTypeInvoker.registerTreeDecorator("leaf_decay", LeafDecayTreeDecorator.CODEC);

            public static final TreeDecorator TREE_HOLLOW = TreeHollowTreeDecorator.INSTANCE;
            public static final TreeDecorator HANGING_BEE_NEST = HangingBeeNestTreeDecorator.INSTANCE;
            public static final TreeDecorator COBWEBS = CobwebTreeDecorator.INSTANCE;
            public static final TreeDecorator POLYPORES_FANCY_BIRCH = new PolyporeTreeDecorator(-1, 2, 2);
            public static final TreeDecorator POLYPORES_DYING_FANCY_BIRCH = new PolyporeTreeDecorator(3, 4, 3);
            public static final TreeDecorator LEAF_DECAY_DYING_FANCY_BIRCH = new LeafDecayTreeDecorator(2, 3, 3, true);

            public static void init() {}
        }

        public static final class Configs {
            public static TreeFeatureConfig.Builder tallBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG), new StraightTrunkPlacer(8, 4, 0),
                        BlockStateProvider.of(Blocks.BIRCH_LEAVES), new BirchFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines();
            }

            public static TreeFeatureConfig.Builder fancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(Blocks.BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES_FANCY_BIRCH, Decorators.COBWEBS)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder fancyBirchWithBeesConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 1, 3, 1, 1),
                        BlockStateProvider.of(Blocks.BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.HANGING_BEE_NEST, Decorators.POLYPORES_FANCY_BIRCH, Decorators.COBWEBS)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder dyingFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(WondrousWildsBlocks.DEAD_BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(Blocks.BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES_DYING_FANCY_BIRCH, Decorators.COBWEBS, Decorators.LEAF_DECAY_DYING_FANCY_BIRCH)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder yellowFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES_FANCY_BIRCH, Decorators.COBWEBS)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder yellowFancyBirchWithBeesConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 1, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.HANGING_BEE_NEST, Decorators.POLYPORES_FANCY_BIRCH, Decorators.COBWEBS)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder dyingYellowFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(WondrousWildsBlocks.DEAD_BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES_DYING_FANCY_BIRCH, Decorators.COBWEBS, Decorators.LEAF_DECAY_DYING_FANCY_BIRCH)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder orangeFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES_FANCY_BIRCH, Decorators.COBWEBS)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder orangeFancyBirchWithBeesConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 1, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.HANGING_BEE_NEST, Decorators.POLYPORES_FANCY_BIRCH, Decorators.COBWEBS)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder dyingOrangeFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(WondrousWildsBlocks.DEAD_BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES_DYING_FANCY_BIRCH, Decorators.COBWEBS, Decorators.LEAF_DECAY_DYING_FANCY_BIRCH)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder redFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.RED_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES_FANCY_BIRCH, Decorators.COBWEBS)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder redFancyBirchWithBeesConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 1, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.RED_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.HANGING_BEE_NEST, Decorators.POLYPORES_FANCY_BIRCH, Decorators.COBWEBS)).ignoreVines();
            }

            public static TreeFeatureConfig.Builder dyingRedFancyBirchConfig() {
                return new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(WondrousWildsBlocks.DEAD_BIRCH_LOG), new StraightBranchingTrunkPlacer(13, 5, 0, 0, 3, 1, 1),
                        BlockStateProvider.of(WondrousWildsBlocks.RED_BIRCH_LEAVES), new FancyBirchFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).decorators(ImmutableList.of(Decorators.POLYPORES_DYING_FANCY_BIRCH, Decorators.COBWEBS, Decorators.LEAF_DECAY_DYING_FANCY_BIRCH)).ignoreVines();
            }

            public static void init() {}
        }

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FANCY_BIRCH_CONFIGURED = registerConfigured("fancy_birch", Feature.TREE, Configs.fancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> FANCY_BIRCH_PLACED = registerPlaced("fancy_birch", FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FANCY_BIRCH_WITH_WOODPECKERS_CONFIGURED = registerConfigured("fancy_birch_with_woodpeckers", Feature.TREE, Configs.fancyBirchConfig().decorators(List.of(Decorators.TREE_HOLLOW)).build());
        public static final RegistryEntry<PlacedFeature> FANCY_BIRCH_WITH_WOODPECKERS_PLACED = registerPlaced("fancy_birch_with_woodpeckers", FANCY_BIRCH_WITH_WOODPECKERS_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("fancy_birch_with_bees", Feature.TREE, Configs.fancyBirchWithBeesConfig().build());
        public static final RegistryEntry<PlacedFeature> FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("fancy_birch_with_bees", FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> DYING_FANCY_BIRCH_CONFIGURED = registerConfigured("dying_fancy_birch", Feature.TREE, Configs.dyingFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> DYING_FANCY_BIRCH_PLACED = registerPlaced("dying_fancy_birch", DYING_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> YELLOW_FANCY_BIRCH_CONFIGURED = registerConfigured("yellow_fancy_birch", Feature.TREE, Configs.yellowFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> YELLOW_FANCY_BIRCH_PLACED = registerPlaced("yellow_fancy_birch", YELLOW_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> YELLOW_FANCY_BIRCH_WITH_WOODPECKERS_CONFIGURED = registerConfigured("yellow_fancy_birch_with_woodpeckers", Feature.TREE, Configs.yellowFancyBirchConfig().decorators(List.of(Decorators.TREE_HOLLOW)).build());
        public static final RegistryEntry<PlacedFeature> YELLOW_FANCY_BIRCH_WITH_WOODPECKERS_PLACED = registerPlaced("yellow_fancy_birch_with_woodpeckers", YELLOW_FANCY_BIRCH_WITH_WOODPECKERS_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> YELLOW_FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("yellow_fancy_birch_with_bees", Feature.TREE, Configs.yellowFancyBirchWithBeesConfig().build());
        public static final RegistryEntry<PlacedFeature> YELLOW_FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("yellow_fancy_birch_with_bees", YELLOW_FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> DYING_YELLOW_FANCY_BIRCH_CONFIGURED = registerConfigured("dying_yellow_fancy_birch", Feature.TREE, Configs.dyingYellowFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> DYING_YELLOW_FANCY_BIRCH_PLACED = registerPlaced("dying_yellow_fancy_birch", DYING_YELLOW_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> ORANGE_FANCY_BIRCH_CONFIGURED = registerConfigured("orange_fancy_birch", Feature.TREE, Configs.orangeFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> ORANGE_FANCY_BIRCH_PLACED = registerPlaced("orange_fancy_birch", ORANGE_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> ORANGE_FANCY_BIRCH_WITH_WOODPECKERS_CONFIGURED = registerConfigured("orange_fancy_birch_with_woodpeckers", Feature.TREE, Configs.orangeFancyBirchConfig().decorators(List.of(Decorators.TREE_HOLLOW)).build());
        public static final RegistryEntry<PlacedFeature> ORANGE_FANCY_BIRCH_WITH_WOODPECKERS_PLACED = registerPlaced("orange_fancy_birch_with_woodpeckers", ORANGE_FANCY_BIRCH_WITH_WOODPECKERS_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> ORANGE_FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("orange_fancy_birch_with_bees", Feature.TREE, Configs.orangeFancyBirchWithBeesConfig().build());
        public static final RegistryEntry<PlacedFeature> ORANGE_FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("orange_fancy_birch_with_bees", ORANGE_FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> DYING_ORANGE_FANCY_BIRCH_CONFIGURED = registerConfigured("dying_orange_fancy_birch", Feature.TREE, Configs.dyingOrangeFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> DYING_ORANGE_FANCY_BIRCH_PLACED = registerPlaced("dying_orange_fancy_birch", DYING_ORANGE_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> RED_FANCY_BIRCH_CONFIGURED = registerConfigured("red_fancy_birch", Feature.TREE, Configs.redFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> RED_FANCY_BIRCH_PLACED = registerPlaced("red_fancy_birch", RED_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> RED_FANCY_BIRCH_WITH_WOODPECKERS_CONFIGURED = registerConfigured("red_fancy_birch_with_woodpeckers", Feature.TREE, Configs.redFancyBirchConfig().decorators(List.of(Decorators.TREE_HOLLOW)).build());
        public static final RegistryEntry<PlacedFeature> RED_FANCY_BIRCH_WITH_WOODPECKERS_PLACED = registerPlaced("red_fancy_birch_with_woodpeckers", RED_FANCY_BIRCH_WITH_WOODPECKERS_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> RED_FANCY_BIRCH_WITH_BEES_CONFIGURED = registerConfigured("red_fancy_birch_with_bees", Feature.TREE, Configs.redFancyBirchWithBeesConfig().build());
        public static final RegistryEntry<PlacedFeature> RED_FANCY_BIRCH_WITH_BEES_PLACED = registerPlaced("red_fancy_birch_with_bees", RED_FANCY_BIRCH_WITH_BEES_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> DYING_RED_FANCY_BIRCH_CONFIGURED = registerConfigured("dying_red_fancy_birch", Feature.TREE, Configs.dyingRedFancyBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> DYING_RED_FANCY_BIRCH_PLACED = registerPlaced("dying_red_fancy_birch", DYING_RED_FANCY_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> TALL_BIRCH_CONFIGURED = registerConfigured("tall_birch", Feature.TREE, Configs.tallBirchConfig().build());
        public static final RegistryEntry<PlacedFeature> TALL_BIRCH_PLACED = registerPlaced("tall_birch", TALL_BIRCH_CONFIGURED, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

        public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> BIRCH_FOREST_TREES_CONFIGURED = registerConfigured("birch_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(
                new RandomFeatureEntry(FANCY_BIRCH_PLACED, 0.85F),
                new RandomFeatureEntry(FANCY_BIRCH_WITH_WOODPECKERS_PLACED, 0.1F),
                new RandomFeatureEntry(FANCY_BIRCH_WITH_BEES_PLACED, 0.075F),
                new RandomFeatureEntry(DYING_FANCY_BIRCH_PLACED, 0.05F)
        ), TALL_BIRCH_PLACED));
        public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_TREES_PLACED = registerPlaced("birch_forest_trees", BIRCH_FOREST_TREES_CONFIGURED, Stream.concat(Stream.of(PlacedFeatures.createCountExtraModifier(5, 0.1F, 1)), VegetationPlacedFeatures.modifiersWithWouldSurvive(BlockFilterPlacementModifier.of(BlockPredicate.not(BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), Blocks.MOSS_BLOCK))), Blocks.BIRCH_SAPLING).stream()).collect(Collectors.toList()));

        public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> OLD_GROWTH_BIRCH_FOREST_TREES_CONFIGURED = registerConfigured("old_growth_birch_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(
                new RandomFeatureEntry(FANCY_BIRCH_PLACED, 0.2125F),
                new RandomFeatureEntry(YELLOW_FANCY_BIRCH_PLACED, 0.2125F),
                new RandomFeatureEntry(ORANGE_FANCY_BIRCH_PLACED, 0.2125F),
                new RandomFeatureEntry(RED_FANCY_BIRCH_PLACED, 0.2125F),
                new RandomFeatureEntry(FANCY_BIRCH_WITH_WOODPECKERS_PLACED, 0.025F),
                new RandomFeatureEntry(YELLOW_FANCY_BIRCH_WITH_WOODPECKERS_PLACED, 0.025F),
                new RandomFeatureEntry(ORANGE_FANCY_BIRCH_WITH_WOODPECKERS_PLACED, 0.025F),
                new RandomFeatureEntry(RED_FANCY_BIRCH_WITH_WOODPECKERS_PLACED, 0.025F),
                new RandomFeatureEntry(FANCY_BIRCH_WITH_BEES_PLACED, 0.01875F),
                new RandomFeatureEntry(YELLOW_FANCY_BIRCH_WITH_BEES_PLACED, 0.01875F),
                new RandomFeatureEntry(ORANGE_FANCY_BIRCH_WITH_BEES_PLACED, 0.01875F),
                new RandomFeatureEntry(RED_FANCY_BIRCH_WITH_BEES_PLACED, 0.01875F),
                new RandomFeatureEntry(DYING_FANCY_BIRCH_PLACED, 0.0125F),
                new RandomFeatureEntry(DYING_YELLOW_FANCY_BIRCH_PLACED, 0.0125F),
                new RandomFeatureEntry(DYING_ORANGE_FANCY_BIRCH_PLACED, 0.0125F),
                new RandomFeatureEntry(DYING_RED_FANCY_BIRCH_PLACED, 0.0125F)
        ), TALL_BIRCH_PLACED));
        public static final RegistryEntry<PlacedFeature> OLD_GROWTH_BIRCH_FOREST_TREES_PLACED = registerPlaced("old_growth_birch_forest_trees", OLD_GROWTH_BIRCH_FOREST_TREES_CONFIGURED, Stream.concat(Stream.of(PlacedFeatures.createCountExtraModifier(5, 0.1F, 1)), VegetationPlacedFeatures.modifiersWithWouldSurvive(BlockFilterPlacementModifier.of(BlockPredicate.not(BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), Blocks.MOSS_BLOCK))), Blocks.BIRCH_SAPLING).stream()).collect(Collectors.toList()));

        public static void init() {}
    }

    public static final Feature<TerrainSplotchFeatureConfig> TERRAIN_SPLOTCH = new TerrainSplotchFeature();
    public static final RegistryEntry<ConfiguredFeature<TerrainSplotchFeatureConfig, ?>> COARSE_DIRT_SPLOTCH_ON_GRASS_CONFIGURED = registerConfigured("coarse_dirt_splotch_on_grass", TERRAIN_SPLOTCH, coarseDirtSplotchOnGrassConfig());
    public static final RegistryEntry<PlacedFeature> COARSE_DIRT_SPLOTCH_ON_GRASS_PLACED = registerPlaced("coarse_dirt_splotch_on_grass", COARSE_DIRT_SPLOTCH_ON_GRASS_CONFIGURED, CountPlacementModifier.of(UniformIntProvider.create(0, 2)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<TerrainSplotchFeatureConfig, ?>> LARGE_COARSE_DIRT_SPLOTCH_ON_GRASS_CONFIGURED = registerConfigured("large_coarse_dirt_splotch_on_grass", TERRAIN_SPLOTCH, largeCoarseDirtSplotchOnGrassConfig());
    public static final RegistryEntry<PlacedFeature> LARGE_COARSE_DIRT_SPLOTCH_ON_GRASS_PLACED = registerPlaced("large_coarse_dirt_splotch_on_grass", LARGE_COARSE_DIRT_SPLOTCH_ON_GRASS_CONFIGURED, RarityFilterPlacementModifier.of(60), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static final Feature<BoulderFeatureConfig> BOULDER = new BoulderFeature();
    public static final RegistryEntry<ConfiguredFeature<BoulderFeatureConfig, ?>> BIRCH_FOREST_BOULDER_CONFIGURED = registerConfigured("birch_forest_boulder", BOULDER, birchForestBoulderConfig());
    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_BOULDER_PLACED = registerPlaced("birch_forest_boulder", BIRCH_FOREST_BOULDER_CONFIGURED, RarityFilterPlacementModifier.of(5), CountPlacementModifier.of(new WeightedListIntProvider(new DataPool.Builder<IntProvider>().add(ConstantIntProvider.create(1), 7).add(BiasedToBottomIntProvider.create(3, 4), 1).build())), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.not(BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), Blocks.MOSS_BLOCK))), BlockFilterPlacementModifier.of(BlockPredicate.anyOf(BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), BlockTags.DIRT), BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), BlockTags.BASE_STONE_OVERWORLD))), BiomePlacementModifier.of());

    public static final Feature<FallenLogFeatureConfig> FALLEN_LOG = new FallenLogFeature();
    public static final RegistryEntry<ConfiguredFeature<FallenLogFeatureConfig, ?>> FALLEN_BIRCH_LOG_CONFIGURED = registerConfigured("fallen_birch_log", FALLEN_LOG, fallenBirchLogConfig());
    public static final RegistryEntry<PlacedFeature> FALLEN_BIRCH_LOG_PLACED = registerPlaced("fallen_birch_log", FALLEN_BIRCH_LOG_CONFIGURED, Stream.concat(Stream.of(RarityFilterPlacementModifier.of(12)), VegetationPlacedFeatures.modifiersWithWouldSurvive(BlockFilterPlacementModifier.of(BlockPredicate.not(BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), Blocks.MOSS_BLOCK))), Blocks.BIRCH_SAPLING).stream()).collect(Collectors.toList()));

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

    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_GRASS_PATCH_PLACED = registerPlaced("birch_forest_grass_patch", VegetationConfiguredFeatures.PATCH_GRASS, VegetationPlacedFeatures.modifiers(8));
    public static final RegistryEntry<PlacedFeature> BIRCH_FOREST_TALL_GRASS_PATCH_PLACED = PlacedFeatures.register("birch_forest_tall_grass_patch", VegetationConfiguredFeatures.PATCH_TALL_GRASS, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    private static TerrainSplotchFeatureConfig coarseDirtSplotchOnGrassConfig() {
        return new TerrainSplotchFeatureConfig(BlockStateProvider.of(Blocks.COARSE_DIRT), BlockPredicate.matchingBlocks(Blocks.GRASS_BLOCK), UniformIntProvider.create(4, 8), UniformIntProvider.create(3, 3), UniformFloatProvider.create(0.3F, 0.7F));
    }

    private static TerrainSplotchFeatureConfig largeCoarseDirtSplotchOnGrassConfig() {
        return new TerrainSplotchFeatureConfig(BlockStateProvider.of(Blocks.COARSE_DIRT), BlockPredicate.matchingBlocks(Blocks.GRASS_BLOCK), UniformIntProvider.create(12, 16), UniformIntProvider.create(5, 5), UniformFloatProvider.create(0.6F, 0.9F));
    }

    private static BoulderFeatureConfig birchForestBoulderConfig() {
        return new BoulderFeatureConfig(
                new WeightedBlockStateProvider(new DataPool.Builder<BlockState>().add(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3).add(Blocks.COBBLESTONE.getDefaultState(), 2).add(Blocks.MOSS_BLOCK.getDefaultState(), 1).build()),
                BoulderFeatureConfig.DEFAULT_BOULDER_REPLACE_PREDICATE,
                new WeightedListIntProvider(new DataPool.Builder<IntProvider>().add(new WeightedListIntProvider(new DataPool.Builder<IntProvider>().add(ConstantIntProvider.create(1), 6).add(ConstantIntProvider.create(2), 3).add(ConstantIntProvider.create(3), 1).build()), 19).add(BiasedToBottomIntProvider.create(4, 6), 1).build()),
                BiasedToBottomIntProvider.create(1, 3)
        );
    }

    private static FallenLogFeatureConfig fallenBirchLogConfig() {
        return new FallenLogFeatureConfig(BlockStateProvider.of(WondrousWildsBlocks.HOLLOW_DEAD_BIRCH_LOG), BlockStateProvider.of(WondrousWildsBlocks.DEAD_BIRCH_LOG), 3, 8, 3);
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

        Registry.register(Registry.FEATURE, new Identifier(WondrousWilds.MOD_ID, "terrain_splotch"), TERRAIN_SPLOTCH);
        Registry.register(Registry.FEATURE, new Identifier(WondrousWilds.MOD_ID, "boulder"), BOULDER);

        Registry.register(Registry.FEATURE, new Identifier(WondrousWilds.MOD_ID, "fallen_log"), FALLEN_LOG);

        Registry.register(Registry.FEATURE, new Identifier(WondrousWilds.MOD_ID, "violet_patch"), VIOLET_PATCH);
    }
}
