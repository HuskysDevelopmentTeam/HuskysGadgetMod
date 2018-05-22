package net.thegaminghuskymc.gadgetmod.programs.system.component;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Image;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationAppStore;
import net.thegaminghuskymc.gadgetmod.programs.system.object.AppEntry;
import net.thegaminghuskymc.gadgetmod.programs.system.object.LocalAppEntry;
import net.thegaminghuskymc.gadgetmod.programs.system.object.RemoteAppEntry;
import net.thegaminghuskymc.gadgetmod.util.GuiHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class AppGrid extends Component
{
    private int padding = 5;
    private int horizontalItems;
    private int verticalItems;
    private List<AppEntry> entries = new ArrayList<>();
    private ApplicationAppStore store;

    private int itemWidth;
    private int itemHeight;

    private long lastClick = 0;
    private int clickedIndex;

    private Layout container;

    private static final int WIDE = 640;
    private static final int HIGH = 240;
    private static final float HUE_MIN = 0;
    private static final float HUE_MAX = 1;
    private float hue = HUE_MIN;
    private Color color1 = Color.white;
    private Color color2 = Color.black;
    private float delta = 0.01f;

    public AppGrid(int left, int top, int horizontalItems, int verticalItems, ApplicationAppStore store)
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
            AppEntry entry = entries.get(i);
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
                hue += delta;
                if (hue > HUE_MAX) {
                    hue = HUE_MIN;
                }
                color1 = Color.getHSBColor(hue, 1, 1);
                color2 = Color.getHSBColor(hue + 16 * delta, 1, 1);
                drawGradientRect(itemX, itemY, itemX + itemWidth, itemY + itemHeight, Color.TRANSLUCENT + color1.getRGB(), color2.getRGB());
                drawGradientRect(itemX + 1, itemY + 1, itemX + itemWidth - 1, itemY + itemHeight - 1, Color.TRANSLUCENT + color1.getRGB(), color2.getRGB());
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
                    store.openApplication(entries.get(i));
                }
                else
                {
                    this.lastClick = System.currentTimeMillis();
                    this.clickedIndex = i;
                }
            }
        }
    }

    public void addEntry(AppInfo info)
    {
        this.entries.add(new LocalAppEntry(info));
    }

    public void addEntry(AppEntry entry)
    {
        this.entries.add(adjustEntry(entry));
    }

    private AppEntry adjustEntry(AppEntry entry)
    {
        AppInfo info = ApplicationManager.getApplication(entry.getId());
        if(info != null)
        {
            return new LocalAppEntry(info);
        }
        return entry;
    }

    private Layout generateAppTile(AppEntry entry, int left, int top)
    {
        Layout layout = new Layout(left, top, itemWidth, itemHeight);

        int iconOffset = (itemWidth - 14 * 3) / 2;
        if(entry instanceof LocalAppEntry)
        {
            LocalAppEntry localAppEntry = (LocalAppEntry) entry;
            Image image = new Image(iconOffset, padding, 14 * 3, 14 * 3, localAppEntry.getInfo().getIconU(), localAppEntry.getInfo().getIconV(), 14, 14, 224, 224, BaseDevice.ICON_TEXTURES);
            layout.addComponent(image);
        }
        else if(entry instanceof RemoteAppEntry)
        {
            RemoteAppEntry remoteAppEntry = (RemoteAppEntry) entry;
            ResourceLocation resource = new ResourceLocation(remoteAppEntry.getId());
            Image image = new Image(iconOffset, padding, 14 * 3, 14 * 3, ApplicationAppStore.CERTIFIED_APPS_URL + "/assets/" + resource.getResourceDomain() + "/" + resource.getResourcePath() + "/icon.png");
            layout.addComponent(image);
        }

        String clippedName = RenderUtil.clipStringToWidth(entry.getName(), itemWidth - padding * 2);
        Label labelName = new Label(clippedName, itemWidth / 2, 50);
        labelName.setAlignment(Component.ALIGN_CENTER);
        layout.addComponent(labelName);

        String clippedAuthor = RenderUtil.clipStringToWidth(entry.getAuthor(), itemWidth - padding * 2);
        Label labelAuthor = new Label(clippedAuthor, itemWidth / 2, 62);
        labelAuthor.setAlignment(Component.ALIGN_CENTER);
        labelAuthor.setShadow(false);
        layout.addComponent(labelAuthor);

        if(store.certifiedApps.contains(entry))
        {
            Image certifiedIcon = new Image(15, 38, Icons.VERIFIED);
            layout.addComponent(certifiedIcon);
        }

        if(entry instanceof LocalAppEntry)
        {
            AppInfo info = ((LocalAppEntry) entry).getInfo();
            if(BaseDevice.getSystem().getInstalledApplications().contains(info))
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