package com.ineffa.wondrouswilds.util.fakeplayer;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WoodpeckerFakePlayer extends FakeServerPlayerEntity {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerFakePlayer(WoodpeckerEntity woodpecker) {
        super((ServerWorld) woodpecker.getWorld(), new GameProfile(UUID.fromString("62ca5b38-99b2-4cea-93a1-b32935725151"), woodpecker.getName().getString()));

        this.copyPositionAndRotation(woodpecker);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = woodpecker.getEquippedStack(slot);
            if (itemStack.isEmpty()) continue;
            this.equipStack(slot, itemStack.copy());
        }

        this.setInvulnerable(woodpecker.isInvulnerable());

        this.woodpecker = woodpecker;
    }

    @Override
    public boolean canConsume(boolean ignoreHunger) {
        return true;
    }

    @Override
    public void setStackInHand(Hand hand, ItemStack stack) {
        if (hand == Hand.MAIN_HAND) this.woodpecker.equipStack(EquipmentSlot.MAINHAND, stack);

        else if (hand == Hand.OFF_HAND) this.woodpecker.dropStack(stack);

        else super.setStackInHand(hand, stack);
    }

    @Override
    public ItemStack getStackInHand(Hand hand) {
        return this.woodpecker.getStackInHand(hand);
    }

    @Nullable
    @Override
    public ItemEntity dropStack(ItemStack stack) {
        return this.woodpecker.dropStack(stack);
    }

    @Nullable
    @Override
    public ItemEntity dropStack(ItemStack stack, float yOffset) {
        return this.woodpecker.dropStack(stack, yOffset);
    }

    @Override
    public boolean giveItemStack(ItemStack stack) {
        if (this.woodpecker.getMainHandStack().isEmpty()) {
            this.setStackInHand(Hand.MAIN_HAND, stack);
            return true;
        }

        return false;
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
