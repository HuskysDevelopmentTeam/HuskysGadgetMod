package io.github.vampirestudios.gadget.programs.system.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import io.github.vampirestudios.gadget.api.app.Component;
import io.github.vampirestudios.gadget.api.app.IIcon;
import io.github.vampirestudios.gadget.api.app.Layout;
import io.github.vampirestudios.gadget.api.app.component.Button;
import io.github.vampirestudios.gadget.api.app.component.Image;
import io.github.vampirestudios.gadget.core.BaseDevice;
import io.github.vampirestudios.gadget.programs.system.ApplicationSettings;
import io.github.vampirestudios.gadget.util.GuiHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class SettingsGrid extends Component
{
    private int padding = 5;
    private int horizontalItems;
    private int verticalItems;
    private List<Button> entries = new ArrayList<>();
    private ApplicationSettings store;

    private int itemWidth;
    private int itemHeight;

    private long lastClick = 0;
    private int clickedIndex;

    private Layout container;

    private String text;
    private IIcon icon;

    public SettingsGrid(int left, int top, int horizontalItems, int verticalItems, ApplicationSettings store, String text, IIcon icon)
    {
        super(left, top);
        this.text = text;
        this.icon = icon;
        this.horizontalItems = horizontalItems;
        this.verticalItems = verticalItems;
        this.store = store;
        this.itemWidth = (store.getWidth() - padding * 2 - padding * (horizontalItems - 1)) / horizontalItems;
        this.itemHeight = 80;
    }

    @Override
    protected void init(Layout layout)
    {
        container = new Layout(0, 0, store.getWidth(), horizontalItems * itemHeight + (horizontalItems + 1) * padding);
        int size = Math.min(entries.size(), verticalItems * horizontalItems);
        for(int i = 0; i < size; i++)
        {
            Button entry = entries.get(i);
            int itemX = left + (i % horizontalItems) * (itemWidth + padding) + padding;
            int itemY = top + (i / horizontalItems) * (itemHeight + padding) + padding;
            container.addComponent(generateAppTile(entry, itemX, itemY, text, icon));
        }
        layout.addComponent(container);
    }

    @Override
    protected void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks)
    {
        int size = Math.min(entries.size(), verticalItems * horizontalItems);
        for(int i = 0; i < size; i++)
        {
            int itemX = x + (i % horizontalItems) * (itemWidth + padding) + padding;
            int itemY = y + (i / horizontalItems) * (itemHeight + padding) + padding;
            if(GuiHelper.isMouseWithin(mouseX, mouseY, itemX, itemY, itemWidth, itemHeight))
            {
                Gui.drawRect(itemX, itemY, itemX + itemWidth, itemY + itemHeight, Color.GRAY.getRGB());
                Gui.drawRect(itemX + 1, itemY + 1, itemX + itemWidth - 1, itemY + itemHeight - 1, BaseDevice.getSystem().getSettings().getColourScheme().getItemBackgroundColour());
            }
        }
    }

    @Override
    protected void handleMouseClick(int mouseX, int mouseY, int mouseButton)
    {
        int size = Math.min(entries.size(), verticalItems * horizontalItems);
        for(int i = 0; i < size; i++)
        {
            int itemX = xPosition + (i % horizontalItems) * (itemWidth + padding) + padding;
            int itemY = yPosition + (i / horizontalItems) * (itemHeight + padding) + padding;
            if(GuiHelper.isMouseWithin(mouseX, mouseY, itemX, itemY, itemWidth, itemHeight))
            {
                if(System.currentTimeMillis() - this.lastClick <= 200 && clickedIndex == i)
                {
                    this.lastClick = 0;
//                    store.openApplication(entries.get(i));
                }
                else
                {
                    this.lastClick = System.currentTimeMillis();
                    this.clickedIndex = i;
                }
            }
        }
    }

    public void addEntry(Button button)
    {
        this.entries.add(button);
    }

    private Layout generateAppTile(Button entry, int left, int top, String text, IIcon icon)
    {
        Layout layout = new Layout(left, top, itemWidth, itemHeight);

        entry = new Button(left, top, text, icon);
        layout.addComponent(entry);

        return layout;
    }

    public void reloadIcons()
    {
        if(container != null)
        {
            reloadIcons(container);
        }
    }

    private void reloadIcons(Layout layout)
    {
        layout.components.forEach(component ->
        {
            if(component instanceof Layout)
            {
                reloadIcons((Layout) component);
            }
            else if(component instanceof Image)
            {
                ((Image) component).reload();
            }
        });
    }
}