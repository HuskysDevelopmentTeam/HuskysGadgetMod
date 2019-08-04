package io.github.vampirestudios.gadget.item;

import io.github.vampirestudios.gadget.HuskyGadgetMod;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static io.github.vampirestudios.gadget.Reference.MOD_ID;

public class ItemComponent extends Item {

    public ItemComponent(String componentName) {
        setRegistryName(new ResourceLocation(MOD_ID, componentName));
        setCreativeTab(HuskyGadgetMod.deviceItems);
    }

    public static Item getComponentFromName(String name) {
        return Item.getByNameOrId(MOD_ID + ":" + name);
    }

}
