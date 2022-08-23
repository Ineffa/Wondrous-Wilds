package com.ineffa.wondrouswilds.blocks;

import com.ineffa.wondrouswilds.blocks.entity.TreeHollowBlockEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TreeHollowBlock extends InhabitableNestBlock {

    public TreeHollowBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TreeHollowBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? null : checkType(type, WondrousWildsBlocks.BlockEntities.TREE_HOLLOW, TreeHollowBlockEntity::serverTick);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && player.isCreative() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && world.getBlockEntity(pos) instanceof TreeHollowBlockEntity treeHollow) {
            ItemStack itemStack = new ItemStack(this);
            if (treeHollow.hasInhabitants()) {
                NbtCompound nbtCompound = new NbtCompound();

                nbtCompound.put(TreeHollowBlockEntity.INHABITANTS_KEY, treeHollow.getInhabitantsNbt());
                BlockItem.setBlockEntityNbt(itemStack, WondrousWildsBlocks.BlockEntities.TREE_HOLLOW, nbtCompound);

                itemStack.setSubNbt("BlockStateTag", nbtCompound);

                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof TreeHollowBlockEntity treeHollow && treeHollow.hasInhabitants()) return 15;

        return 0;
    }
}
