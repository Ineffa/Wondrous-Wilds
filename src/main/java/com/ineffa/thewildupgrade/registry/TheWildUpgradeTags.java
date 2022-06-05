package com.ineffa.thewildupgrade.registry;

import com.ineffa.thewildupgrade.TheWildUpgrade;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheWildUpgradeTags {

    public static class BlockTags {
        public static final TagKey<Block> FIREFLIES_SPAWNABLE_ON = createBlockTag("fireflies_spawnable_on");
        public static final TagKey<Block> VIOLETS = createBlockTag("violets");
    }

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(TheWildUpgrade.MOD_ID, name));
    }
}
