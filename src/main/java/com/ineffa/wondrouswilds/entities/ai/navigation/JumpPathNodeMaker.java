package com.ineffa.wondrouswilds.entities.ai.navigation;

import com.ineffa.wondrouswilds.entities.HoppingMob;
import com.ineffa.wondrouswilds.mixin.LandPathNodeMakerAccessor;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.chunk.ChunkCache;
import org.jetbrains.annotations.Nullable;

public class JumpPathNodeMaker extends LandPathNodeMaker {

    protected HoppingMob hoppingMob;

    @Override
    public void init(ChunkCache cachedWorld, MobEntity entity) {
        if (!(entity instanceof HoppingMob hoppingMob)) throw new IllegalArgumentException("Entity must be an instance of HoppingMob");
        super.init(cachedWorld, entity);

        this.hoppingMob = hoppingMob;
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode rootNode) {
        int jumpNodeCount = 0;

        PathNodeType rootNodeType = this.getNodeType(this.entity, rootNode.x, rootNode.y, rootNode.z);
        int verticalScanRange = 3;
        int horizontalScanRange = 4;
        for (int yOffset = verticalScanRange; yOffset >= -verticalScanRange; --yOffset) {
            for (int xOffset = -horizontalScanRange; xOffset <= horizontalScanRange; ++xOffset) {
                for (int zOffset = -horizontalScanRange; zOffset <= horizontalScanRange; ++zOffset) {
                    if (xOffset == 0 && zOffset == 0) continue;

                    PathNode potentialSuccessorNode = this.getPathNode(
                            rootNode.x + xOffset,
                            rootNode.y + yOffset,
                            rootNode.z + zOffset,
                            this.hoppingMob.getMaxJumpHeight(),
                            0.0D,
                            Direction.UP,
                            rootNodeType
                    );
                    if (this.isValidJumpSuccessorTo(rootNode, potentialSuccessorNode)) {
                        ((JumpablePathNode) potentialSuccessorNode).setShouldBeJumpedTo(true);
                        successors[jumpNodeCount++] = potentialSuccessorNode;
                    }
                }
            }
        }

        return jumpNodeCount > 0 ? jumpNodeCount : super.getSuccessors(successors, rootNode);
    }

    protected boolean isValidJumpSuccessorTo(PathNode node, @Nullable PathNode potentialSuccessor) {
        if (potentialSuccessor == null) return false;
        if (potentialSuccessor.visited) return false;
        if (potentialSuccessor.type == PathNodeType.WALKABLE_DOOR || potentialSuccessor.type == PathNodeType.FENCE) return false;
        if (!(node.penalty < 0.0F || potentialSuccessor.penalty >= 0.0F)) return false;

        boolean hasSpaceBetween = false;

        Vec3d origin = Vec3d.ofCenter(node.getBlockPos());
        Vec3d target = Vec3d.ofCenter(potentialSuccessor.getBlockPos());
        for (int verticalOffset = 0; verticalOffset <= 3; ++verticalOffset) {
            Vec3d raycastStart = origin.add(0.0D, verticalOffset, 0.0D);
            if (this.cachedWorld.raycast(new RaycastContext(raycastStart, target, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, this.entity)).getType() == HitResult.Type.MISS) {
                hasSpaceBetween = true;
                break;
            }
        }

        return hasSpaceBetween;
    }

    @Nullable
    @Override
    protected PathNode getNode(int x, int y, int z) {
        return this.pathNodeCache.computeIfAbsent(PathNode.hash(x, y, z), l -> new PathNode(x, y, z));
    }

