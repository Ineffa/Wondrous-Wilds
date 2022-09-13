package com.ineffa.wondrouswilds.util.fakeplayer;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

public class WoodpeckerItemUsageContext extends ItemUsageContext {

    public WoodpeckerItemUsageContext(WoodpeckerEntity woodpecker, WoodpeckerFakePlayer fakePlayer, ItemStack itemStack, BlockHitResult hit) {
        super(woodpecker.getWorld(), fakePlayer, Hand.MAIN_HAND, itemStack, hit);
    }
}
