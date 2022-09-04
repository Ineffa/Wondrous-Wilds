package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.items.LovifierItem;
import com.ineffa.wondrouswilds.mixin.ComposterBlockInvoker;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.ArrayList;
import java.util.List;

public class WondrousWildsItems {

    public static final BlockItem PURPLE_VIOLET = new BlockItem(WondrousWildsBlocks.PURPLE_VIOLET, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem PINK_VIOLET = new BlockItem(WondrousWildsBlocks.PINK_VIOLET, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem RED_VIOLET = new BlockItem(WondrousWildsBlocks.RED_VIOLET, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem WHITE_VIOLET = new BlockItem(WondrousWildsBlocks.WHITE_VIOLET, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final BlockItem SMALL_POLYPORE = new BlockItem(WondrousWildsBlocks.SMALL_POLYPORE, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem BIG_POLYPORE = new BlockItem(WondrousWildsBlocks.BIG_POLYPORE, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final BlockItem YELLOW_BIRCH_LEAVES = new BlockItem(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem ORANGE_BIRCH_LEAVES = new BlockItem(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem RED_BIRCH_LEAVES = new BlockItem(WondrousWildsBlocks.RED_BIRCH_LEAVES, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final BlockItem DEAD_OAK_LOG = new BlockItem(WondrousWildsBlocks.DEAD_OAK_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem DEAD_SPRUCE_LOG = new BlockItem(WondrousWildsBlocks.DEAD_SPRUCE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
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

    public static final BlockItem HOLLOW_DEAD_OAK_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_DEAD_OAK_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_DEAD_SPRUCE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_DEAD_SPRUCE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_DEAD_BIRCH_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_DEAD_BIRCH_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_OAK_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_OAK_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_SPRUCE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_SPRUCE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_BIRCH_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_BIRCH_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_JUNGLE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_JUNGLE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_ACACIA_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_ACACIA_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_DARK_OAK_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_MANGROVE_LOG = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_MANGROVE_LOG, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_CRIMSON_STEM = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_CRIMSON_STEM, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem HOLLOW_STRIPPED_WARPED_STEM = new BlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_WARPED_STEM, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final BlockItem OAK_TREE_HOLLOW = new BlockItem(WondrousWildsBlocks.OAK_TREE_HOLLOW, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem SPRUCE_TREE_HOLLOW = new BlockItem(WondrousWildsBlocks.SPRUCE_TREE_HOLLOW, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem BIRCH_TREE_HOLLOW = new BlockItem(WondrousWildsBlocks.BIRCH_TREE_HOLLOW, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem JUNGLE_TREE_HOLLOW = new BlockItem(WondrousWildsBlocks.JUNGLE_TREE_HOLLOW, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem ACACIA_TREE_HOLLOW = new BlockItem(WondrousWildsBlocks.ACACIA_TREE_HOLLOW, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem DARK_OAK_TREE_HOLLOW = new BlockItem(WondrousWildsBlocks.DARK_OAK_TREE_HOLLOW, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final BlockItem MANGROVE_TREE_HOLLOW = new BlockItem(WondrousWildsBlocks.MANGROVE_TREE_HOLLOW, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final BlockItem BIRCH_NEST_BOX = new BlockItem(WondrousWildsBlocks.BIRCH_NEST_BOX, new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final Item WOODPECKER_CREST_FEATHER = new Item(new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final Item FIREFLY_SPAWN_EGG = new SpawnEggItem(WondrousWildsEntities.FIREFLY, 2563094, 14876540, new Item.Settings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));
    public static final Item WOODPECKER_SPAWN_EGG = new SpawnEggItem(WondrousWildsEntities.WOODPECKER, 2761271, 16740713, new Item.Settings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP));

    public static final Item LOVIFIER = new LovifierItem(new FabricItemSettings().group(WondrousWilds.WONDROUS_WILDS_ITEM_GROUP).maxCount(1));

    public static void initialize() {
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "purple_violet"), PURPLE_VIOLET);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "pink_violet"), PINK_VIOLET);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "red_violet"), RED_VIOLET);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "white_violet"), WHITE_VIOLET);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "small_polypore"), SMALL_POLYPORE);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "big_polypore"), BIG_POLYPORE);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "yellow_birch_leaves"), YELLOW_BIRCH_LEAVES);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "orange_birch_leaves"), ORANGE_BIRCH_LEAVES);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "red_birch_leaves"), RED_BIRCH_LEAVES);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "dead_oak_log"), DEAD_OAK_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "dead_spruce_log"), DEAD_SPRUCE_LOG);
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

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_dead_oak_log"), HOLLOW_DEAD_OAK_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_dead_spruce_log"), HOLLOW_DEAD_SPRUCE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_dead_birch_log"), HOLLOW_DEAD_BIRCH_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_oak_log"), HOLLOW_STRIPPED_OAK_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_spruce_log"), HOLLOW_STRIPPED_SPRUCE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_birch_log"), HOLLOW_STRIPPED_BIRCH_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_jungle_log"), HOLLOW_STRIPPED_JUNGLE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_acacia_log"), HOLLOW_STRIPPED_ACACIA_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_dark_oak_log"), HOLLOW_STRIPPED_DARK_OAK_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_mangrove_log"), HOLLOW_STRIPPED_MANGROVE_LOG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_crimson_stem"), HOLLOW_STRIPPED_CRIMSON_STEM);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_warped_stem"), HOLLOW_STRIPPED_WARPED_STEM);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "oak_tree_hollow"), OAK_TREE_HOLLOW);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "spruce_tree_hollow"), SPRUCE_TREE_HOLLOW);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "birch_tree_hollow"), BIRCH_TREE_HOLLOW);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "jungle_tree_hollow"), JUNGLE_TREE_HOLLOW);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "acacia_tree_hollow"), ACACIA_TREE_HOLLOW);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "dark_oak_tree_hollow"), DARK_OAK_TREE_HOLLOW);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "mangrove_tree_hollow"), MANGROVE_TREE_HOLLOW);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "birch_nest_box"), BIRCH_NEST_BOX);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "woodpecker_crest_feather"), WOODPECKER_CREST_FEATHER);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "firefly_spawn_egg"), FIREFLY_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "woodpecker_spawn_egg"), WOODPECKER_SPAWN_EGG);

        Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, "lovifier"), LOVIFIER);

        ComposterBlockInvoker.addCompostableItem(0.65F, PURPLE_VIOLET);
        ComposterBlockInvoker.addCompostableItem(0.65F, PINK_VIOLET);
        ComposterBlockInvoker.addCompostableItem(0.65F, RED_VIOLET);
        ComposterBlockInvoker.addCompostableItem(0.65F, WHITE_VIOLET);

        ComposterBlockInvoker.addCompostableItem(0.3F, SMALL_POLYPORE);
        ComposterBlockInvoker.addCompostableItem(0.65F, BIG_POLYPORE);

        ComposterBlockInvoker.addCompostableItem(0.3F, YELLOW_BIRCH_LEAVES);
        ComposterBlockInvoker.addCompostableItem(0.3F, ORANGE_BIRCH_LEAVES);
        ComposterBlockInvoker.addCompostableItem(0.3F, RED_BIRCH_LEAVES);

        Trades.addWandererTrade(true, BIRCH_NEST_BOX, 1, 6, 4);

        Trades.initialize();
    }

    public static final class Trades {
        public static final List<TradeOffers.Factory> COMMON_WANDERER_TRADES = new ArrayList<>();
        public static final List<TradeOffers.Factory> RARE_WANDERER_TRADES = new ArrayList<>();

        public static void addWandererTrade(boolean rare, ItemConvertible itemToSell, int amountToSell, int price, int maxUses) {
            List<TradeOffers.Factory> listToAddTo = rare ? RARE_WANDERER_TRADES : COMMON_WANDERER_TRADES;

            listToAddTo.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, price), new ItemStack(itemToSell, amountToSell), maxUses, 1, 1.0F));
        }

        public static void initialize() {
            if (!COMMON_WANDERER_TRADES.isEmpty()) registerWandererTrades(false, COMMON_WANDERER_TRADES);
            if (!RARE_WANDERER_TRADES.isEmpty()) registerWandererTrades(true, RARE_WANDERER_TRADES);
        }

        private static void registerWandererTrades(boolean rare, List<TradeOffers.Factory> tradesToRegister) {
            TradeOfferHelper.registerWanderingTraderOffers(rare ? 2 : 1, factories -> factories.addAll(tradesToRegister));
        }
    }
}
