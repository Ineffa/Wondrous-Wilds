package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.blocks.*;
import com.ineffa.wondrouswilds.blocks.entity.BirdhouseBlockEntity;
import com.ineffa.wondrouswilds.blocks.entity.TreeHollowBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
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

    public static final Block DEAD_OAK_LOG = registerBlock("dead_oak_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));
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

    public static final Block HOLLOW_DEAD_OAK_LOG = registerBlock("hollow_dead_oak_log", new HollowLogBlock(FabricBlockSettings.copyOf(DEAD_OAK_LOG).nonOpaque()));
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

    public static final TreeHollowBlock OAK_TREE_HOLLOW = (TreeHollowBlock) registerBlock("oak_tree_hollow", new TreeHollowBlock(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0f).sounds(BlockSoundGroup.WOOD)));
    public static final TreeHollowBlock SPRUCE_TREE_HOLLOW = (TreeHollowBlock) registerBlock("spruce_tree_hollow", new TreeHollowBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0f).sounds(BlockSoundGroup.WOOD)));
    public static final TreeHollowBlock BIRCH_TREE_HOLLOW = (TreeHollowBlock) registerBlock("birch_tree_hollow", new TreeHollowBlock(FabricBlockSettings.of(Material.WOOD, MapColor.PALE_YELLOW).strength(2.0f).sounds(BlockSoundGroup.WOOD)));
    public static final TreeHollowBlock JUNGLE_TREE_HOLLOW = (TreeHollowBlock) registerBlock("jungle_tree_hollow", new TreeHollowBlock(FabricBlockSettings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f).sounds(BlockSoundGroup.WOOD)));
    public static final TreeHollowBlock ACACIA_TREE_HOLLOW = (TreeHollowBlock) registerBlock("acacia_tree_hollow", new TreeHollowBlock(FabricBlockSettings.of(Material.WOOD, MapColor.ORANGE).strength(2.0f).sounds(BlockSoundGroup.WOOD)));
    public static final BirdhouseBlock BIRCH_BIRDHOUSE = (BirdhouseBlock) registerBlock("birch_birdhouse", new BirchBirdhouseBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS).nonOpaque()));

    public static final Block YELLOW_BIRCH_LEAVES = registerBlock("yellow_birch_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_LEAVES)));
    public static final Block ORANGE_BIRCH_LEAVES = registerBlock("orange_birch_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_LEAVES)));
    public static final Block RED_BIRCH_LEAVES = registerBlock("red_birch_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_LEAVES)));

    public static final class BlockEntities {
        public static final BlockEntityType<TreeHollowBlockEntity> TREE_HOLLOW = registerBlockEntity("tree_hollow", FabricBlockEntityTypeBuilder.create(TreeHollowBlockEntity::new, OAK_TREE_HOLLOW, SPRUCE_TREE_HOLLOW, BIRCH_TREE_HOLLOW, JUNGLE_TREE_HOLLOW, ACACIA_TREE_HOLLOW).build(null));
        public static final BlockEntityType<BirdhouseBlockEntity> BIRDHOUSE = registerBlockEntity("birdhouse", FabricBlockEntityTypeBuilder.create(BirdhouseBlockEntity::new, BIRCH_BIRDHOUSE).build(null));

        private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType<T> blockEntityType) {
            return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(WondrousWilds.MOD_ID, name), blockEntityType);
        }

        public static void init() {}
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(WondrousWilds.MOD_ID, name), block);
    }

    public static void initialize() {
        BlockEntities.init();

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
