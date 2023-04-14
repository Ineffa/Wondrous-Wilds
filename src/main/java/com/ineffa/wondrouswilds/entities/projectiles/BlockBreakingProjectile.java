package com.ineffa.wondrouswilds.entities.projectiles;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface BlockBreakingProjectile {

    float getHardBlockCeiling();

    float getSoftBlockCeiling();

    double getStrongSpeedThreshold();

    @Nullable
    ProjectileBlockBreakType testBlockBreakCapability(World world, BlockPos pos);

    boolean canPenetratePos(World world, BlockPos pos);
}
