package com.ineffa.wondrouswilds.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class SmallPolyporeBlock extends LadderBlock {

    private static final VoxelShape NORTH_SINGLE_SHAPE = Block.createCuboidShape(3.0D, 7.0D, 8.0D, 13.0D, 9.0D, 16.0D);
    private static final VoxelShape NORTH_DUAL_SHAPE = Block.createCuboidShape(1.0D, 6.0D, 8.0D, 15.0D, 10.0D, 16.0D);
    private static final VoxelShape NORTH_TRIPLE_SHAPE = Block.createCuboidShape(1.0D, 4.0D, 8.0D, 15.0D, 12.0D, 16.0D);

    private static final VoxelShape SOUTH_SINGLE_SHAPE = Block.createCuboidShape(3.0D, 7.0D, 0.0D, 13.0D, 9.0D, 8.0D);
    private static final VoxelShape SOUTH_DUAL_SHAPE = Block.createCuboidShape(1.0D, 6.0D, 0.0D, 15.0D, 10.0D, 8.0D);
    private static final VoxelShape SOUTH_TRIPLE_SHAPE = Block.createCuboidShape(1.0D, 4.0D, 0.0D, 15.0D, 12.0D, 8.0D);

    private static final VoxelShape EAST_SINGLE_SHAPE = Block.createCuboidShape(0.0D, 7.0D, 3.0D, 8.0D, 9.0D, 13.0D);
    private static final VoxelShape EAST_DUAL_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 1.0D, 8.0D, 10.0D, 15.0D);
    private static final VoxelShape EAST_TRIPLE_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 1.0D, 8.0D, 12.0D, 15.0D);

    private static final VoxelShape WEST_SINGLE_SHAPE = Block.createCuboidShape(8.0D, 7.0D, 3.0D, 16.0D, 9.0D, 13.0D);
    private static final VoxelShape WEST_DUAL_SHAPE = Block.createCuboidShape(8.0D, 6.0D, 1.0D, 16.0D, 10.0D, 15.0D);
    private static final VoxelShape WEST_TRIPLE_SHAPE = Block.createCuboidShape(8.0D, 4.0D, 1.0D, 16.0D, 12.0D, 15.0D);

    public static final int MIN_POLYPORES = 1;
    public static final int MAX_POLYPORES = 3;

    public static final IntProperty POLYPORES = IntProperty.of("polypores", MIN_POLYPORES, MAX_POLYPORES);

    public SmallPolyporeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POLYPORES, MIN_POLYPORES).with(FACING, Direction.NORTH).with(WATERLOGGED, false));
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

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int polypores = state.get(POLYPORES);

        return switch (state.get(FACING)) {
            default -> switch (polypores) {
                default -> NORTH_SINGLE_SHAPE;
                case 2 -> NORTH_DUAL_SHAPE;
                case 3 -> NORTH_TRIPLE_SHAPE;
            };
            case SOUTH -> switch (polypores) {
                default -> SOUTH_SINGLE_SHAPE;
                case 2 -> SOUTH_DUAL_SHAPE;
                case 3 -> SOUTH_TRIPLE_SHAPE;
            };
            case EAST -> switch (polypores) {
                default -> EAST_SINGLE_SHAPE;
                case 2 -> EAST_DUAL_SHAPE;
                case 3 -> EAST_TRIPLE_SHAPE;
            };
            case WEST -> switch (polypores) {
                default -> WEST_SINGLE_SHAPE;
                case 2 -> WEST_DUAL_SHAPE;
                case 3 -> WEST_TRIPLE_SHAPE;
            };
        };
    }
}
