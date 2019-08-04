package io.github.vampirestudios.gadget.object.tools;

import io.github.vampirestudios.gadget.object.Canvas;
import io.github.vampirestudios.gadget.object.Tool;

public class ToolBlur extends Tool {

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
