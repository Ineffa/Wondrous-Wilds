package com.ineffa.thewildupgrade.world.features;

import com.ineffa.thewildupgrade.world.features.configs.FallenLogFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.HashSet;
import java.util.Set;

public class FallenLogFeature extends Feature<FallenLogFeatureConfig> {
    private static final BlockState MOSS_CARPET_STATE = Blocks.MOSS_CARPET.getDefaultState();

    public FallenLogFeature() {
        super(FallenLogFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<FallenLogFeatureConfig> context) {
        Random random = context.getRandom();
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        FallenLogFeatureConfig config = context.getConfig();
        int minLength = config.minLength;
        int maxLength = config.maxLength;

        Set<BlockPos> logs = new HashSet<>();

        int logLimit = random.nextBetween(minLength, maxLength);
        int nextPositiveOffsetDistance = 0;
        int nextNegativeOffsetDistance = 0;
        Direction nextOffsetDirection = Direction.fromHorizontal(random.nextInt(4));
        boolean oneDirectional = false;
        while (logs.size() < logLimit) {
            Direction.AxisDirection axisDirection = nextOffsetDirection.getDirection();

            BlockPos tryLogPos = origin.offset(nextOffsetDirection, axisDirection == Direction.AxisDirection.POSITIVE ? nextPositiveOffsetDistance : nextNegativeOffsetDistance);

            if (!oneDirectional) nextOffsetDirection = nextOffsetDirection.getOpposite();

            if (!TreeFeature.canReplace(world, tryLogPos) || TreeFeature.canReplace(world, tryLogPos.down())) {
                if (oneDirectional) break;

                oneDirectional = true;
                continue;
            }

            if (axisDirection == Direction.AxisDirection.POSITIVE) ++nextPositiveOffsetDistance;
            else if (axisDirection == Direction.AxisDirection.NEGATIVE) ++nextNegativeOffsetDistance;

            logs.add(tryLogPos);
        }

        if (logs.size() < minLength) return false;

        for (BlockPos pos : logs) {
            BlockState state = config.logProvider.getBlockState(random, pos);
            if (state.getBlock() instanceof PillarBlock) state = state.with(PillarBlock.AXIS, nextOffsetDirection.getAxis());

            this.setBlockState(world, pos, state);

            if (random.nextInt(3) == 0) {
                BlockPos tryMossCarpetPos = pos.up();
                if (world.isAir(tryMossCarpetPos)) this.setBlockState(world, tryMossCarpetPos, MOSS_CARPET_STATE);
            }
        }

        return true;
    }
}
