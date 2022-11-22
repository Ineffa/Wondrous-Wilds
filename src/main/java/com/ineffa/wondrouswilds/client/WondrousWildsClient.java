package com.ineffa.wondrouswilds.client;

import com.ineffa.wondrouswilds.client.rendering.WondrousWildsColorProviders;
import com.ineffa.wondrouswilds.client.rendering.entity.FireflyRenderer;
import com.ineffa.wondrouswilds.client.rendering.entity.WoodpeckerRenderer;
import com.ineffa.wondrouswilds.client.screen.WondrousWildsScreens;
import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(value = EnvType.CLIENT)
public class WondrousWildsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(WondrousWildsEntities.FIREFLY, FireflyRenderer::new);
        EntityRendererRegistry.register(WondrousWildsEntities.WOODPECKER, WoodpeckerRenderer::new);

        GeoArmorRenderer.registerArmorRenderer(new BycocketRenderer(),
                WondrousWildsItems.BLACK_BYCOCKET
        );

        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.SMALL_POLYPORE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.BIG_POLYPORE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.PURPLE_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.PINK_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.RED_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.WHITE_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.POTTED_PURPLE_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.POTTED_PINK_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.POTTED_RED_VIOLET, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.POTTED_WHITE_VIOLET, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(WondrousWildsBlocks.RED_BIRCH_LEAVES, RenderLayer.getCutoutMipped());

        WondrousWildsColorProviders.register();

        WondrousWildsScreens.register();

        WondrousWildsNetwork.registerS2CPackets();
    }
}
