package com.ineffa.wondrouswilds.entities.projectiles;

import org.jetbrains.annotations.Nullable;

public enum ProjectileBlockDamageType {
    PIERCE((byte) 10),
    BREAK((byte) 10),
    STRONG((byte) 5),
    MEDIUM((byte) 4),
    WEAK((byte) 2);

    private final byte damage;

    ProjectileBlockDamageType(byte damage) {
        this.damage = damage;
    }

    public byte getDamage() {
        return this.damage;
    }

    @Nullable
    public static ProjectileBlockDamageType getTypeForHardness(float hardness) {
        if (hardness < 0.0F) return null;
        if (hardness <= 0.3F) return PIERCE;
        if (hardness <= 1.0F) return BREAK;
        if (hardness <= 2.0F) return STRONG;
        if (hardness <= 3.0F) return MEDIUM;
        if (hardness <= 4.5F) return WEAK;
        return null;
    }
}
