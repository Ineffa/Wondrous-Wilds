package com.ineffa.wondrouswilds.util.fakeplayer;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class WoodpeckerFakePlayer extends FakePlayerEntity {

    private final WoodpeckerEntity woodpecker;
    private final WoodpeckerFakeAbilities woodpeckerAbilities;

    public WoodpeckerFakePlayer(WoodpeckerEntity woodpecker) {
        super(woodpecker.getWorld(), woodpecker.getBlockPos(), woodpecker.getYaw(), new GameProfile(woodpecker.getUuid(), woodpecker.getName().getString()));

        this.woodpecker = woodpecker;
        this.woodpeckerAbilities = new WoodpeckerFakeAbilities(woodpecker);

        this.copyPositionAndRotation(woodpecker);

        this.setInvulnerable(woodpecker.isInvulnerable());
    }

    @Override
    public PlayerAbilities getAbilities() {
        return this.woodpeckerAbilities;
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

    @Nullable
    @Override
    public ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership) {
        if (stack.isEmpty()) return null;

        if (this.woodpecker.getWorld().isClient()) this.woodpecker.swingHand(Hand.MAIN_HAND);

        ItemEntity itemEntity = new ItemEntity(this.woodpecker.getWorld(), this.woodpecker.getX(), this.woodpecker.getEyeY() - 0.3D, this.woodpecker.getZ(), stack);
        itemEntity.setPickupDelay(40);

        if (retainOwnership) itemEntity.setThrower(this.woodpecker.getUuid());

        if (throwRandomly) {
            float f = this.woodpecker.getRandom().nextFloat() * 0.5F;
            float g = this.woodpecker.getRandom().nextFloat() * ((float) Math.PI * 2.0F);
            itemEntity.setVelocity(-MathHelper.sin(g) * f, 0.2F, MathHelper.cos(g) * f);
        }
        else {
            float g = MathHelper.sin(this.woodpecker.getPitch() * ((float) Math.PI / 180.0F));
            float h = MathHelper.cos(this.woodpecker.getPitch() * ((float) Math.PI / 180.0F));
            float i = MathHelper.sin(this.woodpecker.getYaw() * ((float) Math.PI / 180.0F));
            float j = MathHelper.cos(this.woodpecker.getYaw() * ((float) Math.PI / 180.0F));
            float k = this.woodpecker.getRandom().nextFloat() * ((float) Math.PI * 2.0F);
            float l = 0.02F * this.woodpecker.getRandom().nextFloat();
            float f = 0.3F;
            itemEntity.setVelocity((double) (-i * h * f) + Math.cos(k) * (double) l, -g * f + 0.1F + (this.woodpecker.getRandom().nextFloat() - this.woodpecker.getRandom().nextFloat()) * 0.1F, (double) (j * h * f) + Math.sin(k) * (double) l);
        }

        this.woodpecker.getEntityWorld().spawnEntity(itemEntity);
        return itemEntity;
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
