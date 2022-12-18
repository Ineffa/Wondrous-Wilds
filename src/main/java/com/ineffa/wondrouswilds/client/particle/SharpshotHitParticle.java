package com.ineffa.wondrouswilds.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

@Environment(value = EnvType.CLIENT)
public class SharpshotHitParticle extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    private SharpshotHitParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);

        this.maxAge = this.random.nextBetween(3, 5);
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
        this.scale = 0.8F + (this.random.nextFloat() * 0.4F);
        this.angle = (float) Math.random() * ((float) Math.PI * 2.0F);
        this.prevAngle = this.angle;

        this.velocityMultiplier = 0.0F;
        this.collidesWithWorld = false;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Override
    protected int getBrightness(float tint) {
        return (int) MathHelper.lerp((float) this.age / this.maxAge, 240.0F, (float) super.getBrightness(tint));
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        if (this.age++ >= this.maxAge) this.markDead();

        else this.setSpriteForAge(this.spriteProvider);
    }

    @Environment(value = EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SharpshotHitParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
