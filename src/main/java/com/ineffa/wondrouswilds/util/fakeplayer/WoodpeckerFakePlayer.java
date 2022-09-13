package com.ineffa.wondrouswilds.util.fakeplayer;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;

public class WoodpeckerFakePlayer extends FakePlayerEntity {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerFakePlayer(WoodpeckerEntity woodpecker) {
        super(woodpecker.getWorld(), woodpecker.getBlockPos(), woodpecker.getYaw(), new GameProfile(woodpecker.getUuid(), woodpecker.getName().getString()));
        this.woodpecker = woodpecker;

        this.copyPositionAndRotation(woodpecker);

        this.setInvulnerable(woodpecker.isInvulnerable());
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
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        this.woodpecker.equipStack(slot, stack);
    }

    @Override
    public void onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack) {
        this.woodpecker.onEquipStack(slot, oldStack, newStack);
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return this.woodpecker.getEquippedStack(slot);
    }

    @Override
    public ItemStack getOffHandStack() {
        return this.woodpecker.getOffHandStack();
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return this.woodpecker.getPickBlockStack();
    }
}
