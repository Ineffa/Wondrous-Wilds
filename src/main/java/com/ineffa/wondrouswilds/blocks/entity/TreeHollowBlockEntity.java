package com.ineffa.wondrouswilds.blocks.entity;

import com.google.common.collect.Lists;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class TreeHollowBlockEntity extends BlockEntity implements InhabitableNestBlockEntity {

    private final List<Inhabitant> inhabitants = Lists.newArrayList();

    public TreeHollowBlockEntity(BlockPos pos, BlockState state) {
        super(WondrousWildsBlocks.BlockEntities.TREE_HOLLOW, pos, state);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, TreeHollowBlockEntity treeHollow) {
        tickInhabitants(world, pos, state, treeHollow.getInhabitants());
    }

    private static void tickInhabitants(World world, BlockPos pos, BlockState state, List<Inhabitant> inhabitants) {
        boolean released = false;

        Iterator<Inhabitant> iterator = inhabitants.iterator();
        while (iterator.hasNext()) {
            Inhabitant inhabitant = iterator.next();
            if (inhabitant.ticksInNest > inhabitant.minOccupationTicks) {
                if (InhabitableNestBlockEntity.tryReleasingInhabitant(world, pos, state, InhabitantReleaseState.RELEASE, inhabitant, null)) {
                    released = true;
                    iterator.remove();
                }
            }
            ++inhabitant.ticksInNest;
        }

        if (released) markDirty(world, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.put(INHABITANTS_KEY, this.getInhabitantsNbt());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.getInhabitants().clear();

        NbtList nbtList = nbt.getList(INHABITANTS_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);

            Inhabitant inhabitant = new Inhabitant(nbtCompound.getBoolean(IS_FRESH_KEY), nbtCompound.getCompound(ENTITY_DATA_KEY), nbtCompound.getInt(CAPACITY_WEIGHT_KEY), nbtCompound.getInt(MIN_OCCUPATION_TICKS_KEY), nbtCompound.getInt(TICKS_IN_NEST_KEY));
            this.getInhabitants().add(inhabitant);
        }
    }

    @Override
    public List<Inhabitant> getInhabitants() {
        return this.inhabitants;
    }

    @Override
    public World getNestWorld() {
        return this.getWorld();
    }

    @Override
    public BlockPos getNestPos() {
        return this.getPos();
    }

    @Override
    public BlockState getNestCachedState() {
        return this.getCachedState();
    }

    @Override
    public void markNestDirty() {
        this.markDirty();
    }
}
