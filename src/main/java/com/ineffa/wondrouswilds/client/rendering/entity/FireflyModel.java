package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.molang.MolangParser;

public class FireflyModel extends AnimatedGeoModel<FireflyEntity> {
    private static final Identifier TEXTURE = new Identifier(WondrousWilds.MOD_ID, "textures/entity/firefly/firefly.png");

    @Override
    public Identifier getModelResource(FireflyEntity entity) {
        return new Identifier(WondrousWilds.MOD_ID, "geo/firefly.geo.json");
    }

    @Override
    public Identifier getAnimationResource(FireflyEntity entity) {
        return new Identifier(WondrousWilds.MOD_ID, "animations/firefly.animation.json");
    }

    @Override
    public Identifier getTextureResource(FireflyEntity entity) {
        return TEXTURE;
    }

    @Override
    public void setMolangQueries(IAnimatable animatable, double currentTick) {
        super.setMolangQueries(animatable, currentTick);

        MolangParser parser = GeckoLibCache.getInstance().parser;

        FireflyEntity fireflyEntity = (FireflyEntity) animatable;

        float delta = MinecraftClient.getInstance().getTickDelta();
        float limbSwing = fireflyEntity.limbAngle - fireflyEntity.limbDistance * (1.0F - delta);
        float limbSwingAmount = MathHelper.lerp(delta, fireflyEntity.lastLimbDistance, fireflyEntity.limbDistance);

        parser.setValue("query.limb_swing", limbSwing);
        parser.setValue("query.limb_swing_amount", limbSwingAmount + (0.9F * MathHelper.clamp(limbSwingAmount * 10.0F, 0.0F, 1.0F)));
    }
}
