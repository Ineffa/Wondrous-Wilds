package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;

public class FireflyHideGoal extends MoveToTargetPosGoal {

    public FireflyHideGoal(FireflyEntity mob, double speed, int range, int maxYDifference) {
        super(mob, speed, range, maxYDifference);
    }

    @Override
    public boolean canStart() {
        if (!((FireflyEntity) this.mob).shouldHide()) return false;

        return super.canStart();
    }

    @Override
    public void tick() {
        if (this.hasReached()) {
            BlockPos pos = this.getTargetPos();
            this.mob.world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(this.mob.world.getBlockState(pos)));

            this.mob.discard();
        }

        if (!((FireflyEntity) this.mob).isFlying()) {
            ((FireflyEntity) this.mob).setFlying(true);
        }

        super.tick();
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isIn(WondrousWildsTags.BlockTags.FIREFLIES_HIDE_IN);
    }

    @Override
    protected BlockPos getTargetPos() {
        return this.targetPos;
    }
}
