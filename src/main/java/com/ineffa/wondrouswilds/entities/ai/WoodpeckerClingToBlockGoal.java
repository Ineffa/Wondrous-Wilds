package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class WoodpeckerClingToBlockGoal extends MoveToTargetPosGoal {

    private final WoodpeckerEntity woodpecker;

    private boolean shouldStop = false;

    public WoodpeckerClingToBlockGoal(WoodpeckerEntity woodpecker, double speed, int range, int maxYDifference) {
        super(woodpecker, speed, range, maxYDifference);

        this.woodpecker = woodpecker;
    }

    @Override
    public boolean canStart() {
        return this.woodpecker.getRandom().nextInt(80) == 0 && this.woodpecker.isFlying() && this.woodpecker.canWander() && super.canStart();
    }

    @Override
    public void start() {
        super.start();

        this.shouldStop = false;
    }

    @Override
    public boolean shouldContinue() {
        return !this.shouldStop && super.shouldContinue();
    }

    @Override
    public void stop() {
        super.stop();

        if (this.hasReached()) this.woodpecker.tryClingingTo(this.getTargetPos());
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasReached()) this.shouldStop = true;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return this.woodpecker.canClingToPos(pos, true, null);
    }

    @Override
    protected BlockPos getTargetPos() {
        return this.targetPos;
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return 1.5D;
    }
}
