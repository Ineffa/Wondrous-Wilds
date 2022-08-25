package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

public class FlyingAndWalkingAnimalFleeEntityGoal<T extends LivingEntity> extends FleeEntityGoal<T> {

    private final FlyingAndWalkingAnimalEntity animal;
    private final TargetPredicate fleeFromPredicate;
    private final double slowSpeed, fastSpeed;
    private Vec3d targetPos;

    public FlyingAndWalkingAnimalFleeEntityGoal(FlyingAndWalkingAnimalEntity fleeingAnimal, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed) {
        this(fleeingAnimal, classToFleeFrom, fleeDistance, fleeSlowSpeed, fleeFastSpeed, livingEntity -> true);
    }

    public FlyingAndWalkingAnimalFleeEntityGoal(FlyingAndWalkingAnimalEntity fleeingAnimal, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed, Predicate<LivingEntity> inclusionSelector) {
        super(fleeingAnimal, classToFleeFrom, fleeDistance, fleeSlowSpeed, fleeFastSpeed, inclusionSelector);

        this.animal = fleeingAnimal;
        this.slowSpeed = fleeSlowSpeed;
        this.fastSpeed = fleeFastSpeed;

        this.fleeFromPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(fleeDistance).setPredicate(inclusionSelector);
    }

    @Override
    public boolean canStart() {
        this.targetEntity = this.animal.getWorld().getClosestEntity(this.animal.getWorld().getEntitiesByClass(this.classToFleeFrom, this.animal.getBoundingBox().expand(this.fleeDistance, this.fleeDistance, this.fleeDistance), livingEntity -> true), this.fleeFromPredicate, this.animal, this.animal.getX(), this.animal.getY(), this.animal.getZ());
        if (this.targetEntity == null) return false;

        Vec3d direction = Vec3d.ofCenter(this.targetEntity.getBlockPos()).subtract(this.animal.getPos()).normalize().negate();

        Vec3d fleePos;

        Vec3d groundLocation = AboveGroundTargeting.find(this.animal, 24, 12, direction.x, direction.z, 1.5707964F, 12, 1);
        if (groundLocation != null) fleePos = groundLocation;

        else fleePos = NoPenaltySolidTargeting.find(this.animal, 24, 12, -2, direction.x, direction.z, 1.5707963705062866D);

        if (fleePos == null) return false;

        this.targetPos = fleePos;
        return true;
    }

    @Override
    public void start() {
        if (!this.animal.isFlying()) this.animal.setFlying(true);

        double speed = this.mob.squaredDistanceTo(this.targetEntity) < 49.0D ? this.fastSpeed : this.slowSpeed;
        this.animal.getNavigation().startMovingTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), speed);
    }
}
