package com.ineffa.wondrouswilds.blocks.entity;

import com.google.common.collect.Lists;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.BlockNester;
import com.ineffa.wondrouswilds.entities.eggs.NesterEgg;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.screen.NestBoxScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class NestBoxBlockEntity extends LootableContainerBlockEntity implements InhabitableNestBlockEntity {

    private final List<Inhabitant> inhabitants = Lists.newArrayList();
    private final List<NesterEgg> eggs = Lists.newArrayList();

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);

    public NestBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(WondrousWildsBlocks.BlockEntities.NEST_BOX, blockPos, blockState);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, NestBoxBlockEntity nestBox) {
        boolean dirty = false;

        if (tickInhabitants(world, pos, state, nestBox.getInhabitants())) dirty = true;
        if (tickEggs(world, pos, nestBox)) dirty = true;

        if (dirty) markDirty(world, pos, state);
    }

    private static boolean tickInhabitants(World world, BlockPos pos, BlockState state, List<Inhabitant> inhabitants) {
        boolean dirty = false;

        Iterator<Inhabitant> inhabitantIterator = inhabitants.iterator();
        while (inhabitantIterator.hasNext()) {
            Inhabitant inhabitant = inhabitantIterator.next();
            if (inhabitant.ticksInNest > inhabitant.minOccupationTicks) {
                if (InhabitableNestBlockEntity.tryNaturallyReleasingInhabitant(world, pos, state, inhabitant)) {
                    dirty = true;
                    inhabitantIterator.remove();
                }
            }
            ++inhabitant.ticksInNest;
        }

        return dirty;
    }

    private static boolean tickEggs(World world, BlockPos pos, NestBoxBlockEntity nestBox) {
        boolean dirty = false;

        Iterator<NesterEgg> eggIterator = nestBox.getEggs().iterator();
        while (eggIterator.hasNext()) {
            NesterEgg egg = eggIterator.next();

            NesterEgg.EggCrackResult crackResult = egg.tryCracking(world);
            if (crackResult != null) {
                SoundEvent eggSound = SoundEvents.ENTITY_TURTLE_EGG_CRACK;

                if (crackResult == NesterEgg.EggCrackResult.HATCH) {
                    nestBox.hatchEgg(egg);
                    eggSound = SoundEvents.ENTITY_TURTLE_EGG_HATCH;
                    eggIterator.remove();
                }
                world.playSound(null, pos, eggSound, SoundCategory.BLOCKS, 0.5F, 0.9F + world.getRandom().nextFloat() * 0.2F);

                dirty = true;
            }
        }

        return dirty;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.put(INHABITANTS_KEY, this.getInhabitantsNbt());
        nbt.put(EGGS_KEY, this.getEggsNbt());

        if (!this.serializeLootTable(nbt)) Inventories.writeNbt(nbt, this.inventory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.getInhabitants().clear();

        NbtList inhabitants = nbt.getList(INHABITANTS_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < inhabitants.size(); ++i) {
            NbtCompound inhabitantNbt = inhabitants.getCompound(i);

            Inhabitant inhabitant = new Inhabitant(
                    inhabitantNbt.getBoolean(IS_FRESH_KEY),
                    inhabitantNbt.getBoolean(IS_BABY_KEY),
                    inhabitantNbt.getCompound(ENTITY_DATA_KEY),
                    inhabitantNbt.getInt(CAPACITY_WEIGHT_KEY),
                    inhabitantNbt.getInt(MIN_OCCUPATION_TICKS_KEY),
                    inhabitantNbt.getInt(TICKS_IN_NEST_KEY)
            );

            this.getInhabitants().add(inhabitant);
        }

        NbtList eggs = nbt.getList(EGGS_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < eggs.size(); ++i) {
            NbtCompound eggNbt = eggs.getCompound(i);

            NesterEgg egg = new NesterEgg(
                    eggNbt.getInt(CAPACITY_WEIGHT_KEY),
                    (EntityType<? extends BlockNester>) EntityType.get(eggNbt.getString(ENTITY_TYPE_TO_HATCH_KEY)).orElseThrow(),
                    eggNbt.contains(DATA_TO_INHERIT_KEY) ? eggNbt.getCompound(DATA_TO_INHERIT_KEY) : null,
                    eggNbt.getBoolean(NOCTURNAL_KEY),
                    new Pair<>(eggNbt.getInt(MIN_CRACK_COOLDOWN_KEY), eggNbt.getInt(MAX_CRACK_COOLDOWN_KEY)),
                    eggNbt.getInt(CRACK_COOLDOWN_KEY),
                    eggNbt.getInt(CRACKS_UNTIL_HATCH_KEY)
            );

            this.addEgg(egg);
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
    public List<NesterEgg> getEggs() {
        return this.eggs;
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
