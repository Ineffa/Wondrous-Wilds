package com.ineffa.wondrouswilds.entities;

import com.ineffa.wondrouswilds.blocks.entity.InhabitableNestBlockEntity;
import com.ineffa.wondrouswilds.util.WondrousWildsUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface BlockNester {

    String NEST_POS_KEY = "NestPos";
    String TICKS_PEEKING_OUT_OF_NEST_KEY = "TicksPeekingOutOfNest";

    int getNestCapacityWeight();

    int getMinTicksInNest();

    int getMinTicksOutOfNest();

    int getCannotInhabitNestTicks();

    void setCannotInhabitNestTicks(int ticks);

    @Nullable
    BlockPos getNestPos();

    void setNestPos(BlockPos pos);

    default void clearNestPos() {
        this.setNestPos(BlockPos.ORIGIN);
    }

    default boolean hasNestPos() {
        BlockPos nestPos = this.getNestPos();
        return nestPos != null && !WondrousWildsUtils.isPosAtWorldOrigin(nestPos);
    }

    Optional<NestTransition> getCurrentNestTransition();

    default int getDurationOfNestTransitionType(NestTransitionType type) {
        return 0;
    }

    void startNewNestTransition(NestTransitionType transition);

    default void finishCurrentNestTransition() {
        this.clearCurrentNestTransition();
    }

    void clearCurrentNestTransition();

    boolean isPeekingOutOfNest();

    int getTicksPeekingOutOfNest();

    default boolean shouldFindNest() {
        return !this.hasNestPos();
    }

    boolean shouldReturnToNest();

    default boolean shouldDefendNestAgainstVisitor(LivingEntity visitor, InhabitableNestBlockEntity.InhabitantAlertScenario scenario) {
        return scenario == InhabitableNestBlockEntity.InhabitantAlertScenario.INTRUSION || scenario == InhabitableNestBlockEntity.InhabitantAlertScenario.DESTRUCTION;
    }

    default void beforeExitingNest(BlockPos nestPos) {}

    default void onBeginExitingNest(BlockPos nestPos, InhabitableNestBlockEntity.InhabitantReleaseReason reason) {}

    int getWanderRadiusFromNest();

    int getMaxDistanceFromNest();
}
