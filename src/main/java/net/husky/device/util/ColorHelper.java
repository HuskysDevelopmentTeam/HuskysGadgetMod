package net.husky.device.util;

import java.awt.*;

public class ColorHelper {
    private static int newR = 0x55;
    private static int oldR = newR;
    private static int newG = 0xff;
    private static int oldG = newG;
    private static int newB = 0x55;
    private static int oldB = newB;

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

    public static float getRedAsFloat(){
        return newR / 255.0F;
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

    public static float getGreenAsFloat(){
        return newG / 255.0F;
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

    public static float getBlueAsFloat(){
        return newB / 255.0F;
    }

    public static boolean hasColorChanged(){
        return hasRedChanged() && hasGreenChanged() && hasBlueChanged();
    }
}
