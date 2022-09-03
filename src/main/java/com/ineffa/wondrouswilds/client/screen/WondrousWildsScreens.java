package com.ineffa.wondrouswilds.client.screen;

import com.ineffa.wondrouswilds.client.screen.ingame.NestBoxScreen;
import com.ineffa.wondrouswilds.registry.WondrousWildsScreenHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(value = EnvType.CLIENT)
public final class WondrousWildsScreens {

    public static void register() {
        HandledScreens.register(WondrousWildsScreenHandlers.NEST_BOX, NestBoxScreen::new);
    }
}
