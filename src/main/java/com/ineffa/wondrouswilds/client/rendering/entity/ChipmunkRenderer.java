package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.entities.ChipmunkEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ChipmunkRenderer extends GeoEntityRenderer<ChipmunkEntity> {

    public ChipmunkRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChipmunkModel());

        this.shadowRadius = 0.15F;
    }
}
