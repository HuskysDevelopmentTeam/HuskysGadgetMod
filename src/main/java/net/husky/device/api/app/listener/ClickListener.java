package net.husky.device.api.app.listener;

import net.husky.device.api.app.Component;

/**
 * The click listener interface. Used for handling clicks
 * on components.
 * 
 * @author MrCrayfish
 */
public interface ClickListener 
{
	/**
	 * Called when component is clicked
	 * 
	 * @param c the component that was clicked
	 * @param mouseButton the mouse button used to click
	 */
	void onClick(Component c, int mouseButton);
}
