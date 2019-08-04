package io.github.vampirestudios.gadget.core.OSLayouts;

import io.github.vampirestudios.gadget.api.app.Layout;
import io.github.vampirestudios.gadget.api.app.component.Button;
import io.github.vampirestudios.gadget.core.BaseDevice;
import io.github.vampirestudios.gadget.core.Laptop;
import io.github.vampirestudios.gadget.core.operation_systems.CraftOS;
import io.github.vampirestudios.gadget.core.operation_systems.NeonOS;
import io.github.vampirestudios.gadget.core.operation_systems.PixelOS;

public class LayoutOSSelect extends Layout {

    public LayoutOSSelect() {
        super(0, 18, BaseDevice.SCREEN_WIDTH, BaseDevice.SCREEN_HEIGHT);
    }

    @Override
    public void init() {
        Button btnSelectNeonOS = new Button(30, 40, "NeonOS");
        btnSelectNeonOS.setClickListener((mouseX, mouseY, mouseButton) -> BaseDevice.getSystem().getSettings().setOS(new NeonOS(Laptop.taskBar)));
        this.addComponent(btnSelectNeonOS);

        Button btnSelectCraftOS = new Button(60, 40, "CraftOS");
        btnSelectCraftOS.setClickListener((mouseX, mouseY, mouseButton) -> BaseDevice.getSystem().getSettings().setOS(new CraftOS(Laptop.taskBar)));
        this.addComponent(btnSelectCraftOS);

        Button btnSelectPixelOS = new Button(90, 40, "PixelOS");
        btnSelectPixelOS.setClickListener((mouseX, mouseY, mouseButton) -> BaseDevice.getSystem().getSettings().setOS(new PixelOS(Laptop.taskBar)));
        this.addComponent(btnSelectPixelOS);
    }

}
