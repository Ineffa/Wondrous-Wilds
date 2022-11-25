package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.enchantments.VersatilityEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WondrousWildsEnchantments {

    public static final Enchantment VERSATILITY = registerEnchantment("versatility", new VersatilityEnchantment());

    private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        Identifier id = new Identifier(WondrousWilds.MOD_ID, name);
        return Registry.register(Registry.ENCHANTMENT, id, enchantment);
    }

    public static void initialize() {}
}
