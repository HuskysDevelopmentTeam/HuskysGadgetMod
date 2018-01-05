package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.minecraft.client.gui.Gui;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.core.Laptop;

import java.awt.*;

public class LayoutStartMenu extends Layout {

    public LayoutStartMenu() {
        super(19, 30, 80, 100);
    }

    @Override
    public void init() {
        this.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Color color = new Color(Laptop.getSystem().getSettings().getColourScheme().getItemBackgroundColour());
            Gui.drawRect(x, y, x + width, y + 100, color.getRGB());
        });

        Button btnPowerOff = new Button(5, 5, Icons.POWER_OFF);
        btnPowerOff.setToolTip("Power Off", "This will turn off the computer");
        btnPowerOff.setClickListener((mouseX, mouseY, mouseButton) -> HuskyGadgetMod.getLogger().info("You have powered of this laptop"));
        this.addComponent(btnPowerOff);

    }

}
