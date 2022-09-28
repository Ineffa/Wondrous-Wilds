package com.ineffa.wondrouswilds.registry;

import com.google.common.collect.ImmutableMap;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

import java.util.Map;

public class WondrousWildsEntities {

    public static final EntityType<FireflyEntity> FIREFLY = Registry.register(Registry.ENTITY_TYPE, new Identifier(WondrousWilds.MOD_ID, "firefly"), FabricEntityTypeBuilder.createMob()
            .entityFactory(FireflyEntity::new)
            .defaultAttributes(FireflyEntity::createFireflyAttributes)
            .dimensions(EntityDimensions.fixed(0.1875F, 0.25F))
            .spawnGroup(SpawnGroup.WATER_AMBIENT)
            .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, FireflyEntity::canFireflySpawn)
            .build()
    );

    public static final EntityType<WoodpeckerEntity> WOODPECKER = Registry.register(Registry.ENTITY_TYPE, new Identifier(WondrousWilds.MOD_ID, "woodpecker"), FabricEntityTypeBuilder.createMob()
            .entityFactory(WoodpeckerEntity::new)
            .defaultAttributes(WoodpeckerEntity::createWoodpeckerAttributes)
            .dimensions(EntityDimensions.fixed(0.3125F, 0.5F))
            .spawnGroup(SpawnGroup.CREATURE)
            .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, AnimalEntity::isValidNaturalSpawn)
            .build()
    );

    public static void initialize() {
        if (WondrousWilds.config.mobSettings.firefliesSpawnNaturally) BiomeModifications.addSpawn(context -> context.hasTag(ConventionalBiomeTags.IN_OVERWORLD), SpawnGroup.WATER_AMBIENT, FIREFLY, 100, 3, 6);
    }

    public static final Map<EntityType<?>, Integer> DEFAULT_NESTER_CAPACITY_WEIGHTS = new ImmutableMap.Builder<EntityType<?>, Integer>()
            .put(WOODPECKER, 55)
            .build();
}
