package net.husky.device.core;

import net.husky.device.Reference;
import net.minecraft.util.ResourceLocation;

public class Phone extends NeonOS {

    public static final int ID = BASE_ID + 2;

    private static final ResourceLocation PHONE_GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/phone.png");

    private static final int DEVICE_WIDTH = BASE_DEVICE_WIDTH;
    private static final int DEVICE_HEIGHT = BASE_DEVICE_HEIGHT;
    static final int SCREEN_WIDTH = BASE_SCREEN_WIDTH;
    static final int SCREEN_HEIGHT = BASE_SCREEN_HEIGHT;

}
