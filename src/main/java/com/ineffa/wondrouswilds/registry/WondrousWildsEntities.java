package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class WondrousWildsEntities {

    public static final EntityType<FireflyEntity> FIREFLY = Registry.register(Registry.ENTITY_TYPE, new Identifier(WondrousWilds.MOD_ID, "firefly"), FabricEntityTypeBuilder.createMob()
            .entityFactory(FireflyEntity::new)
            .defaultAttributes(FireflyEntity::createFireflyAttributes)
            .dimensions(EntityDimensions.fixed(0.3125F, 0.375F))
            .spawnGroup(SpawnGroup.WATER_AMBIENT)
            .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, FireflyEntity::canFireflySpawn)
            .build()
    );

    public static void initialize() {
        BiomeModifications.addSpawn(context -> context.hasTag(ConventionalBiomeTags.IN_OVERWORLD), SpawnGroup.WATER_AMBIENT, FIREFLY, 100, 3, 6);
    }
}
