package io.github.vampirestudios.gadget.programs.system.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import io.github.vampirestudios.gadget.api.ThemeManager;
import io.github.vampirestudios.gadget.api.app.Component;
import io.github.vampirestudios.gadget.api.app.Layout;
import io.github.vampirestudios.gadget.api.app.component.Image;
import io.github.vampirestudios.gadget.api.app.component.Label;
import io.github.vampirestudios.gadget.api.app.emojie_packs.Icons;
import io.github.vampirestudios.gadget.api.utils.RenderUtil;
import io.github.vampirestudios.gadget.core.BaseDevice;
import io.github.vampirestudios.gadget.object.ThemeInfo;
import io.github.vampirestudios.gadget.programs.system.ApplicationAppStore;
import io.github.vampirestudios.gadget.programs.system.ApplicationSettings;
import io.github.vampirestudios.gadget.programs.system.object.LocalThemeEntry;
import io.github.vampirestudios.gadget.programs.system.object.RemoteThemeEntry;
import io.github.vampirestudios.gadget.programs.system.object.ThemeEntry;
import io.github.vampirestudios.gadget.util.GuiHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ThemesGrid extends Component
{
    private int padding = 5;
    private int horizontalItems;
    private int verticalItems;
    private List<ThemeEntry> entries = new ArrayList<>();
    private ApplicationSettings store;

    private int itemWidth;
    private int itemHeight;

    private long lastClick = 0;
    private int clickedIndex;

    private Layout container;

    public ThemesGrid(int left, int top, int horizontalItems, int verticalItems, ApplicationSettings store)
    {
        super(left, top);
        this.horizontalItems = horizontalItems;
        this.verticalItems = verticalItems;
        this.store = store;
        this.itemWidth = (ApplicationAppStore.LAYOUT_WIDTH - padding * 2 - padding * (horizontalItems - 1)) / horizontalItems;
        this.itemHeight = 80;
    }

    @Override
    protected void init(Layout layout)
    {
        container = new Layout(0, 0, ApplicationAppStore.LAYOUT_WIDTH, horizontalItems * itemHeight + (horizontalItems + 1) * padding);
        int size = Math.min(entries.size(), verticalItems * horizontalItems);
        for(int i = 0; i < size; i++)
        {
            ThemeEntry entry = entries.get(i);
            int itemX = left + (i % horizontalItems) * (itemWidth + padding) + padding;
            int itemY = top + (i / horizontalItems) * (itemHeight + padding) + padding;
            container.addComponent(generateAppTile(entry, itemX, itemY));
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
                }
                else
                {
                    this.lastClick = System.currentTimeMillis();
                    this.clickedIndex = i;
                }
            }
        }
    }

    public void addEntry(ThemeInfo info)
    {
        this.entries.add(new LocalThemeEntry(info));
    }

    public void addEntry(ThemeEntry entry)
    {
        this.entries.add(adjustEntry(entry));
    }

    private ThemeEntry adjustEntry(ThemeEntry entry)
    {
        ThemeInfo info = ThemeManager.getApplication(entry.getId());
        if(info != null)
        {
            return new LocalThemeEntry(info);
        }
        return entry;
    }

    private Layout generateAppTile(ThemeEntry entry, int left, int top)
    {
        Layout layout = new Layout(left, top, itemWidth, itemHeight);

        int iconOffset = (itemWidth - 14 * 3) / 2;
        if(entry instanceof LocalThemeEntry)
        {
            LocalThemeEntry localEntry = (LocalThemeEntry) entry;
            Image image = new Image(iconOffset, padding, 14 * 3, 14 * 3, localEntry.getInfo().getIconU(), localEntry.getInfo().getIconV(), 14, 14, 224, 224, BaseDevice.ICON_TEXTURES);
            layout.addComponent(image);
        }
        else if(entry instanceof RemoteThemeEntry)
        {
            RemoteThemeEntry remoteEntry = (RemoteThemeEntry) entry;
            ResourceLocation resource = new ResourceLocation(remoteEntry.getId());
            Image image = new Image(iconOffset, padding, 14 * 3, 14 * 3, ApplicationAppStore.CERTIFIED_APPS_URL + "/assets/" + resource.getNamespace() + "/" + resource.getPath() + "/icon.png");
            layout.addComponent(image);
        }

        String clippedName = RenderUtil.clipStringToWidth(entry.getName(), itemWidth - padding * 2);
        Label labelName = new Label(clippedName, itemWidth / 2, 50);
        labelName.setAlignment(Component.ALIGN_CENTER);
        layout.addComponent(labelName);

        String clippedAuthor = RenderUtil.clipStringToWidth(entry.getCreator(), itemWidth - padding * 2);
        Label labelAuthor = new Label(clippedAuthor, itemWidth / 2, 62);
        labelAuthor.setAlignment(Component.ALIGN_CENTER);
        labelAuthor.setShadow(false);
        layout.addComponent(labelAuthor);

        if(entry instanceof LocalThemeEntry)
        {
            ThemeInfo info = ((LocalThemeEntry) entry).getInfo();
            if(BaseDevice.getSystem().getInstalledThemes().contains(info))
            {
                Image installedIcon = new Image(itemWidth - 10 - 15, 38, Icons.CHECK);
                layout.addComponent(installedIcon);
            }
        }
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