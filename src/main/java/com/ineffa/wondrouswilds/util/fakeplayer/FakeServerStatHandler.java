package com.ineffa.wondrouswilds.util.fakeplayer;

import com.mojang.datafixers.DataFixer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatType;

public class FakeServerStatHandler extends ServerStatHandler {

    public FakeServerStatHandler(MinecraftServer server) {
        super(server, FabricLoader.getInstance().getConfigDir().toFile());
    }

    @Override
    public void save() {}

    @Override
    public void increaseStat(PlayerEntity player, Stat<?> stat, int value) {}

    @Override
    public void setStat(PlayerEntity player, Stat<?> stat, int value) {}

    @Override
    public <T> int getStat(StatType<T> type, T stat) {
        return 0;
    }

    @Override
    public int getStat(Stat<?> stat) {
        return 0;
    }

    @Override
    public void parse(DataFixer dataFixer, String json) {}

    /*@Override
    protected String asString() {
        return super.asString();
    }*/

    @Override
    public void updateStatSet() {}

    @Override
    public void sendStats(ServerPlayerEntity player) {}
}
