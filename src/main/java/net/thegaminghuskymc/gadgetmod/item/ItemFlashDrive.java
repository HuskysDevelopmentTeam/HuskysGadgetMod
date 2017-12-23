package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;

/**
 * Author: MrCrayfish
 */
public class ItemFlashDrive extends Item implements SubItems
{
    public ItemFlashDrive()
    {
        this.setUnlocalizedName("flash_drive");
        this.setRegistryName("flash_drive");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if(isInCreativeTab(tab))
        {
            for(EnumDyeColor color : EnumDyeColor.values())
            {
                items.add(new ItemStack(this, 1, color.getMetadata()));
            }
        }
    }

    @Override
    public NonNullList<ResourceLocation> getModels()
    {
        NonNullList<ResourceLocation> modelLocations = NonNullList.create();
        for(EnumDyeColor color : EnumDyeColor.values())
        {
            modelLocations.add(new ResourceLocation(getRegistryName() + "/" + color.getName()));
        }
        return modelLocations;
    }
}
