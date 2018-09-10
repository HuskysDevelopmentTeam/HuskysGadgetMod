package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.core.operation_systems.CraftOS;
import net.thegaminghuskymc.gadgetmod.core.operation_systems.NeonOS;
import net.thegaminghuskymc.gadgetmod.core.operation_systems.PixelOS;

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
