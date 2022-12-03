package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.CanTakeSharpshots;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    /**
     * Displays a purple box to visualize sharpshot hitboxes alongside regular hitboxes.
     */
    @Inject(method = "renderHitbox", at = @At("TAIL"))
    private static void addSharpshotHitboxDebugVisualization(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo callback) {
        if (entity instanceof CanTakeSharpshots canTakeSharpshots)
            WorldRenderer.drawBox(matrices, vertices, canTakeSharpshots.getSharpshotHitbox().offset(entity.getPos().negate()), 1.0F, 0.0F, 1.0F, 0.5F);
    }
}
