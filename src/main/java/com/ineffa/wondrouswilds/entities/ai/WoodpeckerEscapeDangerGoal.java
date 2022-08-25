package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;

public class WoodpeckerEscapeDangerGoal extends FlyingAndWalkingAnimalEscapeDangerGoal {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerEscapeDangerGoal(WoodpeckerEntity woodpecker, double speed, int horizontalFleeRange, int verticalFleeRange) {
        super(woodpecker, speed, horizontalFleeRange, verticalFleeRange);

        this.woodpecker = woodpecker;
    }

    @Override
    public void start() {
        if (this.woodpecker.isClinging()) this.woodpecker.stopClinging();

        super.start();
    }

    @Override
    protected boolean isInDanger() {
        return super.isInDanger() && !(this.mob.getAttacker() instanceof WoodpeckerEntity);
    }
}
