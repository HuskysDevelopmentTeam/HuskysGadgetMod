package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;

public class LayoutOSSelect extends Layout {

    public LayoutOSSelect() {
        super(0, 18, BaseDevice.SCREEN_WIDTH, BaseDevice.SCREEN_HEIGHT);
    }

    @Override
    public void init() {
        Button btnSelectNeonOS = new Button(30, 40, "NeonOS");
        this.addComponent(btnSelectNeonOS);

        Button btnSelectCraftOS = new Button(60, 40, "CraftOS");
        this.addComponent(btnSelectCraftOS);

        Button btnSelectPixelOS = new Button(90, 40, "PixelOS");
        this.addComponent(btnSelectPixelOS);
    }

}
