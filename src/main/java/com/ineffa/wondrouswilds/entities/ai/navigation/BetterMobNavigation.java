package com.ineffa.wondrouswilds.entities.ai.navigation;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class BetterMobNavigation extends MobNavigation {

    public BetterMobNavigation(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    public boolean startMovingTo(double x, double y, double z, double speed) {
        return this.startMovingAlong(this.findPathTo(x, y, z, 0), speed);
    }

    @Override
    public boolean startMovingTo(Entity entity, double speed) {
        Path path = this.findPathTo(entity, 0);
        return path != null && this.startMovingAlong(path, speed);
    }
}
