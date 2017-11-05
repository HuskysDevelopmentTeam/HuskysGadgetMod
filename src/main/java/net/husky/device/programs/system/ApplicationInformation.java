package net.husky.device.programs.system;

import net.husky.device.Reference;
import net.husky.device.api.app.Component;
import net.husky.device.api.app.Layout;
import net.husky.device.api.app.component.ButtonTab;
import net.husky.device.api.app.component.Label;
import net.husky.device.api.app.listener.ClickListener;
import net.husky.device.core.Laptop;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ApplicationInformation extends SystemApplication {

    private Label OSVersion;
    private Label OSName;

    @Override
    public void init() {
        OSName = new Label("OS Name: " + Reference.OSName, 10, 10);
        super.addComponent(OSName);

        OSVersion = new Label("OS Version: " + Reference.OSVersion, 10, 25);
        super.addComponent(OSVersion);
    }

    @Override
    public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
        super.render(laptop, mc, x, y, mouseX, mouseY, active, partialTicks);
    }

    @Override
	public void load(NBTTagCompound tagCompound) {

	}

	@Override
	public void save(NBTTagCompound tagCompound) {

	}

}
