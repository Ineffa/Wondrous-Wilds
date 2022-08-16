package com.ineffa.wondrouswilds.util.fakeplayer;

import com.mojang.authlib.GameProfile;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import net.minecraft.util.Identifier;

public class FakeServerPlayerEntity extends ServerPlayerEntity {

    private final FakePlayerAdvancementTracker fakeAdvancementTracker;
    private final FakeServerStatHandler fakeServerStatHandler;

    public FakeServerPlayerEntity(ServerWorld world, GameProfile fakeProfile) {
        super(world.getServer(), world, fakeProfile, null);

        this.fakeAdvancementTracker = new FakePlayerAdvancementTracker(this);
        this.fakeServerStatHandler = new FakeServerStatHandler(world.getServer());
    }

    @Override
    public PlayerAdvancementTracker getAdvancementTracker() {
        return this.fakeAdvancementTracker;
    }

    @Override
    public ServerStatHandler getStatHandler() {
        return this.fakeServerStatHandler;
    }

    @Override
    public void increaseStat(Identifier stat, int amount) {}

    @Override
    public void increaseStat(Stat<?> stat, int amount) {}

    @Override
    public void resetStat(Stat<?> stat) {}

    @Override
    public void tick() {}
}
