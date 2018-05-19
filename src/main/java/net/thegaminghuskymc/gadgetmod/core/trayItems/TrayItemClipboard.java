package net.thegaminghuskymc.gadgetmod.core.trayItems;

import net.minecraft.client.gui.Gui;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.object.TrayItem;

import java.awt.*;

public class TrayItemClipboard extends TrayItem {
    public static TrayItem trayItem;

    public TrayItemClipboard() {
        super(Icons.CLIPBOARD);
    }

    private static Layout createWifiMenu(TrayItem item) {
        trayItem = item;

        Layout layout = new Layout.Context(100, 100);
        layout.yPosition = 70;
        layout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
                Gui.drawRect(x, y, x + width, y + height, new Color(0.65F, 0.65F, 0.65F, 0.9F).getRGB()));

        return layout;
    }

    @Override
    public void init() {
        this.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (BaseDevice.getSystem().hasContext()) {
                BaseDevice.getSystem().closeContext();
            } else {
                BaseDevice.getSystem().openContext(createWifiMenu(this), mouseX - 100, mouseY - 80);
            }
        });
    }

}