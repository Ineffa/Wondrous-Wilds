package com.ineffa.wondrouswilds.entities;

public enum NestTransitionType {
    ENTER("EnterNest"),
    EXIT("ExitNest"),
    PEEK("StartPeekingFromNest"),
    UNPEEK("StopPeekingFromNest");

    private final String animationName;

    public String getAnimationName() {
        return this.animationName;
    }

    NestTransitionType(String animationName) {
        this.animationName = animationName;
    }
}
