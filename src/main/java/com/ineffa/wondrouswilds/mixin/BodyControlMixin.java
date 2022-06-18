package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BodyControl.class)
public class BodyControlMixin {

    @Shadow @Final private MobEntity entity;

    @Inject(at = @At("HEAD"), method = "isIndependent", cancellable = true)
    private void stopFireflyFromControlling(CallbackInfoReturnable<Boolean> callback) {
        if (this.entity.getFirstPassenger() instanceof FireflyEntity) callback.setReturnValue(true);
    }
}
