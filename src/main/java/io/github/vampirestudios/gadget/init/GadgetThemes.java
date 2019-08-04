package io.github.vampirestudios.gadget.init;

import net.minecraft.util.ResourceLocation;
import io.github.vampirestudios.gadget.api.ThemeManager;

import static io.github.vampirestudios.gadget.Reference.MOD_ID;

public class GadgetThemes {

    public static void init() {
        ThemeManager.registerApplication(new ResourceLocation(MOD_ID, "test_theme"));
    }

}
