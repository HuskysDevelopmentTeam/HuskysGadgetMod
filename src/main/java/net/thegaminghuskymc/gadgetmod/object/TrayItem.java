package net.thegaminghuskymc.gadgetmod.object;

import net.thegaminghuskymc.gadgetmod.api.app.IIcon;
import net.thegaminghuskymc.gadgetmod.api.app.listener.ClickListener;

/**
 * Author: MrCrayfish
 */
public class TrayItem {
    private IIcon icon;
    private ClickListener listener;

    public TrayItem(IIcon icon) {
        this.icon = icon;
    }

    public void init() {
    }

    public void tick() {
    }

    public IIcon getIcon() {
        return icon;
    }

    public void setIcon(IIcon icon) {
        this.icon = icon;
    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (listener != null) {
            listener.onClick(mouseX, mouseY, mouseButton);
        }
    }
}