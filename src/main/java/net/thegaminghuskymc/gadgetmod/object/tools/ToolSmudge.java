package net.thegaminghuskymc.gadgetmod.object.tools;

import net.thegaminghuskymc.gadgetmod.object.Canvas;
import net.thegaminghuskymc.gadgetmod.object.Tool;

public class ToolSmudge extends Tool {

    @Override
    public void handleClick(Canvas canvas, int x, int y) {
        canvas.setPixel(x, y, canvas.getCurrentColour());
    }

    @Override
    public void handleRelease(Canvas canvas, int x, int y) {

    }

    @Override
    public void handleDrag(Canvas canvas, int x, int y) {
        canvas.setPixel(x, y, canvas.getCurrentColour());
    }

}
