package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.blocks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WondrousWildsBlocks {

    public static final Block SMALL_POLYPORE = registerBlock("small_polypore", new SmallPolyporeBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BROWN).sounds(BlockSoundGroup.GRASS).nonOpaque().breakInstantly().noCollision()));
    public static final Block BIG_POLYPORE = registerBlock("big_polypore", new BigPolyporeBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BROWN).sounds(BlockSoundGroup.GRASS).nonOpaque().breakInstantly()));

    public static final Block PURPLE_VIOLET = registerBlock("purple_violet", new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
    public static final Block PINK_VIOLET = registerBlock("pink_violet", new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
    public static final Block RED_VIOLET = registerBlock("red_violet", new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
    public static final Block WHITE_VIOLET = registerBlock("white_violet", new VioletBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
    public static final Block POTTED_PURPLE_VIOLET = registerBlock("potted_purple_violet", new FlowerPotBlock(PURPLE_VIOLET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()));
    public static final Block POTTED_PINK_VIOLET = registerBlock("potted_pink_violet", new FlowerPotBlock(PINK_VIOLET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()));
    public static final Block POTTED_RED_VIOLET = registerBlock("potted_red_violet", new FlowerPotBlock(RED_VIOLET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()));
    public static final Block POTTED_WHITE_VIOLET = registerBlock("potted_white_violet", new FlowerPotBlock(WHITE_VIOLET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()));

    public static final Block DEAD_BIRCH_LOG = registerBlock("dead_birch_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_LOG)));


    public static final Block HOLLOW_OAK_LOG = registerBlock("hollow_oak_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).nonOpaque()));
    public static final Block HOLLOW_SPRUCE_LOG = registerBlock("hollow_spruce_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_LOG).nonOpaque()));
    public static final Block HOLLOW_BIRCH_LOG = registerBlock("hollow_birch_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_LOG).nonOpaque()));
    public static final Block HOLLOW_JUNGLE_LOG = registerBlock("hollow_jungle_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_LOG).nonOpaque()));
    public static final Block HOLLOW_ACACIA_LOG = registerBlock("hollow_acacia_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_LOG).nonOpaque()));
    public static final Block HOLLOW_DARK_OAK_LOG = registerBlock("hollow_dark_oak_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_LOG).nonOpaque()));
    public static final Block HOLLOW_MANGROVE_LOG = registerBlock("hollow_mangrove_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_LOG).nonOpaque()));
    public static final Block HOLLOW_CRIMSON_STEM = registerBlock("hollow_crimson_stem", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_STEM).nonOpaque()));
    public static final Block HOLLOW_WARPED_STEM = registerBlock("hollow_warped_stem", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.WARPED_STEM).nonOpaque()));
    public static final Block HOLLOW_DEAD_BIRCH_LOG = registerBlock("hollow_dead_birch_log", new HollowLogBlock(FabricBlockSettings.copyOf(DEAD_BIRCH_LOG).nonOpaque()));
    public static final Block HOLLOW_STRIPPED_OAK_LOG = registerBlock("hollow_stripped_oak_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).nonOpaque()));
    public static final Block HOLLOW_STRIPPED_SPRUCE_LOG = registerBlock("hollow_stripped_spruce_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_SPRUCE_LOG).nonOpaque()));
    public static final Block HOLLOW_STRIPPED_BIRCH_LOG = registerBlock("hollow_stripped_birch_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_BIRCH_LOG).nonOpaque()));
    public static final Block HOLLOW_STRIPPED_JUNGLE_LOG = registerBlock("hollow_stripped_jungle_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_JUNGLE_LOG).nonOpaque()));
    public static final Block HOLLOW_STRIPPED_ACACIA_LOG = registerBlock("hollow_stripped_acacia_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_ACACIA_LOG).nonOpaque()));
    public static final Block HOLLOW_STRIPPED_DARK_OAK_LOG = registerBlock("hollow_stripped_dark_oak_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_DARK_OAK_LOG).nonOpaque()));
    public static final Block HOLLOW_STRIPPED_MANGROVE_LOG = registerBlock("hollow_stripped_mangrove_log", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_MANGROVE_LOG).nonOpaque()));
    public static final Block HOLLOW_STRIPPED_CRIMSON_STEM = registerBlock("hollow_stripped_crimson_stem", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_CRIMSON_STEM).nonOpaque()));
    public static final Block HOLLOW_STRIPPED_WARPED_STEM = registerBlock("hollow_stripped_warped_stem", new HollowLogBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_STEM).nonOpaque()));



    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, name), block);
    }

    public static void initialize() {

        StrippableBlockRegistry.register(DEAD_BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);

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
