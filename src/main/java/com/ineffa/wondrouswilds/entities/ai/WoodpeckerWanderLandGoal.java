package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class WoodpeckerWanderLandGoal extends WanderAroundFarGoal {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerWanderLandGoal(WoodpeckerEntity woodpeckerEntity, double speed) {
        super(woodpeckerEntity, speed);

        this.woodpecker = woodpeckerEntity;
    }

    @Override
    public boolean canStart() {
        if (this.woodpecker.isFlying() || !this.woodpecker.canWander()) return false;

        return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return !this.woodpecker.isFlying() && super.shouldContinue();
    }

    @Override
    public void stop() {
        super.stop();

        if (this.woodpecker.getRandom().nextBoolean()) this.woodpecker.setFlying(true);
    }

    @Nullable
    @Override
    protected Vec3d getWanderTarget() {
        boolean moveTowardsNest = this.woodpecker.hasValidNestPos() && !Objects.requireNonNull(this.woodpecker.getNestPos()).isWithinDistance(this.woodpecker.getPos(), this.woodpecker.getWanderRadiusFromNest());

        if (moveTowardsNest) {
            Vec3d direction = Vec3d.ofCenter(this.woodpecker.getNestPos()).subtract(this.woodpecker.getPos()).normalize();
            return NoPenaltySolidTargeting.find(this.woodpecker, 10, 7, -2, direction.x, direction.z, 1.5707963705062866D);
        }

        return super.getWanderTarget();
    }
}
