package io.github.vampirestudios.gadget.core.OSLayouts;

import io.github.vampirestudios.gadget.api.AppInfo;
import io.github.vampirestudios.gadget.api.ApplicationManager;
import io.github.vampirestudios.gadget.api.app.Layout;
import io.github.vampirestudios.gadget.api.app.component.Button;
import io.github.vampirestudios.gadget.api.app.emojie_packs.Icons;
import io.github.vampirestudios.gadget.core.BaseDevice;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

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
            AppInfo info = ApplicationManager.getApplication("hgm:app_store");
            if(info != null)
            {
                BaseDevice.getSystem().openApplication(info);
            }
        });
        this.addComponent(btnStore);

        Button btnSettings = new Button(5, 49, 82, 20, "Settings", Icons.HAMMER);
        btnSettings.setToolTip("Settings", "Allows you to change things on the computer");
        btnSettings.setClickListener((mouseX, mouseY, mouseButton) -> {
            AppInfo info = ApplicationManager.getApplication("hgm:settings");
            if(info != null)
            {
                BaseDevice.getSystem().openApplication(info);
            }
        });
        this.addComponent(btnSettings);

        Button btnFileBrowser = new Button(5, 71, 82, 20, "File Browser", Icons.FOLDER);
        btnFileBrowser.setToolTip("File Browser", "Allows you to browse your files");
        btnFileBrowser.setClickListener((mouseX, mouseY, mouseButton) -> {
            AppInfo info = ApplicationManager.getApplication("hgm:file_browser");
            if(info != null)
            {
                BaseDevice.getSystem().openApplication(info);
            }
        });
        this.addComponent(btnFileBrowser);
    }

}
