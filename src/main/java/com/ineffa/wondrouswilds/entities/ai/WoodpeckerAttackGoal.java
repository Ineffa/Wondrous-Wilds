package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class WoodpeckerAttackGoal extends MeleeAttackGoal {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerAttackGoal(WoodpeckerEntity woodpecker, double speed, boolean pauseWhenMobIdle) {
        super(woodpecker, speed, pauseWhenMobIdle);

        this.woodpecker = woodpecker;
    }

    @Override
    public void start() {
        super.start();

        if (!this.woodpecker.isFlying()) this.woodpecker.setFlying(true);
    }

    @Override
    public void stop() {
        super.stop();

        if (this.woodpecker.isPecking()) this.woodpecker.stopPecking(true);
    }

    @Override
    protected void attack(LivingEntity target, double squaredDistance) {
        double maxDistance = this.getSquaredMaxAttackDistance(target);
        if (squaredDistance <= maxDistance) {
            if (this.woodpecker.isPecking())
                this.woodpecker.setVelocity(this.woodpecker.getVelocity().multiply(0.9D));

            else if (this.isCooledDown()) {
                this.resetCooldown();

                this.woodpecker.startPeckChain(1);
            }
        }
    }
}
