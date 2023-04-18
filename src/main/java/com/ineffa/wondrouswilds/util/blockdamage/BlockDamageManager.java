package com.ineffa.wondrouswilds.util.blockdamage;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface BlockDamageManager {

    boolean applyDamageToBlock(BlockPos pos, byte amount, @Nullable Entity damagingEntity);

    void sendBlockDamageToClient(BlockPos pos, byte damageStage);
}
