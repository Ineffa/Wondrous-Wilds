package com.ineffa.wondrouswilds.networking.packets.s2c;

import com.ineffa.wondrouswilds.entities.BlockNester;
import com.ineffa.wondrouswilds.entities.NestTransitionType;
import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public final class NestTransitionStartPacket {

    public static final Identifier ID = WondrousWildsNetwork.createChannelId("nest_transition_start");

    @Environment(value = EnvType.CLIENT)
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        int entityId = buf.readVarInt();
        NestTransitionType transitionType = buf.readEnumConstant(NestTransitionType.class);
        int angle = buf.readByte() * 90;

        World world = (World) client.world;
        if (world == null) return;

        client.execute(() -> {
            Entity entity = world.getEntityById(entityId);
            if (entity instanceof BlockNester nester) {
                nester.startNewNestTransition(transitionType);

                entity.setYaw(angle);
                entity.setBodyYaw(entity.getYaw());
                entity.setHeadYaw(entity.getYaw());
            }
        });
    }
}
