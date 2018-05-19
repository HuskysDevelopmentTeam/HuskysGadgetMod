package net.thegaminghuskymc.gadgetmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.thegaminghuskymc.gadgetmod.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GadgetGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new MBEConfigGui(parentScreen);
    }

    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    public static class MBEConfigGui extends GuiConfig {
        public MBEConfigGui(GuiScreen parentScreen) {
            super(parentScreen, getConfigElements(), Reference.MOD_ID,
                    false, false, I18n.format("gui.gadget_mod.mainTitle"));
        }

        private static List<IConfigElement> getConfigElements() {
            List<IConfigElement> list = new ArrayList<>();
            list.add(new DummyConfigElement.DummyCategoryElement("mainCfg", "gui.gadget_mod.general", CategoryEntryGeneral.class));
            list.add(new DummyConfigElement.DummyCategoryElement("miscCfg", "gui.gadget_mod.other", CategoryEntryOther.class));
            return list;
        }

        public static class CategoryEntryGeneral extends GuiConfigEntries.CategoryEntry {
            public CategoryEntryGeneral(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
                super(owningScreen, owningEntryList, prop);
            }

            @Override
            protected GuiScreen buildChildScreen() {
                Configuration configuration = GadgetConfig.getConfig();
                ConfigElement cat_general = new ConfigElement(configuration.getCategory(GadgetConfig.CATEGORY_NAME_GENERAL));
                List<IConfigElement> propertiesOnThisScreen = cat_general.getChildElements();
                String windowTitle = configuration.toString();

                return new GuiConfig(this.owningScreen, propertiesOnThisScreen,
                        this.owningScreen.modID,
                        GadgetConfig.CATEGORY_NAME_GENERAL,
                        this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
                        this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
                        windowTitle);
            }
        }

        public static class CategoryEntryOther extends GuiConfigEntries.CategoryEntry {
            public CategoryEntryOther(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
                super(owningScreen, owningEntryList, prop);
            }

            @Override
            protected GuiScreen buildChildScreen() {
                Configuration configuration = GadgetConfig.getConfig();
                ConfigElement cat_general = new ConfigElement(configuration.getCategory(GadgetConfig.CATEGORY_NAME_OTHER));
                List<IConfigElement> propertiesOnThisScreen = cat_general.getChildElements();
                String windowTitle = configuration.toString();

                return new GuiConfig(this.owningScreen, propertiesOnThisScreen,
                        this.owningScreen.modID,
                        GadgetConfig.CATEGORY_NAME_OTHER,
                        this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
                        this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
                        windowTitle);
            }
        }
    }
}