package net.thegaminghuskymc.gadgetmod.item;

import net.minecraft.item.Item;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;

public class ItemWiiUGamepad extends Item implements SubItems {

    public ItemWiiUGamepad() {
        this.setRegistryName("wiiu_gamepad");
        this.setTranslationKey("wiiu_gamepad");
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public NonNullList<ResourceLocation> getModels() {
        return NonNullList.<ResourceLocation>from(new ResourceLocation("minecraft", "missingno"), new ResourceLocation(Reference.MOD_ID, "wiiu_gamepad/white"), new ResourceLocation(Reference.MOD_ID, "wiiu_gamepad/black"));
    }

}
