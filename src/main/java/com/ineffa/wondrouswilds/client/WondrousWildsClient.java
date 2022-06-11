package com.ineffa.wondrouswilds.client;

import com.ineffa.wondrouswilds.client.rendering.entity.FireflyRenderer;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class WondrousWildsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(WondrousWildsEntities.FIREFLY, FireflyRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.SMALL_POLYPORE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.BIG_POLYPORE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.PURPLE_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.PINK_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.RED_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.WHITE_VIOLET, RenderLayer.getCutout());
    }
}