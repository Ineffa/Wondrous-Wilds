package com.ineffa.wondrouswilds.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WoodpeckerEntity extends AnimalEntity implements Flutterer, IAnimatable {

    public WoodpeckerEntity(EntityType<? extends WoodpeckerEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createWoodpeckerAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.5));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.add(3, new LookAtEntityGoal(this, MobEntity.class, 16.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    @Override
    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    private final AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<WoodpeckerEntity> constantController = new AnimationController<>(this, "constantController", 2, this::constantAnimationPredicate);

        animationData.addAnimationController(constantController);
    }

    private <ENTITY extends IAnimatable> PlayState constantAnimationPredicate(AnimationEvent<ENTITY> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("groundedConstant"));

        return PlayState.CONTINUE;
    }
}
