package com.ineffa.thewildupgrade.registry;

import com.ineffa.thewildupgrade.TheWildUpgrade;
import com.ineffa.thewildupgrade.entities.FireflyEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class TheWildUpgradeEntities {

    public static final EntityType<FireflyEntity> FIREFLY = Registry.register(Registry.ENTITY_TYPE, new Identifier(TheWildUpgrade.MOD_ID, "firefly"), FabricEntityTypeBuilder.createMob().entityFactory(FireflyEntity::new)
            .dimensions(EntityDimensions.fixed(0.3125F, 0.375F))
            .spawnGroup(SpawnGroup.AMBIENT)
            .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, FireflyEntity::canFireflySpawn)
            .build()
    );

    public static void initialize() {
        // Register attributes
        FabricDefaultAttributeRegistry.register(FIREFLY, FireflyEntity.createFireflyAttributes());

        // Add to biome spawns
        BiomeModifications.addSpawn(context -> {
            return (context.getBiome().hasHighHumidity() && context.getBiome().getTemperature() >= 0.5F) || context.getBiomeKey() == BiomeKeys.DARK_FOREST || context.getBiomeKey() == BiomeKeys.LUSH_CAVES;
        }, SpawnGroup.AMBIENT, FIREFLY, 100, 3, 6);
    }
}
