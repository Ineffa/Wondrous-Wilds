package com.ineffa.wondrouswilds.world.features.trees.foliage;

import com.ineffa.wondrouswilds.registry.WondrousWildsFeatures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.ineffa.wondrouswilds.util.WondrousWildsUtils.HORIZONTAL_DIRECTIONS;

public class FancyBirchFoliagePlacer extends FoliagePlacer {

    public static final Codec<FancyBirchFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> FancyBirchFoliagePlacer.fillFoliagePlacerFields(instance).apply(instance, FancyBirchFoliagePlacer::new));

    public FancyBirchFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return WondrousWildsFeatures.Trees.FoliagePlacers.FANCY_BIRCH;
    }

    @Override
    protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos origin = treeNode.getCenter();

        boolean isLarge = random.nextBoolean();

        List<BlockPos> leaves = new ArrayList<>();

        int upwardBound = 0;
        int downwardBound = isLarge ? -7 : -5;
        for (int y = upwardBound; y >= downwardBound; --y) {
            boolean isTip = y == upwardBound || y == downwardBound;
            if (isTip) {
                if (y == upwardBound) leaves.add(origin);
                for (Direction direction : HORIZONTAL_DIRECTIONS) leaves.add(origin.down(MathHelper.abs(y)).offset(direction));
            }
            else {
                boolean isInMiddleRange = y <= upwardBound - 2 && y >= downwardBound + 2;
                int increase = isInMiddleRange ? 1 : 0;

                for (int x = -1 - increase; x <= 1 + increase; ++x) {
                    for (int z = -1 - increase; z <= 1 + increase; ++z) {
                        if (x == origin.getX() && z == origin.getZ()) continue;
                        leaves.add(origin.add(x, y, z));
                    }
                }

                for (Direction direction : HORIZONTAL_DIRECTIONS) {
                    BlockPos offsetPos = origin.down(MathHelper.abs(y)).offset(direction, 2 + increase);

                    leaves.add(offsetPos);
                    if (y != upwardBound - 2 && !(isLarge && y == downwardBound + 2)) {
                        int layersToAdd = y == downwardBound + 3 ? 2 : 1;
                        for (int distance = 1; distance <= layersToAdd; ++distance) {
                            leaves.add(offsetPos.offset(direction.rotateYClockwise(), distance));
                            leaves.add(offsetPos.offset(direction.rotateYCounterclockwise(), distance));
                        }
                    }
                }
            }

            if (y < 0 && y > -5) {
                int increase = y == -2 || y == -3 ? 1 : 0;
                for (int x = -1 - increase; x <= 1 + increase; ++x) {
                    for (int z = -1 - increase; z <= 1 + increase; ++z) {
                        if (x == origin.getX() && z == origin.getZ()) continue;
                        leaves.add(origin.add(x, y, z));
                    }
                }
            }
        }

        for (BlockPos pos : leaves) {
            if (random.nextInt(40) == 0 && !isPosTouchingLog(world, pos, config, random)) continue;
            FancyBirchFoliagePlacer.placeFoliageBlock(world, replacer, random, config, pos);
        }
    }

    private static boolean isPosTouchingLog(TestableWorld world, BlockPos pos, TreeFeatureConfig config, Random random) {
        for (Direction direction : Direction.values()) {
            BlockPos checkPos = pos.offset(direction);
            if (world.testBlockState(checkPos, state -> state.getBlock() == config.trunkProvider.getBlockState(random, checkPos).getBlock())) return true;
        }
        return false;
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
