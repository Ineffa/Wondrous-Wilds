package com.ineffa.wondrouswilds.items;

import com.ineffa.wondrouswilds.entities.projectiles.BodkinArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BodkinArrowItem extends ArrowItem {

    public BodkinArrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new BodkinArrowEntity(world, shooter);
    }
}
