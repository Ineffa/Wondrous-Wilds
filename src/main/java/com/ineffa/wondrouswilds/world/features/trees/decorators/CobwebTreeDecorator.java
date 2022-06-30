package com.ineffa.wondrouswilds.world.features.trees.decorators;

import com.ineffa.wondrouswilds.registry.WondrousWildsFeatures;
import com.mojang.serialization.Codec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.ineffa.wondrouswilds.util.WondrousWildsUtils.HORIZONTAL_DIRECTIONS;

public class CobwebTreeDecorator extends TreeDecorator {

    public static final CobwebTreeDecorator INSTANCE = new CobwebTreeDecorator();
    public static final Codec<CobwebTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    protected TreeDecoratorType<?> getType() {
        return WondrousWildsFeatures.Trees.Decorators.COBWEB_TYPE;
    }

    @Override
    public void generate(Generator generator) {
        Random random = generator.getRandom();
        TestableWorld world = generator.getWorld();

        List<BlockPos> horizontalLogs = generator.getLogPositions().stream().filter(pos -> world.testBlockState(pos, state -> state.contains(PillarBlock.AXIS) && state.get(PillarBlock.AXIS).isHorizontal())).collect(Collectors.toList());
        Collections.shuffle(horizontalLogs);

        for (BlockPos logPos : horizontalLogs) {
            BlockPos cobwebPos = logPos.down();
            if (generator.isAir(cobwebPos) && canSupportCobwebFromSide(world, cobwebPos)) {
                if (random.nextInt(30) != 0) continue;
                generator.replace(cobwebPos, Blocks.COBWEB.getDefaultState());
            }
        }
    }

    private static boolean canSupportCobwebFromSide(TestableWorld world, BlockPos center) {
        boolean canSupport = false;
        for (Direction direction : HORIZONTAL_DIRECTIONS) {
            BlockPos checkPos = center.offset(direction);
            if (world.testBlockState(checkPos, AbstractBlock.AbstractBlockState::isOpaque)) {
                canSupport = true;
                break;
            }
        }

        return canSupport;
    }
}
