package net.husky.device.util;

import java.awt.*;

public class ColorHelper {
    private static int newR = 0;
    private static int oldR = 0;
    private static int newG = 0;
    private static int oldG = 0;
    private static int newB = 0;
    private static int oldB = 0;

    public static void setColor(int c){
        setRed(getRedFromColor(c));
        setGreen(getGreenFromColor(c));
        setBlue(getBlueFromColor(c));
    }

    private static void setRed(int r){
        oldR = newR;
        newR = r;
    }

    private static int getRedFromColor(int c){
        Color color = new Color(c);
        return color.getRed();
    }

    private static boolean hasRedChanged(){
        return oldR != newR;
    }

    private static void setGreen(int r){
        oldR = newR;
        newR = r;
    }

    private static int getGreenFromColor(int c){
        Color color = new Color(c);
        return color.getGreen();
    }

    private static boolean hasGreenChanged(){
        return oldG != newG;
    }

    private static void setBlue(int r){
        oldR = newR;
        newR = r;
    }

    private static int getBlueFromColor(int c){
        Color color = new Color(c);
        return color.getBlue();
    }

    private static boolean hasBlueChanged(){
        return oldB != newB;
    }

    public static boolean hasColorChanged(){
        return hasRedChanged() && hasGreenChanged() && hasBlueChanged();
    }
}
