package com.ineffa.wondrouswilds.entities.ai;

import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class RelaxedBodyControl extends BodyControl {

    private final MobEntity entity;
    private static final float BODY_KEEP_UP_THRESHOLD = 15.0F;
    private float lastHeadYaw;

    public RelaxedBodyControl(MobEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (this.isMoving()) {
            this.entity.bodyYaw = this.entity.getYaw();
            this.keepUpHead();
            this.lastHeadYaw = this.entity.headYaw;
            return;
        }
        if (this.isIndependent()) {
            if (Math.abs(this.entity.headYaw - this.lastHeadYaw) > BODY_KEEP_UP_THRESHOLD) {
                this.lastHeadYaw = this.entity.headYaw;
                this.keepUpBody();
            }
        }
    }

    private void keepUpBody() {
        this.entity.bodyYaw = MathHelper.clampAngle(this.entity.bodyYaw, this.entity.headYaw, this.entity.getMaxHeadRotation());
    }

    private void keepUpHead() {
        this.entity.headYaw = MathHelper.clampAngle(this.entity.headYaw, this.entity.bodyYaw, this.entity.getMaxHeadRotation());
    }

    private boolean isIndependent() {
        return !(this.entity.getFirstPassenger() instanceof MobEntity);
    }

    private boolean isMoving() {
        double e;
        double d = this.entity.getX() - this.entity.prevX;
        return d * d + (e = this.entity.getZ() - this.entity.prevZ) * e > 2.500000277905201E-7;
    }
}
