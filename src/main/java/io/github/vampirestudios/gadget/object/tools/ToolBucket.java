package io.github.vampirestudios.gadget.object.tools;

import io.github.vampirestudios.gadget.object.Canvas;
import io.github.vampirestudios.gadget.object.Tool;

public class ToolBucket extends Tool {

    @Override
    public void handleClick(Canvas canvas, int x, int y) {
        fill(canvas, x, y, canvas.getPixel(x, y), canvas.getCurrentColour());
    }

    @Override
    public void handleRelease(Canvas canvas, int x, int y) {
    }

    @Override
    public void handleDrag(Canvas canvas, int x, int y) {
    }

    public void fill(Canvas canvas, int x, int y, int target, int replacement) {
        if (x < 0 || y < 0 || x >= canvas.picture.getWidth() || y >= canvas.picture.getHeight())
            return;

        if (target == replacement)
            return;

        if (canvas.getPixel(x, y) != target)
            return;

        canvas.setPixel(x, y, replacement);

        fill(canvas, x + 1, y, target, replacement);
        fill(canvas, x - 1, y, target, replacement);
        fill(canvas, x, y + 1, target, replacement);
        fill(canvas, x, y - 1, target, replacement);
    }

}
