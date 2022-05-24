package com.ineffa.thewildupgrade;

import com.ineffa.thewildupgrade.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.util.function.Predicate;

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

		final Predicate<BiomeSelectionContext> BIRCH_FOREST = BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST);
		final Predicate<BiomeSelectionContext> OLD_GROWTH_BIRCH_FOREST = BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST);
		final Predicate<BiomeSelectionContext> ALL_BIRCH_FORESTS = BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST);

		// Global modifications
		birchForestModifier.add(ModificationPhase.REPLACEMENTS, ALL_BIRCH_FORESTS, context -> {
			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, TheWildUpgradePlacedFeatures.BIRCH_FOREST_TREES_PLACED.getKey().orElseThrow());
		});

		birchForestModifier.add(ModificationPhase.ADDITIONS, ALL_BIRCH_FORESTS, context -> {
			context.getSpawnSettings().addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 6, 2, 4));
		});

		// Birch Forest modifications
		birchForestModifier.add(ModificationPhase.REPLACEMENTS, BIRCH_FOREST, context -> {
			context.getGenerationSettings().removeBuiltInFeature(VegetationPlacedFeatures.TREES_BIRCH.value());
		});

		// Old Growth Birch Forest modifications
		birchForestModifier.add(ModificationPhase.REPLACEMENTS, OLD_GROWTH_BIRCH_FOREST, context -> {
			context.getGenerationSettings().removeBuiltInFeature(VegetationPlacedFeatures.BIRCH_TALL.value());
		});
	}
}
