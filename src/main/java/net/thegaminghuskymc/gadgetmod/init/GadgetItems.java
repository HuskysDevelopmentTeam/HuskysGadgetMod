package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.item.*;

public class GadgetItems {

    public static final ItemFlashDrive flash_drive;
    public static final ItemInternalHarddrive internal_harddrive;
    public static final ItemPixelWatch pixel_watch;
    public static final ItemPixelTab pixel_pad;
    public static final ItemPixelPhone pixel_phone;

    public static final ItemCPU cpu;
    public static final ItemMotherBoard motherBoard;
    public static final ItemRamSticks ramSticks;
    public static final ItemVideoCard videoCard;
    public static final ItemSoundCard soundCard;
    public static final ItemWiFiCard wifiCard;

    public static final ItemServerKey serverKey;
    public static final Item ethernet_cable;
    public static final Item hdmi_cable;
    public static final Item usb_cable;
    
    public static final Item easter_egg;
    public static final Item wiiu_gamepad;

    static {
        flash_drive = new ItemFlashDrive();
        internal_harddrive = new ItemInternalHarddrive();
        pixel_watch = new ItemPixelWatch();
        pixel_pad = new ItemPixelTab();
        pixel_phone = new ItemPixelPhone();
        cpu = new ItemCPU();
        motherBoard = new ItemMotherBoard();
        ramSticks = new ItemRamSticks();
        videoCard = new ItemVideoCard();
        soundCard = new ItemSoundCard();
        wifiCard = new ItemWiFiCard();
        serverKey = new ItemServerKey();
        ethernet_cable = new ItemEthernetCable();
        hdmi_cable = new ItemHDMICable();
        usb_cable = new ItemUSBCable();
        easter_egg = new ItemEasterEgg();
        wiiu_gamepad = new ItemWiiUGamepad();
    }

    public static void register() {
        register(flash_drive);
        register(internal_harddrive);
        register(pixel_watch);
        register(pixel_pad);
        register(pixel_phone);
        register(cpu);
        register(motherBoard);
        register(ramSticks);
        register(videoCard);
        register(soundCard);
        register(wifiCard);
        register(serverKey);
        register(ethernet_cable);
        register(hdmi_cable);
        register(usb_cable);
        register(easter_egg);
        register(wiiu_gamepad);
    }

    private static void register(Item item) {
        RegistrationHandler.Items.add(item);
    }

}
