package com.ineffa.wondrouswilds.entities;

import com.ineffa.wondrouswilds.entities.ai.ChipmunkWanderGoal;
import com.ineffa.wondrouswilds.entities.ai.RelaxedBodyControl;
import com.ineffa.wondrouswilds.entities.ai.navigation.JumpNavigation;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import com.ineffa.wondrouswilds.util.WondrousWildsUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ChipmunkEntity extends AnimalEntity implements HoppingMob, IAnimatable {

    @Environment(value = EnvType.CLIENT)
    private double prevMovementTiltAngle;
    @Environment(value = EnvType.CLIENT)
    private double movementTiltAngle;

    public ChipmunkEntity(EntityType<? extends ChipmunkEntity> entityType, World world) {
        super(entityType, world);

        this.ignoreCameraFrustum = true;
    }

    public static DefaultAttributeContainer.Builder createChipmunkAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(4, new TemptGoal(this, 1.0D, Ingredient.ofItems(WondrousWildsItems.ACORN), false));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(10, new ChipmunkWanderGoal(this, 1.0D, 1));
        this.goalSelector.add(11, new LookAtEntityGoal(this, MobEntity.class, 16.0F));
        this.goalSelector.add(12, new LookAroundGoal(this));

        this.targetSelector.add(0, new ActiveTargetGoal<>(this, ArmorStandEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            double verticalSpeed = Math.abs(Math.max(this.prevY, this.getY()) - Math.min(this.prevY, this.getY()));
            double targetAngle = 45.0D * MathHelper.clamp(verticalSpeed * (this.getY() < this.prevY ? 4.0D : -4.0D), -2.0D, 2.0D);

            this.prevMovementTiltAngle = this.movementTiltAngle;
            this.movementTiltAngle = WondrousWildsUtils.stepTowards(this.movementTiltAngle, targetAngle, Math.abs(Math.max(this.movementTiltAngle, targetAngle) - Math.min(this.movementTiltAngle, targetAngle)) * 0.5D);
        }
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.75F;
    }

    @Override
    protected BodyControl createBodyControl() {
        return new RelaxedBodyControl(this);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new JumpNavigation(this, world);
    }

    @Override
    protected Vec3d adjustMovementForSneaking(Vec3d movement, MovementType type) {
        if (movement.getY() <= 0.0D && type == MovementType.SELF && (this.isOnGround() || this.fallDistance < this.stepHeight && !this.getWorld().isSpaceEmpty(this, this.getBoundingBox().offset(0.0, this.fallDistance - this.stepHeight, 0.0)))) {
            double d = movement.x;
            double e = movement.z;
            double f = 0.05D;
            while (d != 0.0D && this.getWorld().isSpaceEmpty(this, this.getBoundingBox().offset(d, -this.stepHeight, 0.0D))) {
                if (d < f && d >= -f) {
                    d = 0.0D;
                    continue;
                }
                if (d > 0.0D) {
                    d -= f;
                    continue;
                }
                d += f;
            }
            while (e != 0.0D && this.getWorld().isSpaceEmpty(this, this.getBoundingBox().offset(0.0D, -this.stepHeight, e))) {
                if (e < f && e >= -f) {
                    e = 0.0D;
                    continue;
                }
                if (e > 0.0D) {
                    e -= f;
                    continue;
                }
                e += f;
            }
            while (d != 0.0D && e != 0.0D && this.getWorld().isSpaceEmpty(this, this.getBoundingBox().offset(d, -this.stepHeight, e))) {
                d = d < f && d >= -f ? 0.0D : (d > 0.0D ? d - f : d + f);
                if (e < f && e >= -f) {
                    e = 0.0D;
                    continue;
                }
                if (e > 0.0D) {
                    e -= f;
                    continue;
                }
                e += f;
            }
            movement = new Vec3d(d, movement.y, e);
        }
        return movement;
    }

    @Override
    public boolean isSneaking() {
        return this.isOnGround() && super.isSneaking();
    }

    @Override
    public boolean isHopping() {
        return !this.getNavigation().isIdle();
    }

    @Override
    public double getMaxJumpVelocity() {
        return 1.0D;
    }

    @Override
    public int getMaxJumpHeight() {
        return 5;
    }

    @Override
    public boolean hasNoDrag() {
        if (this.isHopping() && !this.isOnGround()) return true;
        return super.hasNoDrag();
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return super.computeFallDamage(fallDistance, damageMultiplier) - 20;
    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "constantController", 2, this::constantAnimationPredicate));
    }

    private <E extends IAnimatable> PlayState constantAnimationPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().loop("StandingConstant"));

        return PlayState.CONTINUE;
    }

    @Environment(value = EnvType.CLIENT)
    public double getMovementTiltAngle(float delta) {
        return MathHelper.lerp(delta, this.prevMovementTiltAngle, this.movementTiltAngle);
    }
}
