package com.ineffa.wondrouswilds.advancement.criteria;

import com.google.gson.JsonObject;
import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SharpshotKillCriterion extends AbstractCriterion<SharpshotKillCriterion.Conditions> {

    private static final Identifier ID = new Identifier(WondrousWilds.MOD_ID, "sharpshot_kill");

    @Override
    protected SharpshotKillCriterion.Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        EntityPredicate.Extended predicate = EntityPredicate.Extended.getInJson(obj, "projectile", predicateDeserializer);
        return new SharpshotKillCriterion.Conditions(playerPredicate, predicate);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, ProjectileEntity projectile) {
        LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, projectile);
        this.trigger(player, conditions -> conditions.matches(lootContext));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final EntityPredicate.Extended projectilePredicate;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended projectilePredicate) {
            super(ID, player);
            this.projectilePredicate = projectilePredicate;
        }

        public static SharpshotKillCriterion.Conditions any() {
            return new SharpshotKillCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
        }

        public static SharpshotKillCriterion.Conditions predicate(EntityPredicate.Extended predicate) {
            return new SharpshotKillCriterion.Conditions(EntityPredicate.Extended.EMPTY, predicate);
        }

        public boolean matches(LootContext context) {
            return this.projectilePredicate.test(context);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("projectile", this.projectilePredicate.toJson(predicateSerializer));
            return jsonObject;
        }
    }
}
