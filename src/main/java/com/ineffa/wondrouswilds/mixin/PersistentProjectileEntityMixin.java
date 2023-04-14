package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.projectiles.BlockBreakingProjectile;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;raycast(Lnet/minecraft/world/RaycastContext;)Lnet/minecraft/util/hit/BlockHitResult;", ordinal = 0))
    private BlockHitResult penetrateSoftBlocks(World world, RaycastContext raycastContext) {
        if (!this.getWorld().isClient() && this.getType() == WondrousWildsEntities.BODKIN_ARROW) {
            BlockBreakingProjectile projectile = (BlockBreakingProjectile) this;
            if (this.getVelocity().length() >= projectile.getStrongSpeedThreshold()) {
                boolean finishedPenetrating = false;
                while (!finishedPenetrating) {
                    BlockHitResult result = world.raycast(raycastContext);
                    if (result.getType() == HitResult.Type.BLOCK) {
                        BlockPos hitPos = result.getBlockPos();
                        if (projectile.canPenetratePos(this.getWorld(), hitPos)) {
                            this.getWorld().breakBlock(hitPos, true, this.getOwner());
                            continue;
                        }
                    }
                    finishedPenetrating = true;
                }
            }
        }

        return world.raycast(raycastContext);
    }

    private PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
}
