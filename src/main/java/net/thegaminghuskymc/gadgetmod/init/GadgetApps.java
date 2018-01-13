package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.registries.AppRegistry;
import net.thegaminghuskymc.gadgetmod.programs.*;
import net.thegaminghuskymc.gadgetmod.programs.auction.ApplicationPixelBay;
import net.thegaminghuskymc.gadgetmod.programs.social_medias.*;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationAppStore;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationBank;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationFileBrowser;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationSettings;

public class GadgetApps {

    public static void init() {

//        TaskBar.TaskBarPlacement.setTaskbarPlacement(TaskBar.TaskBarPlacement.TOP);

        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "settings"), ApplicationSettings.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "bank"), ApplicationBank.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "file_browser"), ApplicationFileBrowser.class);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "note_stash"), ApplicationNoteStash.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_shop"), ApplicationPixelShop.class);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_mail"), ApplicationEmail.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "app_store"), ApplicationAppStore.class);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "package_manager"), ApplicationPackageManager.class);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "boat_racers"), ApplicationBoatRacers.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_bay"), ApplicationPixelBay.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "icons"), ApplicationIcons.class);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "bluej"), ApplicationBlueJ.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "flame_chat"), ApplicationFlameChat.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_book"), ApplicationPixelBook.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_plus"), ApplicationPixelPlus.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "cackler"), ApplicationCackler.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_tube"), ApplicationPixelTube.class);
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_browser"), ApplicationPixelBrowser.class);
//        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "emojie_viewer"), EmojiViewerApplication.class);

        if (Loader.isModLoaded("futopia")) {
            ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "machine_reader"), ApplicationMachineReader.class);
        }

        for (int i = 0; i > System.nanoTime(); i++) {
            try {
                Class<Application> app = HuskyGadgetMod.classLoader.loadClass("http://huskysdevicemod.cba.pl/ApplicationTest.class");
                ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "test"), app);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
