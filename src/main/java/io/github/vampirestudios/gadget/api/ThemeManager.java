package io.github.vampirestudios.gadget.api;

import net.minecraft.util.ResourceLocation;
import io.github.vampirestudios.gadget.HuskyGadgetMod;
import io.github.vampirestudios.gadget.api.theme.Theme;
import io.github.vampirestudios.gadget.object.ThemeInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThemeManager {

    public static final Map<ResourceLocation, ThemeInfo> THEME_INFO = new HashMap<>();

    /**
     * Registers an application into the application list
     * <p>
     * The identifier parameter is simply just an id for the application.
     * <p>
     * Example: {@code new ResourceLocation("modid:themeid");}
     *
     * @param identifier the
     */
    @Nullable
    public static Theme registerApplication(ResourceLocation identifier) {
        Theme application = HuskyGadgetMod.proxy.registerTheme(identifier);
        if (application != null) {
            THEME_INFO.put(identifier, application.getInfo());
            return application;
        }
        return application;
    }

    /**
     * Get all applications that are registered. Please note
     * that this list is read only and cannot be modified.
     *
     * @return the theme list
     */
    public static List<ThemeInfo> getAvailableThemes() {
        return new ArrayList<>(THEME_INFO.values());
    }

    public static List<ThemeInfo> getAllThemes() {
        return new ArrayList<>(THEME_INFO.values());
    }

    @Nullable
    public static ThemeInfo getApplication(String appId) {
        return THEME_INFO.get(new ResourceLocation(appId.replace(".", ":")));
    }

}
