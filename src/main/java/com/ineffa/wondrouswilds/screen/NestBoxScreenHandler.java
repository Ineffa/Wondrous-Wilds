package com.ineffa.wondrouswilds.screen;

import com.ineffa.wondrouswilds.registry.WondrousWildsScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class NestBoxScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public NestBoxScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    public NestBoxScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(WondrousWildsScreenHandlers.NEST_BOX, syncId);

        checkSize(inventory, 1);

        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);

        // Container inventory
        this.addSlot(new Slot(inventory, 0, 80, 35));

        // Player inventory
        int i;
        int j;
        for (i = 0; i < 3; ++i)
            for (j = 0; j < 9; ++j) this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        // Player hotbar
        for (i = 0; i < 9; ++i) this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);

        this.inventory.onClose(player);
    }
}
