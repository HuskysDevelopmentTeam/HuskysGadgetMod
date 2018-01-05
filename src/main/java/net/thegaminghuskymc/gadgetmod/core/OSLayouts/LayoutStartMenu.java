package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.core.Laptop;

import java.awt.*;

public class LayoutStartMenu extends Layout {

    public LayoutStartMenu(int left, int top) {
        super(left, top, 80, 100);
    }

    @Override
    public void init() {
        this.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Color color = new Color(Laptop.getSystem().getSettings().getColourScheme().getHeaderColour());
            Gui.drawRect(x, y + 40, x + width, y + 41, color.brighter().getRGB());
            Gui.drawRect(x, y + 41, x + width, y + 60, color.getRGB());
            Gui.drawRect(x, y + 60, x + width, y + 61, color.darker().getRGB());
        });
    }

    @Override
    public void renderOverlay(Laptop laptop, Minecraft mc, int mouseX, int mouseY, boolean windowActive) {
        super.renderOverlay(laptop, mc, mouseX, mouseY, windowActive);
    }

}
