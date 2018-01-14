package net.thegaminghuskymc.gadgetmod.core;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class Server extends GuiContainer {

    public Server(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
