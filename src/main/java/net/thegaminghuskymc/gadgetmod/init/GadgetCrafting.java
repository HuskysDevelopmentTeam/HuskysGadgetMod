package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeCutPaper;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeLaptop;

public class GadgetCrafting {
    public static void register() {
        RegistrationHandler.Recipes.add(new RecipeCutPaper());
        RegistrationHandler.Recipes.add(new RecipeLaptop());

        for(EnumDyeColor color : EnumDyeColor.values()) {
            GameRegistry.addShapedRecipe(new ResourceLocation("laptop_" + color.getDyeColorName()), new ResourceLocation("laptops"), new ItemStack(GadgetBlocks.LAPTOP, 1, color.getMetadata()), "XXX", "YYY", "ZZZ", 'X', Items.REDSTONE, 'Y', Items.GLOWSTONE_DUST, 'Z', Items.IRON_INGOT);
        }
    }
}
