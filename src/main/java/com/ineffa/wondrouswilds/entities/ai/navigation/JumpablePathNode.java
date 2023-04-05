package com.ineffa.wondrouswilds.entities.ai.navigation;

public interface JumpablePathNode {

    boolean shouldBeJumpedTo();

    void setShouldBeJumpedTo(boolean shouldBeJumpedTo);
}
