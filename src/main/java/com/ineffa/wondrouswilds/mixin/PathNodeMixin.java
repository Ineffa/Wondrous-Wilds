package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.ai.navigation.JumpablePathNode;
import net.minecraft.entity.ai.pathing.PathNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PathNode.class)
public class PathNodeMixin implements JumpablePathNode {

    @Unique
    private boolean shouldBeJumpedTo = false;

    @Unique
    @Override
    public boolean shouldBeJumpedTo() {
        return this.shouldBeJumpedTo;
    }

    @Unique
    @Override
    public void setShouldBeJumpedTo(boolean shouldBeJumpedTo) {
        this.shouldBeJumpedTo = shouldBeJumpedTo;
    }
}
