package com.ineffa.thewildupgrade.client;

import com.ineffa.thewildupgrade.client.rendering.entity.FireflyRenderer;
import com.ineffa.thewildupgrade.registry.TheWildUpgradeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class TheWildUpgradeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(TheWildUpgradeEntities.FIREFLY, FireflyRenderer::new);
    }
}
