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

public class TestClass {

    public static final Comparator<TestClass> SORT_NAME = Comparator.comparing(TestClass::getName);

    private transient final ResourceLocation APP_ID;
    private transient int iconU = 0;
    private transient int iconV = 0;
    private transient boolean systemApp;

    private String name;
    private String author;
    private String[] authors;
    private String[] contributors;
    private String description;
    private String version;
    private String icon;
    private String banner;
    private String[] screenshots;
    private Support support;

    public TestClass(ResourceLocation identifier, boolean isSystemApp)
    {
        this.APP_ID = identifier;
        this.systemApp = isSystemApp;
    }

    /**
     * Gets the id of the application
     *
     * @return the app resource location
     */
    public ResourceLocation getId()
    {
        return APP_ID;
    }

    /**
     * Gets the formatted version of the application's id
     *
     * @return a formatted id
     */
    public String getFormattedId()
    {
        return APP_ID.getNamespace() + "." + APP_ID.getPath();
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

    public String getAuthor() {
        return author;
    }

    public boolean hasSingleAuthor() {
        return (this.author != null && this.authors == null);
    }

    public String[] getAuthors() {
        return authors;
    }

    public boolean hasContributors() {
        return this.contributors != null && this.contributors.length > 0;
    }

    public String[] getContributors() {
        return this.contributors;
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

    public String getBanner() {
        return banner;
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

    public Support getSupport() {
        return support;
    }

    public boolean isSystemApp()
    {
        return systemApp;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof TestClass)) return false;
        TestClass info = (TestClass) obj;
        return this == info || getFormattedId().equals(info.getFormattedId());
    }

    public void reload() {
        resetInfo();
        InputStream stream = ClientProxy.class.getResourceAsStream("/assets/" + APP_ID.getNamespace() + "/apps/" + APP_ID.getPath() + ".json");

        if(stream == null)
            throw new RuntimeException("Missing app info json for '" + APP_ID + "'");

        Reader reader = new InputStreamReader(stream);
        JsonParser parser = new JsonParser();
        JsonElement obj = parser.parse(reader);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(TestClass.class, new TestClass.Deserializer(this));
        Gson gson = builder.create();
        gson.fromJson(obj, TestClass.class);
    }

    private void resetInfo() {
        name = null;
        author = null;
        description = null;
        contributors = null;
        version = null;
        icon = null;
        banner = null;
        screenshots = null;
        support = null;
    }

    public static class Support {
        private String paypal;
        private String patreon;
        private String twitter;
        private String youtube;

        public String getPaypal()
        {
            return paypal;
        }

        public String getPatreon()
        {
            return patreon;
        }

        public String getTwitter()
        {
            return twitter;
        }

        public String getYoutube()
        {
            return youtube;
        }
    }

    public static class Deserializer implements JsonDeserializer<TestClass> {

        private static final Pattern LANG = Pattern.compile("\\$\\{[a-z]+}");

        private static final String NAME = "app_name";
        private static final String AUTHOR = "app_author";
        private static final String AUTHORS = "app_authors";
        private static final String CONTRIBUTORS = "app_contributors";
        private static final String DESC = "app_description";
        private static final String VERSION = "app_version";
        private static final String SCREENS = "app_screenshots";
        private static final String ICON = "app_icon";
        private static final String BANNER = "app_banner";
        private static final String SUPPORT = "app_support";

        private TestClass info;

        public Deserializer(TestClass info) {
            this.info = info;
        }

        @Override
        public TestClass deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            try {
                info.name = convertToLocal(json.getAsJsonObject().get(NAME).getAsString());
                if (json.getAsJsonObject().has(AUTHOR))
                    info.author = convertToLocal(json.getAsJsonObject().get(AUTHOR).getAsString());
                else if (json.getAsJsonObject().has(AUTHORS) && json.getAsJsonObject().get(AUTHORS).isJsonArray()) {
                    info.authors = context.deserialize(json.getAsJsonObject().get(AUTHORS), new TypeToken<String[]>() {
                    }.getType());
                }
                if (json.getAsJsonObject().has(CONTRIBUTORS) && json.getAsJsonObject().get(CONTRIBUTORS).isJsonArray()) {
                    info.contributors = this.deserializeArray(json, CONTRIBUTORS, context);
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

                if (json.getAsJsonObject().has(BANNER) && json.getAsJsonObject().get(BANNER).isJsonPrimitive()) {
                    info.banner = json.getAsJsonObject().get(BANNER).getAsString();
                }

                if (json.getAsJsonObject().has(SUPPORT) && json.getAsJsonObject().get(SUPPORT).getAsJsonObject().size() > 0) {
                    JsonObject supportObj = json.getAsJsonObject().get(SUPPORT).getAsJsonObject();
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

                    info.support = support;
                }
            } catch (JsonParseException e) {
                HuskyGadgetMod.getLogger().error("Malformed app info json for '" + info.getFormattedId() + "'");
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
                s = s.replace(found, I18n.format("app." + info.getFormattedId() + "." + found.substring(2, found.length() - 1)));
            }
            return s;
        }
    }
}
