package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.screen.NestBoxScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class WondrousWildsScreenHandlers {

    public static final ScreenHandlerType<NestBoxScreenHandler> NEST_BOX = registerScreenHandler("nest_box", NestBoxScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String name, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registry.SCREEN_HANDLER, new Identifier(WondrousWilds.MOD_ID, name), new ScreenHandlerType<>(factory));
    }

    public static void initialize() {}
}
