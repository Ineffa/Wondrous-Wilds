package com.ineffa.thewildupgrade;

import com.ineffa.thewildupgrade.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

public class TheWildUpgrade implements ModInitializer {
	public static final String MOD_ID = "thewildupgrade";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("The Wild Upgrade initializing!");

		GeckoLib.initialize();
		GeckoLibMod.DISABLE_IN_DEV = true;

		TheWildUpgradeEntities.initialize();
		TheWildUpgradeItems.initialize();

		TheWildUpgradeFeatures.initialize();
		TheWildUpgradeConfiguredFeatures.initialize();
		TheWildUpgradePlacedFeatures.initialize();
		this.upgradeBirchForests();
	}

	private void upgradeBirchForests() {
		BiomeModification birchForestModifier = BiomeModifications.create(new Identifier(MOD_ID, "birch_forest_modifier"));

		birchForestModifier.add(ModificationPhase.REPLACEMENTS, BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST), context -> {
			context.getGenerationSettings().removeBuiltInFeature(VegetationPlacedFeatures.TREES_BIRCH.value());
			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, TheWildUpgradePlacedFeatures.BIRCH_FOREST_TREES_PLACED.getKey().orElseThrow());
		});
	}
}
