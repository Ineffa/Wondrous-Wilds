package com.ineffa.wondrouswilds.entities;

import com.ineffa.wondrouswilds.blocks.TreeHollowBlock;
import com.ineffa.wondrouswilds.entities.ai.*;
import com.ineffa.wondrouswilds.registry.WondrousWildsSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

import static com.ineffa.wondrouswilds.util.WondrousWildsUtils.HORIZONTAL_DIRECTIONS;
import static com.ineffa.wondrouswilds.util.WondrousWildsUtils.TREE_HOLLOW_MAP;

public class WoodpeckerEntity extends FlyingAndWalkingAnimalEntity implements TreeHollowNester, Angerable, IAnimatable {

    public static final String CLING_POS_KEY = "ClingPos";
    public static final String NEST_POS_KEY = "NestPos";

    public static final int PECKS_NEEDED_FOR_NEST = 200;

    private final Predicate<WoodpeckerEntity> AVOID_WOODPECKER_PREDICATE = otherWoodpecker -> otherWoodpecker.isDrumming() || this.getAttacker() == otherWoodpecker;

    private static final TrackedData<BlockPos> CLING_POS = DataTracker.registerData(WoodpeckerEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<Integer> PECK_CHAIN_LENGTH = DataTracker.registerData(WoodpeckerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> PECK_CHAIN_TICKS = DataTracker.registerData(WoodpeckerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DRUMMING_TICKS = DataTracker.registerData(WoodpeckerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ANGER_TICKS = DataTracker.registerData(WoodpeckerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(10, 15);
    @Nullable
    private UUID angryAt;

    private Direction clingSide;

    private int consecutivePecks;

    @Environment(value = EnvType.SERVER)
    private int cannotEnterNestTicks;
    @Nullable
    @Environment(value = EnvType.SERVER)
    private BlockPos nestPos;

    @Environment(value = EnvType.CLIENT)
    public float flapSpeed;
    @Environment(value = EnvType.CLIENT)
    public float prevFlapAngle;
    @Environment(value = EnvType.CLIENT)
    public float flapAngle;

    public WoodpeckerEntity(EntityType<? extends WoodpeckerEntity> entityType, World world) {
        super(entityType, world);

        this.ignoreCameraFrustum = true;
    }

    public static DefaultAttributeContainer.Builder createWoodpeckerAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.5D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(CLING_POS, BlockPos.ORIGIN);
        this.dataTracker.startTracking(PECK_CHAIN_LENGTH, 0);
        this.dataTracker.startTracking(PECK_CHAIN_TICKS, 0);
        this.dataTracker.startTracking(DRUMMING_TICKS, 0);
        this.dataTracker.startTracking(ANGER_TICKS, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.put(CLING_POS_KEY, NbtHelper.fromBlockPos(this.getClingPos()));

        if (this.hasNestPos()) nbt.put(NEST_POS_KEY, NbtHelper.fromBlockPos(Objects.requireNonNull(this.getNestPos())));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        BlockPos clingPos = NbtHelper.toBlockPos(nbt.getCompound(CLING_POS_KEY));
        if (!this.isClinging() && !clingPos.equals(BlockPos.ORIGIN)) this.startClingingTo(clingPos);

        if (nbt.contains(NEST_POS_KEY)) this.setNestPos(NbtHelper.toBlockPos(nbt.getCompound(NEST_POS_KEY)));
    }

    @Override
    public void setFlying(boolean flying) {
        super.setFlying(flying);

        if (flying && this.isClinging()) this.stopClinging();
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

        if (this.isPecking()) this.stopPecking(true);
        else if (this.getConsecutivePecks() > 0) this.resetConsecutivePecks();
    }

    public boolean hasValidClingPos() {
        return this.canClingToPos(this.getClingPos());
    }

    public boolean canClingToPos(BlockPos pos) {
        BlockState state = this.getWorld().getBlockState(pos);

        if (!(state.getBlock() instanceof PillarBlock)) return false;

        return state.isIn(BlockTags.OVERWORLD_NATURAL_LOGS) && state.get(PillarBlock.AXIS).isVertical();
    }

    public boolean canMakeNestInPos(BlockPos pos) {
        if (!this.canClingToPos(pos)) return false;

        Block block = this.getWorld().getBlockState(pos).getBlock();

        return TREE_HOLLOW_MAP.containsKey(block);
    }

    public boolean isMakingNest() {
        return this.isPecking() || this.getConsecutivePecks() > 0;
    }

    public boolean isPecking() {
        return this.getCurrentPeckChainLength() > 0;
    }

    public int getCurrentPeckChainLength() {
        return this.dataTracker.get(PECK_CHAIN_LENGTH);
    }

    public void setPeckChainLength(int length) {
        this.dataTracker.set(PECK_CHAIN_LENGTH, length);
    }

    public int getPeckChainTicks() {
        return this.dataTracker.get(PECK_CHAIN_TICKS);
    }

    public void setPeckChainTicks(int ticks) {
        this.dataTracker.set(PECK_CHAIN_TICKS, ticks);
    }

    public int calculateTicksForPeckChain(int chainLength) {
        return 10 + (10 * chainLength);
    }

    public void startPeckChain(int length) {
        this.setPeckChainLength(length);
        this.setPeckChainTicks(this.calculateTicksForPeckChain(length));
    }

    public void stopPecking(boolean resetConsecutive) {
        this.setPeckChainLength(0);
        this.setPeckChainTicks(0);

        if (resetConsecutive) this.resetConsecutivePecks();
    }

    public int getConsecutivePecks() {
        return this.consecutivePecks;
    }

    public void setConsecutivePecks(int pecks) {
        this.consecutivePecks = pecks;
    }

    public void resetConsecutivePecks() {
        this.setConsecutivePecks(0);
    }

    public int getDrummingTicks() {
        return this.dataTracker.get(DRUMMING_TICKS);
    }

    public void setDrummingTicks(int ticks) {
        this.dataTracker.set(DRUMMING_TICKS, ticks);
    }

    public boolean isDrumming() {
        return this.getDrummingTicks() > 0;
    }

    public void startDrumming() {
        this.setDrummingTicks(55);
    }

    @Override
    public int getNestCapacityWeight() {
        return this.isBaby() ? 15 : 55;
    }

    @Nullable
    @Override
    public BlockPos getNestPos() {
        return this.nestPos;
    }

    @Override
    public void setNestPos(@Nullable BlockPos pos) {
        this.nestPos = pos;
    }

    @Override
    public int getMinTicksInNest() {
        return 200;
    }

    @Override
    public int getMinTicksOutOfNest() {
        return 400;
    }

    @Override
    public int getCannotInhabitNestTicks() {
        if (this.getWorld().isClient()) return 0;

        return this.cannotEnterNestTicks;
    }

    @Override
    public void setCannotInhabitNestTicks(int ticks) {
        if (this.getWorld().isClient()) return;

        this.cannotEnterNestTicks = ticks;
    }

    @Override
    public boolean shouldReturnToNest() {
        if (this.getCannotInhabitNestTicks() > 0 || !this.hasValidNestPos()) return false;

        return this.getWorld().isNight() || this.getWorld().isRaining();
    }

    @Override
    public boolean defendsNest() {
        return true;
    }

    @Override
    public int getWanderRadiusFromNest() {
        return 64;
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TICKS);
    }

    @Override
    public void setAngerTime(int ticks) {
        this.dataTracker.set(ANGER_TICKS, ticks);
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.getRandom()));
    }

    public boolean canWander() {
        return !this.isClinging() && !this.hasAttackTarget();
    }

    public boolean hasAttackTarget() {
        return this.getTarget() != null;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, WoodpeckerEntity.class, 24.0F, 1.0D, 1.5D, entity -> AVOID_WOODPECKER_PREDICATE.test((WoodpeckerEntity) entity)));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(4, new FindOrReturnToTreeHollowGoal(this, 1.0D, 24, 24));
        this.goalSelector.add(5, new WoodpeckerClingToBlockGoal(this, 1.0D, 24, 24));
        this.goalSelector.add(6, new WoodpeckerWanderLandGoal(this, 1.0D));
        this.goalSelector.add(6, new WoodpeckerWanderFlyingGoal(this));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.add(7, new LookAtEntityGoal(this, MobEntity.class, 16.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
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
            if (this.isDrumming()) {
                if (this.getDrummingTicks() == 45) this.playSound(WondrousWildsSounds.WOODPECKER_DRUM, 4.0F, 1.0F);

                this.setDrummingTicks(this.getDrummingTicks() - 1);
            }

            if (this.isClinging()) {
                if (this.isPecking()) {
                    if (this.getPeckChainTicks() <= 0) this.stopPecking(false);

                    else {
                        if (this.getPeckChainTicks() % 10 == 0 && this.getPeckChainTicks() != this.calculateTicksForPeckChain(this.getCurrentPeckChainLength())) {
                            this.setConsecutivePecks(this.getConsecutivePecks() + 1);

                            if (this.getConsecutivePecks() >= PECKS_NEEDED_FOR_NEST) {
                                this.stopPecking(true);

                                if (this.canMakeNestInPos(this.getClingPos())) {
                                    Block clingBlock = this.getWorld().getBlockState(this.getClingPos()).getBlock();
                                    this.getWorld().setBlockState(this.getClingPos(), TREE_HOLLOW_MAP.get(clingBlock).getDefaultState().with(TreeHollowBlock.FACING, this.clingSide));
                                }
                            }

                            this.getWorld().syncWorldEvent(WorldEvents.BLOCK_BROKEN, this.getClingPos(), Block.getRawIdFromState(this.getWorld().getBlockState(this.getClingPos())));
                        }

                        this.setPeckChainTicks(this.getPeckChainTicks() - 1);
                    }
                }
                else {
                    if (!this.isDrumming()) {
                        if (this.shouldFindNest()) {
                            if (this.getRandom().nextInt(20) == 0 && this.canMakeNestInPos(this.getClingPos())) this.startPeckChain(Math.min(1 + this.getRandom().nextInt(4), PECKS_NEEDED_FOR_NEST - this.getConsecutivePecks()));
                        }
                        else if (this.getRandom().nextInt(200) == 0) this.startDrumming();
                    }
                }

                if ((this.shouldReturnToNest() || !this.hasValidClingPos() || this.getRandom().nextInt(1200) == 0) && !this.isMakingNest() && !this.isDrumming()) this.setFlying(true);
                else {
                    this.setYaw(this.clingSide.getOpposite().getHorizontal() * 90.0F);
                    this.setHeadYaw(this.getYaw());
                    this.setBodyYaw(this.getYaw());
                }
            }

            if (this.getCannotInhabitNestTicks() > 0) this.setCannotInhabitNestTicks(this.getCannotInhabitNestTicks() - 1);

            if (this.hasNestPos()) {
                if (!this.hasValidNestPos()) this.clearNestPos();
            }

            this.tickAngerLogic((ServerWorld) this.getWorld(), false);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (super.damage(source, amount)) {
            if (!this.isFlying() && !this.isDrumming()) this.setFlying(true);

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
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.8D));
            }
            else if (this.isInLava()) {
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.5D));
            }
            else {
                this.updateVelocity(this.getMovementSpeed(), movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.91D));
            }
        }

        this.updateLimbs(this, false);
    }

    @Override
    public boolean canMoveVoluntarily() {
        return super.canMoveVoluntarily() && !this.isClinging();
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
        AnimationController<WoodpeckerEntity> overlapController = new AnimationController<>(this, "overlapController", 0, this::overlapAnimationPredicate);
        AnimationController<WoodpeckerEntity> animationController = new AnimationController<>(this, "animationController", 2, this::animationPredicate);

        animationData.addAnimationController(constantController);
        animationData.addAnimationController(overlapController);
        animationData.addAnimationController(animationController);
    }

    private <E extends IAnimatable> PlayState constantAnimationPredicate(AnimationEvent<E> event) {
        if (this.isFlying())
            event.getController().setAnimation(new AnimationBuilder().addAnimation("flyingConstant"));

        else if (this.isClinging())
            event.getController().setAnimation(new AnimationBuilder().addAnimation("clingingConstant"));

        else if (this.isOnGround())
            event.getController().setAnimation(new AnimationBuilder().addAnimation("groundedConstant"));

        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState overlapAnimationPredicate(AnimationEvent<E> event) {
        if (this.isPecking())
            event.getController().setAnimation(new AnimationBuilder().addAnimation(this.getPeckAnimationToPlay()));

        else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("empty"));

        return PlayState.CONTINUE;
    }

    private String getPeckAnimationToPlay() {
        return switch (this.getCurrentPeckChainLength()) {
            case 1 -> "peck1Overlap";
            case 2 -> "peck2Overlap";
            case 3 -> "peck3Overlap";
            case 4 -> "peck4Overlap";
            default -> "empty";
        };
    }

    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (this.isFlying() && this.limbDistance >= 0.9F)
            event.getController().setAnimation(new AnimationBuilder().addAnimation("flap"));

        else if (this.isDrumming() && this.isClinging())
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drum"));

        else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("empty"));

        return PlayState.CONTINUE;
    }
}
