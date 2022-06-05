package com.ineffa.thewildupgrade.registry;

import com.ineffa.thewildupgrade.TheWildUpgrade;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheWildUpgradeTags {

    public static class BlockTags {
        public static final TagKey<Block> FIREFLIES_SPAWNABLE_ON = createBlockTag("fireflies_spawnable_on");
        public static final TagKey<Block> VIOLETS = createBlockTag("violets");
    }

    public static class ItemTags {
        public static final TagKey<Item> VIOLETS = createItemTag("violets");
    }

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(TheWildUpgrade.MOD_ID, name));
    }

    private static TagKey<Item> createItemTag(String name) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(TheWildUpgrade.MOD_ID, name));
    }
}
