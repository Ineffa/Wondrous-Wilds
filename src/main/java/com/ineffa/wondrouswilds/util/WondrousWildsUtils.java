package com.ineffa.wondrouswilds.util;

import com.google.common.collect.ImmutableMap;
import com.ineffa.wondrouswilds.blocks.TreeHollowBlock;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.*;

public class WondrousWildsUtils {

    public static final Direction[] HORIZONTAL_DIRECTIONS = Arrays.stream(Direction.values()).filter((direction) -> direction.getAxis().isHorizontal()).toArray(Direction[]::new);

    public static final Map<Block, TreeHollowBlock> TREE_HOLLOW_MAP = new ImmutableMap.Builder<Block, TreeHollowBlock>()
            .put(Blocks.OAK_LOG, WondrousWildsBlocks.OAK_TREE_HOLLOW)
            .put(Blocks.SPRUCE_LOG, WondrousWildsBlocks.SPRUCE_TREE_HOLLOW)
            .put(Blocks.BIRCH_LOG, WondrousWildsBlocks.BIRCH_TREE_HOLLOW)
            .put(Blocks.JUNGLE_LOG, WondrousWildsBlocks.JUNGLE_TREE_HOLLOW)
            .put(Blocks.ACACIA_LOG, WondrousWildsBlocks.ACACIA_TREE_HOLLOW)
            .put(Blocks.DARK_OAK_LOG, WondrousWildsBlocks.DARK_OAK_TREE_HOLLOW)
            .put(Blocks.MANGROVE_LOG, WondrousWildsBlocks.MANGROVE_TREE_HOLLOW)
            .build();

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

    public static boolean isPosAdjacentToAnyOfPositions(BlockPos pos, Collection<BlockPos> positions) {
        for (Direction direction : Direction.values())
            if (positions.stream().anyMatch(pos1 -> pos1.equals(pos.offset(direction)))) return true;

        return false;
    }

    public static boolean isPosAtWorldOrigin(BlockPos pos) {
        BlockPos origin = BlockPos.ORIGIN;
        return pos.getX() == origin.getX() && pos.getY() == origin.getY() && pos.getZ() == origin.getZ();
    }

    public static boolean canEntitySeeBlock(LivingEntity entity, BlockPos posToCheck, boolean ignoreFluids) {
        World world = entity.getWorld();

        BlockState lookState = world.getBlockState(posToCheck);
        if (lookState == null) return false;

        VoxelShape shape = lookState.getOutlineShape(world, posToCheck);
        if (shape == null || shape.isEmpty()) return false;

        Box blockBoundingBox = shape.getBoundingBox();
        if (blockBoundingBox == null) return false;

        Vec3d raycastStart = new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
        Vec3d raycastEnd = blockBoundingBox.getCenter().add(posToCheck.getX(), posToCheck.getY(), posToCheck.getZ());
        return world.raycast(new RaycastContext(raycastStart, raycastEnd, RaycastContext.ShapeType.COLLIDER, ignoreFluids ? RaycastContext.FluidHandling.NONE : RaycastContext.FluidHandling.ANY, entity)).getBlockPos().equals(posToCheck);
    }
}
