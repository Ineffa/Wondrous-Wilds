package com.ineffa.wondrouswilds.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class BirchBirdhouseBlock extends BirdhouseBlock {

    private static final VoxelShape ROOF_SHAPE = Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_BOX_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 14.0D, 16.0D);
    private static final VoxelShape SOUTH_BOX_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 14.0D);
    private static final VoxelShape EAST_BOX_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 14.0D, 14.0D, 16.0D);
    private static final VoxelShape WEST_BOX_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(ROOF_SHAPE, NORTH_BOX_SHAPE);
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(ROOF_SHAPE, SOUTH_BOX_SHAPE);
    private static final VoxelShape EAST_SHAPE = VoxelShapes.union(ROOF_SHAPE, EAST_BOX_SHAPE);
    private static final VoxelShape WEST_SHAPE = VoxelShapes.union(ROOF_SHAPE, WEST_BOX_SHAPE);

    public BirchBirdhouseBlock(Settings settings) {
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

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
