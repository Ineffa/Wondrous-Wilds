package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class RelaxedBodyControl extends BodyControl {

    protected final MobEntity entity;
    protected static final float BODY_KEEP_UP_THRESHOLD = 15.0F;
    protected float lastHeadYaw;
    protected int bodyAdjustTicks;

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
            this.bodyAdjustTicks = 0;
            return;
        }
        if (this.isIndependent()) {
            if (Math.abs(this.entity.headYaw - this.lastHeadYaw) > BODY_KEEP_UP_THRESHOLD) {
                this.bodyAdjustTicks = 0;
                this.lastHeadYaw = this.entity.headYaw;
                this.keepUpBody();
            }
            else if (this.shouldSlowlyAdjustBody()) {
                ++this.bodyAdjustTicks;
                if (this.bodyAdjustTicks > 10) this.slowlyAdjustBody();

                this.entity.setYaw(this.entity.getBodyYaw()); // Fixes desync present in vanilla body controller
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
        Entity firstPassenger = this.entity.getFirstPassenger();

        if (firstPassenger instanceof FireflyEntity) return true;

        return !(firstPassenger instanceof MobEntity);
    }

    private boolean isMoving() {
        double e;
        double d = this.entity.getX() - this.entity.prevX;
        return d * d + (e = this.entity.getZ() - this.entity.prevZ) * e > 2.500000277905201E-7;
    }

    private void slowlyAdjustBody() {
        int i = this.bodyAdjustTicks - 10;
        float f = MathHelper.clamp((float)i / 10.0f, 0.0f, 1.0f);
        float g = (float)this.entity.getMaxHeadRotation() * (1.0f - f);
        this.entity.bodyYaw = MathHelper.clampAngle(this.entity.bodyYaw, this.entity.headYaw, g);
    }

    protected boolean shouldSlowlyAdjustBody() {
        return false;
    }
}
