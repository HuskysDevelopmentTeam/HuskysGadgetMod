package net.thegaminghuskymc.gadgetmod.init;

import net.thegaminghuskymc.gadgetmod.recipe.RecipeCutPaper;

public class GadgetCrafting
{
	public static void register()
	{
		RegistrationHandler.Recipes.add(new RecipeCutPaper());
	}
}
