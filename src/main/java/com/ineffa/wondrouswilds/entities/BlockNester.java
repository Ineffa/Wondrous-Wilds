package com.ineffa.wondrouswilds.entities;

import com.ineffa.wondrouswilds.blocks.entity.InhabitableNestBlockEntity;
import com.ineffa.wondrouswilds.util.WondrousWildsUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public interface BlockNester {

    String NEST_POS_KEY = "NestPos";

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

    default boolean shouldDefendNestAgainstVisitor(LivingEntity visitor, InhabitableNestBlockEntity.InhabitantAlertScenario scenario) {
        return scenario == InhabitableNestBlockEntity.InhabitantAlertScenario.INTRUSION || scenario == InhabitableNestBlockEntity.InhabitantAlertScenario.DESTRUCTION;
    }

    void onExitingNest(BlockPos nestPos);

    void afterExitingNest(BlockPos nestPos, InhabitableNestBlockEntity.InhabitantReleaseReason reason);

    int getWanderRadiusFromNest();

    int getMaxDistanceFromNest();
}
