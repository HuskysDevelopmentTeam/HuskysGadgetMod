package io.github.vampirestudios.gadget.api.app.interfaces;

import net.minecraft.util.text.TextFormatting;

public interface IHighlight {
    TextFormatting[] getKeywordFormatting(String text);
}
