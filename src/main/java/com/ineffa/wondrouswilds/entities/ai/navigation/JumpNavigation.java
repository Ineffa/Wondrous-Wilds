package com.ineffa.wondrouswilds.entities.ai.navigation;

import com.ineffa.wondrouswilds.entities.HoppingMob;
import com.ineffa.wondrouswilds.mixin.EntityNavigationInvoker;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class JumpNavigation extends MobNavigation {

    protected final HoppingMob hoppingMob;

    public JumpNavigation(HoppingMob hoppingMob, World world) {
        super((MobEntity) hoppingMob, world);
        this.hoppingMob = hoppingMob;
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new JumpPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(false);
        this.nodeMaker.setCanOpenDoors(false);
        //this.nodeMaker.setCanSwim(false);
        return new JumpPathNodeNavigator((JumpPathNodeMaker) this.nodeMaker, range);
    }

    @Override
    public void tick() {
        ++this.tickCount;
        if (this.inRecalculationCooldown) this.recalculatePath();

        if (this.isIdle()) return;

        if (this.isAtValidPosition()) this.continueFollowingPath();
        else if (this.getCurrentPath() != null && !this.getCurrentPath().isFinished()) {
            Vec3d pos = this.getPos();
            Vec3d vec3d2 = this.getCurrentPath().getNodePosition(this.entity);
            if (pos.y > vec3d2.y && !this.entity.isOnGround() && MathHelper.floor(pos.x) == MathHelper.floor(vec3d2.x) && MathHelper.floor(pos.z) == MathHelper.floor(vec3d2.z))
                this.getCurrentPath().next();
        }

        DebugInfoSender.sendPathfindingData(this.world, this.entity, this.getCurrentPath(), this.nodeReachProximity);

        if (this.isIdle()) return;

        // Debug
        if (this.currentPath != null) {
            for (int i = 0; i < this.currentPath.getLength(); ++i) {
                PathNode node = this.currentPath.getNode(i);
                ((ServerWorld) this.world).spawnParticles(ParticleTypes.SMOKE, node.x + 0.5D, node.y + 0.01D, node.z + 0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        if (this.getTargetPos() != null)
            ((ServerWorld) this.world).spawnParticles(ParticleTypes.FLAME, this.getTargetPos().getX() + 0.5D, this.getTargetPos().getY() + 0.01D, this.getTargetPos().getZ() + 0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void continueFollowingPath() {
        super.continueFollowingPath();
        if (this.isIdle()) return;

        if (this.getCurrentPath().getCurrentNode() instanceof JumpablePathNode currentNode) {
            if (currentNode.shouldBeJumpedTo()) {
                if (!this.tryJumpingToCurrentNode()) ((EntityNavigationInvoker) this).invokeResetNodeAndStop();
            }
            else {
                Vec3d positionToMoveTo = this.getCurrentPath().getNodePosition(this.entity);
                this.entity.getMoveControl().moveTo(positionToMoveTo.x, this.adjustTargetY(positionToMoveTo), positionToMoveTo.z, this.speed);
            }
        }
    }

    protected boolean tryJumpingToCurrentNode() {
        Vec3d nodePos = Vec3d.ofBottomCenter(this.getCurrentPath().getCurrentNodePos());
        Vec3d jumpVec = tryCreatingJumpBetween(this.entity, this.entity.getPos(), nodePos, 40, 80);
        if (jumpVec != null) {
            this.entity.setVelocity(jumpVec);

            double deltaX = nodePos.getX() - this.entity.getX();
            double deltaZ = nodePos.getZ() - this.entity.getZ();
            double yaw = Math.atan2(deltaZ, deltaX) * (180.0D / Math.PI) - 90.0D;
            this.entity.setYaw((float) yaw);

            return true;
        }

        return false;
    }

    @Nullable
    public static Vec3d tryCreatingJumpBetween(MobEntity entity, Vec3d from, Vec3d to, int lowerAngleBound, int upperAngleBound) {
        int density = 5;
        int angleBound = upperAngleBound;
        int currentAngle = lowerAngleBound;
        boolean reverse = false;
        while (reverse ? currentAngle >= angleBound : currentAngle <= angleBound) {
            Vec3d jumpVec = getJumpVector(from, to, currentAngle);
            currentAngle += reverse ? -5 : 5;
            if (jumpVec == null) continue;

            if (Double.isNaN(jumpVec.x) || Double.isNaN(jumpVec.y) || Double.isNaN(jumpVec.z)) {
                if (reverse) break;

                reverse = true;
                angleBound = lowerAngleBound;
                currentAngle = upperAngleBound;
                continue;
            }

            boolean validJump = true;
            double e = 5.0D * (5.0D * jumpVec.y + Math.sqrt(25.0D * jumpVec.y * jumpVec.y - 4.0D * (to.y - from.y))) * density;
            for (double t = 0.0D; t < e; t += (1.0D / density)) {
                double x = t * jumpVec.x;
                double y = t * jumpVec.y - 0.04D * t * t;
                double z = t * jumpVec.z;
                Vec3d vec3d = from.add(x, y, z);

                EntityDimensions entityDimensions = entity.getDimensions(EntityPose.LONG_JUMPING);
                Box box = entityDimensions.getBoxAt(vec3d.add(0.0D, entityDimensions.height / 2, 0.0D));
                if (!entity.getWorld().containsFluid(box) && entity.getWorld().isSpaceEmpty(entity, box)) {
                    // Debug
                    ((ServerWorld) entity.getWorld()).spawnParticles(ParticleTypes.HAPPY_VILLAGER, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                    continue;
                }

                if (vec3d.distanceTo(to) <= 0.5D) break;

                // Debug
                ((ServerWorld) entity.getWorld()).spawnParticles(ParticleTypes.ANGRY_VILLAGER, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                validJump = false;
                break;
            }
            if (!validJump) continue;

            return jumpVec;
        }

        return null;
    }

    @Nullable
    public static Vec3d getJumpVector(Vec3d start, Vec3d land, float angle) {
        Vec3d to = land.subtract(start);
        double d = Math.sqrt(to.x * to.x + to.z * to.z);
        double s = to.y;
        double tan = Math.tan(Math.toRadians(angle));
        double k = 5 * Math.sqrt(d * tan - s);
        double vy = (d * tan) / k;
        double vh = d / k;
        double theta = Math.atan2(to.z, to.x);
        double vx = Math.cos(theta) * vh;
        double vz = Math.sin(theta) * vh;

        Vec3d vec3d = new Vec3d(vx, vy, vz);
        if (vec3d.length() <= 0.0D) return null;

        return vec3d;
    }
}
