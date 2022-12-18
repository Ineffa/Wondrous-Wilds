package com.ineffa.wondrouswilds;

import com.ineffa.wondrouswilds.config.WondrousWildsConfig;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.mixin.MobEntityAccessor;
import com.ineffa.wondrouswilds.registry.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.server.world.ServerWorld;
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

public class WondrousWilds implements ModInitializer {

	public static final String MOD_ID = "wondrouswilds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static WondrousWildsConfig config;

	@Override
	public void onInitialize() {
		LOGGER.info("Wondrous Wilds initializing!");

		// Initialize GeckoLib
		GeckoLibMod.DISABLE_IN_DEV = true;
		GeckoLib.initialize();

		// Initialize config
		LOGGER.info("Initializing Wondrous Wilds config");

		AutoConfig.register(WondrousWildsConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(WondrousWildsConfig.class).getConfig();

		// Initialize mod
		LOGGER.info("Initializing Wondrous Wilds content");

		WondrousWildsSounds.initialize();
		WondrousWildsParticles.initialize();

		WondrousWildsEntities.initialize();
		WondrousWildsBlocks.initialize();
		WondrousWildsItems.initialize();
		WondrousWildsEnchantments.initialize();

		WondrousWildsFeatures.initialize();

		WondrousWildsScreenHandlers.initialize();

		WondrousWildsAdvancementCriteria.initialize();

		upgradeBirchForests();

		ServerEntityEvents.ENTITY_LOAD.register(WondrousWilds::hookEntityCreation);

		LOGGER.info("Wondrous Wilds initialized and ready!");
	}

	private static void upgradeBirchForests() {
		BiomeModification birchForestModifier = BiomeModifications.create(new Identifier(MOD_ID, "birch_forest_modifier"));

		final Predicate<BiomeSelectionContext> BIRCH_FOREST = BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST);
		final Predicate<BiomeSelectionContext> OLD_GROWTH_BIRCH_FOREST = BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST);
		final Predicate<BiomeSelectionContext> ALL_BIRCH_FORESTS = BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST);

		// All Birch Forests
		birchForestModifier.add(ModificationPhase.REPLACEMENTS, ALL_BIRCH_FORESTS, context -> {
			context.getGenerationSettings().removeBuiltInFeature(VegetationPlacedFeatures.PATCH_GRASS_FOREST.value());
			context.getGenerationSettings().removeBuiltInFeature(VegetationPlacedFeatures.FOREST_FLOWERS.value());

			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.BIRCH_FOREST_GRASS_PATCH_PLACED.getKey().orElseThrow());
			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.BIRCH_FOREST_TALL_FLOWERS_PLACED.getKey().orElseThrow());
		});

		birchForestModifier.add(ModificationPhase.ADDITIONS, ALL_BIRCH_FORESTS, context -> {
			context.getGenerationSettings().addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS, WondrousWildsFeatures.BIRCH_FOREST_ROCK_PLACED.getKey().orElseThrow());

			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.FALLEN_BIRCH_LOG_PLACED.getKey().orElseThrow());

			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.BIRCH_FOREST_TALL_GRASS_PATCH_PLACED.getKey().orElseThrow());

			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.PURPLE_VIOLETS_PLACED.getKey().orElseThrow());
			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.PINK_VIOLETS_PLACED.getKey().orElseThrow());
			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.RED_VIOLETS_PLACED.getKey().orElseThrow());
			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.WHITE_VIOLETS_PLACED.getKey().orElseThrow());

			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.LILY_OF_THE_VALLEY_PATCH_PLACED.getKey().orElseThrow());

			context.getSpawnSettings().addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 6, 2, 4));
		});

		// Birch Forest
		birchForestModifier.add(ModificationPhase.REPLACEMENTS, BIRCH_FOREST, context -> {
			context.getGenerationSettings().removeBuiltInFeature(VegetationPlacedFeatures.TREES_BIRCH.value());
			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.Trees.BIRCH_FOREST_TREES_PLACED.getKey().orElseThrow());
		});

		// Old Growth Birch Forest
		birchForestModifier.add(ModificationPhase.REPLACEMENTS, OLD_GROWTH_BIRCH_FOREST, context -> {
			context.getGenerationSettings().removeBuiltInFeature(VegetationPlacedFeatures.BIRCH_TALL.value());
			context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WondrousWildsFeatures.Trees.OLD_GROWTH_BIRCH_FOREST_TREES_PLACED.getKey().orElseThrow());

			context.getEffects().setGrassColor(12232267);
		});
	}

	private static void hookEntityCreation(Entity entityBeingCreated, ServerWorld world) {

		if (entityBeingCreated instanceof FoxEntity fox)
			((MobEntityAccessor) fox).getTargetSelector().add(7, new ActiveTargetGoal<>(fox, WoodpeckerEntity.class, 20, true, true, entity -> !((WoodpeckerEntity) entity).isFlying()));
	}
}
