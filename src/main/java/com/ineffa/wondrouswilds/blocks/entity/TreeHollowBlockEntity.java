package com.ineffa.wondrouswilds.blocks.entity;

import com.google.common.collect.Lists;
import com.ineffa.wondrouswilds.blocks.TreeHollowBlock;
import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import com.ineffa.wondrouswilds.entities.TreeHollowNester;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
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

import java.util.*;

import static com.ineffa.wondrouswilds.registry.WondrousWildsEntities.DEFAULT_NESTER_CAPACITY_WEIGHTS;

public class TreeHollowBlockEntity extends BlockEntity {

    public static final String INHABITANTS_KEY = "Inhabitants";
    public static final String ENTITY_DATA_KEY = "EntityData";
    public static final String CAPACITY_WEIGHT_KEY = "CapacityWeight";
    public static final String TICKS_IN_NEST_KEY = "TicksInNest";
    public static final String MIN_OCCUPATION_TICKS_KEY = "MinOccupationTicks";

    private static final List<String> IRRELEVANT_INHABITANT_NBT_KEYS = Arrays.asList("CannotEnterNestTicks", "Air", "DeathTime", "FallDistance", "FallFlying", "Fire", "HurtByTimestamp", "HurtTime", "Motion", "OnGround", "PortalCooldown", "Pos", "Rotation", "Passengers", "Leash", "UUID");

    private final List<Inhabitant> inhabitants = Lists.newArrayList();

    public TreeHollowBlockEntity(BlockPos pos, BlockState state) {
        super(WondrousWildsBlocks.BlockEntities.TREE_HOLLOW, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.put(INHABITANTS_KEY, this.getInhabitantsNbt());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.inhabitants.clear();

        NbtList nbtList = nbt.getList(INHABITANTS_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);

            Inhabitant inhabitant = new Inhabitant(false, nbtCompound.getCompound(ENTITY_DATA_KEY), nbtCompound.getInt(CAPACITY_WEIGHT_KEY), nbtCompound.getInt(MIN_OCCUPATION_TICKS_KEY), nbtCompound.getInt(TICKS_IN_NEST_KEY));
            this.inhabitants.add(inhabitant);
        }
    }

    private static void removeIrrelevantNbt(NbtCompound nbtCompound) {
        for (String key : IRRELEVANT_INHABITANT_NBT_KEYS) nbtCompound.remove(key);
    }

