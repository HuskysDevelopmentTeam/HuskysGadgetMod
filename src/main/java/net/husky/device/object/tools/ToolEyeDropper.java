package net.husky.device.object.tools;

import net.husky.device.object.Canvas;
import net.husky.device.object.Tool;

public class ToolEyeDropper extends Tool {

	@Override
	public void handleClick(Canvas canvas, int x, int y) 
	{
		canvas.setColour(canvas.getPixel(x, y));
	}

	@Override
	public void handleRelease(Canvas canvas, int x, int y) {}

	@Override
	public void handleDrag(Canvas canvas, int x, int y) {}

}
