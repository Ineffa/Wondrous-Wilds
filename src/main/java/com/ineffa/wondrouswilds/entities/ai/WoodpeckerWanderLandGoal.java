package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;

public class WoodpeckerWanderLandGoal extends WanderAroundFarGoal {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerWanderLandGoal(WoodpeckerEntity woodpeckerEntity, double speed) {
        super(woodpeckerEntity, speed);

        this.woodpecker = woodpeckerEntity;
    }

    @Override
    public boolean canStart() {
        if (this.woodpecker.isFlying()) return false;

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
}
