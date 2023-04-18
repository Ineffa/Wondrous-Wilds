package com.ineffa.wondrouswilds.util.blockdamage;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface BlockDamageHolder {

    Map<Long, BlockDamageInstance> getBlockDamageInstanceMap();

    @Nullable
    default BlockDamageInstance getDamageAtPos(BlockPos pos) {
        long posLong = pos.asLong();
        if (!this.getBlockDamageInstanceMap().containsKey(posLong)) return null;
        return this.getBlockDamageInstanceMap().get(posLong);
    }

    default void createOrOverwriteDamage(BlockDamageInstance damage) {
        this.getBlockDamageInstanceMap().put(damage.getPos().asLong(), damage);
    }

    default void removeDamageAtPos(BlockPos pos) {
        this.getBlockDamageInstanceMap().remove(pos.asLong());
    }
}
