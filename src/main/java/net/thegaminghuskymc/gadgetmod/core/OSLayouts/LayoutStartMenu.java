package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;

import java.awt.*;

public class LayoutStartMenu extends Layout {

    public LayoutStartMenu() {
        super(0, 18, 93, 120);
    }

    @Override
    public void init() {
        this.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Color color = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getItemBackgroundColour());
            Gui.drawRect(x, y, x + width, y + 100, color.getRGB());
//            Gui.drawRect(x, y, x + width, y + 100, color.darker().darker().getRGB());
//            Gui.drawRect(x, y, x + width, y + 100, color.brighter().brighter().getRGB());
        });

        Button btnPowerOff = new Button(5, 5, 82, 20, "Shutdown", Icons.POWER_OFF);
        btnPowerOff.setToolTip("Power Off", "This will turn off the computer");
        btnPowerOff.setClickListener((mouseX, mouseY, mouseButton) -> {
            BaseDevice laptop = (BaseDevice) Minecraft.getMinecraft().currentScreen;
            laptop.closeContext();
            laptop.shutdown();
        });
        this.addComponent(btnPowerOff);

        Button btnStore = new Button(5, 27, 82, 20, "App Market", Icons.SHOPPING_CART);
        btnStore.setToolTip("App Market", "Allows you to install apps");
        btnStore.setClickListener((mouseX, mouseY, mouseButton) -> {
            BaseDevice laptop = (BaseDevice) Minecraft.getMinecraft().currentScreen;
            laptop.openApplication(ApplicationManager.getApplication((Reference.MOD_ID + ":app_store")));
            laptop.closeContext();
        });
        this.addComponent(btnStore);

        Button btnSettings = new Button(5, 49, 82, 20, "Settings", Icons.HAMMER);
        btnSettings.setToolTip("Settings", "Allows you to change things on the computer");
        btnSettings.setClickListener((mouseX, mouseY, mouseButton) -> {
            BaseDevice laptop = (BaseDevice) Minecraft.getMinecraft().currentScreen;
            laptop.openApplication(ApplicationManager.getApplication((Reference.MOD_ID + ":settings")));
            laptop.closeContext();
        });
        this.addComponent(btnSettings);

        Button btnFileBrowser = new Button(5, 71, 82, 20, "File Browser", Icons.FOLDER);
        btnFileBrowser.setToolTip("File Browser", "Allows you to browse your files");
        btnFileBrowser.setClickListener((mouseX, mouseY, mouseButton) -> {
            BaseDevice laptop = (BaseDevice) Minecraft.getMinecraft().currentScreen;
            laptop.openApplication(laptop.getApplication(Reference.MOD_ID + ".file_browser").getInfo());
            laptop.closeContext();
        });
        this.addComponent(btnFileBrowser);
    }

}
