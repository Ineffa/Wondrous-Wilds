package com.ineffa.wondrouswilds.items.armor;

import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.DyeColor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.ineffa.wondrouswilds.registry.WondrousWildsItems.WONDROUS_WILDS_ITEM_GROUP;

public class BycocketItem extends ArmorItem implements IAnimatable {

    private final DyeColor color;

    public BycocketItem(DyeColor color) {
        super(WondrousWildsItems.ArmorMaterials.BYCOCKET, EquipmentSlot.HEAD, new FabricItemSettings().group(WONDROUS_WILDS_ITEM_GROUP));

        this.color = color;
    }

    public DyeColor getColor() {
        return this.color;
    }

    private final AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData animationData) {}
}
