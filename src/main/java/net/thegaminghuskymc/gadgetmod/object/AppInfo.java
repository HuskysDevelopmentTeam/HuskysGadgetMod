package net.thegaminghuskymc.gadgetmod.object;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.proxy.ClientProxy;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppInfo {

    private final ResourceLocation APP_ID;
    private transient int iconU = 0, iconV = 0, bannerU = 0, bannerV = 0;
    private transient boolean systemApp;

    private String appName;
    private String appCreator;
    private String appDescription;
    private String appVersion;
    private String appIcon;
    private String appBanner;
    private String[] appScreenshots;
    private Support creatorSupport;

    public AppInfo(ResourceLocation identifier, boolean isSystemApp) {
        this.APP_ID = identifier;
        this.systemApp = isSystemApp;
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
        return appName;
    }

    public String getAuthor() {
        return appCreator;
    }

    public String getDescription() {
        return appDescription;
    }

    public String getVersion() {
        return appVersion;
    }

    public String getIcon() {
        return appIcon;
    }

    public String getBanner() {
        return appBanner;
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

    public String[] getScreenshots() {
        return appScreenshots;
    }

    public Support getSupport() {
        return creatorSupport;
    }

    public boolean isSystemApp() {
        return systemApp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof AppInfo)) return false;
        AppInfo info = (AppInfo) obj;
        return this == info || getFormattedId().equals(info.getFormattedId());
    }

    public void reload() {
        resetInfo();
        InputStream stream = ClientProxy.class.getResourceAsStream("/assets/" + APP_ID.getResourceDomain() + "/apps/" + APP_ID.getResourcePath() + ".json");

        if (stream == null)
            throw new RuntimeException("Missing app info json for '" + APP_ID + "'");

        Reader reader = new InputStreamReader(stream);
        JsonParser parser = new JsonParser();
        JsonElement obj = parser.parse(reader);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AppInfo.class, new AppInfo.Deserializer(this));
        Gson gson = builder.create();
        gson.fromJson(obj, AppInfo.class);
    }

    private void resetInfo() {
        appName = null;
        appCreator = null;
        appDescription = null;
        appVersion = null;
        appIcon = null;
        appBanner = null;
        appScreenshots = null;
        creatorSupport = null;
    }

    private static class Support {
        private String paypal;
        private String patreon;
        private String twitter;
        private String youtube;
    }

    public static class Deserializer implements JsonDeserializer<AppInfo> {

        private static final Pattern LANG = Pattern.compile("\\$\\{[a-z]+}");

        private AppInfo info;

        public Deserializer(AppInfo info) {
            this.info = info;
        }

        @Override
        public AppInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            try {
                info.appName = convertToLocal(json.getAsJsonObject().get("app_name").getAsString());
                info.appCreator = convertToLocal(json.getAsJsonObject().get("app_creator").getAsString());
                info.appDescription = convertToLocal(json.getAsJsonObject().get("app_description").getAsString());
                info.appVersion = json.getAsJsonObject().get("app_version").getAsString();

                if (json.getAsJsonObject().has("app_screenshots") && json.getAsJsonObject().get("app_screenshots").isJsonArray()) {
                    info.appScreenshots = context.deserialize(json.getAsJsonObject().get("app_screenshots"), new TypeToken<String[]>() {
                    }.getType());
                }

                if (json.getAsJsonObject().has("app_banner") && json.getAsJsonObject().get("app_banner").isJsonPrimitive()) {
                    info.appBanner = json.getAsJsonObject().get("app_banner").getAsString();
                }

                if (json.getAsJsonObject().has("app_icon") && json.getAsJsonObject().get("app_icon").isJsonPrimitive()) {
                    info.appIcon = json.getAsJsonObject().get("app_icon").getAsString();
                }

                if (json.getAsJsonObject().has("creator_support") && json.getAsJsonObject().get("creator_support").getAsJsonObject().size() > 0) {
                    JsonObject supportObj = json.getAsJsonObject().get("creator_support").getAsJsonObject();
                    Support support = new Support();

                    if (supportObj.has("paypal")) {
                        support.paypal = supportObj.get("paypal").getAsString();
                    }
                    if (supportObj.has("patreon")) {
                        support.patreon = supportObj.get("patreon").getAsString();
                    }
                    if (supportObj.has("twitter")) {
                        support.twitter = supportObj.get("twitter").getAsString();
                    }
                    if (supportObj.has("youtube")) {
                        support.youtube = supportObj.get("youtube").getAsString();
                    }

                    info.creatorSupport = support;
                }
            } catch (JsonParseException e) {
                HuskyGadgetMod.getLogger().error("Malformed app info json for '" + info.getFormattedId() + "'");
            }

            return info;
        }

        private String convertToLocal(String s) {
            Matcher m = LANG.matcher(s);
            while (m.find()) {
                String found = m.group();
                s = s.replace(found, I18n.format("app." + info.getFormattedId() + "." + found.substring(2, found.length() - 1)));
            }
            return s;
        }
    }

}
