package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeCutPaper;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeLaptop;

public class GadgetCrafting
{
	public static void register()
	{
		RegistrationHandler.Recipes.add(new RecipeCutPaper());
		RegistrationHandler.Recipes.add(new RecipeLaptop());
		for(EnumDyeColor colour : EnumDyeColor.values()) {
            GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID,"laptop" + colour.getDyeColorName()), new ResourceLocation("gadgets"), new ItemStack(GadgetBlocks.LAPTOP, 1, colour.getMetadata()), "XYX", "YXY", "XYX", 'X', Items.REDSTONE, 'Y', Items.IRON_INGOT);
        }
	}
}
