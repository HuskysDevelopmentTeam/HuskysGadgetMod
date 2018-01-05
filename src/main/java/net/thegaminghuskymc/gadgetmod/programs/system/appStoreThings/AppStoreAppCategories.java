package net.thegaminghuskymc.gadgetmod.programs.system.appStoreThings;

import net.thegaminghuskymc.gadgetmod.util.RenderHelper;

public class AppStoreAppCategories {

	public static String[] categories = {
			"Games",
			"Tools",
			"Education",
            "Entertainment",
            "Sports",
            "VR",
            "Finance",
            "Multiplayer",
            "Shopping",
            "Social"
	};
	
	public static int getSize() {
		return categories.length;
	}
	
	public static String getUnlocalizedName(int i) {
		return RenderHelper.unlocaliseName("apps.category." + categories[i].toLowerCase());
	}
}