package com.ineffa.wondrouswilds.enchantments;

import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class VersatilityEnchantment extends Enchantment implements SimulatesCustomEnchantmentTarget {

    public VersatilityEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isIn(WondrousWildsTags.ItemTags.BYCOCKETS);
    }

    @Override
    public boolean isAcceptableItemInEnchantingTable(ItemStack stack) {
        return this.isAcceptableItem(stack);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 40;
    }
}
