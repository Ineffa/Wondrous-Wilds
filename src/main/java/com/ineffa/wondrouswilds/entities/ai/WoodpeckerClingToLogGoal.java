package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class WoodpeckerClingToLogGoal extends MoveToTargetPosGoal {

    private final WoodpeckerEntity woodpecker;

    private boolean shouldStop = false;
    private int ticksUnableToReach;
    private int ticksTryingToReach;

    public WoodpeckerClingToLogGoal(WoodpeckerEntity woodpecker, double speed, int range, int maxYDifference) {
        super(woodpecker, speed, range, maxYDifference);

        this.woodpecker = woodpecker;
    }

    @Override
    public boolean canStart() {
        return this.woodpecker.getRandom().nextInt(200) == 0 && this.woodpecker.canWander() && this.findTargetPos();
    }

    @Override
    public void start() {
        super.start();

        this.shouldStop = false;
        this.ticksUnableToReach = 0;
        this.ticksTryingToReach = 0;

        if (!this.woodpecker.isFlying()) this.woodpecker.setFlying(true);
    }

    @Override
    public boolean shouldContinue() {
        return !this.shouldStop && this.woodpecker.canWander() && this.isTargetPos(this.woodpecker.getWorld(), this.targetPos);
    }

    @Override
    public void stop() {
        super.stop();

        if (this.hasReached()) this.woodpecker.tryClingingTo(this.getTargetPos());
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasReached()) {
            this.shouldStop = true;
            this.ticksUnableToReach = 0;
            return;
        }

        if (this.woodpecker.getNavigation().isIdle()) {
            if (this.ticksUnableToReach >= 100) {
                if (this.woodpecker.isFlying()) {
                    this.woodpecker.setFlying(true);
                    this.ticksUnableToReach = 0;
                }
                else this.shouldStop = true;

                return;
            }
            ++this.ticksUnableToReach;
        }
        else this.ticksUnableToReach = 0;

        if (this.ticksTryingToReach >= 400) {
            this.shouldStop = true;
            return;
        }
        ++this.ticksTryingToReach;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return !world.getBlockState(pos).isIn(WondrousWildsTags.BlockTags.WOODPECKERS_INTERACT_WITH) && this.woodpecker.canClingToPos(pos, true, null);
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
