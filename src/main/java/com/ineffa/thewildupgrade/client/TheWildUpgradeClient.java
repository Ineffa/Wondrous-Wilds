package com.ineffa.thewildupgrade.client;

import com.ineffa.thewildupgrade.client.rendering.entity.FireflyRenderer;
import com.ineffa.thewildupgrade.registry.TheWildUpgradeBlocks;
import com.ineffa.thewildupgrade.registry.TheWildUpgradeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class TheWildUpgradeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(TheWildUpgradeEntities.FIREFLY, FireflyRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(TheWildUpgradeBlocks.SMALL_POLYPORE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TheWildUpgradeBlocks.BIG_POLYPORE, RenderLayer.getCutout());
    }
}
