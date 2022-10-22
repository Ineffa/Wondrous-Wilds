package com.ineffa.wondrouswilds.blocks.entity;

import com.ineffa.wondrouswilds.blocks.InhabitableNestBlock;
import com.ineffa.wondrouswilds.entities.BlockNester;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.ineffa.wondrouswilds.registry.WondrousWildsEntities.DEFAULT_NESTER_CAPACITY_WEIGHTS;

public interface InhabitableNestBlockEntity {

    String INHABITANTS_KEY = "Inhabitants";
    String IS_FRESH_KEY = "IsFresh";
    String ENTITY_DATA_KEY = "EntityData";
    String CAPACITY_WEIGHT_KEY = "CapacityWeight";
    String TICKS_IN_NEST_KEY = "TicksInNest";
    String MIN_OCCUPATION_TICKS_KEY = "MinOccupationTicks";
    String IS_BABY_KEY = "IsBaby";

    List<String> IRRELEVANT_INHABITANT_NBT_KEYS = Arrays.asList("CannotEnterNestTicks", "Air", "DeathTime", "FallDistance", "FallFlying", "Fire", "HurtByTimestamp", "HurtTime", "Motion", "OnGround", "PortalCooldown", "Pos", "Rotation", "Passengers", "Leash", "UUID");

    List<Inhabitant> getInhabitants();

    static void removeIrrelevantNbt(NbtCompound nbtCompound) {
        for (String key : IRRELEVANT_INHABITANT_NBT_KEYS) nbtCompound.remove(key);
    }

    default NbtList getInhabitantsNbt() {
        NbtList nbtList = new NbtList();
        for (Inhabitant inhabitant : this.getInhabitants()) {
            NbtCompound nbt = new NbtCompound();

            nbt.putBoolean(IS_FRESH_KEY, inhabitant.isFresh);
            nbt.put(ENTITY_DATA_KEY, inhabitant.entityData.copy());
            nbt.putInt(CAPACITY_WEIGHT_KEY, inhabitant.capacityWeight);
            nbt.putInt(MIN_OCCUPATION_TICKS_KEY, inhabitant.minOccupationTicks);
            nbt.putInt(TICKS_IN_NEST_KEY, inhabitant.ticksInNest);
            nbt.putBoolean(IS_BABY_KEY, inhabitant.isBaby);

            nbtList.add(nbt);
        }
        return nbtList;
    }

    default int getInhabitantCount() {
        return this.getInhabitants().size();
    }

    default int getBabyInhabitantCount() {
        int babyCount = 0;

        for (Inhabitant inhabitant : this.getInhabitants()) if (inhabitant.isBaby) ++babyCount;

        return babyCount;
    }

    default boolean hasInhabitants() {
        return !this.getInhabitants().isEmpty();
    }

    default int getMaxCapacity() {
        return 100;
    }

    default int getMaxBabyInhabitantCount() {
        return 3;
    }

    default int getUsedCapacity() {
        int usedCapacity = 0;

        for (Inhabitant inhabitant : this.getInhabitants()) usedCapacity += inhabitant.capacityWeight;

        return usedCapacity;
    }

    default int getRemainingCapacity() {
        return this.getMaxCapacity() - this.getUsedCapacity();
    }

    default boolean isCapacityFilled() {
        return this.getUsedCapacity() >= this.getMaxCapacity();
    }

    default boolean canFitNester(BlockNester nester) {
        return nester.getNestCapacityWeight() <= this.getRemainingCapacity();
    }

    default boolean tryAddingInhabitant(BlockNester nester) {
        if (!(nester instanceof MobEntity nesterEntity)) return false;

        boolean canFit = !this.isCapacityFilled() && this.canFitNester(nester) && !(nesterEntity.isBaby() && this.getBabyInhabitantCount() >= this.getMaxBabyInhabitantCount());

        if (this.hasInhabitants() && this.alertInhabitants(InhabitantReleaseReason.ALERT, canFit ? InhabitantAlertScenario.VISITOR : InhabitantAlertScenario.INTRUSION, nesterEntity)) return false;

        if (!canFit) return false;

        nesterEntity.stopRiding();
        nesterEntity.removeAllPassengers();

        this.addInhabitant(nester);

        World world = this.getNestWorld();
        if (world != null) {
            BlockPos pos = this.getNestPos();

            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(nesterEntity, this.getNestCachedState()));
        }

