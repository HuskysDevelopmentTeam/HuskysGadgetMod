package net.thegaminghuskymc.gadgetmod.programs.webBrowser.module;

import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.programs.webBrowser.component.PixelBrowserFrame;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public abstract class Module {
    public abstract String[] getRequiredData();

    public abstract int calculateHeight(Map<String, String> data, int width);

    public abstract void generate(PixelBrowserFrame frame, Layout layout, int width, Map<String, String> data);

    //TODO: nav module, footer module, slideshow module, text area syntax highlighting
}