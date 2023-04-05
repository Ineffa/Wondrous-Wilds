package com.ineffa.wondrouswilds.mixin;

import net.minecraft.entity.ai.pathing.EntityNavigation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityNavigation.class)
public interface EntityNavigationInvoker {

    @Invoker("resetNodeAndStop")
    void invokeResetNodeAndStop();
}
