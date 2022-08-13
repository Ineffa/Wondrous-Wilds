package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.blocks.entity.TreeHollowBlockEntity;
import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import com.ineffa.wondrouswilds.entities.TreeHollowNester;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class FindOrReturnToTreeHollowGoal extends MoveToTargetPosGoal {

    private final TreeHollowNester nester;
    private final MobEntity nesterEntity;

    private boolean shouldStop = false;

    private boolean lookingForNest = false;

    public FindOrReturnToTreeHollowGoal(TreeHollowNester nester, double speed, int range, int maxYDifference) {
        super((PathAwareEntity) nester, speed, range, maxYDifference);

        this.nester = nester;
        this.nesterEntity = (MobEntity) nester;
    }

    @Override
    public boolean canStart() {
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
        return !this.shouldStop && this.isTargetPos(this.nester.getWorld(), this.targetPos);
    }

    @Override
    public void stop() {
        super.stop();

        if (!(this.nesterEntity.getWorld().getBlockEntity(this.getTargetPos()) instanceof TreeHollowBlockEntity treeHollow)) return;

        if (this.hasReached()) {
            if (!treeHollow.tryAddingInhabitant(this.nester)) {
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

        return world.getBlockEntity(pos) instanceof TreeHollowBlockEntity;
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
