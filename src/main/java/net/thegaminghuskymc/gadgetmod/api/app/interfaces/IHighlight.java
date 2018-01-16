package net.thegaminghuskymc.gadgetmod.api.app.interfaces;

import net.minecraft.util.text.TextFormatting;

public interface IHighlight {
    TextFormatting[] getKeywordFormatting(String text);
}
