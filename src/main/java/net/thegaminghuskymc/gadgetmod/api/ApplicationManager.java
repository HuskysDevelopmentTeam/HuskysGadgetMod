package net.thegaminghuskymc.gadgetmod.api;

import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ApplicationManager {

    private static AppInfo appInfo;

    public static final Map<ResourceLocation, AppInfo> APP_INFO = new HashMap<>();

    /**
     * Registers an application into the application list
     * <p>
     * The identifier parameter is simply just an id for the application.
     * <p>
     * Example: {@code new ResourceLocation("modid:appid");}
     *
     * @param identifier the
     * @param clazz
     */
    @Nullable
    public static Application registerApplication(ResourceLocation identifier, Class<? extends Application> clazz) {
        Application application = HuskyGadgetMod.proxy.registerApplication(identifier, clazz);
        if (application != null) {
            APP_INFO.put(identifier, application.getInfo());
            return application;
        }
        return null;
    }

    /**
     * Get all applications that are registered. Please note
     * that this list is read only and cannot be modified.
     *
     * @return the application list
     */
    public static Collection<AppInfo> getAvailableApps() {
        return APP_INFO.values();
    }

    @Nullable
    public static AppInfo getApplication(String appId) {
        return APP_INFO.get(new ResourceLocation(appId.replace(".", ":")));
    }
}
