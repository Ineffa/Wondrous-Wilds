package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.entities.BycocketUser;
import com.ineffa.wondrouswilds.entities.CanTakeSharpshots;
import com.ineffa.wondrouswilds.entities.projectiles.CanSharpshot;
import com.ineffa.wondrouswilds.registry.WondrousWildsEnchantments;
import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * @author Ineffa
 * <p> Processes Bycocket abilities through living entities by controlling when they should trigger, and processing some effects of them when they do.
 */
@Mixin(LivingEntity.class)
public abstract class MixinBycocketAbilityProcessor extends Entity implements BycocketUser, CanTakeSharpshots {

    private MixinBycocketAbilityProcessor(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Unique
    @Override
    public boolean isAccurateWith(ProjectileEntity projectile) {
        ItemStack headItem = this.getEquippedStack(EquipmentSlot.HEAD);

        if (!headItem.isIn(WondrousWildsTags.ItemTags.BYCOCKETS)) return false;

        return EnchantmentHelper.getLevel(WondrousWildsEnchantments.VERSATILITY, headItem) > 0 || projectile.getType().isIn(WondrousWildsTags.EntityTypeTags.BYCOCKET_ALWAYS_ACCURATE);
    }

    @Unique
    @Override
    public boolean canSharpshotWith(ProjectileEntity projectile) {
        ItemStack headItem = this.getEquippedStack(EquipmentSlot.HEAD);

        if (!headItem.isIn(WondrousWildsTags.ItemTags.BYCOCKETS)) return false;

        return EnchantmentHelper.getLevel(WondrousWildsEnchantments.VERSATILITY, headItem) > 0 || projectile.getType().isIn(WondrousWildsTags.EntityTypeTags.BYCOCKET_ALWAYS_SHARPSHOTS);
    }

    /**
     * <p> Creates a small and dynamic hitbox for an entity, which controls where sharpshots can be landed on it.
     * <p> Vertically, the sharpshot hitbox will attempt to center on the entity's eye level when possible, and will scale proportionally to the entity's height.
     * <p> Horizontally, it will always match the measurements of the entity's base hitbox.
     * <p> The sharpshot hitbox will never exceed the sides of the entity's base hitbox on any axis.
     */
    @Unique
    @Override
    public Box getSharpshotHitbox() {
        double height = this.getHeight() / 8.0D;
        return new Box(this.getBoundingBox().minX, this.getBoundingBox().minY + (this.getStandingEyeHeight() - height), this.getBoundingBox().minZ, this.getBoundingBox().maxX, Math.min(this.getBoundingBox().minY + (this.getStandingEyeHeight() + height), this.getBoundingBox().maxY), this.getBoundingBox().maxZ);
    }

    /**
     * Applies a multiplier to the damage taken from a projectile when it is landing a sharpshot.
     */
    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float applySharpshotDamageBonus(float damage, DamageSource source, float amount) {
        if (source.getSource() instanceof CanSharpshot sharpshotProjectile && sharpshotProjectile.hasRegisteredSharpshot())
            damage *= 1.5F;

        return damage;
    }
}
