package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.print.PrintingManager;
import net.thegaminghuskymc.gadgetmod.programs.ApplicationPixelShop;

public class GadgetTasks {

    public static void register() {
        PrintingManager.registerPrint(new ResourceLocation(Reference.MOD_ID, "picture"), ApplicationPixelShop.PicturePrint.class);
    }

}
