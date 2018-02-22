package net.thegaminghuskymc.gadgetmod.init;

import net.thegaminghuskymc.gadgetmod.recipe.RecipeCutPaper;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeLaptop;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeMotherboard;

public class GadgetCrafting {
    public static void register() {
        RegistrationHandler.Recipes.add(new RecipeCutPaper());
        RegistrationHandler.Recipes.add(new RecipeLaptop());
        RegistrationHandler.Recipes.add(new RecipeMotherboard());
    }
}
