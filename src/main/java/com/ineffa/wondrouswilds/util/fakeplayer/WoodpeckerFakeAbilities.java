package com.ineffa.wondrouswilds.util.fakeplayer;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.nbt.NbtCompound;

public class WoodpeckerFakeAbilities extends PlayerAbilities {

    public WoodpeckerFakeAbilities(WoodpeckerEntity woodpecker) {
        this.invulnerable = woodpecker.isInvulnerable();

        this.flying = woodpecker.isFlying();
        this.allowFlying = true;

        this.creativeMode = false;
        this.allowModifyWorld = true;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {}

    @Override
    public void readNbt(NbtCompound nbt) {}
}
