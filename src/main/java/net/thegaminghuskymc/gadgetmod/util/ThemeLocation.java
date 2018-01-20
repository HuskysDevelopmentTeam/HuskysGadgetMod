package net.thegaminghuskymc.gadgetmod.util;

import com.google.gson.*;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Type;
import java.util.Locale;

public class ThemeLocation implements Comparable<ThemeLocation> {

    protected final String themeDomain;
    protected final String themePath;

    protected ThemeLocation(int unused, String... themeName) {
        this.themeDomain = org.apache.commons.lang3.StringUtils.isEmpty(themeName[0]) ? "hgm" : themeName[0].toLowerCase(Locale.ROOT);
        this.themePath = themeName[1].toLowerCase(Locale.ROOT);
        Validate.notNull(this.themePath);
    }

    public ThemeLocation(String themeName) {
        this(0, splitObjectName(themeName));
    }

    public ThemeLocation(String themeDomainIn, String themePathIn) {
        this(0, themeDomainIn, themePathIn);
    }

    /**
     * Splits an object name (such as minecraft:apple) into the domain and path parts and returns these as an array of
     * length 2. If no colon is present in the passed value the returned array will contain {null, toSplit}.
     */
    public static String[] splitObjectName(String toSplit) {
        String[] astring = new String[]{"hgm", toSplit};
        int i = toSplit.indexOf(58);

        if (i >= 0) {
            astring[1] = toSplit.substring(i + 1, toSplit.length());

            if (i > 1) {
                astring[0] = toSplit.substring(0, i);
            }
        }

        return astring;
    }

    public String getThemePath() {
        return this.themePath;
    }

    public String getThemeDomain() {
        return this.themeDomain;
    }

    public String toString() {
        return this.themeDomain + ':' + this.themePath;
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        } else if (!(p_equals_1_ instanceof ThemeLocation)) {
            return false;
        } else {
            ThemeLocation resourcelocation = (ThemeLocation) p_equals_1_;
            return this.themeDomain.equals(resourcelocation.themeDomain) && this.themePath.equals(resourcelocation.themePath);
        }
    }

    public int hashCode() {
        return 31 * this.themeDomain.hashCode() + this.themePath.hashCode();
    }

    public int compareTo(ThemeLocation p_compareTo_1_) {
        int i = this.themeDomain.compareTo(p_compareTo_1_.themeDomain);

        if (i == 0) {
            i = this.themePath.compareTo(p_compareTo_1_.themePath);
        }

        return i;
    }

    public static class Serializer implements JsonDeserializer<ThemeLocation>, JsonSerializer<ThemeLocation> {
        public ThemeLocation deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
            return new ThemeLocation(JsonUtils.getString(p_deserialize_1_, "location"));
        }

        public JsonElement serialize(ThemeLocation p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
            return new JsonPrimitive(p_serialize_1_.toString());
        }
    }
}