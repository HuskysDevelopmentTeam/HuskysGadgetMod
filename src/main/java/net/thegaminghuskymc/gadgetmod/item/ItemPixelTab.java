package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.enums.EnumPhoneColours;

public class ItemPixelTab extends Item implements SubItems {

    public ItemPixelTab() {
        this.setTranslationKey("pixel_tab");
        this.setRegistryName(Reference.MOD_ID, "pixel_tab");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (EnumPhoneColours color : EnumPhoneColours.values()) {
                items.add(new ItemStack(this, 1, color.getID()));
            }
        }
    }

    @Override
    public NonNullList<ResourceLocation> getModels() {
        NonNullList<ResourceLocation> modelLocations = NonNullList.create();
        for (EnumPhoneColours color : EnumPhoneColours.values()) {
            modelLocations.add(new ResourceLocation(getRegistryName() + "/" + color.getName()));
        }
        return modelLocations;
    }

    /*@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        EnumPhoneColours color = EnumPhoneColours.byMetadata(stack.getMetadata());
        String colorName = color.getName();
        colorName = WordUtils.capitalize(colorName);
        tooltip.add("Color: " + TextFormatting.BOLD.toString() + getFromColor(color).toString() + colorName);
    }

    private static TextFormatting getFromColor(EnumPhoneColours color)
    {
        switch(color)
        {
            case WHITE: return TextFormatting.WHITE;
            case SILVER: return TextFormatting.GRAY;
            case BLACK: return TextFormatting.BLACK;
            default: return TextFormatting.WHITE;
        }
    }*/

}
