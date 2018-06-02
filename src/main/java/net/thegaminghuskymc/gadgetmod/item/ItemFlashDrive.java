package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.init.RegistrationHandler;
import net.hdt.huskylib2.items.ItemColored;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

public class ItemFlashDrive extends ItemColored implements SubItems {

    public ItemFlashDrive(EnumDyeColor color) {
        super("flash_drive", MOD_ID, color);
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
        RegistrationHandler.Models.registerRender(this);
    }

    @Override
    public NonNullList<ResourceLocation> getModels() {
        NonNullList<ResourceLocation> modelLocations = NonNullList.create();
        modelLocations.add(new ResourceLocation(MOD_ID, "flash_drive/" + this.color.getName()));
        return modelLocations;
    }

}
