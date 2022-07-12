package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WoodpeckerRenderer extends GeoEntityRenderer<WoodpeckerEntity> {

    public WoodpeckerRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WoodpeckerModel());

        this.shadowRadius = 0.225F;
    }
}
