package com.ineffa.wondrouswilds.entities;

import com.ineffa.wondrouswilds.networking.packets.s2c.NestTransitionClearPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class NestTransition {

    private final NestTransitionType type;

    private final BlockNester nester;
    private final MobEntity nesterEntity;
    private final boolean isClient;

    private int remainingTicks;

    public NestTransition(NestTransitionType type, BlockNester nester, boolean isClient) {
        this.type = type;
        this.nester = nester;
        this.nesterEntity = (MobEntity) nester;
        this.isClient = isClient;

        this.remainingTicks = nester.getDurationOfNestTransitionType(type);
    }

    public void tick() {
        if (!this.isClient && this.nester.getNestPos() == null) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeVarInt(this.nesterEntity.getId());
            for (ServerPlayerEntity receiver : PlayerLookup.tracking(this.nesterEntity)) ServerPlayNetworking.send(receiver, NestTransitionClearPacket.ID, buf);

            this.nester.clearCurrentNestTransition();

            return;
        }

        if (this.remainingTicks-- <= 0) this.nester.finishCurrentNestTransition();
    }

    public NestTransitionType getType() {
        return this.type;
    }
}
