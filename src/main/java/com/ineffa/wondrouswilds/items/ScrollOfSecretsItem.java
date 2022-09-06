package com.ineffa.wondrouswilds.items;

import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ScrollOfSecretsItem extends Item {

    protected final Set<Item> itemsToUnlock = new HashSet<>();
    protected final Map<Item, Identifier> itemIds = new HashMap<>();

    public ScrollOfSecretsItem(Settings settings, Item... itemsToUnlock) {
        super(settings);

        for (Item item : itemsToUnlock) {
            if (!this.itemsToUnlock.add(item)) continue;
            this.itemIds.put(item, Registry.ITEM.getId(item));
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient()) {
            boolean hasAllRecipes = true;
            ArrayList<Recipe<?>> recipesToUnlock = new ArrayList<>();

            RecipeManager recipeManager = Objects.requireNonNull(world.getServer()).getRecipeManager();
            for (Item itemToUnlock : this.itemsToUnlock) {
                Identifier itemToUnlockId = this.itemIds.get(itemToUnlock);

                if (user instanceof ServerPlayerEntity serverPlayer && serverPlayer.getRecipeBook().contains(itemToUnlockId)) continue;
                else if (hasAllRecipes) hasAllRecipes = false;

                Optional<? extends Recipe<?>> recipe = recipeManager.get(itemToUnlockId);
                if (recipe.isPresent()) recipesToUnlock.add(recipe.get());

                else WondrousWilds.LOGGER.error("Scroll of Secrets failed to find recipe for " + itemToUnlockId.toString());
            }

            if (hasAllRecipes) return TypedActionResult.fail(itemStack);

            if (!user.getAbilities().creativeMode) user.setStackInHand(hand, ItemStack.EMPTY);

            user.incrementStat(Stats.USED.getOrCreateStat(this));

            world.syncWorldEvent(WorldEvents.LECTERN_BOOK_PAGE_TURNED, user.getBlockPos(), 0);

            user.unlockRecipes(recipesToUnlock);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        for (Item item : this.itemsToUnlock) tooltip.add(item.getName().copy().formatted(Formatting.GRAY));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
