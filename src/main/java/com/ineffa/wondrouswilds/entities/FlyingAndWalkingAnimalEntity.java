package com.ineffa.wondrouswilds.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public abstract class FlyingAndWalkingAnimalEntity extends AnimalEntity implements Flutterer {

    public static final String IS_FLYING_KEY = "IsFlying";
    public static final String WANTS_TO_LAND_KEY = "WantsToLand";

    private static final TrackedData<Boolean> IS_FLYING = DataTracker.registerData(FlyingAndWalkingAnimalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> WANTS_TO_LAND = DataTracker.registerData(FlyingAndWalkingAnimalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private final FlightMoveControl airMoveControl;
    private final MoveControl landMoveControl;

    private final BirdNavigation airNavigation;
    private final MobNavigation landNavigation;

    public FlyingAndWalkingAnimalEntity(EntityType<? extends FlyingAndWalkingAnimalEntity> entityType, World world) {
        super(entityType, world);

        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanEnterOpenDoors(true);
        birdNavigation.setCanSwim(false);
        this.airNavigation = birdNavigation;
        this.landNavigation = new MobNavigation(this, world);

        this.airMoveControl = new FlightMoveControl(this, 20, true);
        this.landMoveControl = new MoveControl(this);
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

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(IS_FLYING, false);
        this.dataTracker.startTracking(WANTS_TO_LAND, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putBoolean(IS_FLYING_KEY, this.isFlying());
        nbt.putBoolean(WANTS_TO_LAND_KEY, this.wantsToLand());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        boolean isFlying = nbt.getBoolean(IS_FLYING_KEY);
        if (this.isFlying() != isFlying) this.setFlying(isFlying);

        this.setWantsToLand(nbt.getBoolean(WANTS_TO_LAND_KEY));
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

        this.moveControl = flying ? this.airMoveControl : this.landMoveControl;
        this.navigation = flying ? this.airNavigation : this.landNavigation;
    }

    public boolean wantsToLand() {
        return this.dataTracker.get(WANTS_TO_LAND);
    }

    public void setWantsToLand(boolean wantsToLand) {
        this.dataTracker.set(WANTS_TO_LAND, wantsToLand);
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    @Override
    protected boolean hasWings() {
        return this.isFlying();
    }
}
