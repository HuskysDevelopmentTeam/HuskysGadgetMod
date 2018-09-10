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

public class Themes {

    private final ResourceLocation THEME_ID;
    private transient int iconU = 0, iconV = 0;
    private transient boolean defaultTheme;

    private String themeName;
    private String themeCreator;
    private String themeDescription;
    private String themeVersion;
    private String themePreview;
    private String[] themeWallpapers;

    public Themes(ResourceLocation identifier, boolean isDefaultTheme) {
        this.THEME_ID = identifier;
        this.defaultTheme = isDefaultTheme;
    }

    public ResourceLocation getId() {
        return THEME_ID;
    }

    /**
     * Gets the formatted version of the application's id
     *
     * @return a formatted id
     */
    public String getFormattedId() {
        return THEME_ID.getNamespace() + "." + THEME_ID.getPath();
    }

    public String getThemeCreator() {
        return themeCreator;
    }

    public String getThemeName() {
        return themeName;
    }

    public String getThemeDescription() {
        return themeDescription;
    }

    public String getThemePreview() {
        return themePreview;
    }

    public String getThemeVersion() {
        return themeVersion;
    }

    public String[] getThemeWallpapers() {
        return themeWallpapers;
    }

    public int getIconU() {
        return iconU;
    }

    public int getIconV() {
        return iconV;
    }

    public boolean isDefaultTheme() {
        return defaultTheme;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Themes)) return false;
        Themes info = (Themes) obj;
        return this == info || getFormattedId().equals(info.getFormattedId());
    }

    public void reload() {
        resetInfo();
        InputStream stream = ClientProxy.class.getResourceAsStream("/assets/" + THEME_ID.getNamespace() + "/themes/" + THEME_ID.getPath() + ".json");

        if (stream == null)
            throw new RuntimeException("Missing theme json for '" + THEME_ID + "'");

        Reader reader = new InputStreamReader(stream);
        JsonParser parser = new JsonParser();
        JsonElement obj = parser.parse(reader);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Themes.class, new Themes.Deserializer(this));
        Gson gson = builder.create();
        gson.fromJson(obj, Themes.class);
    }

    private void resetInfo() {
        themeName = null;
        themeCreator = null;
        themeDescription = null;
        themeVersion = null;
        themeWallpapers = null;
        themePreview = null;
    }

    public static class Deserializer implements JsonDeserializer<Themes> {

        private static final Pattern LANG = Pattern.compile("\\$\\{[a-z]+}");

        private Themes themes;

        public Deserializer(Themes themes) {
            this.themes = themes;
        }

        @Override
        public Themes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            try {
                themes.themeName = convertToLocal(json.getAsJsonObject().get("theme_name").getAsString());
                themes.themeCreator = convertToLocal(json.getAsJsonObject().get("theme_creator").getAsString());
                themes.themeDescription = convertToLocal(json.getAsJsonObject().get("theme_description").getAsString());
                themes.themeVersion = json.getAsJsonObject().get("theme_version").getAsString();

                if (json.getAsJsonObject().has("theme_wallpapers") && json.getAsJsonObject().get("theme_wallpapers").isJsonArray()) {
                    themes.themeWallpapers = context.deserialize(json.getAsJsonObject().get("theme_wallpapers"), new TypeToken<String[]>() {
                    }.getType());
                }

                if (json.getAsJsonObject().has("theme_preview") && json.getAsJsonObject().get("theme_preview").isJsonPrimitive()) {
                    themes.themePreview = json.getAsJsonObject().get("theme_preview").getAsString();
                }
            } catch (JsonParseException e) {
                HuskyGadgetMod.getLogger().error("Malformed theme json for '" + themes.getFormattedId() + "'");
            }

            return themes;
        }

        private String convertToLocal(String s) {
            Matcher m = LANG.matcher(s);
            while (m.find()) {
                String found = m.group();
                s = s.replace(found, I18n.format("theme." + themes.getFormattedId() + "." + found.substring(2, found.length() - 1)));
            }
            return s;
        }
    }

}
