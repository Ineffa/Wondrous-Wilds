package com.ineffa.wondrouswilds.entities.ai.navigation;

import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;

public class JumpPathNodeNavigator extends PathNodeNavigator implements ModifiesSuccessorsCapacity {

    public JumpPathNodeNavigator(JumpPathNodeMaker pathNodeMaker, int range) {
        super(pathNodeMaker, range);
    }

    private final PathNode[] jumpSuccessors = new PathNode[1320];

    @Override
    public PathNode[] getSuccessorArray() {
        return this.jumpSuccessors;
    }
}