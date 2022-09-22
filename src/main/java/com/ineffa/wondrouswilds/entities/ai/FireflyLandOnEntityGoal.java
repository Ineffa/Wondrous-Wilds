package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsAdvancementCriteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.EnumSet;

public class FireflyLandOnEntityGoal extends Goal {

    private final FireflyEntity firefly;
    private final World world;
    private final Class<? extends LivingEntity> classToTarget;

    private LivingEntity entityToLandOn;

    private final TargetPredicate LAND_ON_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0D).setPredicate(this::canEntityBeLandedOn);

    public FireflyLandOnEntityGoal(FireflyEntity firefly, Class<? extends LivingEntity> classToTarget) {
        this.firefly = firefly;
        this.world = firefly.getWorld();
        this.classToTarget = classToTarget;

        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (!this.firefly.canSearchForEntityToLandOn()) return false;

        if (this.firefly.getRandom().nextInt(400) != 0) return false;

        return this.findEntityToLandOn();
    }

    @Override
    public void start() {
        if (!this.firefly.isFlying()) this.firefly.setFlying(true);
    }

    @Override
    public boolean shouldContinue() {
        return this.firefly.canSearchForEntityToLandOn() && this.canEntityBeLandedOn(this.entityToLandOn) && this.firefly.canSee(this.entityToLandOn) && this.firefly.distanceTo(this.entityToLandOn) <= 16.0F;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        this.firefly.getNavigation().startMovingTo(this.entityToLandOn, 1.0D);

        if (this.firefly.distanceTo(this.entityToLandOn) <= 1.0F) {
            this.firefly.setLandOnEntityCooldown(2400);

            this.firefly.setFlying(false);

            this.firefly.startRiding(this.entityToLandOn);

            if (this.world instanceof ServerWorld serverWorld && this.entityToLandOn instanceof ServerPlayerEntity playerToLandOn) {
                WondrousWildsAdvancementCriteria.FIREFLY_LANDED_ON_HEAD.trigger(playerToLandOn, this.firefly);

                for (ServerPlayerEntity player : serverWorld.getPlayers()) player.networkHandler.sendPacket(new EntityPassengersSetS2CPacket(playerToLandOn));
            }
        }
    }

    private boolean findEntityToLandOn() {
        return (this.entityToLandOn = this.world.getClosestEntity(this.classToTarget, LAND_ON_PREDICATE, this.firefly, this.firefly.getX(), this.firefly.getY(), this.firefly.getZ(), this.firefly.getBoundingBox().expand(8.0D))) != null;
    }

    private boolean canEntityBeLandedOn(LivingEntity entity) {
        return entity.getClass() != this.firefly.getClass() && !entity.hasPassengers();
    }
}
