package net.thegaminghuskymc.gadgetmod.core.trayItems;

import net.minecraft.client.gui.Gui;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;

import java.awt.*;

public class TrayItemUtils {

    public static Layout createMenu(int menuWidth, int menuHeight) {
        Layout layout = new Layout.Context(menuWidth, menuHeight);
        layout.yPosition = 70;
        layout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
                Gui.drawRect(x, y, x + width, y + height, new Color(0.65F, 0.65F, 0.65F, 0.9F).getRGB()));
        return layout;
    }

}
