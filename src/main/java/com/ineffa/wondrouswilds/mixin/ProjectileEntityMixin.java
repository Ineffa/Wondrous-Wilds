package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.BycocketUser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity {

    @ModifyVariable(method = "setVelocity(DDDFF)V", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    public float preventSpreadWithBycocket(float divergence) {
        if (this.getOwner() instanceof LivingEntity shooter && ((BycocketUser) shooter).shouldPreventSpread((ProjectileEntity) (Object) this))
            return 0.0F;

        return divergence;
    }

    @Shadow public abstract @Nullable Entity getOwner();

    private ProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
}
