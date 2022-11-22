package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.molang.MolangParser;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;

public class WoodpeckerModel extends AnimatedGeoModel<WoodpeckerEntity> {

    @Override
    public Identifier getModelResource(WoodpeckerEntity entity) {
        return new Identifier(WondrousWilds.MOD_ID, "geo/woodpecker.geo.json");
    }

    @Override
    public Identifier getTextureResource(WoodpeckerEntity entity) {
        return new Identifier(WondrousWilds.MOD_ID, "textures/entity/woodpecker.png");
    }

    @Override
    public Identifier getAnimationResource(WoodpeckerEntity entity) {
        return new Identifier(WondrousWilds.MOD_ID, "animations/woodpecker.animation.json");
    }

    @Override
    public void setMolangQueries(IAnimatable animatable, double currentTick) {
        super.setMolangQueries(animatable, currentTick);

        MolangParser parser = GeckoLibCache.getInstance().parser;

        WoodpeckerEntity entity = (WoodpeckerEntity) animatable;
        boolean flying = entity.isFlying();

        float delta = MinecraftClient.getInstance().getTickDelta();

        float headPitch = MathHelper.lerp(delta, entity.prevPitch, entity.getPitch());
        float f = MathHelper.lerpAngleDegrees(delta, entity.prevBodyYaw, entity.getBodyYaw());
        float f1 = MathHelper.lerpAngleDegrees(delta, entity.prevHeadYaw, entity.getHeadYaw());
        float headYaw = f1 - f;

        parser.setValue("query.head_pitch", () -> headPitch);
        parser.setValue("query.head_yaw", () -> headYaw);

        float swing = entity.limbAngle - entity.limbDistance * (1.0F - delta);
        float swingAmount = MathHelper.lerp(delta, entity.lastLimbDistance, entity.limbDistance);
        float extraSwing = flying ? 0.0F : 0.5F * MathHelper.clamp(swingAmount * 10.0F, 0.0F, 1.0F);

        parser.setValue("query.swing", () -> swing * 0.15D);
        parser.setValue("query.swing_amount", () -> MathHelper.clamp(swingAmount + extraSwing, 0.0D, flying ? 1.0D : 1.25D));

        float flapAngle = MathHelper.lerp(delta, entity.prevFlapAngle, entity.flapAngle);

        parser.setValue("query.flap_angle", () -> flapAngle);
    }
}
