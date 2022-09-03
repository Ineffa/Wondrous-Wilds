package com.ineffa.wondrouswilds.blocks.entity;

import com.google.common.collect.Lists;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.screen.NestBoxScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class NestBoxBlockEntity extends LootableContainerBlockEntity implements InhabitableNestBlockEntity {

    private final List<Inhabitant> inhabitants = Lists.newArrayList();

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);

    public NestBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(WondrousWildsBlocks.BlockEntities.NEST_BOX, blockPos, blockState);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, NestBoxBlockEntity nestBox) {
        tickInhabitants(world, pos, state, nestBox.getInhabitants());
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

        if (!this.serializeLootTable(nbt)) Inventories.writeNbt(nbt, this.inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.getInhabitants().clear();

        NbtList nbtList = nbt.getList(INHABITANTS_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);

            Inhabitant inhabitant = new Inhabitant(false, nbtCompound.getCompound(ENTITY_DATA_KEY), nbtCompound.getInt(CAPACITY_WEIGHT_KEY), nbtCompound.getInt(MIN_OCCUPATION_TICKS_KEY), nbtCompound.getInt(TICKS_IN_NEST_KEY));
            this.getInhabitants().add(inhabitant);
        }

        if (!this.deserializeLootTable(nbt)) Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container." + WondrousWilds.MOD_ID + ".nest_box");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new NestBoxScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return 1;
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
