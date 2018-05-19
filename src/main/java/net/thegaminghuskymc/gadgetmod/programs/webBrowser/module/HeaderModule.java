package net.thegaminghuskymc.gadgetmod.programs.webBrowser.module;

import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.programs.webBrowser.component.PixelBrowserFrame;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class HeaderModule extends Module
{
    @Override
    public String[] getRequiredData()
    {
        return new String[] { "text" };
    }

    @Override
    public int calculateHeight(Map<String, String> data, int width)
    {
        if(data.containsKey("scale"))
        {
            return Integer.parseInt(data.get("scale")) * BaseDevice.fontRenderer.FONT_HEIGHT + 10;
        }
        return BaseDevice.fontRenderer.FONT_HEIGHT + 10;
    }

    @Override
    public void generate(PixelBrowserFrame frame, Layout layout, int width, Map<String, String> data)
    {
        Label label = new Label(data.get("text"), width / 2, 5);
        label.setAlignment(Component.ALIGN_CENTER);

        int scale = 1;
        if(data.containsKey("scale"))
        {
            scale = Integer.parseInt(data.get("scale"));
        }
        label.setScale(scale);

        String align = data.getOrDefault("align", "center");
        if("left".equals(align))
        {
            label.left = 5;
            label.setAlignment(Component.ALIGN_LEFT);
        }
        else if("right".equals(align))
        {
            label.left = width - 5;
            label.setAlignment(Component.ALIGN_RIGHT);
        }

        layout.addComponent(label);
    }
}