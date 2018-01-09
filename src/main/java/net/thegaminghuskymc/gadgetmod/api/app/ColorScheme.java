package net.thegaminghuskymc.gadgetmod.api.app;

import java.awt.*;

public class ColorScheme {

    private Color[] yellow = new Color[]{
            new Color(255, 214, 0, 255),
            new Color(255, 234, 0, 255),
            new Color(255, 255, 0, 255),
            new Color(255, 247, 20, 255),
    };

    private Color[] green = new Color[]{
            new Color(180, 255, 173, 255),
            new Color(133, 255, 122, 255),
            new Color(86, 255, 71, 255),
            new Color(39, 255, 20, 255)
    };

    private Color[] red = new Color[]{
            new Color(255, 173, 173, 255),
            new Color(255, 122, 122, 255),
            new Color(255, 71, 71, 255),
            new Color(255, 20, 20, 255)
    };

    private Color[] blue = new Color[]{

    };

    private Color[] gray = new Color[]{

    };

    private Color[] black = new Color[]{

    };

    private Color[] white = new Color[]{

    };

    public Color[] getYellow() {
        return yellow;
    }

    public Color[] getGreen() {
        return green;
    }

    public Color[] getRed() {
        return red;
    }

    public Color[] getBlue() {
        return blue;
    }

    public Color[] getGray() {
        return gray;
    }

    public Color[] getBlack() {
        return black;
    }

    public Color[] getWhite() {
        return white;
    }
}