package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.entity.LivingEntity;

import java.util.function.Predicate;

public class WoodpeckerFleeEntityGoal<T extends LivingEntity> extends FlyingAndWalkingAnimalFleeEntityGoal<T> {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerFleeEntityGoal(WoodpeckerEntity fleeingWoodpecker, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed) {
        this(fleeingWoodpecker, classToFleeFrom, fleeDistance, fleeSlowSpeed, fleeFastSpeed, livingEntity -> true);
    }

    public WoodpeckerFleeEntityGoal(WoodpeckerEntity fleeingWoodpecker, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed, Predicate<LivingEntity> inclusionSelector) {
        super(fleeingWoodpecker, classToFleeFrom, fleeDistance, fleeSlowSpeed, fleeFastSpeed, inclusionSelector);

        this.woodpecker = fleeingWoodpecker;
    }

    @Override
    public void start() {
        if (this.woodpecker.isClinging()) this.woodpecker.stopClinging();

        super.start();
    }
}
