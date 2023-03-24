package com.ineffa.wondrouswilds.world.features;

import com.ineffa.wondrouswilds.world.features.configs.TerrainSplotchFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.List;

public class TerrainSplotchFeature extends Feature<TerrainSplotchFeatureConfig> {

    public TerrainSplotchFeature() {
        super(TerrainSplotchFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<TerrainSplotchFeatureConfig> context) {
        TerrainSplotchFeatureConfig config = context.getConfig();
        Random random = context.getRandom();
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();

        List<BlockPos> replacePositions = new ArrayList<>();

        int horizontalRadius = config.horizontalRadius.get(random);
        int verticalSpread = config.verticalSpread.get(random);
        for (BlockPos pos : BlockPos.iterate(origin.add(-horizontalRadius, 0, -horizontalRadius), origin.add(horizontalRadius, 0, horizontalRadius))) {
            int xDiff = pos.getX() - origin.getX();
            int zDiff = pos.getZ() - origin.getZ();
            if (xDiff * xDiff + zDiff * zDiff > horizontalRadius * horizontalRadius) continue;

            for (int yOffset = -verticalSpread; yOffset <= verticalSpread; ++yOffset) {
                BlockPos tryReplacePos = pos.add(0, yOffset, 0);
                if (config.replaceTargetPredicate.test(world, tryReplacePos)) replacePositions.add(tryReplacePos);
            }
        }

        if (replacePositions.isEmpty()) return false;

        int amountToRemove = MathHelper.floor(replacePositions.size() * (1.0F - config.densityMultiplier.get(random)));
        for (int amountRemoved = 0; amountRemoved < amountToRemove; ++amountRemoved) replacePositions.remove(random.nextInt(replacePositions.size()));

        for (BlockPos replacePos : replacePositions) {
            world.setBlockState(replacePos, config.splotchStateProvider.getBlockState(random, replacePos), Block.NOTIFY_LISTENERS);
            this.markBlocksAboveForPostProcessing(world, replacePos);
        }

        return true;
    }
}
