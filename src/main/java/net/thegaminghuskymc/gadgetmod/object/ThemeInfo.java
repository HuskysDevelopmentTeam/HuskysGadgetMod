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
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThemeInfo {

    public static final Comparator<ThemeInfo> SORT_NAME = Comparator.comparing(ThemeInfo::getName);

    private transient final ResourceLocation themeID;
    private transient int iconU = 0;
    private transient int iconV = 0;

    private String name;
    private String creator;
    private String[] creators;
    private String description;
    private String version;
    private String icon;
    private String[] screenshots;

    public ThemeInfo(ResourceLocation identifier)
    {
        this.themeID = identifier;
    }

    /**
     * Gets the id of the application
     *
     * @return the app resource location
     */
    public ResourceLocation getId()
    {
        return themeID;
    }

    /**
     * Gets the formatted version of the application's id
     *
     * @return a formatted id
     */
    public String getFormattedId()
    {
        return themeID.getResourceDomain() + "." + themeID.getResourcePath();
    }

    /**
     * Gets the name of the application
     *
     * @return the application name
     */
    public String getName()
    {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public boolean hasSingleCreator() {
        return (this.creator != null && this.creators == null);
    }

    public String[] getCreators() {
        return creators;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public String getIcon() {
        return icon;
    }

    public int getIconU() {
        return iconU;
    }

    public int getIconV() {
        return iconV;
    }

    public String[] getScreenshots() {
        return screenshots;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof ThemeInfo)) return false;
        ThemeInfo info = (ThemeInfo) obj;
        return this == info || getFormattedId().equals(info.getFormattedId());
    }

    public void reload() {
        resetInfo();
        InputStream stream = ClientProxy.class.getResourceAsStream("/assets/" + themeID.getResourceDomain() + "/themes/" + themeID.getResourcePath() + ".json");

        if(stream == null)
            throw new RuntimeException("Missing theme info json for '" + themeID + "'");

        Reader reader = new InputStreamReader(stream);
        JsonParser parser = new JsonParser();
        JsonElement obj = parser.parse(reader);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ThemeInfo.class, new ThemeInfo.Deserializer(this));
        Gson gson = builder.create();
        gson.fromJson(obj, ThemeInfo.class);
    }

    private void resetInfo() {
        name = null;
        creator = null;
        description = null;
        version = null;
        icon = null;
        screenshots = null;
    }

    public static class Deserializer implements JsonDeserializer<ThemeInfo> {

        private static final Pattern LANG = Pattern.compile("\\$\\{[a-z]+}");

        private static final String NAME = "theme_name";
        private static final String AUTHOR = "theme_author";
        private static final String AUTHORS = "theme_authors";
        private static final String DESC = "theme_description";
        private static final String VERSION = "theme_version";
        private static final String SCREENS = "theme_screenshots";
        private static final String ICON = "theme_icon";

        private ThemeInfo info;

        public Deserializer(ThemeInfo info) {
            this.info = info;
        }

        @Override
        public ThemeInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            try {
                info.name = convertToLocal(json.getAsJsonObject().get(NAME).getAsString());
                if (json.getAsJsonObject().has(AUTHOR))
                    info.creator = convertToLocal(json.getAsJsonObject().get(AUTHOR).getAsString());
                else if (json.getAsJsonObject().has(AUTHORS) && json.getAsJsonObject().get(AUTHORS).isJsonArray()) {
                    info.creators = context.deserialize(json.getAsJsonObject().get(AUTHORS), new TypeToken<String[]>() {
                    }.getType());
                }
                info.description = convertToLocal(json.getAsJsonObject().get(DESC).getAsString());
                info.version = json.getAsJsonObject().get(VERSION).getAsString();

                if (json.getAsJsonObject().has(SCREENS) && json.getAsJsonObject().get(SCREENS).isJsonArray()) {
                    info.screenshots = context.deserialize(json.getAsJsonObject().get(SCREENS), new TypeToken<String[]>() {
                    }.getType());
                }

                if (json.getAsJsonObject().has(ICON) && json.getAsJsonObject().get(ICON).isJsonPrimitive()) {
                    info.icon = json.getAsJsonObject().get(ICON).getAsString();
                }
            } catch (JsonParseException e) {
                HuskyGadgetMod.getLogger().error("Malformed theme info json for '" + info.getFormattedId() + "'");
            }

            return info;
        }

        private String[] deserializeArray(JsonElement json, String name, JsonDeserializationContext context) {
            return context.deserialize(json.getAsJsonObject().get(name), new TypeToken<String[]>() {
            }.getType());
        }

        private String convertToLocal(String s) {
            Matcher m = LANG.matcher(s);
            while (m.find()) {
                String found = m.group();
                s = s.replace(found, I18n.format("theme." + info.getFormattedId() + "." + found.substring(2, found.length() - 1)));
            }
            return s;
        }
    }
}
