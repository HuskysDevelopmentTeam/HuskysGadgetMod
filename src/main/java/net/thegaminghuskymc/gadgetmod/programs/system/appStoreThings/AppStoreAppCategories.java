package net.thegaminghuskymc.gadgetmod.programs.system.appStoreThings;

import net.thegaminghuskymc.gadgetmod.util.RenderHelper;

public class AppStoreAppCategories {

    public static String[] categories = {
            "Education",
            "Entertainment",
            "Finance",
            "Games",
            "Multiplayer",
            "Shopping",
            "Social",
            "Sports",
            "Tools",
            "VR"
    };

    public static int getSize() {
        return categories.length;
    }

    public static String getUnlocalizedName(int i) {
        return RenderHelper.unlocaliseName("apps.category." + categories[i].toLowerCase());
    }
}