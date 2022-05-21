package com.ineffa.thewildupgrade.registry;

import com.ineffa.thewildupgrade.TheWildUpgrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheWildUpgradeItems {

    public static final Item FIREFLY_SPAWN_EGG = new SpawnEggItem(TheWildUpgradeEntities.FIREFLY, 2563094, 14876540, new Item.Settings().group(ItemGroup.MISC));

    public static void initialize() {
        Registry.register(Registry.ITEM, new Identifier(TheWildUpgrade.MOD_ID, "firefly_spawn_egg"), FIREFLY_SPAWN_EGG);
    }
}
