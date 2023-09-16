package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mixin(GeoArmorRenderer.class)
public abstract class GeoArmorRendererMixin<T extends ArmorItem & IAnimatable> {

    @Shadow protected T currentArmorItem;
    @Shadow protected ItemStack itemStack;
    @Shadow public abstract Identifier getTextureLocation(T instance);

    @ModifyArg(method = "renderRecursively", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getArmorGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
    private RenderLayer forceOverrideBycocketRenderLayers(RenderLayer renderLayer) {
        if (this.itemStack.isIn(WondrousWildsTags.ItemTags.BYCOCKETS)) renderLayer = RenderLayer.getEntityCutout(this.getTextureLocation(this.currentArmorItem));
        return renderLayer;
    }
}
