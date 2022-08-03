package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WondrousWildsSounds {

    public static SoundEvent WOODPECKER_DRUM = createSoundEvent("entity.woodpecker.drum");

    private static SoundEvent createSoundEvent(String name) {
        Identifier id = new Identifier(WondrousWilds.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void initialize() {}
}
