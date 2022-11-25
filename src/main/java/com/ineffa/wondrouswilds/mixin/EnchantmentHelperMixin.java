package com.ineffa.wondrouswilds.mixin;

import com.google.common.collect.Lists;
import com.ineffa.wondrouswilds.enchantments.SimulatesCustomEnchantmentTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    /**
     * @author Ineffa
     * @reason Required to add custom enchantment target functionality without the need for messy enum modification. Can hopefully be removed once Fabric API adds a proper way of doing such.
     */
    @Overwrite
    public static List<EnchantmentLevelEntry> getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed) {
        ArrayList<EnchantmentLevelEntry> list = Lists.newArrayList();

        Item item = stack.getItem();
        block0: for (Enchantment enchantment : Registry.ENCHANTMENT) {
            // Simulates custom enchantment target behavior
            if (enchantment instanceof SimulatesCustomEnchantmentTarget customEnchantment && !customEnchantment.isAcceptableItemInEnchantingTable(stack)) continue;

            if (enchantment.isTreasure() && !treasureAllowed || !enchantment.isAvailableForRandomSelection() || !enchantment.type.isAcceptableItem(item) && !stack.isOf(Items.BOOK)) continue;

            for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                if (power < enchantment.getMinPower(i) || power > enchantment.getMaxPower(i)) continue;
                list.add(new EnchantmentLevelEntry(enchantment, i));
                continue block0;
            }
        }

        return list;
    }
}
