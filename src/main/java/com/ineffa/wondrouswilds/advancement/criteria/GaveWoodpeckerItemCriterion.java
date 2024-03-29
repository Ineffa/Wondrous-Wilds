package com.ineffa.wondrouswilds.advancement.criteria;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class GaveWoodpeckerItemCriterion extends AbstractCriterion<GaveWoodpeckerItemCriterion.Conditions> {

    private static final Identifier ID = new Identifier(WondrousWilds.MOD_ID, "gave_woodpecker_item");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(playerPredicate, ItemPredicate.fromJson(obj.get("item")));
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack) {
        this.trigger(player, conditions -> conditions.matches(stack));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final ItemPredicate itemPredicate;

        public Conditions(EntityPredicate.Extended player, ItemPredicate itemPredicate) {
            super(ID, player);
            this.itemPredicate = itemPredicate;
        }

        public static Conditions any() {
            return new Conditions(EntityPredicate.Extended.EMPTY, ItemPredicate.ANY);
        }

        public static Conditions predicate(ItemPredicate predicate) {
            return new Conditions(EntityPredicate.Extended.EMPTY, predicate);
        }

        public static Conditions item(ItemConvertible item) {
            return new Conditions(EntityPredicate.Extended.EMPTY, new ItemPredicate(null, ImmutableSet.of(item.asItem()), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY));
        }

        public boolean matches(ItemStack stack) {
            return this.itemPredicate.test(stack);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("item", this.itemPredicate.toJson());
            return jsonObject;
        }
    }
}
