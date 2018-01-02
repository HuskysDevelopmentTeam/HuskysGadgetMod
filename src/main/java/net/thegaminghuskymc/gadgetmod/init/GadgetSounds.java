package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thegaminghuskymc.gadgetmod.Reference;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class GadgetSounds {
    public static final SoundEvent PRINTER_PRINTING;
    public static final SoundEvent PRINTER_LOADING_PAPER;
    public static final SoundEvent FANS_BLOWING;

    public static SoundEvent fart_1;
    public static SoundEvent fart_2;
    public static SoundEvent fart_3;
    public static SoundEvent microwave_running;
    public static SoundEvent microwave_finish;
    public static SoundEvent toaster_down;
    public static SoundEvent toaster_up;
    public static SoundEvent channel_news;
    public static SoundEvent channel_cooking;
    public static SoundEvent channel_sam_tabor;
    public static SoundEvent channel_heman;
    public static SoundEvent channel_switch;

    static {
        PRINTER_PRINTING = registerSound(Reference.RESOURCE_PREFIX + "printing_ink");
        PRINTER_LOADING_PAPER = registerSound(Reference.RESOURCE_PREFIX + "printing_paper");
        FANS_BLOWING = registerSound(Reference.RESOURCE_PREFIX + "fans_blowing");
        fart_1 = registerSound(Reference.RESOURCE_PREFIX + "fart_1");
        fart_2 = registerSound(Reference.RESOURCE_PREFIX + "fart_2");
        fart_3 = registerSound(Reference.RESOURCE_PREFIX + "fart_3");
        microwave_running = registerSound(Reference.RESOURCE_PREFIX + "microwave_running");
        microwave_finish = registerSound(Reference.RESOURCE_PREFIX + "microwave_finish");
        toaster_down = registerSound(Reference.RESOURCE_PREFIX + "toaster_down");
        toaster_up = registerSound(Reference.RESOURCE_PREFIX + "toaster_up");
        channel_news = registerSound(Reference.RESOURCE_PREFIX + "channel_news");
        channel_cooking = registerSound(Reference.RESOURCE_PREFIX + "channel_cooking");
        channel_sam_tabor = registerSound(Reference.RESOURCE_PREFIX + "channel_sam_tabor");
        channel_heman = registerSound(Reference.RESOURCE_PREFIX + "channel_heman");
        channel_switch = registerSound(Reference.RESOURCE_PREFIX + "channel_switch");
    }

    public static SoundEvent getRandomFart(Random rand) {
        int num = rand.nextInt(3);
        switch (num) {
            case 1:
                return fart_2;
            case 2:
                return fart_3;
            default:
                return fart_1;
        }
    }

    private static SoundEvent registerSound(String soundNameIn) {
        ResourceLocation resource = new ResourceLocation(soundNameIn);
        SoundEvent sound = new SoundEvent(resource).setRegistryName(soundNameIn);
        RegistrationHandler.SOUNDS.add(sound);
        return sound;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler {
        public static final List<SoundEvent> SOUNDS = new LinkedList<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<SoundEvent> event) {
            SOUNDS.stream().forEach(sound -> event.getRegistry().register(sound));
        }
    }
}
