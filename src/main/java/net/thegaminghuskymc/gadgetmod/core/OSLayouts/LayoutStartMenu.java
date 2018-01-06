package net.thegaminghuskymc.gadgetmod.core.OSLayouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.init.GadgetApps;

import java.awt.*;
import java.lang.reflect.Field;

public class LayoutStartMenu extends Layout {

    public LayoutStartMenu() {
        super(-3, 15, 80, 100);
    }

    @Override
    public void init() {
        this.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            //Color color = new Color(Laptop.getSystem().getSettings().getColourScheme().getItemBackgroundColour());
            Gui.drawRect(x, y, x + width, y + 100, /*color.getRGB()*/ Laptop.getSystem().getSettings().getColourScheme().getItemBackgroundColour());
        });

        Button btnPowerOff = new Button(5, 5, "Shutdown", Icons.POWER_OFF);
        btnPowerOff.setToolTip("Power Off", "This will turn off the computer");
        btnPowerOff.setClickListener((mouseX, mouseY, mouseButton) -> {
        	Laptop laptop = (Laptop) Minecraft.getMinecraft().currentScreen;
        	laptop.closeContext();
        	laptop.shutdown();
        });
        this.addComponent(btnPowerOff);
        
        Button btnStore = new Button(5, 30, 69, 20, "Store", Icons.SHOPPING_CART);
        btnStore.setToolTip("App store", "Allows you to install apps");
        btnStore.setClickListener((mouseX, mouseY, mouseButton) -> {
        	Laptop laptop = (Laptop) Minecraft.getMinecraft().currentScreen;
        	laptop.open(laptop.getApplication(Reference.MOD_ID + ".app_store"));
        	laptop.closeContext();
        });
        this.addComponent(btnStore);
    }

}
