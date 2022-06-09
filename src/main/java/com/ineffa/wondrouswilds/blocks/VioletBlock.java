package com.ineffa.wondrouswilds.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import org.jetbrains.annotations.Nullable;

public class VioletBlock extends FlowerBlock {

    public static final int MIN_VIOLETS = 1;
    public static final int MAX_VIOLETS = 4;

    public static final IntProperty VIOLETS = IntProperty.of("violets", MIN_VIOLETS, MAX_VIOLETS);

    public VioletBlock(Settings settings) {
        super(StatusEffects.REGENERATION, 8, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(VIOLETS, MIN_VIOLETS));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(VIOLETS);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) return blockState.with(VIOLETS, Math.min(MAX_VIOLETS, blockState.get(VIOLETS) + 1));

        return super.getPlacementState(ctx);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(VIOLETS) < MAX_VIOLETS) return true;

        return super.canReplace(state, context);
    }
}
