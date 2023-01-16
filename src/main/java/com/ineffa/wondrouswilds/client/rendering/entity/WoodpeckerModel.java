package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.molang.MolangParser;

public class WoodpeckerModel extends AnimatedGeoModel<WoodpeckerEntity> {

    public static final Identifier MODEL_PATH = new Identifier(WondrousWilds.MOD_ID, "geo/woodpecker.geo.json");
    public static final Identifier TEXTURE_PATH = new Identifier(WondrousWilds.MOD_ID, "textures/entity/woodpecker/woodpecker.png");
    public static final Identifier ANIMATION_PATH = new Identifier(WondrousWilds.MOD_ID, "animations/woodpecker.animation.json");

    public static final Identifier BABY_MODEL_PATH = new Identifier(WondrousWilds.MOD_ID, "geo/woodpecker_baby.geo.json");
    public static final Identifier BABY_TEXTURE_PATH = new Identifier(WondrousWilds.MOD_ID, "textures/entity/woodpecker/woodpecker_baby.png");
    public static final Identifier BABY_ANIMATION_PATH = new Identifier(WondrousWilds.MOD_ID, "animations/woodpecker_baby.animation.json");

    @Override
    public Identifier getModelResource(WoodpeckerEntity entity) {
        return entity.isBaby() ? BABY_MODEL_PATH : MODEL_PATH;
    }

    @Override
    public Identifier getTextureResource(WoodpeckerEntity entity) {
        return entity.isBaby() ? BABY_TEXTURE_PATH : TEXTURE_PATH;
    }

    @Override
    public Identifier getAnimationResource(WoodpeckerEntity entity) {
        return entity.isBaby() ? BABY_ANIMATION_PATH : ANIMATION_PATH;
    }

    @Override
    public void setMolangQueries(IAnimatable animatable, double currentTick) {
        super.setMolangQueries(animatable, currentTick);

        MolangParser parser = GeckoLibCache.getInstance().parser;

        WoodpeckerEntity woodpecker = (WoodpeckerEntity) animatable;
        boolean flying = woodpecker.isFlying();

        float delta = MinecraftClient.getInstance().getTickDelta();

        float headPitch = MathHelper.lerp(delta, woodpecker.prevPitch, woodpecker.getPitch());
        float f = MathHelper.lerpAngleDegrees(delta, woodpecker.prevBodyYaw, woodpecker.getBodyYaw());
        float f1 = MathHelper.lerpAngleDegrees(delta, woodpecker.prevHeadYaw, woodpecker.getHeadYaw());
        float headYaw = f1 - f;

        parser.setValue("query.head_pitch", headPitch);
        parser.setValue("query.head_yaw", headYaw);

        float swing = woodpecker.shouldPreventMovementAnimations() ? 0.0F : woodpecker.limbAngle - woodpecker.limbDistance * (1.0F - delta);
        float swingAmount = woodpecker.shouldPreventMovementAnimations() ? 0.0F : MathHelper.lerp(delta, woodpecker.lastLimbDistance, woodpecker.limbDistance);
        float extraSwing = 0.0F;
        if (!flying) extraSwing = 0.5F * MathHelper.clamp(swingAmount * 10.0F, 0.0F, 1.0F);

        parser.setValue("query.swing", swing * (woodpecker.isBaby() ? 0.3D : 0.15D));
        parser.setValue("query.swing_amount", MathHelper.clamp(swingAmount + extraSwing, 0.0D, flying ? 1.0D : 1.25D));

        float flapAngle = MathHelper.lerp(delta, woodpecker.prevFlapAngle, woodpecker.flapAngle);

        parser.setValue("query.flap_angle", flapAngle);
    }
}
