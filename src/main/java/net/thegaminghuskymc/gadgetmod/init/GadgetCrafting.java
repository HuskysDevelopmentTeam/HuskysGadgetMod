package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.item.EnumDyeColor;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeCutPaper;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeLaptop;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeMotherboard;

public class GadgetCrafting {

    public static void register() {
        RegistrationHandler.Recipes.add(new RecipeCutPaper());
        RegistrationHandler.Recipes.add(new RecipeMotherboard());
        for(EnumDyeColor color : EnumDyeColor.values()) {
            RegistrationHandler.Recipes.add(new RecipeLaptop(GadgetBlocks.laptops[color.getMetadata()].color.getName()));
        }
    }

}