    public NbtList getInhabitantsNbt() {
        NbtList nbtList = new NbtList();
        for (Inhabitant inhabitant : this.inhabitants) {
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

    public int getInhabitantCount() {
        return this.inhabitants.size();
    }

    public boolean hasInhabitants() {
        return !this.inhabitants.isEmpty();
    }

    public int getMaxCapacity() {
        return 100;
    }

    public int getUsedCapacity() {
        int usedCapacity = 0;

        for (Inhabitant inhabitant : this.inhabitants) usedCapacity += inhabitant.capacityWeight;

        return usedCapacity;
    }

    public int getRemainingCapacity() {
        return this.getMaxCapacity() - this.getUsedCapacity();
    }

    public boolean isCapacityFilled() {
        return this.getUsedCapacity() >= this.getMaxCapacity();
    }

    public boolean entityMatchesInhabitants(MobEntity entity) {
        String nesterId = EntityType.getId(entity.getType()).toString();

        for (Inhabitant inhabitant : this.inhabitants) {
            String inhabitantId = inhabitant.entityData.getString(Entity.ID_KEY);

            if (!Objects.equals(inhabitantId, nesterId)) return false;
        }

        return true;
    }

    public boolean canFitNester(TreeHollowNester nester) {
        return nester.getNestCapacityWeight() <= this.getRemainingCapacity();
    }

    public boolean tryAddingInhabitant(TreeHollowNester nester) {
        if (!(nester instanceof MobEntity nesterEntity)) return false;

        if ((this.isCapacityFilled() || !this.canFitNester(nester)) || !this.entityMatchesInhabitants(nesterEntity)) {
            if (nesterEntity instanceof AnimalEntity animal && animal.isBaby() && this.entityMatchesInhabitants(animal)) return false;

            this.alertInhabitants(nesterEntity, InhabitantReleaseState.ALERT);
            return false;
        }

        nesterEntity.stopRiding();
        nesterEntity.removeAllPassengers();

        this.addInhabitant(nester);

        World world = this.getWorld();
        if (world != null) {
            BlockPos pos = this.getPos();

            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(nesterEntity, this.getCachedState()));
        }

        nesterEntity.discard();

        super.markDirty();

        return true;
    }

    private void addInhabitant(TreeHollowNester inhabitant) {
        if (!(inhabitant instanceof MobEntity entity)) return;

        NbtCompound entityData = new NbtCompound();
        entity.saveNbt(entityData);

        this.inhabitants.add(new Inhabitant(false, entityData, inhabitant.getNestCapacityWeight(), 0, inhabitant.getMinTicksInNest()));
    }

    public void addFreshInhabitant(EntityType<?> entityType) {
        if (!DEFAULT_NESTER_CAPACITY_WEIGHTS.containsKey(entityType)) return;

        NbtCompound entityData = new NbtCompound();
        entityData.putString("id", Registry.ENTITY_TYPE.getId(entityType).toString());
        entityData.put("NestPos", NbtHelper.fromBlockPos(this.getPos()));

        this.inhabitants.add(new Inhabitant(true, entityData, DEFAULT_NESTER_CAPACITY_WEIGHTS.get(entityType), 0, 600));
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, TreeHollowBlockEntity treeHollow) {

        tickInhabitants(world, pos, state, treeHollow.inhabitants);

        /*if (treeHollow.hasInhabitants() && world.getRandom().nextDouble() < 0.005D) {
            double d = (double) pos.getX() + 0.5D;
            double e = pos.getY();
            double f = (double) pos.getZ() + 0.5D;
            world.playSound(null, d, e, f, SoundEvents.BLOCK_BEEHIVE_WORK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }*/
    }

    private static void tickInhabitants(World world, BlockPos pos, BlockState state, List<Inhabitant> inhabitants) {
        boolean released = false;

        Iterator<Inhabitant> iterator = inhabitants.iterator();
        while (iterator.hasNext()) {
            Inhabitant inhabitant = iterator.next();
            if (inhabitant.ticksInNest > inhabitant.minOccupationTicks) {
                if (tryReleasingInhabitant(world, pos, state, InhabitantReleaseState.RELEASE, inhabitant, null)) {
                    released = true;
                    iterator.remove();
                }
            }
            ++inhabitant.ticksInNest;
        }

        if (released) markDirty(world, pos, state);
    }

    public void alertInhabitants(@Nullable LivingEntity attacker, InhabitantReleaseState releaseState) {
        this.alertInhabitants(attacker, Objects.requireNonNull(this.getWorld()).getBlockState(this.getPos()), releaseState);
    }

    public void alertInhabitants(@Nullable LivingEntity attacker, BlockState state, InhabitantReleaseState releaseState) {
        List<TreeHollowNester> inhabitants = this.tryReleasingInhabitants(state, releaseState);

        if (attacker != null) {
            for (TreeHollowNester inhabitant : inhabitants) {
                if (!inhabitant.defendsNest() || !(inhabitant instanceof MobEntity inhabitantEntity)) continue;

                if (attacker.getPos().squaredDistanceTo(inhabitantEntity.getPos()) > inhabitantEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)) continue;

                inhabitantEntity.setTarget(attacker);

                //inhabitant.setCannotEnterNestTicks(inhabitant.getMinTicksOutOfNest());
            }
        }
    }

    private List<TreeHollowNester> tryReleasingInhabitants(BlockState state, InhabitantReleaseState releaseState) {
        ArrayList<TreeHollowNester> releasedInhabitants = new ArrayList<>();

        this.inhabitants.removeIf(inhabitant -> tryReleasingInhabitant(Objects.requireNonNull(this.getWorld()), this.pos, state, releaseState, inhabitant, releasedInhabitants));

        if (!releasedInhabitants.isEmpty()) super.markDirty();

        return releasedInhabitants;
    }

