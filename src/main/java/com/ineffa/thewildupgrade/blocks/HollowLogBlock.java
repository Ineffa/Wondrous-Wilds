package com.ineffa.thewildupgrade.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class HollowLogBlock extends PillarBlock {

    private static final VoxelShape VERTICAL_NORTH_WALL_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    private static final VoxelShape VERTICAL_SOUTH_WALL_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape VERTICAL_EAST_WALL_SHAPE = Block.createCuboidShape(13.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape VERTICAL_WEST_WALL_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 3.0D, 3.0D, 16.0D, 13.0D);

    private static final VoxelShape HORIZONTAL_TOP_SHAPE = Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape HORIZONTAL_BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

    private static final VoxelShape X_AXIS_NORTH_WALL_SHAPE = Block.createCuboidShape(0.0D, 3.0D, 0.0D, 16.0D, 13.0D, 3.0D);
    private static final VoxelShape X_AXIS_SOUTH_WALL_SHAPE = Block.createCuboidShape(0.0D, 3.0D, 13.0D, 16.0D, 13.0D, 16.0D);

    private static final VoxelShape Z_AXIS_EAST_WALL_SHAPE = Block.createCuboidShape(13.0D, 3.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    private static final VoxelShape Z_AXIS_WEST_WALL_SHAPE = Block.createCuboidShape(0.0D, 3.0D, 0.0D, 3.0D, 13.0D, 16.0D);

    private static final VoxelShape Y_AXIS_SHAPE = VoxelShapes.union(VERTICAL_NORTH_WALL_SHAPE, VERTICAL_SOUTH_WALL_SHAPE, VERTICAL_EAST_WALL_SHAPE, VERTICAL_WEST_WALL_SHAPE);
    private static final VoxelShape X_AXIS_SHAPE = VoxelShapes.union(HORIZONTAL_TOP_SHAPE, HORIZONTAL_BOTTOM_SHAPE, X_AXIS_NORTH_WALL_SHAPE, X_AXIS_SOUTH_WALL_SHAPE);
    private static final VoxelShape Z_AXIS_SHAPE = VoxelShapes.union(HORIZONTAL_TOP_SHAPE, HORIZONTAL_BOTTOM_SHAPE, Z_AXIS_EAST_WALL_SHAPE, Z_AXIS_WEST_WALL_SHAPE);

    private static final VoxelShape RAYCAST_SHAPE = VoxelShapes.fullCube();

    public HollowLogBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction.Axis axis = state.get(AXIS);

        if (axis == Direction.Axis.X) return X_AXIS_SHAPE;
        if (axis == Direction.Axis.Z) return Z_AXIS_SHAPE;

        return Y_AXIS_SHAPE;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }
}
