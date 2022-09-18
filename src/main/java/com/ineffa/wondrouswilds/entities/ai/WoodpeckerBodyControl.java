package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;

public class WoodpeckerBodyControl extends RelaxedBodyControl {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerBodyControl(WoodpeckerEntity entity) {
        super(entity);

        this.woodpecker = entity;
    }

    @Override
    protected boolean shouldSlowlyAdjustBody() {
        return this.woodpecker.isPecking();
    }
}
