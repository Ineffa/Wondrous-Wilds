package com.ineffa.wondrouswilds.world.features.trees.trunks;

import com.google.common.collect.ImmutableList;
import com.ineffa.wondrouswilds.registry.WondrousWildsFeatures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.ineffa.wondrouswilds.util.WondrousWildsUtils.HORIZONTAL_DIRECTIONS;

public class StraightBranchingTrunkPlacer extends TrunkPlacer {

    public static final Codec<StraightBranchingTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> StraightBranchingTrunkPlacer.fillTrunkPlacerFields(instance).and(instance.group(
            Codecs.NONNEGATIVE_INT.fieldOf("min_branches").forGetter(StraightBranchingTrunkPlacer::getMinBranches),
            Codecs.POSITIVE_INT.fieldOf("max_branches").forGetter(StraightBranchingTrunkPlacer::getMaxBranches),
            Codecs.POSITIVE_INT.fieldOf("min_branch_length").forGetter(StraightBranchingTrunkPlacer::getMinBranchLength),
            Codecs.POSITIVE_INT.fieldOf("max_branch_length").forGetter(StraightBranchingTrunkPlacer::getMaxBranchLength)
    )).apply(instance, StraightBranchingTrunkPlacer::new));

    private final int minBranches;
    private final int maxBranches;
    private final int minBranchLength;
    private final int maxBranchLength;

    public int getMinBranches() {
        return this.minBranches;
    }

    public int getMaxBranches() {
        return this.maxBranches;
    }

    public int getMinBranchLength() {
        return this.minBranchLength;
    }

    public int getMaxBranchLength() {
        return this.maxBranchLength;
    }

    public StraightBranchingTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, int minBranches, int maxBranches, int minBranchLength, int maxBranchLength) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);

        this.minBranches = minBranches;
        this.maxBranches = maxBranches;

        this.minBranchLength = minBranchLength;
        this.maxBranchLength = maxBranchLength;
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return WondrousWildsFeatures.Trees.TrunkPlacers.STRAIGHT_BRANCHING_TRUNK;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        StraightTrunkPlacer.setToDirt(world, replacer, random, startPos.down(), config);

        BlockPos topTrunkLog = startPos.up(height);

        List<BlockPos> trunkLogsSuitableForBranch = new ArrayList<>();

        for (int currentHeight = 0; currentHeight < height; ++currentHeight) {
            BlockPos logPos = startPos.up(currentHeight);
            if (this.getAndSetState(world, replacer, random, logPos, config) && logPos.getY() < topTrunkLog.up().getY() - 7) trunkLogsSuitableForBranch.add(logPos);
        }

        int branches = this.minBranches; while (branches < this.maxBranches) if (random.nextBoolean()) ++branches; else break;

        List<BlockPos> successfulBranchPositions = new ArrayList<>();
        List<BlockPos> trunkLogsWithBranchAttempts = new ArrayList<>();
        while (trunkLogsWithBranchAttempts.size() < branches) {
            if (trunkLogsWithBranchAttempts.size() >= trunkLogsSuitableForBranch.size()) break;

            BlockPos trunkLogWithBranchPos = trunkLogsSuitableForBranch.get(random.nextInt(trunkLogsSuitableForBranch.size()));
            if (trunkLogsWithBranchAttempts.contains(trunkLogWithBranchPos)) continue;

            trunkLogsWithBranchAttempts.add(trunkLogWithBranchPos);

            Direction branchDirection;

            List<Direction> suitableBranchDirections = new ArrayList<>();
            for (Direction checkDirection : HORIZONTAL_DIRECTIONS) {
                BlockPos checkPos = trunkLogWithBranchPos.offset(checkDirection);

                if (successfulBranchPositions.contains(checkPos.down()) || successfulBranchPositions.contains(checkPos.up())) continue;

                suitableBranchDirections.add(checkDirection);
            }
            if (suitableBranchDirections.isEmpty()) continue;
            else branchDirection = suitableBranchDirections.get(random.nextInt(suitableBranchDirections.size()));

            int nextBranchLogDistance = 1;
            boolean isNotMinimumLength = nextBranchLogDistance <= this.minBranchLength;
            boolean isNotMaximumLength = nextBranchLogDistance <= this.maxBranchLength;
            while (isNotMaximumLength || isNotMinimumLength) {
                if (!isNotMinimumLength && random.nextBoolean()) break;

                BlockPos branchPos = trunkLogWithBranchPos.offset(branchDirection, nextBranchLogDistance);
                if (!this.getAndSetState(world, replacer, random, branchPos, config, state -> state.with(PillarBlock.AXIS, branchDirection.getAxis()))) break;
                else successfulBranchPositions.add(branchPos);

                ++nextBranchLogDistance;
                isNotMinimumLength = nextBranchLogDistance <= this.minBranchLength;
                isNotMaximumLength = nextBranchLogDistance <= this.maxBranchLength;
            }
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(topTrunkLog, 0, false));
    }
}
