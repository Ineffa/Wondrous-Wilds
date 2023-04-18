package com.ineffa.wondrouswilds.networking.packets.s2c;

import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import com.ineffa.wondrouswilds.util.blockdamage.BlockDamageHolder;
import com.ineffa.wondrouswilds.util.blockdamage.BlockDamageInstance;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockDamagePacket {

    public static final Identifier ID = WondrousWildsNetwork.createChannelId("block_damage");

    @Environment(value = EnvType.CLIENT)
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        long posKey = buf.readLong();
        byte damageStage = buf.readByte();

        World world = (World) client.world;
        if (world == null) return;

        client.execute(() -> {
            BlockDamageHolder holder = (BlockDamageHolder) world;
            BlockPos pos = BlockPos.fromLong(posKey);

            if (damageStage < BlockDamageInstance.MINIMUM_STAGE || damageStage >= BlockDamageInstance.MAXIMUM_STAGE) {
                holder.removeDamageAtPos(pos);
                return;
            }

            holder.createOrOverwriteDamage(new BlockDamageInstance(pos, damageStage));
        });
    }
}
