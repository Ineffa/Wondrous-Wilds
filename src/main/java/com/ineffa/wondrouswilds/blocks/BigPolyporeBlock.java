package com.ineffa.wondrouswilds.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BigPolyporeBlock extends LadderBlock {

    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(1.0D, 7.0D, 4.0D, 15.0D, 9.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(1.0D, 7.0D, 0.0D, 15.0D, 9.0D, 12.0D);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0D, 7.0D, 1.0D, 12.0D, 9.0D, 15.0D);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(4.0D, 7.0D, 1.0D, 16.0D, 9.0D, 15.0D);

    public BigPolyporeBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            default -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
