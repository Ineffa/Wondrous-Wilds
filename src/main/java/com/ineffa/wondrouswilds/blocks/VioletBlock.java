package com.ineffa.wondrouswilds.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class VioletBlock extends FlowerBlock {

    private static final VoxelShape SINGLE_VIOLET_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D);
    private static final VoxelShape DOUBLE_VIOLET_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D);
    private static final VoxelShape TRIPLE_VIOLET_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 5.0D, 15.0D);
    private static final VoxelShape QUADRUPLE_VIOLET_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);

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

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(VIOLETS)) {
            default -> SINGLE_VIOLET_SHAPE;
            case 2 -> DOUBLE_VIOLET_SHAPE;
            case 3 -> TRIPLE_VIOLET_SHAPE;
            case 4 -> QUADRUPLE_VIOLET_SHAPE;
        };
    }
}
