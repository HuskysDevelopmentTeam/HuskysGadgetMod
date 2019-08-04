package io.github.vampirestudios.gadget.object.tools;

import net.minecraft.client.Minecraft;
import io.github.vampirestudios.gadget.object.Canvas;
import io.github.vampirestudios.gadget.object.Tool;

public class ToolMove extends Tool {

    @Override
    public void handleClick(Canvas canvas, int x, int y) {

    }

    @Override
    public void handleRelease(Canvas canvas, int x, int y) {

    }

    @Override
    public void handleDrag(Canvas canvas, int x, int y) {
        canvas.xPosition = Minecraft.getMinecraft().mouseHelper.deltaX;
        canvas.yPosition = Minecraft.getMinecraft().mouseHelper.deltaY;
    }

}
