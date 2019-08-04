package io.github.vampirestudios.gadget.programs.retro_games;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.api.app.Layout;
import io.github.vampirestudios.gadget.api.app.annontation.DeviceApplication;
import io.github.vampirestudios.gadget.core.BaseDevice;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static io.github.vampirestudios.gadget.Reference.MOD_ID;

@DeviceApplication(modId = MOD_ID, appId = "snake")
public class ApplicationSnake extends Application {

    private Random rnd;
    private int size = 8;
    private int width = 200;
    private int height = 100;
    private int ticks = 0;
    private Point food;
    private Point dir;
    private ArrayList<Point> snake;

    @Override
    public void init(@Nullable NBTTagCompound intent) {

        // AUTO ADJUST WIDTH AND HEIGHT BASED ON SNAKE SCALE.
        width -= (width % size);
        height -= (height % size);

        Layout main = new Layout(width, height);
        rnd = new Random();
        dir = new Point(0, 0);
        ticks = 0;

        snake = new ArrayList<>();

        snake.add(new Point(2, 0));
        snake.add(new Point(1, 0));
        snake.add(new Point(0, 0));

        food = new Point(rnd.nextInt(width / size), rnd.nextInt(height / size));

        this.setCurrentLayout(main);
    }

    @Override
    public void onTick() {
        super.onTick();
        if (ticks + 1 == Integer.MAX_VALUE) ticks = 0;

        int gameSpeed = 2;
        if (ticks % gameSpeed == 0) {
            Point head = snake.get(0);
            Point newLoc = new Point(head.x + dir.x, head.y + dir.y);
            if (food.equals(newLoc)) {
                snake.add(0, newLoc);
                do {
                    food.setLocation(rnd.nextInt(width / size), rnd.nextInt(height / size));
                }
                while (snake.contains(food));
            } else if (newLoc.x < 0 || newLoc.y < 0 || (newLoc.y > height / size - 1) || (newLoc.x > width / size - 1) || (snake.contains(newLoc))) {
                // die.
                dir.setLocation(0, 0);
                snake.clear();

                snake.add(new Point(2, 0));
                snake.add(new Point(1, 0));
                snake.add(new Point(0, 0));

            } else moveForward();
        }
        ticks++;
    }

    @Override
    public void handleKeyTyped(char character, int code) {
        super.handleKeyTyped(character, code);
        int speed = 1;
        if (code == 200 || code == 17) {
            // UP ARROW && 'W':
            if (!dir.equals(new Point(0, speed)))
                dir.setLocation(new Point(0, -speed));
        }
        if (code == 203 || code == 30) {
            // LEFT ARROW && 'A':
            if (!dir.equals(new Point(speed, 0)))
                dir.setLocation(new Point(-speed, 0));
        }
        if (code == 205 || code == 32) {
            // RIGHT ARROW && 'D':
            if (!dir.equals(new Point(-speed, 0)))
                dir.setLocation(new Point(speed, 0));
        }
        if (code == 208 || code == 31) {
            // DOWN ARROW && 'S':
            if (!dir.equals(new Point(0, -speed)))
                dir.setLocation(new Point(0, speed));
        }
    }

    @Override
    public void render(BaseDevice lap, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
        super.render(lap, mc, x, y, mouseX, mouseY, active, partialTicks);
        for (Point aSnake : snake) {
            Gui.drawRect(x + aSnake.x * size, y + aSnake.y * size, x + (aSnake.x * size) + size, y + (aSnake.y * size) + size, Color.GRAY.getRGB());
        }
        if (food != null) {
            Gui.drawRect(x + food.x * size, y + food.y * size, x + (food.x * size) + size, y + (food.y * size) + size, Color.RED.getRGB());
        }
    }


    private void moveForward() {
        Point head = snake.get(0);
        snake.add(0, new Point(head.x + dir.x, head.y + dir.y));
        snake.remove(snake.size() - 1);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }

}
