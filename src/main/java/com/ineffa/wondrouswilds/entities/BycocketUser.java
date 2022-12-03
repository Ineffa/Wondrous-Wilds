package com.ineffa.wondrouswilds.entities;

import net.minecraft.entity.projectile.ProjectileEntity;

public interface BycocketUser {

    boolean isAccurateWith(ProjectileEntity projectile);

    boolean canSharpshotWith(ProjectileEntity projectile);
}
