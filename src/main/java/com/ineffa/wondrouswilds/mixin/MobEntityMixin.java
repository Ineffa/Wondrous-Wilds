package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends Entity {

    @Shadow @Final protected GoalSelector goalSelector;

    private MobEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "updateGoalControls", cancellable = true)
    private void stopFireflyFromControlling(CallbackInfo callback) {
        boolean shouldNotBeControlled = !(this.getPrimaryPassenger() instanceof MobEntity) || this.getPrimaryPassenger() instanceof FireflyEntity;
        boolean isNotRidingBoat = !(this.getVehicle() instanceof BoatEntity);

        this.goalSelector.setControlEnabled(Goal.Control.MOVE, shouldNotBeControlled);
        this.goalSelector.setControlEnabled(Goal.Control.JUMP, shouldNotBeControlled && isNotRidingBoat);
        this.goalSelector.setControlEnabled(Goal.Control.LOOK, shouldNotBeControlled);

        callback.cancel();
    }
}