    private static boolean tryReleasingInhabitant(World world, BlockPos treeHollowPos, BlockState state, InhabitantReleaseState releaseState, Inhabitant inhabitant, @Nullable List<TreeHollowNester> inhabitantGetter) {
        if ((world.isNight() || world.isRaining()) && releaseState == InhabitantReleaseState.RELEASE) return false;

        NbtCompound nbtCompound = inhabitant.entityData.copy();
        removeIrrelevantNbt(nbtCompound);
        nbtCompound.put("NestPos", NbtHelper.fromBlockPos(treeHollowPos));

        Direction frontDirection = state.get(TreeHollowBlock.FACING);
        BlockPos frontPos = treeHollowPos.offset(frontDirection);

        boolean hasSpaceToRelease = world.getBlockState(frontPos).getCollisionShape(world, frontPos).isEmpty();

        if (!hasSpaceToRelease && releaseState != InhabitantReleaseState.EMERGENCY) return false;

        MobEntity inhabitantEntity = (MobEntity) EntityType.loadEntityWithPassengers(nbtCompound, world, e -> e);
        if (inhabitantEntity != null) {
            double d0 = !hasSpaceToRelease ? 0.0D : 0.55D + (double) (inhabitantEntity.getWidth() / 2.0F);
            double d1 = (double) treeHollowPos.getX() + 0.5D + d0 * (double) frontDirection.getOffsetX();
            double d2 = (double) treeHollowPos.getY() + 0.5D - (double) (inhabitantEntity.getHeight() / 2.0F);
            double d3 = (double) treeHollowPos.getZ() + 0.5D + d0 * (double) frontDirection.getOffsetZ();
            inhabitantEntity.refreshPositionAndAngles(d1, d2, d3, inhabitantEntity.getYaw(), inhabitantEntity.getPitch());

            if (inhabitantEntity instanceof AnimalEntity) ageInhabitant(inhabitant.ticksInNest, (AnimalEntity) inhabitantEntity);

            if (inhabitant.isFresh) {
                if (world instanceof ServerWorldAccess serverWorldAccess) {
                    inhabitantEntity.initialize(serverWorldAccess, serverWorldAccess.getLocalDifficulty(inhabitantEntity.getBlockPos()), SpawnReason.CHUNK_GENERATION, null, nbtCompound);
                }
                if (inhabitantEntity instanceof FlyingAndWalkingAnimalEntity flyingEntity) flyingEntity.setFlying(true);
            }

            if (inhabitantGetter != null) inhabitantGetter.add((TreeHollowNester) inhabitantEntity);

            world.playSound(null, treeHollowPos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, treeHollowPos, GameEvent.Emitter.of(inhabitantEntity, world.getBlockState(treeHollowPos)));

            return world.spawnEntity(inhabitantEntity);
        }
        return false;
    }

    private static void ageInhabitant(int ticks, AnimalEntity inhabitant) {
        int i = inhabitant.getBreedingAge();
        if (i < 0) inhabitant.setBreedingAge(Math.min(0, i + ticks));
        else if (i > 0) inhabitant.setBreedingAge(Math.max(0, i - ticks));

        inhabitant.setLoveTicks(Math.max(0, inhabitant.getLoveTicks() - ticks));
    }

    private static class Inhabitant {
        final boolean isFresh;
        final NbtCompound entityData;
        final int capacityWeight;
        final int minOccupationTicks;
        int ticksInNest;

        private Inhabitant(boolean isFresh, NbtCompound entityData, int capacityWeight, int ticksInNest, int minOccupationTicks) {

            removeIrrelevantNbt(entityData);

            this.isFresh = isFresh;
            this.entityData = entityData;
            this.capacityWeight = capacityWeight;
            this.ticksInNest = ticksInNest;
            this.minOccupationTicks = minOccupationTicks;
        }
    }

    public enum InhabitantReleaseState {
        RELEASE,
        ALERT,
        EMERGENCY
    }
}
