package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.HoppingMob;
import com.ineffa.wondrouswilds.entities.projectiles.BodkinArrowEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "travel", at = @At(value = "STORE", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
    private float preventHoppingMobSliding(float slipperiness) {
        if (this instanceof HoppingMob) slipperiness = 0.0F;

        return slipperiness;
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V", ordinal = 0, shift = At.Shift.AFTER))
    private void bodkinArrowDisableShield(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
        if ((LivingEntity) (Object) this instanceof PlayerEntity player && source.getSource() instanceof BodkinArrowEntity bodkinArrow && bodkinArrow.getVelocity().length() >= bodkinArrow.getStrongVelocityThreshold()) {
            player.getItemCooldownManager().set(Items.SHIELD, 40);
            player.clearActiveItem();
            player.getWorld().sendEntityStatus(this, EntityStatuses.BREAK_SHIELD);
        }
    }
}
