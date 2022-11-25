package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.BycocketUser;
import com.ineffa.wondrouswilds.registry.WondrousWildsEnchantments;
import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinBycocketAbilityManager implements BycocketUser {

    // SPREAD PREVENTION

    @Override
    public boolean shouldPreventSpread(ProjectileEntity projectile) {
        ItemStack headItem = this.getEquippedStack(EquipmentSlot.HEAD);

        if (!headItem.isIn(WondrousWildsTags.ItemTags.BYCOCKETS)) return false;

        return EnchantmentHelper.getLevel(WondrousWildsEnchantments.VERSATILITY, headItem) > 0 || projectile.getType().isIn(WondrousWildsTags.EntityTypeTags.BYCOCKET_ALWAYS_ACCURATE);
    }

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot var1);
}
