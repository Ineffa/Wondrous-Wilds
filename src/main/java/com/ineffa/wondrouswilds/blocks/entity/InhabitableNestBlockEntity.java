package com.ineffa.wondrouswilds.blocks.entity;

import com.ineffa.wondrouswilds.blocks.InhabitableNestBlock;
import com.ineffa.wondrouswilds.entities.BlockNester;
import com.ineffa.wondrouswilds.entities.eggs.NesterEgg;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
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

    // Inhabitant data
    String INHABITANTS_KEY = "Inhabitants";
    String IS_FRESH_KEY = "IsFresh";
    String IS_BABY_KEY = "IsBaby";
    String ENTITY_DATA_KEY = "EntityData";
    String CAPACITY_WEIGHT_KEY = "CapacityWeight";
    String MIN_OCCUPATION_TICKS_KEY = "MinOccupationTicks";
    String TICKS_IN_NEST_KEY = "TicksInNest";

    // Egg data
    String EGGS_KEY = "Eggs";

    String ENTITY_TYPE_TO_HATCH_KEY = "EntityTypeToHatch";
    String DATA_TO_INHERIT_KEY = "DataToInherit";
    String NOCTURNAL_KEY = "Nocturnal";

    String MIN_CRACK_COOLDOWN_KEY = "MinCrackCooldown";
    String MAX_CRACK_COOLDOWN_KEY = "MaxCrackCooldown";
    String CRACK_COOLDOWN_KEY = "CrackCooldown";
    String CRACKS_UNTIL_HATCH_KEY = "CracksUntilHatch";

    List<String> IRRELEVANT_INHABITANT_NBT_KEYS = Arrays.asList("CannotEnterNestTicks", "Air", "DeathTime", "FallDistance", "FallFlying", "Fire", "HurtByTimestamp", "HurtTime", "Motion", "OnGround", "PortalCooldown", "Pos", "Rotation", "Passengers", "Leash");

    List<Inhabitant> getInhabitants();

    List<NesterEgg> getEggs();

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

    default NbtList getEggsNbt() {
        NbtList nbtList = new NbtList();
        for (NesterEgg egg : this.getEggs()) {
            NbtCompound nbt = new NbtCompound();

            nbt.putInt(CAPACITY_WEIGHT_KEY, egg.capacityWeight);

            nbt.putString(ENTITY_TYPE_TO_HATCH_KEY, Registry.ENTITY_TYPE.getId(egg.getEntityTypeToHatch()).toString());

            NbtCompound dataToInherit = egg.getDataToInherit();
            if (dataToInherit != null) nbt.put(DATA_TO_INHERIT_KEY, dataToInherit);

            nbt.putBoolean(NOCTURNAL_KEY, egg.isNocturnal());

            nbt.putInt(MIN_CRACK_COOLDOWN_KEY, egg.crackCooldownRange.getLeft());
            nbt.putInt(MAX_CRACK_COOLDOWN_KEY, egg.crackCooldownRange.getRight());
            nbt.putInt(CRACK_COOLDOWN_KEY, egg.getCrackCooldown());
            nbt.putInt(CRACKS_UNTIL_HATCH_KEY, egg.getCracksUntilHatch());

            nbtList.add(nbt);
        }
        return nbtList;
    }

    default int getInhabitantCount() {
        return this.getInhabitants().size();
    }

    default int getEggCount() {
        return this.getEggs().size();
    }

    default int getBabyCount() {
        int babyCount = 0;

        for (Inhabitant inhabitant : this.getInhabitants()) if (inhabitant.isBaby) ++babyCount;

        babyCount += this.getEggCount();

        return babyCount;
    }

    default boolean hasInhabitants() {
        return !this.getInhabitants().isEmpty();
    }

    default int getMaxCapacity() {
        return 100;
    }

    default int getMaxBabyCount() {
        return 3;
    }

    default int getUsedCapacity() {
        int usedCapacity = 0;

        for (Inhabitant inhabitant : this.getInhabitants()) usedCapacity += inhabitant.capacityWeight;
        for (NesterEgg egg : this.getEggs()) usedCapacity += egg.capacityWeight;

        return usedCapacity;
    }

    default int getRemainingCapacity() {
        return this.getMaxCapacity() - this.getUsedCapacity();
    }

    default boolean isCapacityFilled() {
        return this.getUsedCapacity() >= this.getMaxCapacity();
    }

    default boolean isFullWithBabies() {
        return this.getBabyCount() >= this.getMaxBabyCount();
    }

    default boolean canFitNester(BlockNester nester) {
        return nester.getNestCapacityWeight() <= this.getRemainingCapacity();
    }

    default boolean canFitEgg(NesterEgg egg) {
        return egg.capacityWeight <= this.getRemainingCapacity() && !this.isFullWithBabies();
    }

    default boolean tryAddingInhabitant(BlockNester nester) {
        if (!(nester instanceof MobEntity nesterEntity)) return false;

        boolean canFit = !this.isCapacityFilled() && this.canFitNester(nester) && !(nesterEntity.isBaby() && this.isFullWithBabies());

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

        this.getInhabitants().add(new Inhabitant(false, entity.isBaby(), entityData, inhabitant.getNestCapacityWeight(), inhabitant.getMinTicksInNest(), 0));
    }

    default void addFreshInhabitant(EntityType<? extends BlockNester> entityType, boolean isBaby) {
        if (!DEFAULT_NESTER_CAPACITY_WEIGHTS.containsKey(entityType)) return;

        NbtCompound entityData = new NbtCompound();
        entityData.putString(Entity.ID_KEY, Registry.ENTITY_TYPE.getId(entityType).toString());
        entityData.put(BlockNester.NEST_POS_KEY, NbtHelper.fromBlockPos(this.getNestPos()));
        if (isBaby) entityData.putInt("Age", PassiveEntity.BABY_AGE);

        this.getInhabitants().add(new Inhabitant(true, isBaby, entityData, DEFAULT_NESTER_CAPACITY_WEIGHTS.get(entityType), 600, 0));
    }

    default boolean tryAddingEgg(NesterEgg egg) {
        if (!this.canFitEgg(egg)) return false;

        this.addEgg(egg);
        return true;
    }

    default void addEgg(NesterEgg egg) {
        this.getEggs().add(egg);
    }

    default void hatchEgg(NesterEgg egg) {
        this.addFreshInhabitant(egg.getEntityTypeToHatch(), true);
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
        final boolean isBaby;
        final NbtCompound entityData;
        final int capacityWeight;
        final int minOccupationTicks;

        int ticksInNest;

        protected Inhabitant(boolean isFresh, boolean isBaby, NbtCompound entityData, int capacityWeight, int minOccupationTicks, int ticksInNest) {
            removeIrrelevantNbt(entityData);

            this.isFresh = isFresh;
            this.isBaby = isBaby;
            this.entityData = entityData;
            this.capacityWeight = capacityWeight;
            this.minOccupationTicks = minOccupationTicks;

            this.ticksInNest = ticksInNest;
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
