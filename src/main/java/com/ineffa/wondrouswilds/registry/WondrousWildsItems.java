package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WondrousWildsItems {

    public static final Item FIREFLY_SPAWN_EGG = new SpawnEggItem(WondrousWildsEntities.FIREFLY, 2563094, 14876540, new Item.Settings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final Item WOODPECKER_SPAWN_EGG = new SpawnEggItem(WondrousWildsEntities.WOODPECKER, 2761271, 16740713, new Item.Settings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final BlockItem PURPLE_VIOLET = new BlockItem(WondrousWildsBlocks.PURPLE_VIOLET, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem PINK_VIOLET = new BlockItem(WondrousWildsBlocks.PINK_VIOLET, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem RED_VIOLET = new BlockItem(WondrousWildsBlocks.RED_VIOLET, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem WHITE_VIOLET = new BlockItem(WondrousWildsBlocks.WHITE_VIOLET, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final BlockItem SMALL_POLYPORE = new BlockItem(WondrousWildsBlocks.SMALL_POLYPORE, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem BIG_POLYPORE = new BlockItem(WondrousWildsBlocks.BIG_POLYPORE, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final BlockItem DEAD_BIRCH_LOG = new BlockItem(WondrousWildsBlocks.DEAD_BIRCH_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final BlockItem HOLLOW_OAK_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_OAK_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_SPRUCE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_SPRUCE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_BIRCH_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_BIRCH_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_JUNGLE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_JUNGLE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_ACACIA_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_ACACIA_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_DARK_OAK_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_DARK_OAK_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_MANGROVE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_MANGROVE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_CRIMSON_STEM = new BlockItem(WondrousWildsBlocks.HOLLOW_CRIMSON_STEM, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_WARPED_STEM = new BlockItem(WondrousWildsBlocks.HOLLOW_WARPED_STEM, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_OAK_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_OAK_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_SPRUCE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_SPRUCE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_BIRCH_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_BIRCH_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_JUNGLE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_JUNGLE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_ACACIA_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_ACACIA_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_DARK_OAK_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_MANGROVE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_MANGROVE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_CRIMSON_STEM = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_CRIMSON_STEM, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_WARPED_STEM = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_WARPED_STEM, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static void initialize() {
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "firefly_spawn_egg"), FIREFLY_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "woodpecker_spawn_egg"), WOODPECKER_SPAWN_EGG);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "purple_violet"), PURPLE_VIOLET);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "pink_violet"), PINK_VIOLET);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "red_violet"), RED_VIOLET);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "white_violet"), WHITE_VIOLET);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "small_polypore"), SMALL_POLYPORE);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "big_polypore"), BIG_POLYPORE);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "dead_birch_log"), DEAD_BIRCH_LOG);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_oak_log"), HOLLOW_OAK_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_spruce_log"), HOLLOW_SPRUCE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_birch_log"), HOLLOW_BIRCH_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_jungle_log"), HOLLOW_JUNGLE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_acacia_log"), HOLLOW_ACACIA_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_dark_oak_log"), HOLLOW_DARK_OAK_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_mangrove_log"), HOLLOW_MANGROVE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_crimson_stem"), HOLLOW_CRIMSON_STEM);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_warped_stem"), HOLLOW_WARPED_STEM);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_oak_log"), HOLLOW_STRIPPED_OAK_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_spruce_log"), HOLLOW_STRIPPED_SPRUCE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_birch_log"), HOLLOW_STRIPPED_BIRCH_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_jungle_log"), HOLLOW_STRIPPED_JUNGLE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_acacia_log"), HOLLOW_STRIPPED_ACACIA_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_dark_oak_log"), HOLLOW_STRIPPED_DARK_OAK_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_mangrove_log"), HOLLOW_STRIPPED_MANGROVE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_crimson_stem"), HOLLOW_STRIPPED_CRIMSON_STEM);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_warped_stem"), HOLLOW_STRIPPED_WARPED_STEM);
    }
}
