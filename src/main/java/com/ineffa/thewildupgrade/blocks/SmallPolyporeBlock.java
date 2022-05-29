package com.ineffa.thewildupgrade.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import org.jetbrains.annotations.Nullable;

public class SmallPolyporeBlock extends LadderBlock {

    private static final int MIN_POLYPORES = 1;
    private static final int MAX_POLYPORES = 3;

    public static final IntProperty POLYPORES = IntProperty.of("polypores", MIN_POLYPORES, MAX_POLYPORES);

    public SmallPolyporeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POLYPORES, MIN_POLYPORES));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POLYPORES);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) return blockState.with(POLYPORES, Math.min(MAX_POLYPORES, blockState.get(POLYPORES) + 1));

        return super.getPlacementState(ctx);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(POLYPORES) < MAX_POLYPORES) return true;

        return super.canReplace(state, context);
    }
}
