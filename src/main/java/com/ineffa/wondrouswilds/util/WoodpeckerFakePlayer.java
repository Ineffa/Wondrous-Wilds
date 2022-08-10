package com.ineffa.wondrouswilds.util;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

import java.util.UUID;

public class WoodpeckerFakePlayer extends FakeServerPlayerEntity {

    public WoodpeckerFakePlayer(WoodpeckerEntity woodpecker) {
        super((ServerWorld) woodpecker.getWorld(), new GameProfile(UUID.fromString("62ca5b38-99b2-4cea-93a1-b32935725151"), woodpecker.getName().getString()));

        this.copyPositionAndRotation(woodpecker);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = woodpecker.getEquippedStack(slot);
            if (itemStack.isEmpty()) continue;
            this.equipStack(slot, itemStack.copy());
        }

        this.setInvulnerable(woodpecker.isInvulnerable());
    }

    @Override
    public boolean canConsume(boolean ignoreHunger) {
        return true;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    @Override
    public boolean isSpectator() {
        return false;
    }
}
