package com.ineffa.wondrouswilds.client.rendering.entity.projectile;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.projectiles.BodkinArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class BodkinArrowRenderer extends ProjectileEntityRenderer<BodkinArrowEntity> {

    public static final Identifier TEXTURE = new Identifier(WondrousWilds.MOD_ID, "textures/entity/projectiles/bodkin_arrow.png");

    public BodkinArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(BodkinArrowEntity bodkinArrowEntity) {
        return TEXTURE;
    }
}
