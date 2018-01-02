package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ItemFlashDrive extends Item implements SubItems {

    public ItemFlashDrive() {
        this.setUnlocalizedName("flash_drive");
        this.setRegistryName(Reference.MOD_ID, "flash_drive");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
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

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        for (EnumDyeColor color : EnumDyeColor.values()) {
            stack.getTagCompound().setString("color", color.getDyeColorName());
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (EnumDyeColor color : EnumDyeColor.values()) {
                items.add(new ItemStack(this, 1, color.getMetadata()));
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        EnumDyeColor color = EnumDyeColor.byMetadata(stack.getMetadata());
        String colorName = color.getName().replace("_", " ");
        colorName = WordUtils.capitalize(colorName);
        tooltip.add("Color: " + TextFormatting.BOLD.toString() + getFromColor(color).toString() + colorName);
    }

    @Override
    public NonNullList<ResourceLocation> getModels() {
        NonNullList<ResourceLocation> modelLocations = NonNullList.create();
        for (EnumDyeColor color : EnumDyeColor.values()) {
            modelLocations.add(new ResourceLocation(getRegistryName() + "/" + color.getName()));
        }
        return modelLocations;
    }
}
