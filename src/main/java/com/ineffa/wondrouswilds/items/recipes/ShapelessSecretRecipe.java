package com.ineffa.wondrouswilds.items.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class ShapelessSecretRecipe extends ShapelessRecipe implements SecretRecipe {

    public ShapelessSecretRecipe(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input) {
        super(id, group, output, input);
    }

    public static class Serializer implements RecipeSerializer<ShapelessSecretRecipe> {

        @Override
        public ShapelessSecretRecipe read(Identifier id, JsonObject json) {
            DefaultedList<Ingredient> defaultedList = Serializer.getIngredients(JsonHelper.getArray(json, "ingredients"));

            if (defaultedList.isEmpty()) throw new JsonParseException("No ingredients for shapeless secret recipe");
            if (defaultedList.size() > 9) throw new JsonParseException("Too many ingredients for shapeless secret recipe");

            String string = JsonHelper.getString(json, "group", "");
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            return new ShapelessSecretRecipe(id, string, itemStack, defaultedList);
        }

        @Override
        public ShapelessSecretRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            for (int j = 0; j < defaultedList.size(); ++j) defaultedList.set(j, Ingredient.fromPacket(buf));

            ItemStack itemStack = buf.readItemStack();
            return new ShapelessSecretRecipe(id, buf.readString(), itemStack, defaultedList);
        }

        @Override
        public void write(PacketByteBuf buf, ShapelessSecretRecipe recipe) {
            buf.writeString(recipe.getGroup());
            buf.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) ingredient.write(buf);

            buf.writeItemStack(recipe.getOutput());
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();
            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (ingredient.isEmpty()) continue;
                defaultedList.add(ingredient);
            }
            return defaultedList;
        }
    }
}
