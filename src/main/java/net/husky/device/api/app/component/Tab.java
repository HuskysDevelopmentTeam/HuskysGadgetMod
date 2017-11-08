package net.husky.device.api.app.component;

import net.husky.device.api.app.Component;
import net.husky.device.core.Laptop;
import net.husky.device.core.NeonOS;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;

public class Tab extends Component {

    public Item item;

    public Tab(int width, int height, Item item) {
        super(width, height);
        this.item = item;
    }

    @Override
    public void render(NeonOS laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
    }

    @Override
    public void renderOverlay(NeonOS laptop, Minecraft mc, int mouseX, int mouseY, boolean windowActive) {
        super.renderOverlay(laptop, mc, mouseX, mouseY, windowActive);
    }

}
