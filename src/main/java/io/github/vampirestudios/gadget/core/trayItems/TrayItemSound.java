package io.github.vampirestudios.gadget.core.trayItems;

import io.github.vampirestudios.gadget.api.app.Layout;
import io.github.vampirestudios.gadget.api.app.emojie_packs.Icons;
import io.github.vampirestudios.gadget.core.BaseDevice;
import io.github.vampirestudios.gadget.object.TrayItem;

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