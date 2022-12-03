package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.projectiles.CanSharpshot;
import com.ineffa.wondrouswilds.registry.WondrousWildsAdvancementCriteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "updateKilledAdvancementCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/OnKilledCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)V", shift = At.Shift.AFTER))
    private void triggerSharpshotKillCriterion(Entity entityKilled, int score, DamageSource damageSource, CallbackInfo callback) {
        if (damageSource.getSource() instanceof CanSharpshot sharpshotProjectile && sharpshotProjectile.hasRegisteredSharpshot())
            WondrousWildsAdvancementCriteria.SHARPSHOT_KILL.trigger((ServerPlayerEntity) (Object) this, entityKilled, damageSource);
    }
}
