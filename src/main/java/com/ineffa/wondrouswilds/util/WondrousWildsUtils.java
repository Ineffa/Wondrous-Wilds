package com.ineffa.wondrouswilds.util;

import net.minecraft.util.math.Direction;

import java.util.Arrays;

public class WondrousWildsUtils {

    public static final Direction[] HORIZONTAL_DIRECTIONS = Arrays.stream(Direction.values()).filter((direction) -> direction.getAxis().isHorizontal()).toArray(Direction[]::new);
}
