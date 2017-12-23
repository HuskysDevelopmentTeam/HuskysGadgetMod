package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.item.Item;
import net.thegaminghuskymc.gadgetmod.item.*;

public class GadgetItems {

    public static final ItemFlashDrive flash_drive;
    public static final ItemAppleWatch apple_watch;
    public static final ItemiPad iPad;
    public static final ItemiPhone iPhone;

    public static final ItemCPU cpu;
    public static final ItemMotherBoard motherBoard;
    public static final ItemRamSticks ramSticks;
    public static final ItemVideoCard videoCard;
    public static final ItemSoundCard soundCard;
    public static final ItemWiFiCard wifiCard;

    public static final ItemServerKey serverKey;
    public static final Item ethernet_cable;
    public static final Item hdmi_cable;

    static {
        flash_drive = new ItemFlashDrive();
        apple_watch = new ItemAppleWatch();
        iPad = new ItemiPad();
        iPhone = new ItemiPhone();
        cpu = new ItemCPU();
        motherBoard = new ItemMotherBoard();
        ramSticks = new ItemRamSticks();
        videoCard = new ItemVideoCard();
        soundCard = new ItemSoundCard();
        wifiCard = new ItemWiFiCard();
        serverKey = new ItemServerKey();
        ethernet_cable = new ItemEthernetCable();
        hdmi_cable = new ItemHDMICable();
    }

    public static void register() {
        register(flash_drive);
        register(apple_watch);
        register(iPad);
        register(iPhone);
        register(cpu);
        register(motherBoard);
        register(ramSticks);
        register(videoCard);
        register(soundCard);
        register(wifiCard);
        register(serverKey);
        register(ethernet_cable);
        register(hdmi_cable);
    }

    private static void register(Item item)
    {
        RegistrationHandler.Items.add(item);
    }

}
