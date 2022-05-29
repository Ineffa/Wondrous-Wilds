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
    private static final BlockState WEB_STATE = Blocks.COBWEB.getDefaultState();

    private static final Direction[] HORIZONTAL_DIRECTIONS = Arrays.stream(Direction.values()).filter((direction) -> direction.getAxis().isHorizontal()).toArray(Direction[]::new);

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
        int treeHeight = random.nextBetween(config.minHeight, config.maxHeight);

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
            if (logsWithBranches.size() >= logs.size()) break;

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
            else if (random.nextInt(15) == 0) {
                BlockPos webPos = branchPos.down();
                if (world.isAir(webPos)) this.setBlockState(world, webPos, WEB_STATE);
            }
        }

        this.generateLeaves(world, random, mutablePos, random.nextBoolean());

        return true;
    }

    private void generateLeaves(WorldAccess world, AbstractRandom random, BlockPos origin, boolean isLarge) {
        List<BlockPos> leaves = new ArrayList<>();

        int upwardBound = 0;
        int downwardBound = isLarge ? -7 : -5;
        for (int y = upwardBound; y >= downwardBound; --y) {
            boolean isTip = y == upwardBound || y == downwardBound;
            if (isTip) {
                if (y == upwardBound) leaves.add(origin);
                for (Direction direction : HORIZONTAL_DIRECTIONS) leaves.add(origin.down(MathHelper.abs(y)).offset(direction));
            }
            else {
                boolean isInMiddleRange = y <= upwardBound - 2 && y >= downwardBound + 2;
                int increase = isInMiddleRange ? 1 : 0;

                for (int x = -1 - increase; x <= 1 + increase; ++x) {
                    for (int z = -1 - increase; z <= 1 + increase; ++z) {
                        if (x == origin.getX() && z == origin.getZ()) continue;
                        leaves.add(origin.add(x, y, z));
                    }
                }

                for (Direction direction : HORIZONTAL_DIRECTIONS) {
                    BlockPos offsetPos = origin.down(MathHelper.abs(y)).offset(direction, 2 + increase);

                    leaves.add(offsetPos);
                    if (y != upwardBound - 2 && !(isLarge && y == downwardBound + 2)) {
                        int layersToAdd = y == downwardBound + 3 ? 2 : 1;
                        for (int distance = 1; distance <= layersToAdd; ++distance) {
                            leaves.add(offsetPos.offset(direction.rotateYClockwise(), distance));
                            leaves.add(offsetPos.offset(direction.rotateYCounterclockwise(), distance));
                        }
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

        for (BlockPos pos : leaves) {
            if (random.nextInt(40) == 0 && !isPosTouchingLog(world, pos)) continue;
            if (world.isAir(pos) || world.isWater(pos)) this.setBlockState(world, pos, LEAVES_STATE);
        }
    }

    private static boolean isPosTouchingLog(WorldAccess world, BlockPos pos) {
        for (Direction direction : Direction.values()) if (world.getBlockState(pos.offset(direction)).isOf(Blocks.BIRCH_LOG)) return true;
        return false;
    }
}
