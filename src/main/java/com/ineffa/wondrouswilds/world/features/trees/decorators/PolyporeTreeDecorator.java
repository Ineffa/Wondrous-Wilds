package com.ineffa.wondrouswilds.world.features.trees.decorators;

import com.ineffa.wondrouswilds.blocks.BigPolyporeBlock;
import com.ineffa.wondrouswilds.blocks.SmallPolyporeBlock;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.registry.WondrousWildsFeatures;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.ineffa.wondrouswilds.util.WondrousWildsUtils.HORIZONTAL_DIRECTIONS;

public class PolyporeTreeDecorator extends TreeDecorator {

    public static final PolyporeTreeDecorator INSTANCE = new PolyporeTreeDecorator();
    public static final Codec<PolyporeTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    private static final BlockState SMALL_POLYPORE_STATE = WondrousWildsBlocks.SMALL_POLYPORE.getDefaultState();
    private static final BlockState BIG_POLYPORE_STATE = WondrousWildsBlocks.BIG_POLYPORE.getDefaultState();

    @Override
    protected TreeDecoratorType<?> getType() {
        return WondrousWildsFeatures.Trees.Decorators.POLYPORE_TYPE;
    }

    @Override
    public void generate(Generator generator) {
        Random random = generator.getRandom();
        TestableWorld world = generator.getWorld();

        List<BlockPos> verticalLogs = generator.getLogPositions().stream().filter(pos -> world.testBlockState(pos, state -> state.contains(PillarBlock.AXIS) && state.get(PillarBlock.AXIS).isVertical())).collect(Collectors.toList());
        Collections.shuffle(verticalLogs);

        int clustersToPlace = 0; while (clustersToPlace < 3) if (random.nextBoolean()) ++clustersToPlace; else break;
        int clustersPlaced = 0;
        clusterStarter: for (BlockPos logPos : verticalLogs) {
            if (clustersPlaced >= clustersToPlace) break;

            boolean clusterSuccessful = false;

            int steps = 1 + random.nextInt(3);
            Direction nextOffsetDirection = random.nextBoolean() ? Direction.UP : Direction.DOWN;
            int nextUpOffset = 0;
            int nextDownOffset = 0;
            for (int step = 0; step <= steps; ++step) {
                BlockPos polyporesCenter = logPos.offset(nextOffsetDirection, nextOffsetDirection == Direction.UP ? nextUpOffset : nextDownOffset);

                if (!canPlacePolyporesAround(generator, world, polyporesCenter)) continue;

                if (nextOffsetDirection == Direction.UP) ++nextUpOffset;
                else if (nextOffsetDirection == Direction.DOWN) ++nextDownOffset;
                nextOffsetDirection = nextOffsetDirection.getOpposite();

                for (Direction polyporeDirection : HORIZONTAL_DIRECTIONS) {
                    int polyporeScale = random.nextInt(5);
                    if (polyporeScale <= 0) continue;

                    BlockPos polyporePos = polyporesCenter.offset(polyporeDirection);
                    if (!isOpenSpace(generator, world, polyporePos)) continue;

                    generator.replace(polyporePos, polyporeScale > 3 ? BIG_POLYPORE_STATE.with(BigPolyporeBlock.FACING, polyporeDirection) : SMALL_POLYPORE_STATE.with(SmallPolyporeBlock.POLYPORES, polyporeScale).with(SmallPolyporeBlock.FACING, polyporeDirection));
                }

                clusterSuccessful = true;
            }

            if (clusterSuccessful) ++clustersPlaced;
        }
    }

    private static boolean canPlacePolyporesAround(Generator generator, TestableWorld world, BlockPos center) {
        if (!world.testBlockState(center, state -> state.isIn(BlockTags.LOGS))) return false;

        boolean hasOpenSpace = false;
        for (Direction direction : HORIZONTAL_DIRECTIONS) {
            BlockPos offsetPos = center.offset(direction);
            if (isOpenSpace(generator, world, offsetPos)) {
                hasOpenSpace = true;
                break;
            }
        }

        return hasOpenSpace;
    }

    private static boolean isOpenSpace(Generator generator, TestableWorld world, BlockPos pos) {
        return generator.isAir(pos) || world.testFluidState(pos, state -> state.isOf(Fluids.WATER) && state.isStill());
    }
}
