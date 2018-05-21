package net.thegaminghuskymc.gadgetmod.core.trayItems;

import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.object.TrayItem;

public class TrayItemSound extends TrayItem {

    private static Layout layout = TrayItemUtils.createMenu(100, 100);

    public TrayItemSound() {
        super(Icons.VOLUME_OFF);
    }

    @Override
    public void init() {
        this.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (BaseDevice.getSystem().hasContext()) {
                BaseDevice.getSystem().closeContext();
            } else {
                BaseDevice.getSystem().openContext(layout, layout.width - 100, layout.height - 80);
            }
        });
    }

}