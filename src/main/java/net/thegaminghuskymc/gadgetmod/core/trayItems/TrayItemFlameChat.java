package net.thegaminghuskymc.gadgetmod.core.trayItems;

import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.object.TrayItem;

public class TrayItemFlameChat extends TrayItem {

    private static Layout layout = TrayItemUtils.createMenu(100, 100);

    public TrayItemFlameChat() {
        super(Icons.ONLINE);
    }

    @Override
    public void init() {
        this.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (BaseDevice.getSystem().hasContext()) {
                BaseDevice.getSystem().closeContext();
            } else {
                BaseDevice.getSystem().openContext(layout, layout.width + 100, layout.height - 80);
            }
        });
    }

}