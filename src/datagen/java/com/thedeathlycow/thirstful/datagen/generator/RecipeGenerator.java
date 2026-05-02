package com.thedeathlycow.thirstful.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        cookRecipes(
                exporter,
                "smelting",
                RecipeSerializer.SMELTING_RECIPE,
                SmeltingRecipe::new,
                200
        );

        cookRecipes(
                exporter,
                "smoking",
                RecipeSerializer.SMOKING_RECIPE,
                SmokingRecipe::new,
                100
        );

        cookRecipes(
                exporter,
                "campfire_cooking",
                RecipeSerializer.CAMPFIRE_COOKING_RECIPE,
                CampfireCookingRecipe::new,
                600
        );
    }

    public static <T extends AbstractCookingRecipe> void cookRecipes(
            RecipeOutput recipeOutput,
            String cookingMethod,
            RecipeSerializer<T> cookingSerializer,
            AbstractCookingRecipe.Factory<T> recipeFactory,
            int cookingTime
    ) {
        simpleCookingRecipe(
                recipeOutput,
                cookingMethod,
                cookingSerializer,
                recipeFactory,
                cookingTime,
                Items.POTION,
                Items.POTION,
                0.35f
        );
    }
}