    @Nullable
    @Override
    protected PathNode getPathNode(int x, int y, int z, int maxYStep, double prevFeetY, Direction direction, PathNodeType nodeType) {
        double h;
        double g;
        PathNode pathNode = null;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        PathNodeType pathNodeType = this.getNodeType(this.entity, x, y, z);
        float f = this.entity.getPathfindingPenalty(pathNodeType);
        double e = (double) this.entity.getWidth() / 2.0F;
        if (f >= 0.0F) pathNode = this.method_43569(x, y, z, pathNodeType, f);
        if (isBlocked(nodeType) && pathNode != null && pathNode.penalty >= 0.0F && !this.isBlocked(pathNode)) pathNode = null;
        if (pathNodeType == PathNodeType.WALKABLE || this.isAmphibious() && pathNodeType == PathNodeType.WATER) return pathNode;
        if ((pathNode == null || pathNode.penalty < 0.0F) && maxYStep > 0 && pathNodeType != PathNodeType.FENCE && pathNodeType != PathNodeType.UNPASSABLE_RAIL && pathNodeType != PathNodeType.TRAPDOOR && pathNodeType != PathNodeType.POWDER_SNOW && (pathNode = this.getPathNode(x, y + 1, z, maxYStep - 1, prevFeetY, direction, nodeType)) != null && (pathNode.type == PathNodeType.OPEN || pathNode.type == PathNodeType.WALKABLE) && this.entity.getWidth() < 1.0F && this.checkBoxCollision(new Box((g = (double )(x - direction.getOffsetX()) + 0.5D) - e, LandPathNodeMaker.getFeetY(this.cachedWorld, mutable.set(g, y + 1, h = (double) (z - direction.getOffsetZ()) + 0.5D)) + 0.001D, h - e, g + e, (double) this.entity.getHeight() + LandPathNodeMaker.getFeetY(this.cachedWorld, mutable.set(pathNode.x, pathNode.y, (double)pathNode.z)) - 0.002D, h + e)))
            pathNode = null;

        if (!this.isAmphibious() && pathNodeType == PathNodeType.WATER && !this.canSwim()) {
            if (this.getNodeType(this.entity, x, y - 1, z) != PathNodeType.WATER) {
                return pathNode;
            }
            while (y > this.entity.world.getBottomY()) {
                if ((pathNodeType = this.getNodeType(this.entity, x, --y, z)) == PathNodeType.WATER) {
                    pathNode = this.method_43569(x, y, z, pathNodeType, this.entity.getPathfindingPenalty(pathNodeType));
                    continue;
                }
                return pathNode;
            }
        }
        if (pathNodeType == PathNodeType.OPEN) {
            int i = 0;
            int j = y;
            while (pathNodeType == PathNodeType.OPEN) {
                if (--y < this.entity.world.getBottomY()) return this.method_43570(x, j, z);
                if (i++ >= this.entity.getSafeFallDistance()) return this.method_43570(x, y, z);
                pathNodeType = this.getNodeType(this.entity, x, y, z);
                f = this.entity.getPathfindingPenalty(pathNodeType);
                if (pathNodeType != PathNodeType.OPEN && f >= 0.0F) {
                    pathNode = this.method_43569(x, y, z, pathNodeType, f);
                    break;
                }
                if (!(f < 0.0F)) continue;
                return this.method_43570(x, y, z);
            }
        }
        if (isBlocked(pathNodeType) && (pathNode = this.getNode(x, y, z)) != null) {
            pathNode.visited = true;
            pathNode.type = pathNodeType;
            pathNode.penalty = pathNodeType.getDefaultPenalty();
        }
        return pathNode;
    }

    private static boolean isBlocked(PathNodeType nodeType) {
        return nodeType == PathNodeType.FENCE || nodeType == PathNodeType.DOOR_WOOD_CLOSED || nodeType == PathNodeType.DOOR_IRON_CLOSED;
    }

    private boolean isBlocked(PathNode node) {
        Box box = this.entity.getBoundingBox();
        Vec3d vec3d = new Vec3d((double) node.x - this.entity.getX() + box.getXLength() / 2.0D, (double) node.y - this.entity.getY() + box.getYLength() / 2.0D, (double) node.z - this.entity.getZ() + box.getZLength() / 2.0D);
        int i = MathHelper.ceil(vec3d.length() / box.getAverageSideLength());
        vec3d = vec3d.multiply(1.0F / (float)i);
        for (int j = 1; j <= i; ++j) {
            if (!this.checkBoxCollision(box = box.offset(vec3d))) continue;
            return false;
        }
        return true;
    }

    @Nullable
    private PathNode method_43569(int i, int j, int k, PathNodeType pathNodeType, float f) {
        PathNode pathNode = this.getNode(i, j, k);
        if (pathNode != null) {
            pathNode.type = pathNodeType;
            pathNode.penalty = Math.max(pathNode.penalty, f);
        }
        return pathNode;
    }

    @Nullable
    private PathNode method_43570(int i, int j, int k) {
        PathNode pathNode = this.getNode(i, j, k);
        if (pathNode != null) {
            pathNode.type = PathNodeType.BLOCKED;
            pathNode.penalty = -1.0F;
        }
        return pathNode;
    }

    private boolean checkBoxCollision(Box box) {
        return ((LandPathNodeMakerAccessor) this).getCollidedBoxes().computeIfAbsent(box, object -> !this.cachedWorld.isSpaceEmpty(this.entity, box));
    }
}
