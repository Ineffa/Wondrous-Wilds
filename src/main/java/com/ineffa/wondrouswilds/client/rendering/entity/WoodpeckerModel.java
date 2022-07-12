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
        float delta = MinecraftClient.getInstance().getTickDelta();

        float headPitch = MathHelper.lerp(delta, entity.prevPitch, entity.getPitch());
        float f = MathHelper.lerpAngleDegrees(delta, entity.prevBodyYaw, entity.getBodyYaw());
        float f1 = MathHelper.lerpAngleDegrees(delta, entity.prevHeadYaw, entity.getHeadYaw());
        float headYaw = f1 - f;

        float limbSwing = entity.limbAngle - entity.limbDistance * (1.0F - delta);
        float limbSwingAmount = MathHelper.lerp(delta, entity.lastLimbDistance, entity.limbDistance);

        parser.setValue("query.head_pitch", headPitch);
        parser.setValue("query.head_yaw", headYaw);

        parser.setValue("query.limb_swing", limbSwing * (entity.isOnGround() ? 0.15D : 0.0D));
        parser.setValue("query.limb_swing_amount", limbSwingAmount + (0.5F * MathHelper.clamp(limbSwingAmount * 10.0F, 0.0F, 1.0F)));
    }
}
