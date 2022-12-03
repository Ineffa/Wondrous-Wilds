package com.ineffa.wondrouswilds.entities.projectiles;

public interface CanSharpshot {

    boolean canLandSharpshot();

    boolean hasRegisteredSharpshot();

    void registerSharpshot();
}
