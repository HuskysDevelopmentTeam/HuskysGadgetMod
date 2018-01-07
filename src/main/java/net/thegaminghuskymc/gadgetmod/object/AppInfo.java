package net.thegaminghuskymc.gadgetmod.object;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AppInfo {

    private final ResourceLocation APP_ID;
    private int iconU = 0, iconV = 0, bannerU = 0, bannerV = 0;

    private String appName;
    private String appCreator;
    private String appDescription;
    private String appVersion;
    private String appIcon;
    private ArrayList<String> appScreenshots;
    private ArrayList<String> creatorSupport;

    public AppInfo(ResourceLocation identifier) {
        this.APP_ID = identifier;
    }

    /**
     * Gets the id of the application
     *
     * @return the app resource location
     */
    public ResourceLocation getId() {
        return APP_ID;
    }

    /**
     * Gets the formatted version of the application's id
     *
     * @return a formatted id
     */
    public String getFormattedId() {
        return APP_ID.getResourceDomain() + "." + APP_ID.getResourcePath();
    }

    /**
     * Gets the name of the application
     *
     * @return the application name
     */
    public String getName() {
        return I18n.format("app." + this.getFormattedId() + ".name");
    }

    public String getCreator() {
        return I18n.format("app." + this.getFormattedId() + ".author");
    }

    public String getDescription() {
        return I18n.format("app." + this.getFormattedId() + ".desc");
    }

    public String getIcon() {
        return appIcon;
    }

    public String getVersion() {
        return appVersion;
    }

    public int getIconU() {
        return iconU;
    }

    public int getIconV() {
        return iconV;
    }

    public int getBannerU() {
        return bannerU;
    }

    public int getBannerV() {
        return bannerV;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof AppInfo))
            return false;
        AppInfo info = (AppInfo) obj;
        return getFormattedId().equals(info.getFormattedId());
    }

    public class Deserializer implements JsonDeserializer {

        @Override
        public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


            return null;
        }

    }

}
