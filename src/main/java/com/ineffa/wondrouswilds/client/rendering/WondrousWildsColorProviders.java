package com.ineffa.wondrouswilds.client.rendering;

import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public final class WondrousWildsColorProviders {

    public static void register() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getYellowBirchLeavesColor(), WondrousWildsBlocks.YELLOW_BIRCH_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getOrangeBirchLeavesColor(), WondrousWildsBlocks.ORANGE_BIRCH_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getRedBirchLeavesColor(), WondrousWildsBlocks.RED_BIRCH_LEAVES);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getYellowBirchLeavesColor(), WondrousWildsItems.YELLOW_BIRCH_LEAVES);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getOrangeBirchLeavesColor(), WondrousWildsItems.ORANGE_BIRCH_LEAVES);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getRedBirchLeavesColor(), WondrousWildsItems.RED_BIRCH_LEAVES);
    }

    public static int getYellowBirchLeavesColor() {
        return 14924323;
    }

    public static int getOrangeBirchLeavesColor() {
        return 15304240;
    }

    public static int getRedBirchLeavesColor() {
        return 16204080;
    }
}
