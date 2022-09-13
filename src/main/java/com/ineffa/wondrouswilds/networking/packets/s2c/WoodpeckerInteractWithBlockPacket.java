package com.ineffa.wondrouswilds.networking.packets.s2c;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import com.ineffa.wondrouswilds.util.fakeplayer.WoodpeckerFakePlayer;
import com.ineffa.wondrouswilds.util.fakeplayer.WoodpeckerItemUsageContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;

public final class WoodpeckerInteractWithBlockPacket {

    public static final Identifier ID = WondrousWildsNetwork.createChannelId("woodpecker_interact_with_block");

    @Environment(value = EnvType.CLIENT)
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ClientWorld world = client.world;
        if (world == null) return;

        int entityId = buf.readVarInt();
        Entity entity = client.world.getEntityById(entityId);
        if (!(entity instanceof WoodpeckerEntity woodpecker)) return;

        BlockHitResult hitResult = buf.readBlockHitResult();
        boolean useItem = buf.readBoolean();

        client.execute(() -> {
            WoodpeckerFakePlayer fakePlayer = new WoodpeckerFakePlayer(woodpecker);

            if (useItem) world.getBlockState(hitResult.getBlockPos()).onUse(world, fakePlayer, Hand.MAIN_HAND, hitResult);
            else {
                ItemStack heldItem = woodpecker.getMainHandStack();
                if (!heldItem.isEmpty()) heldItem.useOnBlock(new WoodpeckerItemUsageContext(woodpecker, fakePlayer, heldItem, hitResult));
            }
        });
    }
}
