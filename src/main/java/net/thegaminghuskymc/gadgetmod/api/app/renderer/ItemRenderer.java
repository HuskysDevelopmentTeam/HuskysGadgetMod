package net.thegaminghuskymc.gadgetmod.api.app.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public abstract class ItemRenderer<E> {
    public abstract void render(E e, Gui gui, Minecraft mc, int x, int y, int width, int height);
}
