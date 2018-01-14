package net.thegaminghuskymc.gadgetmod.api.app.component;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.api.app.IIcon;

public class ButtonToggle extends Button implements RadioGroup.Item {

    protected boolean toggle = false;
    protected RadioGroup group = null;

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     * @param icon an icon that you choose
     */
    public ButtonToggle(int left, int top, IIcon icon) {
        super(left, top, icon);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public ButtonToggle(int left, int top, ResourceLocation iconResource, int iconU, int iconV, int iconWidth, int iconHeight) {
        super(left, top, iconResource, iconU, iconV, iconWidth, iconHeight);
    }

    /**
     * Sets the radio group for this button.
     *
     * @param group the radio group.
     */
    public void setRadioGroup(RadioGroup group) {
        this.group = group;
        this.group.add(this);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible || !this.enabled)
            return;

        if (super.isInside(mouseX, mouseY)) {
            if (clickListener != null) {
                clickListener.onClick(mouseX, mouseY, mouseButton);
            }
            playClickSound(Minecraft.getMinecraft().getSoundHandler());
            if (group != null) {
                group.deselect();
                this.toggle = true;
            } else {
                this.toggle = !toggle;
            }
        }
    }

    @Override
    public boolean isInside(int mouseX, int mouseY) {
        return super.isInside(mouseX, mouseY) || toggle;
    }

    @Override
    public boolean isSelected() {
        return toggle;
    }

    @Override
    public void setSelected(boolean selected) {
        this.toggle = selected;
    }
}
