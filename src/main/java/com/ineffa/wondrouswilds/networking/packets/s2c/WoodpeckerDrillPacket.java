package com.ineffa.wondrouswilds.networking.packets.s2c;

import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public final class WoodpeckerDrillPacket {

    public static final Identifier ID = WondrousWildsNetwork.createChannelId("woodpecker_drill");

    @Environment(value = EnvType.CLIENT)
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos drillPos = buf.readBlockPos();
        Direction drillSide = buf.readEnumConstant(Direction.class);

        client.execute(() -> {
            for (int i = 0; i < 10; ++i) client.particleManager.addBlockBreakingParticles(drillPos, drillSide);
        });
    }
}
