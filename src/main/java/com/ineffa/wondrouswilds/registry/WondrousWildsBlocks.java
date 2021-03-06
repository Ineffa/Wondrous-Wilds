package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.blocks.BigPolyporeBlock;
import com.ineffa.wondrouswilds.blocks.HollowLogBlock;
import com.ineffa.wondrouswilds.blocks.SmallPolyporeBlock;
import com.ineffa.wondrouswilds.blocks.VioletBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WondrousWildsBlocks {

    public static final SmallPolyporeBlock SMALL_POLYPORE = new SmallPolyporeBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BROWN).sounds(BlockSoundGroup.GRASS).nonOpaque().breakInstantly().noCollision());
    public static final BigPolyporeBlock BIG_POLYPORE = new BigPolyporeBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BROWN).sounds(BlockSoundGroup.GRASS).nonOpaque().breakInstantly());

    public static final VioletBlock PURPLE_VIOLET = new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final VioletBlock PINK_VIOLET = new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final VioletBlock RED_VIOLET = new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final VioletBlock WHITE_VIOLET = new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final FlowerPotBlock POTTED_PURPLE_VIOLET = new FlowerPotBlock(PURPLE_VIOLET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final FlowerPotBlock POTTED_PINK_VIOLET = new FlowerPotBlock(PINK_VIOLET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final FlowerPotBlock POTTED_RED_VIOLET = new FlowerPotBlock(RED_VIOLET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final FlowerPotBlock POTTED_WHITE_VIOLET = new FlowerPotBlock(WHITE_VIOLET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static final PillarBlock DEAD_BIRCH_LOG = new PillarBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_LOG));

    public static final HollowLogBlock HOLLOW_OAK_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_SPRUCE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_BIRCH_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_JUNGLE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_ACACIA_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_DARK_OAK_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_MANGROVE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_CRIMSON_STEM = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_STEM).nonOpaque());
    public static final HollowLogBlock HOLLOW_WARPED_STEM = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.WARPED_STEM).nonOpaque());
    public static final HollowLogBlock HOLLOW_DEAD_BIRCH_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(DEAD_BIRCH_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_OAK_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_SPRUCE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_SPRUCE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_BIRCH_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_BIRCH_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_JUNGLE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_JUNGLE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_ACACIA_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_ACACIA_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_DARK_OAK_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_DARK_OAK_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_MANGROVE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_MANGROVE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_CRIMSON_STEM = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_CRIMSON_STEM).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_WARPED_STEM = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_STEM).nonOpaque());

    public static void initialize() {
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "small_polypore"), SMALL_POLYPORE);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "big_polypore"), BIG_POLYPORE);

        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "purple_violet"), PURPLE_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "pink_violet"), PINK_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "red_violet"), RED_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "white_violet"), WHITE_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "potted_purple_violet"), POTTED_PURPLE_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "potted_pink_violet"), POTTED_PINK_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "potted_red_violet"), POTTED_RED_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "potted_white_violet"), POTTED_WHITE_VIOLET);

        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "dead_birch_log"), DEAD_BIRCH_LOG);

        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_oak_log"), HOLLOW_OAK_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_spruce_log"), HOLLOW_SPRUCE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_birch_log"), HOLLOW_BIRCH_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_jungle_log"), HOLLOW_JUNGLE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_acacia_log"), HOLLOW_ACACIA_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_dark_oak_log"), HOLLOW_DARK_OAK_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_mangrove_log"), HOLLOW_MANGROVE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_crimson_stem"), HOLLOW_CRIMSON_STEM);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_warped_stem"), HOLLOW_WARPED_STEM);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_dead_birch_log"), HOLLOW_DEAD_BIRCH_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_oak_log"), HOLLOW_STRIPPED_OAK_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_spruce_log"), HOLLOW_STRIPPED_SPRUCE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_birch_log"), HOLLOW_STRIPPED_BIRCH_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_jungle_log"), HOLLOW_STRIPPED_JUNGLE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_acacia_log"), HOLLOW_STRIPPED_ACACIA_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_dark_oak_log"), HOLLOW_STRIPPED_DARK_OAK_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_mangrove_log"), HOLLOW_STRIPPED_MANGROVE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_crimson_stem"), HOLLOW_STRIPPED_CRIMSON_STEM);
        Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, "hollow_stripped_warped_stem"), HOLLOW_STRIPPED_WARPED_STEM);

        StrippableBlockRegistry.register(HOLLOW_OAK_LOG, HOLLOW_STRIPPED_OAK_LOG);
        StrippableBlockRegistry.register(HOLLOW_SPRUCE_LOG, HOLLOW_STRIPPED_SPRUCE_LOG);
        StrippableBlockRegistry.register(HOLLOW_BIRCH_LOG, HOLLOW_STRIPPED_BIRCH_LOG);
        StrippableBlockRegistry.register(HOLLOW_JUNGLE_LOG, HOLLOW_STRIPPED_JUNGLE_LOG);
        StrippableBlockRegistry.register(HOLLOW_ACACIA_LOG, HOLLOW_STRIPPED_ACACIA_LOG);
        StrippableBlockRegistry.register(HOLLOW_DARK_OAK_LOG, HOLLOW_STRIPPED_DARK_OAK_LOG);
        StrippableBlockRegistry.register(HOLLOW_MANGROVE_LOG, HOLLOW_STRIPPED_MANGROVE_LOG);
        StrippableBlockRegistry.register(HOLLOW_CRIMSON_STEM, HOLLOW_STRIPPED_CRIMSON_STEM);
        StrippableBlockRegistry.register(HOLLOW_WARPED_STEM, HOLLOW_STRIPPED_WARPED_STEM);
    }
}
