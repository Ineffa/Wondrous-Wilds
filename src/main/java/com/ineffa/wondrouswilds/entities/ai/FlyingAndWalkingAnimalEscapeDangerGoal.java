package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.util.math.Vec3d;

public class FlyingAndWalkingAnimalEscapeDangerGoal extends EscapeDangerGoal {

    private final FlyingAndWalkingAnimalEntity animal;

    private final int horizontalFleeRange, verticalFleeRange;

    public FlyingAndWalkingAnimalEscapeDangerGoal(FlyingAndWalkingAnimalEntity mob, double speed, int horizontalFleeRange, int verticalFleeRange) {
        super(mob, speed);

        this.animal = mob;

        this.horizontalFleeRange = horizontalFleeRange;
        this.verticalFleeRange = verticalFleeRange;
    }

    @Override
    public void start() {
        super.start();

        if (!this.animal.isFlying()) this.animal.setFlying(true);
    }

    @Override
    protected boolean findTarget() {
        Vec3d target;

        Vec3d direction = this.mob.getRotationVec(0.0F);

        Vec3d groundLocation = AboveGroundTargeting.find(this.mob, this.horizontalFleeRange, this.verticalFleeRange, direction.x, direction.z, 1.5707964F, 6, 1);
        if (groundLocation != null) target = groundLocation;

        else target = NoPenaltySolidTargeting.find(this.mob, this.horizontalFleeRange, this.verticalFleeRange, -2, direction.x, direction.z, 1.5707963705062866D);

        if (target == null) return false;

        this.targetX = target.x;
        this.targetY = target.y;
        this.targetZ = target.z;
        return true;
    }
}
