package com.ineffa.wondrouswilds.blocks.entity;

import com.ineffa.wondrouswilds.blocks.InhabitableNestBlock;
import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import com.ineffa.wondrouswilds.entities.BlockNester;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
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
    String ENTITY_DATA_KEY = "EntityData";
    String CAPACITY_WEIGHT_KEY = "CapacityWeight";
    String TICKS_IN_NEST_KEY = "TicksInNest";
    String MIN_OCCUPATION_TICKS_KEY = "MinOccupationTicks";

    List<String> IRRELEVANT_INHABITANT_NBT_KEYS = Arrays.asList("CannotEnterNestTicks", "Air", "DeathTime", "FallDistance", "FallFlying", "Fire", "HurtByTimestamp", "HurtTime", "Motion", "OnGround", "PortalCooldown", "Pos", "Rotation", "Passengers", "Leash", "UUID");

    List<Inhabitant> getInhabitants();

    static void removeIrrelevantNbt(NbtCompound nbtCompound) {
        for (String key : IRRELEVANT_INHABITANT_NBT_KEYS) nbtCompound.remove(key);
    }

    default NbtList getInhabitantsNbt() {
        NbtList nbtList = new NbtList();
        for (Inhabitant inhabitant : this.getInhabitants()) {
            NbtCompound copy = inhabitant.entityData.copy();
            NbtCompound nbt = new NbtCompound();

            nbt.put(ENTITY_DATA_KEY, copy);
            nbt.putInt(CAPACITY_WEIGHT_KEY, inhabitant.capacityWeight);
            nbt.putInt(MIN_OCCUPATION_TICKS_KEY, inhabitant.minOccupationTicks);
            nbt.putInt(TICKS_IN_NEST_KEY, inhabitant.ticksInNest);

            nbtList.add(nbt);
        }
        return nbtList;
    }

    /*default int getInhabitantCount() {
        return this.getInhabitantList(.size();
    }*/

    default boolean hasInhabitants() {
        return !this.getInhabitants().isEmpty();
    }

    default int getMaxCapacity() {
        return 100;
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

    default boolean entityMatchesInhabitants(MobEntity entity) {
        String nesterId = EntityType.getId(entity.getType()).toString();

        for (Inhabitant inhabitant : this.getInhabitants()) {
            String inhabitantId = inhabitant.entityData.getString(Entity.ID_KEY);

            if (!Objects.equals(inhabitantId, nesterId)) return false;
        }

        return true;
    }

    default boolean canFitNester(BlockNester nester) {
        return nester.getNestCapacityWeight() <= this.getRemainingCapacity();
    }

    default boolean tryAddingInhabitant(BlockNester nester) {
        if (!(nester instanceof MobEntity nesterEntity)) return false;

        if ((this.isCapacityFilled() || !this.canFitNester(nester)) || !this.entityMatchesInhabitants(nesterEntity)) {
            if (nesterEntity instanceof AnimalEntity animal && animal.isBaby() && this.entityMatchesInhabitants(animal)) return false;

            this.alertInhabitants(nesterEntity, InhabitantReleaseState.ALERT);
            return false;
        }

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

        this.getInhabitants().add(new Inhabitant(false, entityData, inhabitant.getNestCapacityWeight(), 0, inhabitant.getMinTicksInNest()));
    }

    default void addFreshInhabitant(EntityType<?> entityType) {
        if (!DEFAULT_NESTER_CAPACITY_WEIGHTS.containsKey(entityType)) return;

        NbtCompound entityData = new NbtCompound();
        entityData.putString("id", Registry.ENTITY_TYPE.getId(entityType).toString());
        entityData.put("NestPos", NbtHelper.fromBlockPos(this.getNestPos()));

        this.getInhabitants().add(new Inhabitant(true, entityData, DEFAULT_NESTER_CAPACITY_WEIGHTS.get(entityType), 0, 600));
    }

    default void alertInhabitants(@Nullable LivingEntity attacker, InhabitantReleaseState releaseState) {
        this.alertInhabitants(attacker, Objects.requireNonNull(this.getNestWorld()).getBlockState(this.getNestPos()), releaseState);
    }

    default void alertInhabitants(@Nullable LivingEntity attacker, BlockState state, InhabitantReleaseState releaseState) {
        List<BlockNester> inhabitants = this.tryReleasingInhabitants(state, releaseState);

        if (attacker != null) {
            for (BlockNester inhabitant : inhabitants) {
                if (!inhabitant.defendsNest() || !(inhabitant instanceof MobEntity inhabitantEntity) || (inhabitant instanceof WoodpeckerEntity woodpecker && woodpecker.isTame())) continue;

                if (attacker.getPos().squaredDistanceTo(inhabitantEntity.getPos()) > inhabitantEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)) continue;

                inhabitantEntity.setTarget(attacker);

                //inhabitant.setCannotEnterNestTicks(inhabitant.getMinTicksOutOfNest());
            }
        }
    }

    default List<BlockNester> tryReleasingInhabitants(BlockState state, InhabitantReleaseState releaseState) {
        ArrayList<BlockNester> releasedInhabitants = new ArrayList<>();

        this.getInhabitants().removeIf(inhabitant -> tryReleasingInhabitant(Objects.requireNonNull(this.getNestWorld()), this.getNestPos(), state, releaseState, inhabitant, releasedInhabitants));

        if (!releasedInhabitants.isEmpty()) this.markNestDirty();

        return releasedInhabitants;
    }

    static boolean tryReleasingInhabitant(World world, BlockPos nestPos, BlockState state, InhabitantReleaseState releaseState, Inhabitant inhabitant, @Nullable List<BlockNester> inhabitantGetter) {
        if ((world.isNight() || world.isRaining()) && releaseState == InhabitantReleaseState.RELEASE) return false;

        NbtCompound nbtCompound = inhabitant.entityData.copy();
        removeIrrelevantNbt(nbtCompound);
        nbtCompound.put("NestPos", NbtHelper.fromBlockPos(nestPos));

        Direction frontDirection = state.get(InhabitableNestBlock.FACING);
        BlockPos frontPos = nestPos.offset(frontDirection);

        boolean hasSpaceToRelease = world.getBlockState(frontPos).getCollisionShape(world, frontPos).isEmpty();

        if (!hasSpaceToRelease && releaseState != InhabitantReleaseState.EMERGENCY) return false;

        MobEntity inhabitantEntity = (MobEntity) EntityType.loadEntityWithPassengers(nbtCompound, world, e -> e);
        if (inhabitantEntity != null) {
            double d0 = !hasSpaceToRelease ? 0.0D : 0.55D + (double) (inhabitantEntity.getWidth() / 2.0F);
            double d1 = (double) nestPos.getX() + 0.5D + d0 * (double) frontDirection.getOffsetX();
            double d2 = (double) nestPos.getY() + 0.5D - (double) (inhabitantEntity.getHeight() / 2.0F);
            double d3 = (double) nestPos.getZ() + 0.5D + d0 * (double) frontDirection.getOffsetZ();
            inhabitantEntity.refreshPositionAndAngles(d1, d2, d3, inhabitantEntity.getYaw(), inhabitantEntity.getPitch());

            if (inhabitantEntity instanceof AnimalEntity) ageInhabitant(inhabitant.ticksInNest, (AnimalEntity) inhabitantEntity);

            if (inhabitant.isFresh) {
                if (world instanceof ServerWorldAccess serverWorldAccess)
                    inhabitantEntity.initialize(serverWorldAccess, serverWorldAccess.getLocalDifficulty(inhabitantEntity.getBlockPos()), SpawnReason.CHUNK_GENERATION, null, nbtCompound);

                if (inhabitantEntity instanceof FlyingAndWalkingAnimalEntity flyingEntity) flyingEntity.setFlying(true);
            }

            if (inhabitantEntity instanceof WoodpeckerEntity woodpecker && woodpecker.isTame() && woodpecker.getMainHandStack().isEmpty() && world.getBlockEntity(nestPos) instanceof NestBoxBlockEntity nestBox && !nestBox.isEmpty()) {
                int slotToTakeFrom = 0;
                ItemStack nestBoxItem = nestBox.getStack(slotToTakeFrom);
                if (!nestBoxItem.isEmpty()) {
                    woodpecker.setStackInHand(Hand.MAIN_HAND, nestBoxItem.copy());
                    nestBox.removeStack(slotToTakeFrom);
                }
            }

            if (inhabitantGetter != null) inhabitantGetter.add((BlockNester) inhabitantEntity);

            world.playSound(null, nestPos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, nestPos, GameEvent.Emitter.of(inhabitantEntity, world.getBlockState(nestPos)));

            return world.spawnEntity(inhabitantEntity);
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

        protected Inhabitant(boolean isFresh, NbtCompound entityData, int capacityWeight, int ticksInNest, int minOccupationTicks) {
            removeIrrelevantNbt(entityData);

            this.isFresh = isFresh;
            this.entityData = entityData;
            this.capacityWeight = capacityWeight;
            this.ticksInNest = ticksInNest;
            this.minOccupationTicks = minOccupationTicks;
        }
    }

    enum InhabitantReleaseState {
        RELEASE,
        ALERT,
        EMERGENCY
    }
}
