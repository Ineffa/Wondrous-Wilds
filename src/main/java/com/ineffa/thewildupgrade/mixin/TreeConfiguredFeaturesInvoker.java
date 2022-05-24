package com.ineffa.thewildupgrade.mixin;

import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TreeConfiguredFeatures.class)
public interface TreeConfiguredFeaturesInvoker {

    @Invoker("superBirch")
    static TreeFeatureConfig.Builder getTallBirch() {
        throw new AssertionError();
    }
}
