package com.ineffa.wondrouswilds.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WondrousWildsUtils {

    public static final Direction[] HORIZONTAL_DIRECTIONS = Arrays.stream(Direction.values()).filter((direction) -> direction.getAxis().isHorizontal()).toArray(Direction[]::new);

    public static Set<BlockPos> getCenteredCuboid(BlockPos center, int horizontalRadius) {
        return getCenteredCuboid(center, horizontalRadius, 0);
    }

    public static Set<BlockPos> getCenteredCuboid(BlockPos center, int horizontalRadius, int verticalRadius) {
        Set<BlockPos> positions = new HashSet<>();

        for (int y = -verticalRadius; y <= verticalRadius; ++y) {
            for (int x = -horizontalRadius; x <= horizontalRadius; ++x) {
                for (int z = -horizontalRadius; z <= horizontalRadius; ++z) {
                    BlockPos pos = center.add(x, y, z);
                    positions.add(pos);
                }
            }
        }

        return positions;
    }

    public static Set<BlockPos> getEdges(BlockPos center, int edgeDistance, int edgeRadius) {
        Set<BlockPos> positions = new HashSet<>();

        for (Direction direction : HORIZONTAL_DIRECTIONS) {
            BlockPos offsetPos = center.offset(direction, edgeDistance);

            positions.add(offsetPos);

            if (edgeRadius < 1) continue;

            for (int distance = 1; distance <= edgeRadius; ++distance) {
                positions.add(offsetPos.offset(direction.rotateYClockwise(), distance));
                positions.add(offsetPos.offset(direction.rotateYCounterclockwise(), distance));
            }
        }

        return positions;
    }
}
