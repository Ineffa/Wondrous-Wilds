package com.ineffa.wondrouswilds.entities;

import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;

public interface TreeHollowNester {

    default int getNestCapacityWeight() {
        return WondrousWildsEntities.DEFAULT_NESTER_CAPACITY_WEIGHTS.get(this.getType());
    }

    int getMinTicksInNest();

    int getMinTicksOutOfNest();

    int getCannotInhabitNestTicks();

    void setCannotInhabitNestTicks(int ticks);

    BlockPos getNestPos();

    void setNestPos(BlockPos pos);

    default void clearNestPos() {
        this.setNestPos(BlockPos.ORIGIN);
    }

    default boolean hasNestPos() {
        return !(this.getNestPos() == null || this.getNestPos() == BlockPos.ORIGIN);
    }

    default boolean shouldFindNest() {
        return !this.hasNestPos();
    }

    boolean shouldReturnToNest();

    boolean defendsNest();

    int getWanderRadiusFromNest();

    EntityType<?> getType();
}
