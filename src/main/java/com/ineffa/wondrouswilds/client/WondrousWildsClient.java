package com.ineffa.wondrouswilds.client;

import com.ineffa.wondrouswilds.client.particle.SharpshotHitParticle;
import com.ineffa.wondrouswilds.client.rendering.WondrousWildsColorProviders;
import com.ineffa.wondrouswilds.client.rendering.entity.ChipmunkRenderer;
import com.ineffa.wondrouswilds.client.rendering.entity.FireflyRenderer;
import com.ineffa.wondrouswilds.client.rendering.entity.WoodpeckerRenderer;
import com.ineffa.wondrouswilds.client.rendering.entity.armor.BycocketRenderer;
import com.ineffa.wondrouswilds.client.screen.WondrousWildsScreens;
import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import com.ineffa.wondrouswilds.registry.WondrousWildsParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Environment(value = EnvType.CLIENT)
public class WondrousWildsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(WondrousWildsEntities.FIREFLY, FireflyRenderer::new);
        EntityRendererRegistry.register(WondrousWildsEntities.WOODPECKER, WoodpeckerRenderer::new);
        EntityRendererRegistry.register(WondrousWildsEntities.CHIPMUNK, ChipmunkRenderer::new);

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

        registerParticles();

        WondrousWildsColorProviders.register();

        WondrousWildsScreens.register();

        WondrousWildsNetwork.registerS2CPackets();
    }

    private static void registerParticles() {
        ParticleFactoryRegistry.getInstance().register(WondrousWildsParticles.SHARPSHOT_HIT, SharpshotHitParticle.Factory::new);
    }
}