        nesterEntity.discard();

        this.markNestDirty();

        return true;
    }

    default void addInhabitant(BlockNester inhabitant) {
        if (!(inhabitant instanceof MobEntity entity)) return;

        NbtCompound entityData = new NbtCompound();
        entity.saveNbt(entityData);

        this.getInhabitants().add(new Inhabitant(false, entityData, inhabitant.getNestCapacityWeight(), 0, inhabitant.getMinTicksInNest(), entity.isBaby()));
    }

    default void addFreshInhabitant(EntityType<?> entityType, boolean isBaby) {
        if (!DEFAULT_NESTER_CAPACITY_WEIGHTS.containsKey(entityType)) return;

        NbtCompound entityData = new NbtCompound();
        entityData.putString(Entity.ID_KEY, Registry.ENTITY_TYPE.getId(entityType).toString());
        entityData.put(BlockNester.NEST_POS_KEY, NbtHelper.fromBlockPos(this.getNestPos()));

        this.getInhabitants().add(new Inhabitant(true, entityData, DEFAULT_NESTER_CAPACITY_WEIGHTS.get(entityType), 0, 600, isBaby));
    }

    default boolean alertInhabitants(InhabitantReleaseReason reason, @Nullable InhabitableNestBlockEntity.InhabitantAlertScenario scenario, @Nullable LivingEntity visitor) {
        return this.alertInhabitants(Objects.requireNonNull(this.getNestWorld()).getBlockState(this.getNestPos()), reason, scenario, visitor);
    }

    default boolean alertInhabitants(BlockState state, InhabitantReleaseReason reason, @Nullable InhabitableNestBlockEntity.InhabitantAlertScenario scenario, @Nullable LivingEntity visitor) {
        List<BlockNester> releasedInhabitants = this.tryReleasingInhabitants(state, reason, scenario, visitor);

        return !releasedInhabitants.isEmpty();
    }

    default List<BlockNester> tryReleasingInhabitants(BlockState state, InhabitantReleaseReason reason, @Nullable InhabitableNestBlockEntity.InhabitantAlertScenario scenario, @Nullable LivingEntity visitor) {
        ArrayList<BlockNester> releasedInhabitants = new ArrayList<>();

        this.getInhabitants().removeIf(inhabitant -> tryReleasingInhabitant(Objects.requireNonNull(this.getNestWorld()), this.getNestPos(), state, reason, inhabitant, releasedInhabitants, scenario, visitor));

        if (!releasedInhabitants.isEmpty()) this.markNestDirty();

        return releasedInhabitants;
    }

    static boolean tryNaturallyReleasingInhabitant(World world, BlockPos nestPos, BlockState state, Inhabitant inhabitant) {
        return tryReleasingInhabitant(world, nestPos, state, InhabitantReleaseReason.NATURAL, inhabitant, null, null, null);
    }

    static boolean tryReleasingInhabitant(World world, BlockPos nestPos, BlockState state, InhabitantReleaseReason reason, Inhabitant inhabitant, @Nullable List<BlockNester> inhabitantGetter, @Nullable InhabitableNestBlockEntity.InhabitantAlertScenario scenario, @Nullable LivingEntity visitor) {
        if ((world.isNight() || world.isRaining()) && reason == InhabitantReleaseReason.NATURAL) return false;

        NbtCompound nbtCompound = inhabitant.entityData.copy();
        removeIrrelevantNbt(nbtCompound);
        nbtCompound.put(BlockNester.NEST_POS_KEY, NbtHelper.fromBlockPos(nestPos));

        Direction frontDirection = state.get(InhabitableNestBlock.FACING);
        BlockPos frontPos = nestPos.offset(frontDirection);

        boolean hasSpaceToRelease = world.getBlockState(frontPos).getCollisionShape(world, frontPos).isEmpty();

        if (!hasSpaceToRelease && reason != InhabitantReleaseReason.EMERGENCY) return false;

        MobEntity inhabitantEntity = (MobEntity) EntityType.loadEntityWithPassengers(nbtCompound, world, e -> e);
        if (inhabitantEntity != null) {
            double d0 = !hasSpaceToRelease ? 0.0D : 0.55D + (double) (inhabitantEntity.getWidth() / 2.0F);
            double d1 = (double) nestPos.getX() + 0.5D + d0 * (double) frontDirection.getOffsetX();
            double d2 = (double) nestPos.getY() + 0.5D - (double) (inhabitantEntity.getHeight() / 2.0F);
            double d3 = (double) nestPos.getZ() + 0.5D + d0 * (double) frontDirection.getOffsetZ();
            inhabitantEntity.refreshPositionAndAngles(d1, d2, d3, inhabitantEntity.getYaw(), inhabitantEntity.getPitch());

            BlockNester nesterInhabitant = (BlockNester) inhabitantEntity;

            if (scenario != null && visitor != null) {
                if (nesterInhabitant.shouldDefendNestAgainstVisitor(visitor, scenario) && visitor.getPos().distanceTo(inhabitantEntity.getPos()) <= inhabitantEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE))
                    inhabitantEntity.setTarget(visitor);
                else if (reason == InhabitantReleaseReason.ALERT) return false;
            }

            if (inhabitantEntity instanceof AnimalEntity) ageInhabitant(inhabitant.ticksInNest, (AnimalEntity) inhabitantEntity);

            if (inhabitant.isFresh) {
                if (world instanceof ServerWorldAccess serverWorldAccess) inhabitantEntity.initialize(serverWorldAccess, serverWorldAccess.getLocalDifficulty(inhabitantEntity.getBlockPos()), SpawnReason.CHUNK_GENERATION, null, nbtCompound);

                if (!nesterInhabitant.hasNestPos()) nesterInhabitant.setNestPos(nestPos);
            }

            nesterInhabitant.onExitingNest(nestPos);

            if (inhabitantGetter != null) inhabitantGetter.add(nesterInhabitant);

            world.playSound(null, nestPos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, nestPos, GameEvent.Emitter.of(inhabitantEntity, world.getBlockState(nestPos)));

            if (world.spawnEntity(inhabitantEntity)) {
                nesterInhabitant.afterExitingNest(nestPos, reason);
                return true;
            }
            else return false;
        }
        return false;
    }

    static void ageInhabitant(int ticks, AnimalEntity inhabitant) {
        int i = inhabitant.getBreedingAge();
        if (i < 0) inhabitant.setBreedingAge(Math.min(0, i + ticks));
        else if (i > 0) inhabitant.setBreedingAge(Math.max(0, i - ticks));

        inhabitant.setLoveTicks(Math.max(0, inhabitant.getLoveTicks() - ticks));
    }

    World getNestWorld();

    BlockPos getNestPos();

    BlockState getNestCachedState();

    void markNestDirty();

    class Inhabitant {
        final boolean isFresh;
        final NbtCompound entityData;
        final int capacityWeight;
        final int minOccupationTicks;
        int ticksInNest;
        final boolean isBaby;

        protected Inhabitant(boolean isFresh, NbtCompound entityData, int capacityWeight, int ticksInNest, int minOccupationTicks, boolean isBaby) {
            removeIrrelevantNbt(entityData);

            this.isFresh = isFresh;
            this.entityData = entityData;
            this.capacityWeight = capacityWeight;
            this.ticksInNest = ticksInNest;
            this.minOccupationTicks = minOccupationTicks;
            this.isBaby = isBaby;
        }
    }

    enum InhabitantReleaseReason {
        NATURAL,
        ALERT,
        EMERGENCY
    }

    enum InhabitantAlertScenario {
        VISITOR,
        INTRUSION,
        DESTRUCTION
    }
}
