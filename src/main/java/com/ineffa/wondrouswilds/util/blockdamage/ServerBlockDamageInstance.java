package com.ineffa.wondrouswilds.util.blockdamage;

import net.minecraft.util.math.BlockPos;

public class ServerBlockDamageInstance extends BlockDamageInstance {

    private int ticksUnchanged;

    public ServerBlockDamageInstance(BlockPos pos, byte stage) {
        super(pos, stage);
    }

    public void tick(BlockDamageManager damageManager) {
        if (this.ticksUnchanged > 0 && this.ticksUnchanged % 200 == 0) damageManager.applyDamageToBlock(this.getPos(), (byte) -1, null);

        ++this.ticksUnchanged;
    }

    public void setStage(byte stage) {
        this.stage = stage;
        this.ticksUnchanged = 0;
    }
}
