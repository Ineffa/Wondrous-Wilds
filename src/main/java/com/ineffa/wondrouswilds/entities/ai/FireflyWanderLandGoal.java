package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;

public class FireflyWanderLandGoal extends WanderAroundFarGoal {

    private final FireflyEntity firefly;

    public FireflyWanderLandGoal(FireflyEntity fireflyEntity, double speed) {
        super(fireflyEntity, speed);

        this.firefly = fireflyEntity;
    }

    @Override
    public boolean canStart() {
        if (this.firefly.isFlying()) return false;

        return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return !this.firefly.isFlying() && super.shouldContinue();
    }

    @Override
    public void stop() {
        super.stop();

        if (this.firefly.getRandom().nextBoolean()) this.firefly.setFlying(true);
    }
}
