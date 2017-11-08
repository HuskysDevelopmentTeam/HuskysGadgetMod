package net.husky.device.util;

import net.husky.device.HuskyDeviceMod;

import java.awt.*;

public class ColorHelper {
    private static int newR = 0x55;
    private static int oldR = newR;
    private static int newG = 0xff;
    private static int oldG = newR;
    private static int newB = 0x55;
    private static int oldB = newR;

    public static void setColor(int c){
        HuskyDeviceMod.getLogger().info("Setting colors...");
        setRed(getRedFromColor(c));
        setGreen(getGreenFromColor(c));
        setBlue(getBlueFromColor(c));
    }

    private static void setRed(int r){
        HuskyDeviceMod.getLogger().info("Red is being set...");
        oldR = newR;
        newR = r;
        HuskyDeviceMod.getLogger().info("Red has been set...");
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
        HuskyDeviceMod.getLogger().info("Green is being set...");
        oldR = newR;
        newR = r;
        HuskyDeviceMod.getLogger().info("Green has been set...");
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
        HuskyDeviceMod.getLogger().info("Blue is being set...");
        oldR = newR;
        newR = r;
        HuskyDeviceMod.getLogger().info("Blue has been set...");
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
//        HuskyDeviceMod.getLogger().info(hasRedChanged() && hasGreenChanged() && hasBlueChanged());
        return hasRedChanged() || hasGreenChanged() || hasBlueChanged();
    }
}
