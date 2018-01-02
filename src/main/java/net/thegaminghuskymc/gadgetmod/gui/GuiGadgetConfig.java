package net.thegaminghuskymc.gadgetmod.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.thegaminghuskymc.gadgetmod.handler.ConfigurationHandler;

import java.util.ArrayList;
import java.util.List;

public class GuiGadgetConfig extends GuiConfig {
    public GuiGadgetConfig(GuiScreen parent) {
        super(parent, getConfigElements(), "hgm", false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }

    public static List<IConfigElement> getConfigElements() {
        List<IConfigElement> configs = new ArrayList<IConfigElement>();
        configs.addAll(new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_SETTINGS)).getChildElements());
        return configs;
    }
}