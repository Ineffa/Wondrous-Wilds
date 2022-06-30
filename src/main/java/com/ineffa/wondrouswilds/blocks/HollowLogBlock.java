package com.ineffa.wondrouswilds.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class HollowLogBlock extends PillarBlock implements Waterloggable {

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

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public HollowLogBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) return Fluids.WATER.getStill(false);

        return super.getFluidState(state);
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

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
