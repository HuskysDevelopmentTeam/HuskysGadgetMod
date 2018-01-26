package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeCutPaper;
import net.thegaminghuskymc.gadgetmod.recipe.RecipeLaptop;

public class GadgetCrafting {
    public static void register() {
        RegistrationHandler.Recipes.add(new RecipeCutPaper());

        GameRegistry.addShapedRecipe(new ResourceLocation(GadgetBlocks.LAPTOP.getRegistryName().toString()),
                new ResourceLocation(Reference.MOD_ID, "laptops"), new ItemStack(GadgetBlocks.LAPTOP, 1, 0),
                "ISI", "IRI", "IBI", 'I', Items.IRON_INGOT, 'S', GadgetBlocks.SCREEN, 'R', Items.REDSTONE, 'B', Item.getItemFromBlock(Blocks.IRON_BLOCK));

        GameRegistry.addShapedRecipe(new ResourceLocation(GadgetBlocks.PLAYSTATION_4_PRO.getRegistryName().toString()),
                new ResourceLocation(Reference.MOD_ID, "playstations"), new ItemStack(GadgetBlocks.PLAYSTATION_4_PRO, 1, 0),
                "ISI", "IRI", "IBI", 'I', Items.IRON_INGOT, 'S', GadgetBlocks.SCREEN, 'R', Items.REDSTONE, 'B', Item.getItemFromBlock(Blocks.IRON_BLOCK));

        GameRegistry.addShapedRecipe(new ResourceLocation(GadgetBlocks.BENCHMARK_STATION.getRegistryName().toString()),
                new ResourceLocation(Reference.MOD_ID, "benchmark_stations"), new ItemStack(GadgetBlocks.BENCHMARK_STATION, 1),
                "ISI", "IRI", "IBI", 'I', Items.IRON_INGOT, 'S', GadgetBlocks.SCREEN, 'R', Items.REDSTONE, 'B', Item.getItemFromBlock(Blocks.IRON_BLOCK));

        GameRegistry.addShapedRecipe(new ResourceLocation(GadgetBlocks.DRAWING_TABLET.getRegistryName().toString()),
                new ResourceLocation(Reference.MOD_ID, "drawing_tablets"), new ItemStack(GadgetBlocks.DRAWING_TABLET, 1),
                "ISI", "IRI", "IBI", 'I', Items.IRON_INGOT, 'S', GadgetBlocks.SCREEN, 'R', Items.REDSTONE, 'B', Item.getItemFromBlock(Blocks.IRON_BLOCK));

        GameRegistry.addShapedRecipe(new ResourceLocation(GadgetBlocks.GAMING_DESK.getRegistryName().toString()),
                new ResourceLocation(Reference.MOD_ID, "gaming_desks"), new ItemStack(GadgetBlocks.GAMING_DESK, 1),
                "ISI", "IRI", "IBI", 'I', Items.IRON_INGOT, 'S', GadgetBlocks.SCREEN, 'R', Items.REDSTONE, 'B', Item.getItemFromBlock(Blocks.IRON_BLOCK));

        GameRegistry.addShapedRecipe(new ResourceLocation(GadgetBlocks.ROUTER.getRegistryName().toString()),
                new ResourceLocation(Reference.MOD_ID, "routers"), new ItemStack(GadgetBlocks.ROUTER, 1),
                "ISI", "IRI", "IBI", 'I', Items.IRON_INGOT, 'S', GadgetBlocks.SCREEN, 'R', Items.REDSTONE, 'B', Item.getItemFromBlock(Blocks.IRON_BLOCK));

    }
}
