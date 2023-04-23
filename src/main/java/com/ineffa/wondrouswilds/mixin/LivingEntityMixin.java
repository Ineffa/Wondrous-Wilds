package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.HoppingMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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
}
