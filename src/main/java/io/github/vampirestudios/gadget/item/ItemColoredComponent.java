package io.github.vampirestudios.gadget.item;

import io.github.vampirestudios.gadget.HuskyGadgetMod;
import net.minecraft.item.EnumDyeColor;

public class ItemColoredComponent extends ItemColored {

    public ItemColoredComponent(String componentName, EnumDyeColor color) {
        super(componentName, color);
        setCreativeTab(HuskyGadgetMod.deviceItems);
    }

}
