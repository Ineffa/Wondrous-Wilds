package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.CanTakeSharpshots;
import com.ineffa.wondrouswilds.entities.projectiles.CanSharpshot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(ProjectileUtil.class)
public class ProjectileUtilMixin {

    /**
     * <p> Detects and registers when a projectile should land a sharpshot.
     * <p> First, it will check that the projectile is capable of landing a sharpshot.
     * <p> Then, it will register a sharpshot if the projectile detects a collision with an entity's sharpshot hitbox.
     */
    @Inject(method = "getEntityCollision(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;F)Lnet/minecraft/util/hit/EntityHitResult;", at = @At("HEAD"))
    private static void detectAndRegisterSharpshots(World world, Entity entity, Vec3d min, Vec3d max, Box box, Predicate<Entity> predicate, float f, CallbackInfoReturnable<@Nullable EntityHitResult> callback) {
        if (entity instanceof CanSharpshot sharpshotProjectile && sharpshotProjectile.canLandSharpshot()) {
            boolean registerSharpshot = false;
            for (Entity targetEntity : world.getOtherEntities(entity, box, predicate)) {
                if (!(targetEntity instanceof CanTakeSharpshots canTakeSharpshots)) continue;

                Box sharpshotHitbox = canTakeSharpshots.getSharpshotHitbox().expand(f, 0.0D, f);
                Optional<Vec3d> sharpshot = sharpshotHitbox.raycast(min, max);

                if (sharpshot.isPresent()) {
                    registerSharpshot = true;
                    break;
                }
            }
            if (registerSharpshot) sharpshotProjectile.registerSharpshot();
        }
    }
}
