package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class WoodpeckerPeckBlockGoal extends MoveToTargetPosGoal {

    private final WoodpeckerEntity woodpecker;

    private boolean shouldStop = false;

    public WoodpeckerPeckBlockGoal(WoodpeckerEntity woodpecker, double speed, int range, int maxYDifference) {
        super(woodpecker, speed, range, maxYDifference);
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP, Control.LOOK));

        this.woodpecker = woodpecker;
    }

    @Override
    public boolean canStart() {
        return this.woodpecker.getRandom().nextInt(80) == 0 && this.woodpecker.canWander() && super.canStart();
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

        if (this.woodpecker.isPecking()) this.woodpecker.stopPecking(false);
    }

    @Override
    public void tick() {
        super.tick();

        World world = this.woodpecker.getWorld();
        BlockPos lookPos = this.getTargetPos();
        if (lookPos != null) {
            BlockState lookState = world.getBlockState(lookPos);
            if (lookState != null) {
                VoxelShape shape = lookState.getOutlineShape(world, lookPos);
                if (shape != null && !shape.isEmpty()) {
                    Box box = shape.getBoundingBox();
                    if (box != null) this.woodpecker.getLookControl().lookAt(box.getCenter().add(lookPos.getX(), lookPos.getY(), lookPos.getZ()));
                }
            }
        }

        if (this.hasReached()) {
            if (!this.woodpecker.isPecking()) {
                if (this.woodpecker.getRandom().nextInt(400) == 0) {
                    this.shouldStop = true;
                    return;
                }

                if (this.woodpecker.getRandom().nextInt(60) == 0) this.woodpecker.startPeckChain(1 + this.woodpecker.getRandom().nextInt(4));
            }
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return !this.woodpecker.canClingToPos(pos, false, null) && world.getBlockState(pos).isIn(WondrousWildsTags.BlockTags.WOODPECKERS_INTERACT_WITH);
    }

    @Override
    protected BlockPos getTargetPos() {
        return this.targetPos;
    }
}
