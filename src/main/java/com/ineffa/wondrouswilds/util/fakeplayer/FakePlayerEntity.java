package com.ineffa.wondrouswilds.util.fakeplayer;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class FakePlayerEntity extends PlayerEntity {

    public FakePlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile, null);
    }

    @Override
    public void tick() {}

    @Override
    public void tickRiding() {}

    @Override
    protected void tickStatusEffects() {}

    @Override
    protected void tickRiptide(Box a, Box b) {}

    @Override
    protected void tickNewAi() {}

    @Override
    protected void tickNetherPortalCooldown() {}

    @Override
    protected void tickNetherPortal() {}

    @Override
    protected void tickItemStackUsage(ItemStack stack) {}

    @Override
    protected void tickInVoid() {}

    @Override
    protected void tickHandSwing() {}

    @Override
    protected void tickCramming() {}

    @Override
    public void tickMovement() {}

    @Override
    public void baseTick() {}

    @Override
    public void attemptTickInVoid() {}

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
