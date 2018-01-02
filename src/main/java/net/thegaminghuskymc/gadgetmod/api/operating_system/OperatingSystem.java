package net.thegaminghuskymc.gadgetmod.api.operating_system;

import net.minecraft.client.Minecraft;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.core.TaskBar;
import net.thegaminghuskymc.gadgetmod.core.Window;

import java.io.IOException;
import java.util.List;

public interface OperatingSystem {

    String getOSName();

    String getOSVersion();

    TaskBar getTaskBar();

    void initGui();

    void onGuiClosed();

    void onResize(Minecraft mcIn, int width, int height);

    void updateScreen();

    void drawScreen(int mouseX, int mouseY, float partialTicks);

    void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException;

    void mouseReleased(int mouseX, int mouseY, int state);

    void handleKeyboardInput() throws IOException;

    void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);

    void handleMouseInput() throws IOException;

    void drawHoveringText(List<String> textLines, int x, int y);

    void open(Application app);

    void close(Application app);

    void addWindow(Window<Application> window);

    void updateWindowStack();

    boolean hasReachedWindowLimit();

    boolean isMouseOnScreen(int mouseX, int mouseY);

    boolean isMouseWithinWindowBar(int mouseX, int mouseY, Window window);

    boolean isMouseWithinWindow(int mouseX, int mouseY, Window window);

}
