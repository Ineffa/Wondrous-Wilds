package com.ineffa.thewildupgrade.world.features;

import com.ineffa.thewildupgrade.world.features.configs.FancyBirchFeatureConfig;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FancyBirchFeature extends Feature<FancyBirchFeatureConfig> {
    private static final BlockState LOG_STATE = Blocks.BIRCH_LOG.getDefaultState();
    private static final BlockState LEAVES_STATE = Blocks.BIRCH_LEAVES.getDefaultState();
    private static final BlockState NEST_STATE = Blocks.BEE_NEST.getDefaultState();

    public FancyBirchFeature() {
        super(FancyBirchFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<FancyBirchFeatureConfig> context) {
        AbstractRandom random = context.getRandom();
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        FancyBirchFeatureConfig config = context.getConfig();

        boolean hasBees = config.hasBees;
        int branches = hasBees ? 1 : 0; while (branches < 3) if (random.nextBoolean()) ++branches; else break;
        int treeHeight = random.nextBetween(12, 20);

        return this.generateLogs(world, random, origin, treeHeight, branches, hasBees);
    }

    private boolean generateLogs(WorldAccess world, AbstractRandom random, BlockPos origin, int height, int branches, boolean hasBees) {
        boolean hasPlacedBees = false;

        BlockPos.Mutable mutablePos = origin.mutableCopy();
        int currentHeight = 0;

        List<BlockPos> logs = new ArrayList<>();

        while (currentHeight < height) {
            if (TreeFeature.canReplace(world, mutablePos)) logs.add(mutablePos);
            else return false;

            mutablePos = mutablePos.up().mutableCopy();
            ++currentHeight;
        }

        for (BlockPos pos : logs) this.setBlockState(world, pos, LOG_STATE);

        int branchCount = 0;
        List<BlockPos> logsWithBranches = new ArrayList<>();
        while (branchCount < branches) {
            BlockPos tryBranchPos = logs.get(random.nextInt(logs.size()));
            if (logsWithBranches.contains(tryBranchPos)) continue;

            Direction branchDirection = Direction.fromHorizontal(random.nextInt(4));
            BlockPos branchPos = tryBranchPos.offset(branchDirection);
            if (!TreeFeature.canReplace(world, branchPos)) continue;

            this.setBlockState(world, branchPos, LOG_STATE.with(PillarBlock.AXIS, branchDirection.getAxis()));

            logsWithBranches.add(tryBranchPos);
            ++branchCount;

            if (hasBees && !hasPlacedBees) {
                BlockPos nestPos = branchPos.down();
                if (world.isAir(nestPos)) {
                    Direction nestFacingDirection = Util.getRandom(Arrays.stream(Direction.values()).filter((direction) -> direction.getAxis().isHorizontal() && direction != branchDirection.getOpposite()).toArray(Direction[]::new), random);
                    this.setBlockState(world, nestPos, NEST_STATE.with(BeehiveBlock.FACING, nestFacingDirection));

                    world.getBlockEntity(nestPos, BlockEntityType.BEEHIVE).ifPresent(blockEntity -> {
                        int beeCount = 2 + random.nextInt(2);
                        for (int i = 0; i < beeCount; ++i) {
                            NbtCompound nbt = new NbtCompound();
                            nbt.putString("id", Registry.ENTITY_TYPE.getId(EntityType.BEE).toString());
                            blockEntity.addBee(nbt, random.nextInt(599), false);
                        }
                    });

                    hasPlacedBees = true;
                }
            }
        }

        this.generateLeaves(world, random, mutablePos);

        return true;
    }

    private void generateLeaves(WorldAccess world, AbstractRandom random, BlockPos origin) {
        Direction[] horizontalDirections = Arrays.stream(Direction.values()).filter((direction) -> direction.getAxis().isHorizontal()).toArray(Direction[]::new);

        List<BlockPos> leaves = new ArrayList<>();

        for (int y = 0; y >= -5; --y) {
            if (y == 0 || y == -5) {
                if (y == 0) leaves.add(origin);
                for (Direction direction : horizontalDirections) leaves.add(origin.down(MathHelper.abs(y)).offset(direction));
            }
            else {
                for (Direction direction : horizontalDirections) {
                    BlockPos offsetPos = origin.down(MathHelper.abs(y)).offset(direction, y == -2 || y == -3 ? 3 : 2);

                    leaves.add(offsetPos);
                    if (y != -2) {
                        leaves.add(offsetPos.offset(direction.rotateYClockwise()));
                        leaves.add(offsetPos.offset(direction.rotateYCounterclockwise()));
                    }
                }
            }

            if (y < 0 && y > -5) {
                int increase = y == -2 || y == -3 ? 1 : 0;
                for (int x = -1 - increase; x <= 1 + increase; ++x) {
                    for (int z = -1 - increase; z <= 1 + increase; ++z) {
                        if (x == origin.getX() && z == origin.getZ()) continue;
                        leaves.add(origin.add(x, y, z));
                    }
                }
            }
        }

        for (BlockPos pos : leaves) if (random.nextInt(15) != 0 && (world.isAir(pos) || world.isWater(pos))) this.setBlockState(world, pos, LEAVES_STATE);
    }
}
