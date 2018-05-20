package net.thegaminghuskymc.gadgetmod.api.theme;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.core.Wrappable;
import net.thegaminghuskymc.gadgetmod.object.ThemeInfo;

import javax.annotation.Nullable;

/**
 * The abstract base class for creating themes.
 */
public abstract class Theme extends Wrappable {

    protected final ThemeInfo info = null;

    public ThemeInfo getInfo()
    {
        return info;
    }

    /**
     * The default initialization method. Clears any components in the default
     * layout and sets it as the current layout. If you override this method and
     * are using the default layout, make sure you call it using
     * <code>super.init(x, y)</code>
     * <p>
     * The parameters passed are the x and y location of the top left corner or
     * your application window.
     */
    @Override
    public abstract void init(@Nullable NBTTagCompound intent);

    @Override
    public void onTick()
    {

    }

    // TODO Remove laptop instance

    /**
     * @param laptop
     * @param mc
     * @param x
     * @param y
     * @param mouseX
     * @param mouseY
     * @param active
     * @param partialTicks
     */
    @Override
    public void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks)
    {

    }

    /**
     * Called when you press a mouse button. Note if you override, make sure you
     * call this super method.
     *
     * @param mouseX      the current x position of the mouse
     * @param mouseY      the current y position of the mouse
     * @param mouseButton the clicked mouse button
     */
    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton)
    {

    }

    /**
     * Called when you drag the mouse with a button pressed down Note if you
     * override, make sure you call this super method.
     *
     * @param mouseX      the current x position of the mouse
     * @param mouseY      the current y position of the mouse
     * @param mouseButton the pressed mouse button
     */
    @Override
    public void handleMouseDrag(int mouseX, int mouseY, int mouseButton)
    {

    }

    /**
     * Called when you release the currently pressed mouse button. Note if you
     * override, make sure you call this super method.
     *
     * @param mouseX      the x position of the release
     * @param mouseY      the y position of the release
     * @param mouseButton the button that was released
     */
    @Override
    public void handleMouseRelease(int mouseX, int mouseY, int mouseButton)
    {

    }

    /**
     * Called when you scroll the wheel on your mouse. Note if you override,
     * make sure you call this super method.
     *
     * @param mouseX    the x position of the mouse
     * @param mouseY    the y position of the mouse
     * @param direction the direction of the scroll. true is up, false is down
     */
    @Override
    public void handleMouseScroll(int mouseX, int mouseY, boolean direction)
    {

    }

    /**
     * Called when a key is typed from your keyboard. Note if you override, make
     * sure you call this super method.
     *
     * @param character the typed character
     * @param code      the typed character code
     */
    @Override
    public void handleKeyTyped(char character, int code)
    {

    }

    @Override
    public void handleKeyReleased(char character, int code)
    {

    }

    // TODO: Remove from here and put into core

    /**
     * Updates the components of the current layout to adjust to new window
     * position. There is really be no reason to call this method.
     *
     * @param x
     * @param y
     */
    @Override
    public final void updateComponents(int x, int y)
    {

    }

    /**
     * Called when the application is closed
     */
    @Override
    public void onClose()
    {

    }

    /**
     * Check if a theme is equal to another. Checking the ID is
     * sufficient as they should be unique.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (!(obj instanceof Theme))
            return false;
        Theme app = (Theme) obj;
        return app.info.getFormattedId().equals(this.info.getFormattedId());
    }

}