package com.ineffa.wondrouswilds.entities.projectiles;

import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BodkinArrowEntity extends PersistentProjectileEntity implements BlockBreakingProjectile {

    public BodkinArrowEntity(EntityType<? extends BodkinArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public BodkinArrowEntity(World world, LivingEntity owner) {
        super(WondrousWildsEntities.BODKIN_ARROW, owner, world);
    }

    public BodkinArrowEntity(World world, double x, double y, double z) {
        super(WondrousWildsEntities.BODKIN_ARROW, x, y, z, world);
    }

    @Override
    public float getHardBlockCeiling() {
        return 3.0F;
    }

    @Override
    public float getSoftBlockCeiling() {
        return 0.3F;
    }

    @Override
    public double getStrongSpeedThreshold() {
        return 1.0D;
    }

    @Nullable
    @Override
    public ProjectileBlockBreakType testBlockBreakCapability(World world, BlockPos pos) {
        if (!this.canModifyAt(world, pos)) return null;

        float hardness = world.getBlockState(pos).getBlock().getHardness();
        if (hardness > this.getHardBlockCeiling() || hardness < 0.0F) return null;

        if (hardness <= this.getSoftBlockCeiling()) return ProjectileBlockBreakType.SOFT;
        return this.getVelocity().length() >= this.getStrongSpeedThreshold() ? ProjectileBlockBreakType.HARD : null;
    }

    @Override
    public boolean canPenetratePos(World world, BlockPos pos) {
        if (!this.canModifyAt(world, pos)) return false;

        float hardness = world.getBlockState(pos).getBlock().getHardness();
        return hardness <= this.getSoftBlockCeiling() && hardness >= 0.0F;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockPos hitPos = blockHitResult.getBlockPos();
        ProjectileBlockBreakType breakType = null;
        if (!this.getWorld().isClient()) breakType = this.testBlockBreakCapability(this.getWorld(), hitPos);

        super.onBlockHit(blockHitResult);

        if (breakType != null) {
            this.getWorld().breakBlock(hitPos, true, this.getOwner());

            if (breakType == ProjectileBlockBreakType.HARD) {
                this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.25F, 1.4F + (this.random.nextFloat() * 0.4F));
                this.discard();
            }
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(WondrousWildsItems.BODKIN_ARROW);
    }

    public static ProjectileDispenserBehavior getDispenserBehavior() {
        return new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                BodkinArrowEntity bodkinArrow = new BodkinArrowEntity(world, position.getX(), position.getY(), position.getZ());
                bodkinArrow.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                return bodkinArrow;
            }
        };
    }
}
