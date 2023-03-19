package com.ineffa.wondrouswilds.world.features.trees.decorators;

import com.ineffa.wondrouswilds.registry.WondrousWildsFeatures;
import com.ineffa.wondrouswilds.util.WondrousWildsUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.ArrayList;
import java.util.List;

public class LeafDecayTreeDecorator extends TreeDecorator {

    public static final Codec<LeafDecayTreeDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.POSITIVE_INT.fieldOf("min_divisor").forGetter(LeafDecayTreeDecorator::getMinDivisor),
            Codecs.POSITIVE_INT.fieldOf("max_divisor").forGetter(LeafDecayTreeDecorator::getMaxDivisor),
            Codecs.NONNEGATIVE_INT.fieldOf("max_cobwebs").forGetter(LeafDecayTreeDecorator::getMaxCobwebs),
            Codec.BOOL.fieldOf("conceal_logs").forGetter(LeafDecayTreeDecorator::concealsLogs)
    ).apply(instance, LeafDecayTreeDecorator::new));

    private final int minDivisor, maxDivisor;

    private final int maxCobwebs;

    private final boolean concealLogs;

    public int getMinDivisor() {
        return this.minDivisor;
    }

    public int getMaxDivisor() {
        return this.maxDivisor;
    }

    public int getMaxCobwebs() {
        return this.maxCobwebs;
    }

    public boolean concealsLogs() {
        return this.concealLogs;
    }

    public LeafDecayTreeDecorator(int minDivisor, int maxDivisor, int maxCobwebs, boolean concealLogs) {
        this.minDivisor = minDivisor;
        this.maxDivisor = maxDivisor;

        this.maxCobwebs = maxCobwebs;

        this.concealLogs = concealLogs;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return WondrousWildsFeatures.Trees.Decorators.LEAF_DECAY_TYPE;
    }

    @Override
    public void generate(Generator generator) {
        Random random = generator.getRandom();

        List<BlockPos> leaves = Util.copyShuffled(generator.getLeavesPositions().stream().filter(leavesPos -> !this.concealLogs || !WondrousWildsUtils.isPosAdjacentToAnyOfPositions(leavesPos, generator.getLogPositions())), random);
        int amountToRemove = leaves.size() / random.nextBetween(this.minDivisor, this.maxDivisor);
        List<BlockPos> leavesToRemove = new ArrayList<>();
        for (int amountRemoved = 0; amountRemoved < amountToRemove; ++amountRemoved) leavesToRemove.add(leaves.get(amountRemoved));

        int cobwebsRemaining = random.nextInt(this.maxCobwebs + 1);
        for (BlockPos leavesPos : leavesToRemove) {
            BlockState replacementState = Blocks.AIR.getDefaultState();
            if (cobwebsRemaining > 0) {
                replacementState = Blocks.COBWEB.getDefaultState();
                --cobwebsRemaining;
            }
            generator.replace(leavesPos, replacementState);
        }
    }
}
