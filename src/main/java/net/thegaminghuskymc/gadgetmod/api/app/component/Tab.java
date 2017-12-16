package net.thegaminghuskymc.gadgetmod.api.app.component;

import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.core.Laptop;

public class Tab extends Component {

    public Item item;

    public Tab(int width, int height, Item item) {
        super(width, height);
        this.item = item;
    }

    @Override
    public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
    }

    @Override
    public void renderOverlay(Laptop laptop, Minecraft mc, int mouseX, int mouseY, boolean windowActive) {
        super.renderOverlay(laptop, mc, mouseX, mouseY, windowActive);
    }

}
