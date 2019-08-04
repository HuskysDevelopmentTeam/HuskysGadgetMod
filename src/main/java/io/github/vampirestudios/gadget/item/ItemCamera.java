package io.github.vampirestudios.gadget.item;

import net.minecraft.item.Item;
import io.github.vampirestudios.gadget.HuskyGadgetMod;
import io.github.vampirestudios.gadget.Reference;

public class ItemCamera extends Item {

    public ItemCamera() {
        this.setTranslationKey("camera");
        this.setRegistryName(Reference.MOD_ID, "camera");
        this.setCreativeTab(HuskyGadgetMod.deviceItems);
    }
}
