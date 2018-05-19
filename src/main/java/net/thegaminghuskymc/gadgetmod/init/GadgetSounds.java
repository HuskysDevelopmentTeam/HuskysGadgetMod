package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thegaminghuskymc.gadgetmod.Reference;

import java.util.LinkedList;
import java.util.List;

public class GadgetSounds {
    public static final SoundEvent PRINTER_PRINTING;
    public static final SoundEvent PRINTER_LOADING_PAPER;
    public static final SoundEvent FANS_BLOWING;
    public static final SoundEvent ZAP;
    public static final SoundEvent LASER;


    static {
        PRINTER_PRINTING = registerSound(Reference.RESOURCE_PREFIX + "printing_ink");
        PRINTER_LOADING_PAPER = registerSound(Reference.RESOURCE_PREFIX + "printing_paper");
        FANS_BLOWING = registerSound(Reference.RESOURCE_PREFIX + "fans_blowing");
        ZAP = registerSound(Reference.RESOURCE_PREFIX + "zap");
        LASER = registerSound(Reference.RESOURCE_PREFIX + "lasers");
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
