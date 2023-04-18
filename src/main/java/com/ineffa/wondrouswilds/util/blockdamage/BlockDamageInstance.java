package com.ineffa.wondrouswilds.util.blockdamage;

import net.minecraft.util.math.BlockPos;

public class BlockDamageInstance {

    public static final byte MINIMUM_STAGE = 1;
    public static final byte MAXIMUM_STAGE = 10;

    private final BlockPos pos;

    protected byte stage;

    public BlockDamageInstance(BlockPos pos, byte stage) {
        this.pos = pos;

        this.stage = stage;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public byte getStage() {
        return this.stage;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (!(object instanceof BlockDamageInstance instance)) return false;

        return this.pos.equals(instance.pos);
    }
}
