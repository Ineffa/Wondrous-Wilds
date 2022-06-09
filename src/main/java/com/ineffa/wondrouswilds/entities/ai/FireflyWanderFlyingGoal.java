package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class FireflyWanderFlyingGoal extends Goal {

    private final FireflyEntity firefly;

    public FireflyWanderFlyingGoal(FireflyEntity fireflyEntity) {
        this.setControls(EnumSet.of(Goal.Control.MOVE));

        this.firefly = fireflyEntity;
    }

    @Override
    public boolean canStart() {
        if (!this.firefly.isFlying()) return false;

        return this.firefly.getNavigation().isIdle() && this.firefly.getRandom().nextInt(10) == 0;
    }

    @Override
    public boolean shouldContinue() {
        return this.firefly.isFlying() && this.firefly.getNavigation().isFollowingPath();
    }

    @Override
    public void start() {
        Vec3d vec3d = this.getRandomLocation();
        if (vec3d != null) this.firefly.getNavigation().startMovingAlong(this.firefly.getNavigation().findPathTo(new BlockPos(vec3d), 1), 1.0D);
    }

    @Override
    public void stop() {
        if (!this.firefly.wantsToLand()) {
            if (this.firefly.getRandom().nextInt(10) == 0) this.firefly.setWantsToLand(true);
        }
        else this.firefly.setFlying(false);
    }

    @Nullable
    private Vec3d getRandomLocation() {
        Vec3d vec3d = this.firefly.getRotationVec(0.0f);
        Vec3d vec3d3 = AboveGroundTargeting.find(this.firefly, 8, 8, vec3d.x, vec3d.z, 1.5707964f, this.firefly.wantsToLand() ? 1 : 6, 1);

        if (vec3d3 != null) return vec3d3;

        return NoPenaltySolidTargeting.find(this.firefly, 16, 8, -2, vec3d.x, vec3d.z, 1.5707963705062866);
    }
}
