package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.enums.EnumStorage;

public class ItemRamSticks extends Item implements SubItems{

    public ItemRamSticks() {
        this.setUnlocalizedName("ram_stick");
        this.setRegistryName(Reference.MOD_ID, "ram_stick");
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
            for(EnumStorage color : EnumStorage.values())
            {
                items.add(new ItemStack(this, 1, color.getID()));
            }
        }
    }

    @Override
    public NonNullList<ResourceLocation> getModels()
    {
        NonNullList<ResourceLocation> modelLocations = NonNullList.create();
        for(EnumStorage color : EnumStorage.values())
        {
            modelLocations.add(new ResourceLocation(getRegistryName() + "/" + color.getName()));
        }
        return modelLocations;
    }

}
