package io.github.vampirestudios.gadget.handler;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler {
    public static final String CATEGORY_SETTINGS = "settings";
    public static Configuration config;
    public static String[] items = {};
    public static boolean canDisplay = true;
    public static boolean hasDisplayedOnce = false;

    public static void init(File file) {
        if (config == null) {
            config = new Configuration(file);
            loadConfig(false);
        }
    }

    public static void loadConfig(boolean shouldChange) {
        canDisplay = config.getBoolean("welcome_message", CATEGORY_SETTINGS, canDisplay, "Enabled or disable the welcome message");

        config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.getModID().equals("hgm")) {
            loadConfig(true);
        }
    }
}
