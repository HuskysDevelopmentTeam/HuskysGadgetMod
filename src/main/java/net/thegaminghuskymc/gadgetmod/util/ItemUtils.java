package net.thegaminghuskymc.gadgetmod.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class ItemUtils {

    public static NonNullList<ResourceLocation> getModels(ResourceLocation registryName) {
        NonNullList<ResourceLocation> modelLocations = NonNullList.create();
        for (EnumDyeColor color : EnumDyeColor.values()) {
            modelLocations.add(new ResourceLocation(registryName + "/" + color.getName()));
        }
        return modelLocations;
    }

    public static void addInformation(ItemStack stack, List<String> tooltip) {
        EnumDyeColor color = EnumDyeColor.byMetadata(stack.getMetadata());
        String colorName = color.getName().replace("_", " ");
        colorName = WordUtils.capitalize(colorName);
        tooltip.add("Color: " + TextFormatting.BOLD.toString() + getFromColor(color).toString() + colorName);
    }

    private static TextFormatting getFromColor(EnumDyeColor color) {
        switch (color) {
            case ORANGE:
                return TextFormatting.GOLD;
            case MAGENTA:
                return TextFormatting.LIGHT_PURPLE;
            case LIGHT_BLUE:
                return TextFormatting.BLUE;
            case YELLOW:
                return TextFormatting.YELLOW;
            case LIME:
                return TextFormatting.GREEN;
            case PINK:
                return TextFormatting.LIGHT_PURPLE;
            case GRAY:
                return TextFormatting.DARK_GRAY;
            case SILVER:
                return TextFormatting.GRAY;
            case CYAN:
                return TextFormatting.DARK_AQUA;
            case PURPLE:
                return TextFormatting.DARK_PURPLE;
            case BLUE:
                return TextFormatting.DARK_BLUE;
            case BROWN:
                return TextFormatting.GOLD;
            case GREEN:
                return TextFormatting.DARK_GREEN;
            case RED:
                return TextFormatting.DARK_RED;
            case BLACK:
                return TextFormatting.BLACK;
            default:
                return TextFormatting.WHITE;
        }
    }

}
