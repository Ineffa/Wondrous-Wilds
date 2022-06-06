package com.ineffa.thewildupgrade.registry;

import com.ineffa.thewildupgrade.TheWildUpgrade;
import com.ineffa.thewildupgrade.blocks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheWildUpgradeBlocks {

    public static final SmallPolyporeBlock SMALL_POLYPORE = new SmallPolyporeBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BROWN).sounds(BlockSoundGroup.GRASS).nonOpaque().breakInstantly().noCollision());
    public static final BigPolyporeBlock BIG_POLYPORE = new BigPolyporeBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BROWN).sounds(BlockSoundGroup.GRASS).nonOpaque().breakInstantly());

    public static final VioletBlock PURPLE_VIOLET = new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final VioletBlock PINK_VIOLET = new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final VioletBlock RED_VIOLET = new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final VioletBlock WHITE_VIOLET = new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));

    public static final HollowLogBlock HOLLOW_OAK_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_SPRUCE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_BIRCH_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_JUNGLE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_ACACIA_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_DARK_OAK_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_MANGROVE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_CRIMSON_STEM = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_STEM).nonOpaque());
    public static final HollowLogBlock HOLLOW_WARPED_STEM = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.WARPED_STEM).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_OAK_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_SPRUCE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_SPRUCE_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_BIRCH_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_BIRCH_LOG).nonOpaque());
    public static final HollowLogBlock HOLLOW_STRIPPED_JUNGLE_LOG = new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_JUNGLE_LOG).nonOpaque());

    public static void initialize() {
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "small_polypore"), SMALL_POLYPORE);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "big_polypore"), BIG_POLYPORE);

        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "purple_violet"), PURPLE_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "pink_violet"), PINK_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "red_violet"), RED_VIOLET);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "white_violet"), WHITE_VIOLET);

        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_oak_log"), HOLLOW_OAK_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_spruce_log"), HOLLOW_SPRUCE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_birch_log"), HOLLOW_BIRCH_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_jungle_log"), HOLLOW_JUNGLE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_acacia_log"), HOLLOW_ACACIA_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_dark_oak_log"), HOLLOW_DARK_OAK_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_mangrove_log"), HOLLOW_MANGROVE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_crimson_stem"), HOLLOW_CRIMSON_STEM);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_warped_stem"), HOLLOW_WARPED_STEM);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_stripped_oak_log"), HOLLOW_STRIPPED_OAK_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_stripped_spruce_log"), HOLLOW_STRIPPED_SPRUCE_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_stripped_birch_log"), HOLLOW_STRIPPED_BIRCH_LOG);
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "hollow_stripped_jungle_log"), HOLLOW_STRIPPED_JUNGLE_LOG);
    }
}
