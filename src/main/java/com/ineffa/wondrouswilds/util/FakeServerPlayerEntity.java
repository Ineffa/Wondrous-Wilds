package com.ineffa.wondrouswilds.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class FakeServerPlayerEntity extends ServerPlayerEntity {

    public FakeServerPlayerEntity(ServerWorld world, GameProfile fakeProfile) {
        super(world.getServer(), world, fakeProfile, null);
    }

    @Override
    public void tick() {}
}
