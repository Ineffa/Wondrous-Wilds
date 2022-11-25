package com.ineffa.wondrouswilds.enchantments;

import net.minecraft.item.ItemStack;

public interface SimulatesCustomEnchantmentTarget {

    boolean isAcceptableItemInEnchantingTable(ItemStack stack);
}
