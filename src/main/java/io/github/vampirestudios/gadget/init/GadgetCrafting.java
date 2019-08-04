package io.github.vampirestudios.gadget.init;

import net.minecraft.item.EnumDyeColor;
import io.github.vampirestudios.gadget.recipe.RecipeCutPaper;
import io.github.vampirestudios.gadget.recipe.RecipeLaptop;
import io.github.vampirestudios.gadget.recipe.RecipeMotherboard;

public class GadgetCrafting {

    public static void register() {
        RegistrationHandler.Recipes.add(new RecipeCutPaper());
        RegistrationHandler.Recipes.add(new RecipeMotherboard());
        for(EnumDyeColor color : EnumDyeColor.values()) {
            RegistrationHandler.Recipes.add(new RecipeLaptop(GadgetBlocks.laptops[color.getMetadata()].color.getName()));
        }
    }

}
