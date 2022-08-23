package com.ineffa.wondrouswilds.entities;

import com.ineffa.wondrouswilds.util.WondrousWildsUtils;
import net.minecraft.util.math.BlockPos;

public interface BlockNester {

    int getNestCapacityWeight();

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
        BlockPos nestPos = this.getNestPos();
        return nestPos != null && !WondrousWildsUtils.isPosAtWorldOrigin(nestPos);
    }

    default boolean shouldFindNest() {
        return !this.hasNestPos();
    }

    boolean shouldReturnToNest();

    boolean defendsNest();

    int getWanderRadiusFromNest();
}
