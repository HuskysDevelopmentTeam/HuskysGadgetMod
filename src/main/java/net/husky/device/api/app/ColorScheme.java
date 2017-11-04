package net.husky.device.api.app;

import codechicken.lib.colour.ColourRGBA;

public class ColorScheme {

    private ColourRGBA[] yellow = new ColourRGBA[] {
            new ColourRGBA(255, 214, 0, 255),
            new ColourRGBA(255, 234, 0, 255),
            new ColourRGBA(255, 255, 0, 255),
            new ColourRGBA(255, 247, 20, 255),
    };

    private ColourRGBA[] green = new ColourRGBA[] {
            new ColourRGBA(180, 255, 173, 255),
            new ColourRGBA(133, 255, 122, 255),
            new ColourRGBA(86, 255, 71, 255),
            new ColourRGBA(39, 255, 20, 255)
    };

    private ColourRGBA[] red = new ColourRGBA[] {
            new ColourRGBA(255, 173, 173, 255),
            new ColourRGBA(255, 122, 122, 255),
            new ColourRGBA(255, 71, 71, 255),
            new ColourRGBA(255, 20, 20, 255)
    };

    private ColourRGBA[] blue = new ColourRGBA[] {

    };

    private ColourRGBA[] gray = new ColourRGBA[] {

    };

    private ColourRGBA[] black = new ColourRGBA[] {

    };

    private ColourRGBA[] white = new ColourRGBA[] {

    };

    public ColourRGBA[] getYellow() {
        return yellow;
    }

    public ColourRGBA[] getGreen() {
        return green;
    }

    public ColourRGBA[] getRed() {
        return red;
    }

    public ColourRGBA[] getBlue() {
        return blue;
    }

    public ColourRGBA[] getGray() {
        return gray;
    }

    public ColourRGBA[] getBlack() {
        return black;
    }

    public ColourRGBA[] getWhite() {
        return white;
    }
}