package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.items.LovifierItem;
import com.ineffa.wondrouswilds.mixin.ComposterBlockInvoker;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.ArrayList;
import java.util.List;

public class WondrousWildsItems {

    public static final ItemGroup WONDROUS_WILDS_ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(WondrousWilds.MOD_ID, "wondrous_wilds"), () -> new ItemStack(WondrousWildsItems.LOVIFIER));

    public static final BlockItem PURPLE_VIOLET = registerBlockItem(WondrousWildsBlocks.PURPLE_VIOLET);
    public static final BlockItem PINK_VIOLET = registerBlockItem(WondrousWildsBlocks.PINK_VIOLET);
    public static final BlockItem RED_VIOLET = registerBlockItem(WondrousWildsBlocks.RED_VIOLET);
    public static final BlockItem WHITE_VIOLET = registerBlockItem(WondrousWildsBlocks.WHITE_VIOLET);

    public static final BlockItem SMALL_POLYPORE = registerBlockItem(WondrousWildsBlocks.SMALL_POLYPORE);
    public static final BlockItem BIG_POLYPORE = registerBlockItem(WondrousWildsBlocks.BIG_POLYPORE);

    public static final BlockItem YELLOW_BIRCH_LEAVES = registerBlockItem(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES);
    public static final BlockItem ORANGE_BIRCH_LEAVES = registerBlockItem(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES);
    public static final BlockItem RED_BIRCH_LEAVES = registerBlockItem(WondrousWildsBlocks.RED_BIRCH_LEAVES);

    public static final BlockItem DEAD_OAK_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_OAK_LOG);
    public static final BlockItem DEAD_SPRUCE_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_SPRUCE_LOG);
    public static final BlockItem DEAD_BIRCH_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_BIRCH_LOG);

    public static final BlockItem HOLLOW_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_OAK_LOG);
    public static final BlockItem HOLLOW_SPRUCE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_SPRUCE_LOG);
    public static final BlockItem HOLLOW_BIRCH_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_BIRCH_LOG);
    public static final BlockItem HOLLOW_JUNGLE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_JUNGLE_LOG);
    public static final BlockItem HOLLOW_ACACIA_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_ACACIA_LOG);
    public static final BlockItem HOLLOW_DARK_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DARK_OAK_LOG);
    public static final BlockItem HOLLOW_MANGROVE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_MANGROVE_LOG);
    public static final BlockItem HOLLOW_CRIMSON_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_CRIMSON_STEM);
    public static final BlockItem HOLLOW_WARPED_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_WARPED_STEM);

    public static final BlockItem HOLLOW_DEAD_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_OAK_LOG);
    public static final BlockItem HOLLOW_DEAD_SPRUCE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_SPRUCE_LOG);
    public static final BlockItem HOLLOW_DEAD_BIRCH_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_BIRCH_LOG);

    public static final BlockItem HOLLOW_STRIPPED_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_OAK_LOG);
    public static final BlockItem HOLLOW_STRIPPED_SPRUCE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_SPRUCE_LOG);
    public static final BlockItem HOLLOW_STRIPPED_BIRCH_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_BIRCH_LOG);
    public static final BlockItem HOLLOW_STRIPPED_JUNGLE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_JUNGLE_LOG);
    public static final BlockItem HOLLOW_STRIPPED_ACACIA_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_ACACIA_LOG);
    public static final BlockItem HOLLOW_STRIPPED_DARK_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG);
    public static final BlockItem HOLLOW_STRIPPED_MANGROVE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_MANGROVE_LOG);
    public static final BlockItem HOLLOW_STRIPPED_CRIMSON_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_CRIMSON_STEM);
    public static final BlockItem HOLLOW_STRIPPED_WARPED_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_WARPED_STEM);

    public static final BlockItem OAK_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.OAK_TREE_HOLLOW);
    public static final BlockItem SPRUCE_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.SPRUCE_TREE_HOLLOW);
    public static final BlockItem BIRCH_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.BIRCH_TREE_HOLLOW);
    public static final BlockItem JUNGLE_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.JUNGLE_TREE_HOLLOW);
    public static final BlockItem ACACIA_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.ACACIA_TREE_HOLLOW);
    public static final BlockItem DARK_OAK_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.DARK_OAK_TREE_HOLLOW);
    public static final BlockItem MANGROVE_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.MANGROVE_TREE_HOLLOW);

    public static final BlockItem BIRCH_NEST_BOX = registerBlockItem(WondrousWildsBlocks.BIRCH_NEST_BOX);

    public static final Item WOODPECKER_CREST_FEATHER = registerItem("woodpecker_crest_feather", new Item(new FabricItemSettings().group(WONDROUS_WILDS_ITEM_GROUP)));

    public static final Item FIREFLY_SPAWN_EGG = registerItem("firefly_spawn_egg", new SpawnEggItem(WondrousWildsEntities.FIREFLY, 2563094, 14876540, new Item.Settings().group(WONDROUS_WILDS_ITEM_GROUP)));
    public static final Item WOODPECKER_SPAWN_EGG = registerItem("woodpecker_spawn_egg", new SpawnEggItem(WondrousWildsEntities.WOODPECKER, 2761271, 16740713, new Item.Settings().group(WONDROUS_WILDS_ITEM_GROUP)));

    public static final Item LOVIFIER = registerItem("lovifier", new LovifierItem(new FabricItemSettings().group(WONDROUS_WILDS_ITEM_GROUP).maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(WondrousWilds.MOD_ID, name), item);
    }

    private static BlockItem registerBlockItem(Block block) {
        return (BlockItem) registerItem(Registry.BLOCK.getId(block).getPath(), new BlockItem(block, new FabricItemSettings().group(WONDROUS_WILDS_ITEM_GROUP)));
    }

    public static void initialize() {
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
