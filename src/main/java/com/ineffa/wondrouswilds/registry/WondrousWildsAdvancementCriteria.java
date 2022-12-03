package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.advancement.criteria.FireflyLandedOnHeadCriterion;
import com.ineffa.wondrouswilds.advancement.criteria.GaveWoodpeckerItemCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.OnKilledCriterion;
import net.minecraft.util.Identifier;

public class WondrousWildsAdvancementCriteria {

    public static final FireflyLandedOnHeadCriterion FIREFLY_LANDED_ON_HEAD = Criteria.register(new FireflyLandedOnHeadCriterion());
    public static final GaveWoodpeckerItemCriterion GAVE_WOODPECKER_ITEM = Criteria.register(new GaveWoodpeckerItemCriterion());
    public static final OnKilledCriterion SHARPSHOT_KILL = Criteria.register(new OnKilledCriterion(new Identifier(WondrousWilds.MOD_ID, "sharpshot_kill")));

    public static void initialize() {}
}
