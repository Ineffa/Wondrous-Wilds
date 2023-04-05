package com.ineffa.wondrouswilds.entities.ai;

import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class ChipmunkWanderGoal extends WanderAroundFarGoal {

    protected final int chance;

    public ChipmunkWanderGoal(PathAwareEntity pathAwareEntity, double speed, int chance) {
        super(pathAwareEntity, speed);

        this.chance = chance;
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() {
        if (this.mob.isInsideWaterOrBubbleColumn()) {
            Vec3d vec3d = FuzzyTargeting.find(this.mob, 15, 7);
            return vec3d == null ? super.getWanderTarget() : vec3d;
        }

        if (this.mob.getRandom().nextInt(this.chance) == 0) return FuzzyTargeting.find(this.mob, 32, 8);

        return super.getWanderTarget();
    }
}
