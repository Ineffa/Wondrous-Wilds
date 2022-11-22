package com.ineffa.wondrouswilds.client.rendering.entity.armor;

import com.ineffa.wondrouswilds.items.armor.BycocketItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BycocketRenderer extends GeoArmorRenderer<BycocketItem> {

    public BycocketRenderer() {
        super(new BycocketModel());

        this.headBone = "armorHead";
        this.bodyBone = null;
        this.rightArmBone = null;
        this.leftArmBone = null;
        this.rightLegBone = null;
        this.leftLegBone = null;
        this.rightBootBone = null;
        this.leftBootBone = null;
    }
}
