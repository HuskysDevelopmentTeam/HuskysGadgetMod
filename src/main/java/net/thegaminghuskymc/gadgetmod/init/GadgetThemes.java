package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.api.ThemeManager;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

public class GadgetThemes {

    public static void init() {
        ThemeManager.registerApplication(new ResourceLocation(MOD_ID, "test_theme"));
    }

}
