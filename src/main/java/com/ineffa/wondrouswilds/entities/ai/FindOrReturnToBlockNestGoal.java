package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.blocks.entity.InhabitableNestBlockEntity;
import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import com.ineffa.wondrouswilds.entities.BlockNester;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class FindOrReturnToBlockNestGoal extends MoveToTargetPosGoal {

    private final BlockNester nester;
    private final MobEntity nesterEntity;

    private int nextCheckDelay = 40;
    private boolean shouldStop = false;

    private boolean lookingForNest = false;

    public FindOrReturnToBlockNestGoal(BlockNester nester, double speed, int range, int maxYDifference) {
        super((PathAwareEntity) nester, speed, range, maxYDifference);

        this.nester = nester;
        this.nesterEntity = (MobEntity) nester;
    }

    @Override
    public boolean canStart() {
        if (this.nextCheckDelay > 0) {
            --this.nextCheckDelay;
            return false;
        }
        else this.nextCheckDelay = 40;

        if (!(this.nester.shouldReturnToNest() || (this.nester.shouldFindNest() && this.nester.getCannotInhabitNestTicks() <= 0))) return false;

        this.lookingForNest = this.nester.shouldFindNest();

        return this.findTargetPos();
    }

    @Override
    public void start() {
        super.start();

        this.shouldStop = false;

        if (this.nesterEntity instanceof FlyingAndWalkingAnimalEntity flyingEntity && !flyingEntity.isFlying()) flyingEntity.setFlying(true);
    }

    @Override
    public boolean shouldContinue() {
        return !this.shouldStop && this.isTargetPos(this.nesterEntity.getWorld(), this.targetPos);
    }

    @Override
    public void stop() {
        super.stop();

        this.nextCheckDelay = 40;

        if (!(this.nesterEntity.getWorld().getBlockEntity(this.getTargetPos()) instanceof InhabitableNestBlockEntity nestBlock)) {
            this.nester.clearNestPos();
            return;
        }

        if (this.hasReached()) {
            if (!nestBlock.tryAddingInhabitant(this.nester)) {
                this.nester.setCannotInhabitNestTicks(this.nester.getMinTicksOutOfNest());
                this.nester.clearNestPos();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasReached()) this.shouldStop = true;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        if (!this.lookingForNest) return true;

        return world.getBlockEntity(pos) instanceof InhabitableNestBlockEntity;
    }

    @Override
    protected BlockPos getTargetPos() {
        return this.targetPos;
    }

    @Override
    protected boolean findTargetPos() {
        if (!this.lookingForNest) {
            this.targetPos = this.nester.getNestPos();
            return true;
        }

        return super.findTargetPos();
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return 1.5D;
    }
}
