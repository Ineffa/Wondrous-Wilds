package com.ineffa.wondrouswilds.world.features;

import com.ineffa.wondrouswilds.util.WondrousWildsUtils;
import com.ineffa.wondrouswilds.world.features.configs.BoulderFeatureConfig;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BoulderFeature extends Feature<BoulderFeatureConfig> {

    public BoulderFeature() {
        super(BoulderFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<BoulderFeatureConfig> context) {
        BoulderFeatureConfig config = context.getConfig();
        Random random = context.getRandom();
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();

        List<BlockPos> positions = new ArrayList<>();
        this.createSphericalBoulderChunkAround(positions, origin, (float) config.startingRadius().get(random), true, config, random, world);

        if (positions.isEmpty()) return false;

        for (BlockPos pos : positions) {
            if (random.nextInt(3) == 0) {
                int adjacentNonBoulderPositionsVertically = 0;
                int adjacentNonBoulderPositionsHorizontally = 0;
                for (Direction verticalDirection : WondrousWildsUtils.VERTICAL_DIRECTIONS) {
                    if (adjacentNonBoulderPositionsVertically >= 1) break;
                    if (!positions.contains(pos.offset(verticalDirection))) ++adjacentNonBoulderPositionsVertically;
                }
                for (Direction horizontalDirection : WondrousWildsUtils.HORIZONTAL_DIRECTIONS) {
                    if (adjacentNonBoulderPositionsHorizontally >= 2) break;
                    if (!positions.contains(pos.offset(horizontalDirection))) ++adjacentNonBoulderPositionsHorizontally;
                }

                if (adjacentNonBoulderPositionsVertically >= 1 && adjacentNonBoulderPositionsHorizontally >= 2) continue;
            }

            this.setBlockState(world, pos, config.blockProvider().getBlockState(random, pos));
        }

        return true;
    }

    protected boolean createSphericalBoulderChunkAround(List<BlockPos> positionList, BlockPos center, float radius, boolean extend, BoulderFeatureConfig config, Random random, StructureWorldAccess world) {
        List<BlockPos> successfulNewPositions = new ArrayList<>();
        for (int y = (int) -radius; y <= radius; ++y) {
            for (int x = (int) -radius; x <= radius; ++x) {
                for (int z = (int) -radius; z <= radius; ++z) {
                    BlockPos newPos = center.add(x, y, z);
                    if (positionList.contains(newPos) || !this.isWithinDistance(newPos, center, radius)) continue;

                    if (!(world.getBlockState(newPos).isAir() || config.replaceBlockPredicate().test(world, newPos))) return false;

                    successfulNewPositions.add(newPos);
                }
            }
        }
        positionList.addAll(successfulNewPositions);

        boolean success = true;

        if (extend) {
            List<BlockPos> extensionCenterBlacklist = new ArrayList<>(List.of(center));
            for (int i = 0; i < config.extensions().get(random); ++i) {
                final float extensionRadius = random.nextBoolean() ? radius / 2.0F : random.nextBoolean() ? radius : radius / 3.0F;

                Optional<BlockPos> extensionCenter = Util.getRandomOrEmpty(successfulNewPositions.stream().filter(posFromPredecessor ->
                        !extensionCenterBlacklist.contains(posFromPredecessor) && !this.isWithinDistance(posFromPredecessor, center, radius - extensionRadius)
                ).collect(Collectors.toList()), random);
                if (extensionCenter.isEmpty()) continue;

                extensionCenterBlacklist.add(extensionCenter.get());

                success = this.createSphericalBoulderChunkAround(positionList, extensionCenter.get(), extensionRadius, false, config, random, world);
            }
        }

        return success;
    }

    protected final boolean isWithinDistance(BlockPos to, BlockPos from, float distance) {
        return Math.sqrt(Math.pow(to.getX() - from.getX(), 2.0D) + Math.pow(to.getY() - from.getY(), 2.0D) + Math.pow(to.getZ() - from.getZ(), 2.0D)) <= distance + 0.5D;
    }
}
