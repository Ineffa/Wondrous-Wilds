package com.ineffa.wondrouswilds.client.rendering.entity.armor;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.items.armor.BycocketItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BycocketModel extends AnimatedGeoModel<BycocketItem> {

    public static final Identifier MODEL_PATH = new Identifier(WondrousWilds.MOD_ID, "geo/bycocket.geo.json");

    @Override
    public Identifier getModelResource(BycocketItem bycocket) {
        return MODEL_PATH;
    }

    @Override
    public Identifier getTextureResource(BycocketItem bycocket) {
        return new Identifier(WondrousWilds.MOD_ID, "textures/entity/armor/bycocket/" + bycocket.getColor().getName() + "_bycocket.png");
    }

    @Override
    public Identifier getAnimationResource(BycocketItem bycocket) {
        return null;
    }
}
