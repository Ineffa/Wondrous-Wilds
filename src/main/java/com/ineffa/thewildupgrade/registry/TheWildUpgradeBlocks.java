package com.ineffa.thewildupgrade.registry;

import com.ineffa.thewildupgrade.TheWildUpgrade;
import com.ineffa.thewildupgrade.blocks.BigPolyporeBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheWildUpgradeBlocks {

    public static final BigPolyporeBlock BIG_POLYPORE = new BigPolyporeBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BROWN).sounds(BlockSoundGroup.GRASS).nonOpaque().noCollision().breakInstantly());

    public static void initialize() {
        Registry.register(Registry.BLOCK, new Identifier(TheWildUpgrade.MOD_ID, "big_polypore"), BIG_POLYPORE);
    }
}
