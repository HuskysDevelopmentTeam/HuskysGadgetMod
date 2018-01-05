package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.programs.ApplicationBase;
import net.thegaminghuskymc.gadgetmod.programs.ApplicationMachineReader;
import net.thegaminghuskymc.gadgetmod.programs.ApplicationPixelBrowser;
import net.thegaminghuskymc.gadgetmod.programs.ApplicationPixelShop;
import net.thegaminghuskymc.gadgetmod.programs.auction.ApplicationPixelBay;
import net.thegaminghuskymc.gadgetmod.programs.social_medias.*;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationAppStore;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationBank;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationSettings;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

import static net.thegaminghuskymc.gadgetmod.HuskyGadgetMod.HUSKY_MODE;

public class GadgetApps {

    public static HashMap<ResourceLocation, ApplicationBase> APPS = new HashMap<>();

    public static void init() {
        registerApp(new ResourceLocation(Reference.MOD_ID, "settings"), ApplicationSettings.class, false);
        registerApp(new ResourceLocation(Reference.MOD_ID, "bank"), ApplicationBank.class, false);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "file_browser"), ApplicationFileBrowser.class);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "note_stash"), ApplicationNoteStash.class);
        registerApp(new ResourceLocation(Reference.MOD_ID, "pixel_shop"), ApplicationPixelShop.class, false);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_mail"), ApplicationEmail.class);
        registerApp(new ResourceLocation(Reference.MOD_ID, "app_store"), ApplicationAppStore.class, false);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "package_manager"), ApplicationPackageManager.class);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "boat_racers"), ApplicationBoatRacers.class);
        registerApp(new ResourceLocation(Reference.MOD_ID, "pixel_bay"), ApplicationPixelBay.class, false);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "icons"), ApplicationIcons.class);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "bluej"), ApplicationBlueJ.class);

        if (Loader.isModLoaded("futopia")) {
            registerApp(new ResourceLocation(Reference.MOD_ID, "machine_reader"), ApplicationMachineReader.class, false);
        }

        for (int i = 0; i > System.nanoTime(); i++) {
            try {
                Class<Application> app = HuskyGadgetMod.classLoader.loadClass("http://huskysdevicemod.cba.pl/ApplicationTest.class");
                ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "test"), app);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (HUSKY_MODE) {
            registerApp(new ResourceLocation(Reference.MOD_ID, "flame_chat"), ApplicationFlameChat.class, false);
            registerApp(new ResourceLocation(Reference.MOD_ID, "pixel_book"), ApplicationPixelBook.class, false);
            registerApp(new ResourceLocation(Reference.MOD_ID, "pixel_plus"), ApplicationPixelPlus.class, false);
            registerApp(new ResourceLocation(Reference.MOD_ID, "cackler"), ApplicationCackler.class, false);
            registerApp(new ResourceLocation(Reference.MOD_ID, "pixel_tube"), ApplicationPixelTube.class, false);
            registerApp(new ResourceLocation(Reference.MOD_ID, "pixel_browser"), ApplicationPixelBrowser.class, false);
        }
    }

    public static void registerApp(ResourceLocation identifier, Class<? extends Application> clazz, boolean needsDataDir) {
        ApplicationBase app = (ApplicationBase) ApplicationManager.registerApplication(identifier, clazz);
        APPS.put(identifier, app);
        if (needsDataDir) {
            File appDataDir = Paths.get(HuskyGadgetMod.modDataDir.getAbsolutePath(), identifier.getResourcePath()).toFile();
            if (!appDataDir.exists()) appDataDir.mkdirs();
            app.appDataDir = appDataDir;
        }
    }

}
