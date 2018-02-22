package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.util.ItemUtils;
import net.thegaminghuskymc.huskylib2.lib.items.ItemColored;

import java.util.Objects;

public class ItemFlashDrive extends ItemColored implements SubItems {

    public ItemFlashDrive(EnumDyeColor color) {
        super(Reference.MOD_ID, "flash_drive", color);
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }

    @Override
    public NonNullList<ResourceLocation> getModels() {
        NonNullList<ResourceLocation> modelLocations = NonNullList.create();
        modelLocations.add(new ResourceLocation(Reference.MOD_ID, "flash_drive/" + this.color.getName()));
        return modelLocations;
    }

    @Override
    public String getPrefix() {
        return Reference.MOD_ID;
    }
}
