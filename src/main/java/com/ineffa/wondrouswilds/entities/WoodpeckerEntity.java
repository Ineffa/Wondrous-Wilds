package com.ineffa.wondrouswilds.entities;

import com.ineffa.wondrouswilds.entities.ai.RelaxedBodyControl;
import com.ineffa.wondrouswilds.entities.ai.WoodpeckerClingToBlockGoal;
import com.ineffa.wondrouswilds.entities.ai.WoodpeckerWanderFlyingGoal;
import com.ineffa.wondrouswilds.entities.ai.WoodpeckerWanderLandGoal;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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

import static com.ineffa.wondrouswilds.util.WondrousWildsUtils.HORIZONTAL_DIRECTIONS;

public class WoodpeckerEntity extends AnimalEntity implements Flutterer, IAnimatable {

    private static final TrackedData<Boolean> IS_FLYING = DataTracker.registerData(WoodpeckerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> WANTS_TO_LAND = DataTracker.registerData(WoodpeckerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<BlockPos> CLING_POS = DataTracker.registerData(WoodpeckerEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);

    public static final String IS_FLYING_KEY = "IsFlying";
    public static final String WANTS_TO_LAND_KEY = "WantsToLand";
    public static final String CLING_POS_KEY = "ClingPos";

    private Direction clingSide;

    @Environment(value = EnvType.CLIENT)
    public float flapSpeed;
    @Environment(value = EnvType.CLIENT)
    public float prevFlapAngle;
    @Environment(value = EnvType.CLIENT)
    public float flapAngle;

    private final FlightMoveControl airMoveControl;
    private final MoveControl landMoveControl;

    private final BirdNavigation airNavigation;
    private final MobNavigation landNavigation;

    public WoodpeckerEntity(EntityType<? extends WoodpeckerEntity> entityType, World world) {
        super(entityType, world);

        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanEnterOpenDoors(true);
        birdNavigation.setCanSwim(false);
        this.airNavigation = birdNavigation;
        this.landNavigation = new MobNavigation(this, world);

        this.airMoveControl = new FlightMoveControl(this, 20, true);
        this.landMoveControl = new MoveControl(this);

        this.ignoreCameraFrustum = true;
    }

    public static DefaultAttributeContainer.Builder createWoodpeckerAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(IS_FLYING, false);
        this.dataTracker.startTracking(WANTS_TO_LAND, false);
        this.dataTracker.startTracking(CLING_POS, BlockPos.ORIGIN);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putBoolean(IS_FLYING_KEY, this.isFlying());
        nbt.putBoolean(WANTS_TO_LAND_KEY, this.wantsToLand());
        nbt.put(CLING_POS_KEY, NbtHelper.fromBlockPos(this.getClingPos()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        boolean isFlying = nbt.getBoolean(IS_FLYING_KEY);
        if (this.isFlying() != isFlying) this.setFlying(isFlying);

        this.setWantsToLand(nbt.getBoolean(WANTS_TO_LAND_KEY));

        BlockPos clingPos = NbtHelper.toBlockPos(nbt.getCompound(CLING_POS_KEY));
        if (!this.isClinging() && !clingPos.equals(BlockPos.ORIGIN)) this.startClingingTo(clingPos);
    }

    public boolean isFlying() {
        return this.dataTracker.get(IS_FLYING);
    }

    public void setIsFlying(boolean isFlying) {
        this.dataTracker.set(IS_FLYING, isFlying);
    }

    public void setFlying(boolean flying) {
        this.setIsFlying(flying);

        if (!flying) {
            this.setNoGravity(false);
            this.setWantsToLand(false);
        }
        else if (this.isClinging()) this.stopClinging();

        this.moveControl = flying ? this.airMoveControl : this.landMoveControl;
        this.navigation = flying ? this.airNavigation : this.landNavigation;
    }

    public boolean wantsToLand() {
        return this.dataTracker.get(WANTS_TO_LAND);
    }

    public void setWantsToLand(boolean wantsToLand) {
        this.dataTracker.set(WANTS_TO_LAND, wantsToLand);
    }

    public BlockPos getClingPos() {
        return this.dataTracker.get(CLING_POS);
    }

    public void setClingPos(BlockPos pos) {
        this.dataTracker.set(CLING_POS, pos);
    }

    public boolean isClinging() {
        return this.dataTracker.get(CLING_POS) != BlockPos.ORIGIN;
    }

    public void startClingingTo(BlockPos clingPos) {
        this.setClingPos(clingPos);

        Direction clingSide = Direction.fromHorizontal(this.getRandom().nextInt(4));
        double closestSideDistance = 100.0D;
        for (Direction side : HORIZONTAL_DIRECTIONS) {
            double distanceFromSide = this.getBlockPos().getSquaredDistance(clingPos.offset(side));
            if (distanceFromSide < closestSideDistance) {
                clingSide = side;
                closestSideDistance = distanceFromSide;
            }
        }
        this.clingSide = clingSide;

        BlockPos pos = clingPos.offset(clingSide);
        this.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

        this.setFlying(false);
    }

    public void stopClinging() {
        this.setClingPos(BlockPos.ORIGIN);
    }

    public boolean hasValidClingPos() {
        return this.canClingToPos(this.getClingPos());
    }

    public boolean canClingToPos(BlockPos pos) {
        BlockState state = this.getWorld().getBlockState(pos);

        if (!(state.getBlock() instanceof PillarBlock)) return false;

        return state.isIn(BlockTags.OVERWORLD_NATURAL_LOGS) && state.get(PillarBlock.AXIS).isVertical();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(2, new WoodpeckerClingToBlockGoal(this, 1.0D, 24, 24));
        this.goalSelector.add(3, new WoodpeckerWanderLandGoal(this, 1.0D));
        this.goalSelector.add(3, new WoodpeckerWanderFlyingGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.add(4, new LookAtEntityGoal(this, MobEntity.class, 16.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        if (this.isFlying()) {
            BirdNavigation birdNavigation = new BirdNavigation(this, world);
            birdNavigation.setCanPathThroughDoors(false);
            birdNavigation.setCanEnterOpenDoors(true);
            birdNavigation.setCanSwim(false);

            return birdNavigation;
        }

        return new MobNavigation(this, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.flapSpeed = MathHelper.clamp(1.0F - (this.limbDistance * 0.5F), 0.0F, 1.0F);
            this.prevFlapAngle = this.flapAngle;
            this.flapAngle += this.flapSpeed;
        }
        else {
            if (this.isClinging()) {
                if (!this.hasValidClingPos() || this.getRandom().nextInt(1200) == 0) this.setFlying(true);
                else {
                    this.setYaw(this.clingSide.getOpposite().getHorizontal() * 90);
                    this.setHeadYaw(this.getYaw());
                    this.setBodyYaw(this.getYaw());
                }
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (super.damage(source, amount)) {
            if (!this.isFlying()) this.setFlying(true);

            return true;
        }
        else return false;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (!this.isFlying()) {
            super.travel(movementInput);
            return;
        }

        if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
            if (this.isTouchingWater()) {
                this.updateVelocity(0.02f, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.8f));
            }
            else if (this.isInLava()) {
                this.updateVelocity(0.02f, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.5));
            }
            else {
                this.updateVelocity(this.getMovementSpeed(), movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.91f));
            }
        }

        this.updateLimbs(this, false);
    }

    @Override
    public boolean canMoveVoluntarily() {
        return super.canMoveVoluntarily() && !this.isClinging();
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    @Override
    protected boolean hasWings() {
        return this.isFlying();
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.95F;
    }

    @Override
    protected BodyControl createBodyControl() {
        return new RelaxedBodyControl(this);
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
        //AnimationController<WoodpeckerEntity> overlapController = new AnimationController<>(this, "overlapController", 2, this::overlapAnimationPredicate);
        AnimationController<WoodpeckerEntity> animationController = new AnimationController<>(this, "animationController", 2, this::animationPredicate);

        animationData.addAnimationController(constantController);
        animationData.addAnimationController(animationController);
    }

    private <ENTITY extends IAnimatable> PlayState constantAnimationPredicate(AnimationEvent<ENTITY> event) {
        if (this.isFlying()) event.getController().setAnimation(new AnimationBuilder().addAnimation("flyingConstant"));

        else if (this.isClinging()) event.getController().setAnimation(new AnimationBuilder().addAnimation("clingingConstant"));

        else if (this.isOnGround()) event.getController().setAnimation(new AnimationBuilder().addAnimation("groundedConstant"));

        return PlayState.CONTINUE;
    }

    private <ENTITY extends IAnimatable> PlayState animationPredicate(AnimationEvent<ENTITY> event) {
        if (this.isFlying() && this.limbDistance >= 0.9F) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("flap"));
            return PlayState.CONTINUE;
        }
        else return PlayState.STOP;
    }
}
