package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WondrousWildsParticles {

    public static final DefaultParticleType SHARPSHOT_HIT = registerSimpleParticleType("sharpshot_hit");

    private static DefaultParticleType registerSimpleParticleType(String name) {
        return registerParticleType(name, FabricParticleTypes.simple());
    }

    private static DefaultParticleType registerParticleType(String name, DefaultParticleType type) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(WondrousWilds.MOD_ID, name), type);
    }

    public static void initialize() {}
}
