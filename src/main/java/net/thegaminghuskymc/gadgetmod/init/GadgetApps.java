package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManagerOld;
import net.thegaminghuskymc.gadgetmod.programs.*;
import net.thegaminghuskymc.gadgetmod.programs.auction.ApplicationPixelBay;
import net.thegaminghuskymc.gadgetmod.programs.debug.ApplicationTextArea;
import net.thegaminghuskymc.gadgetmod.programs.gitweb.ApplicationGitWeb;
import net.thegaminghuskymc.gadgetmod.programs.retro_games.ApplicationSnake;
import net.thegaminghuskymc.gadgetmod.programs.social_medias.*;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationAppStore;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationBank;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationFileBrowser;
import net.thegaminghuskymc.gadgetmod.programs.system.ApplicationSettings;
import net.thegaminghuskymc.gadgetmod.programs.system.admin.ApplicationServerAdmin;

import static net.thegaminghuskymc.gadgetmod.Reference.MOD_ID;

public class GadgetApps {

    public static void init() {
        ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "settings"), ApplicationSettings.class);
        ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "bank"), ApplicationBank.class);
        ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "file_browser"), ApplicationFileBrowser.class);
        ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "note_stash"), ApplicationNoteStash.class);
//        ApplicationManagerOld.registerApplication(new ResourceLocation(Reference.MOD_ID, "pixel_mail"), ApplicationEmail.class);
        ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "app_store"), ApplicationAppStore.class);
//        ApplicationManagerOld.registerApplication(new ResourceLocation(Reference.MOD_ID, "package_manager"), ApplicationPackageManager.class);
        ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "snake"), ApplicationSnake.class);
        ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "gitweb"), ApplicationGitWeb.class);

        ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "boat_racer"), ApplicationBoatRacers.class);

        if(HuskyGadgetMod.isServerAdmin) {
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "server_admin"), ApplicationServerAdmin.class);
        }

        if(HuskyGadgetMod.isArtist) {
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "icons"), ApplicationIcons.class);
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "pixel_shop"), ApplicationPixelShop.class);
        }

        if(HuskyGadgetMod.isDeveloper) {
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "text_field"), ApplicationTextArea.class);
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "test"), ApplicationTest.class);
            if (Loader.isModLoaded("futopia") || Loader.isModLoaded("futopia2") || Loader.isModLoaded("ae2") || Loader.isModLoaded("refined_storage")) {
                ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "machine_reader"), ApplicationMachineReader.class);
            }
        }

        if(HuskyGadgetMod.likesSocialMedias) {
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "pixel_bay"), ApplicationPixelBay.class);
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "flame_chat"), ApplicationFlameChat.class);
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "pixel_book"), ApplicationPixelBook.class);
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "pixel_plus"), ApplicationPixelPlus.class);
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "cackler"), ApplicationCackler.class);
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "pixel_tube"), ApplicationPixelTube.class);
            ApplicationManagerOld.registerApplication(new ResourceLocation(MOD_ID, "pixel_browser"), ApplicationPixelBrowser.class);
        }

    }

}
