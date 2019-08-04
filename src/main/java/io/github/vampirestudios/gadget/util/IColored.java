package io.github.vampirestudios.gadget.util;

import net.minecraft.item.EnumDyeColor;

public interface IColored {
    EnumDyeColor getColor();

    void setColor(EnumDyeColor color);
}