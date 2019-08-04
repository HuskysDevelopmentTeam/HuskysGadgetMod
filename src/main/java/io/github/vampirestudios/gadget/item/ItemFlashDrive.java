package io.github.vampirestudios.gadget.item;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import io.github.vampirestudios.gadget.HuskyGadgetMod;
import io.github.vampirestudios.gadget.init.RegistrationHandler;

import static io.github.vampirestudios.gadget.Reference.MOD_ID;

public class ItemFlashDrive extends ItemColored implements SubItems {

    public ItemFlashDrive(EnumDyeColor color) {
        super("flash_drive", color);
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
