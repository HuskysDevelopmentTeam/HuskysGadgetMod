package json_generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import net.minecraft.item.EnumDyeColor;

import java.io.*;

public class JsonGenerator {

    public static void main(String[] args) {

        for (EnumDyeColor type : EnumDyeColor.values()) {
//            genBlock("hgm", "office_chair_" + type.getName(), "");
            genBlock("hgm", "laptop_" + type.getName(), "laptop");
        }

    }

    private static void genBlock(String modId, String blockName, String textureName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\blockstates\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("forge_marker").value(1);
            jw.name("defaults");
            jw.beginObject();
            jw.name("model").value(modId + ":" + "laptop");
            jw.name("transform").value("forge:default-block");
            jw.endObject();
            jw.name("variants");
            jw.beginObject();

            jw.name("normal");
            jw.beginArray();
            jw.beginObject();
            jw.endObject();
            jw.endArray();

            jw.name("inventory");
            jw.beginArray();
            jw.beginObject();
            jw.endObject();
            jw.endArray();

            jw.endObject();
            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        genBlockModel(modId, blockName, textureName);
        genBlockItemModel(modId, blockName, textureName);

    }

    private static void genBlockModel(String modId, String blockName, String textureName) {
        try {

            File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\block\\");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value(modId + ":" + "block/" + "laptop");
            jw.name("textures");
            jw.beginObject();
            jw.name("normal").value(modId + ":blocks/" + textureName);
            jw.endObject();
            jw.endObject();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void genBlockItemModel(String modId, String blockName, String textureName) {
        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\item\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();

            jw.name("parent").value("th2:block/laptop_open");
            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genOrientedBlock(String modId, String blockName, String topTextureName, String frontTextureName, String sidesTextureName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\blockstates\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("variants");
            jw.beginObject();

            jw.name("facing=north");
            jw.beginObject();
            jw.name("model").value(blockName);
            jw.endObject();

            jw.name("facing=south");
            jw.beginObject();
            jw.name("model").value(blockName);
            jw.name("y").value(180);
            jw.endObject();

            jw.name("facing=west");
            jw.beginObject();
            jw.name("model").value(blockName);
            jw.name("y").value(270);
            jw.endObject();

            jw.name("facing=east");
            jw.beginObject();
            jw.name("model").value(blockName);
            jw.name("y").value(90);
            jw.endObject();

            jw.endObject();
            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        genBlockOrientedModel(modId, blockName, topTextureName, frontTextureName, sidesTextureName);
        genBlockOrientedItemModel(modId, blockName);

    }

    private static void genBlockOrientedModel(String modId, String blockName, String topTextureName, String frontTextureName, String sidesTextureName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\block\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value("block/orientable");
            jw.name("textures");
            jw.beginObject();
            jw.name("top").value(modId + ":blocks/" + topTextureName);
            jw.name("front").value(modId + ":blocks/" + frontTextureName);
            jw.name("side").value(modId + ":blocks/" + sidesTextureName);
            jw.endObject();
            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void genBlockOrientedItemModel(String modId, String blockName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\item\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();

            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value(modId + ":block/" + blockName);

            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void genPressurePlateBlock(String modId, String blockName, String textureName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\blockstates\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("variants");
            jw.beginObject();

            jw.name("powered=false");
            jw.beginObject();
            jw.name("model").value(modId + ":" + blockName + "_up");
            jw.endObject();

            jw.name("powered=true");
            jw.beginObject();
            jw.name("model").value(modId + ":" + blockName + "_down");
            jw.endObject();

            jw.endObject();
            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        genBlockPressurePlateModel(modId, blockName, textureName);
        genBlockPressurePlateItemModel(modId, blockName);

    }

    private static void genBlockPressurePlateModel(String modId, String blockName, String textureName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\block\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + "_up" + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value("block/pressure_plate_up");
            jw.name("textures");
            jw.beginObject();
            jw.name("texture").value(modId + ":blocks/" + textureName);
            jw.endObject();
            jw.endObject();

            writer.close();

            Writer writer2 = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + "_down" + ".json"), "UTF-8");
            JsonWriter jw2 = gson.newJsonWriter(writer2);

            jw2.beginObject();
            jw2.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw2.name("parent").value("block/pressure_plate_down");
            jw2.name("textures");
            jw2.beginObject();
            jw2.name("texture").value(modId + ":blocks/" + textureName);
            jw2.endObject();
            jw2.endObject();

            writer2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void genBlockPressurePlateItemModel(String modId, String blockName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\item\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();

            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value(modId + ":block/" + blockName + "_up");

            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void genSlabBlock(String modId, String blockName, String textureName, String blockMockName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\blockstates\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("variants");
            jw.beginObject();

            jw.name("half=bottom");
            jw.beginObject();
            jw.name("model").value(modId + ":half_" + blockName);
            jw.endObject();

            jw.name("half=top");
            jw.beginObject();
            jw.name("model").value(modId + ":upper_" + blockName);
            jw.endObject();

            jw.endObject();
            jw.endObject();

            writer.close();

            Writer writer2 = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + "_double" + ".json"), "UTF-8");
            Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw2 = gson2.newJsonWriter(writer2);

            jw2.beginObject();
            jw2.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw2.name("variants");
            jw2.beginObject();

            jw2.name("normal");
            jw2.beginObject();
            jw2.name("model").value(modId + ":" + blockMockName);
            jw2.endObject();

            jw2.name("all");
            jw2.beginObject();
            jw2.name("model").value(modId + ":" + blockMockName);
            jw2.endObject();

            jw2.endObject();
            jw2.endObject();

            writer2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        genBlockSlabModel(modId, blockName, textureName);
        genBlockSlabItemModel(modId, blockName);

    }

    private static void genBlockSlabModel(String modId, String blockName, String textureName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\block\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + "half_" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value("th2:block/cube_bottom_half_overlay_all");
            jw.name("textures");
            jw.beginObject();
            jw.name("overlay").value(modId + ":blocks/runes/" + textureName);
            jw.name("all").value(modId + ":blocks/rune_stone");
            jw.endObject();
            jw.endObject();

            writer.close();

            Writer writer2 = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + "upper_" + blockName + ".json"), "UTF-8");
            JsonWriter jw2 = gson.newJsonWriter(writer2);

            jw2.beginObject();
            jw2.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw2.name("parent").value("th2:block/cube_top_half_overlay_all");
            jw2.name("textures");
            jw2.beginObject();
            jw2.name("overlay").value(modId + ":blocks/runes/" + textureName);
            jw2.name("all").value(modId + ":blocks/rune_stone");
            jw2.endObject();
            jw2.endObject();

            writer2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void genBlockSlabItemModel(String modId, String blockName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\item\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();

            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value(modId + ":block/" + "half_" + blockName);

            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void genFenceBlock(String modId, String blockName, String textureName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\blockstates\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("multipart");
            jw.beginArray();
            jw.beginObject();
            jw.name("apply");
            jw.beginObject();
            jw.name("model").value(modId + ":" + blockName + "_post");
            jw.endObject();
            jw.endObject();
            jw.beginObject();
            jw.name("when");
            jw.beginObject();
            jw.name("north").value("true");
            jw.endObject();
            jw.name("apply");
            jw.beginObject();
            jw.name("model").value(modId + ":" + blockName + "_side");
            jw.name("uvlock").value(true);
            jw.endObject();
            jw.endObject();
            jw.beginObject();
            jw.name("when");
            jw.beginObject();
            jw.name("east").value("true");
            jw.endObject();
            jw.name("apply");
            jw.beginObject();
            jw.name("model").value(modId + ":" + blockName + "_side");
            jw.name("y").value(90);
            jw.name("uvlock").value(true);
            jw.endObject();
            jw.endObject();
            jw.beginObject();
            jw.name("when");
            jw.beginObject();
            jw.name("south").value("true");
            jw.endObject();
            jw.name("apply");
            jw.beginObject();
            jw.name("model").value(modId + ":" + blockName + "_side");
            jw.name("y").value(180);
            jw.name("uvlock").value(true);
            jw.endObject();
            jw.endObject();
            jw.beginObject();
            jw.name("when");
            jw.beginObject();
            jw.name("west").value("true");
            jw.endObject();
            jw.name("apply");
            jw.beginObject();
            jw.name("model").value(modId + ":" + blockName + "_side");
            jw.name("y").value(270);
            jw.name("uvlock").value(true);
            jw.endObject();
            jw.endObject();
            jw.endArray();
            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        genBlockFenceModel(modId, blockName, textureName);
        genBlockFenceItemModel(modId, blockName);

    }

    private static void genBlockFenceModel(String modId, String blockName, String textureName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\block\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + "_post" + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value("block/fence_post");
            jw.name("textures");
            jw.beginObject();
            jw.name("texture").value(modId + ":blocks/" + textureName);
            jw.endObject();
            jw.endObject();

            writer.close();

            Writer writer2 = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + "_side" + ".json"), "UTF-8");
            JsonWriter jw2 = gson.newJsonWriter(writer2);

            jw2.beginObject();
            jw2.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw2.name("parent").value("block/fence_side");
            jw2.name("textures");
            jw2.beginObject();
            jw2.name("texture").value(modId + ":blocks/" + textureName);
            jw2.endObject();
            jw2.endObject();

            writer2.close();

            Writer writer3 = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + "_inventory" + ".json"), "UTF-8");
            JsonWriter jw3 = gson.newJsonWriter(writer3);

            jw3.beginObject();
            jw3.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw3.name("parent").value("block/fence_inventory");
            jw3.name("textures");
            jw3.beginObject();
            jw3.name("texture").value(modId + ":blocks/" + textureName);
            jw3.endObject();
            jw3.endObject();

            writer3.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void genBlockFenceItemModel(String modId, String blockName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\item\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + blockName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();

            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value(modId + ":block/" + blockName + "_inventory");

            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void genModInfo(String modId, String modName, String version, String gameVersion, String author, String url, String description, String credits) {

        File fileDir = new File("src\\main\\resources\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\mcmod.info"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginArray();
            jw.beginObject();

            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("modid").value(modId);
            jw.name("name").value(modName);
            jw.name("description").value(description);
            jw.name("version").value(version);
            jw.name("credits").value(credits);
            jw.name("logoFile").value("");
            jw.name("mcversion").value(gameVersion);
            jw.name("url").value(url);
            jw.name("updateUrl").value("");
            jw.name("authorList");
            jw.beginArray();
            jw.value(author);
            jw.endArray();
            jw.name("parent").value("");
            jw.name("screenshots");
            jw.beginArray();
            jw.endArray();
            jw.name("dependencies");
            jw.beginArray();
            jw.value("mod_MinecraftForge");
            jw.endArray();

            jw.endObject();
            jw.endArray();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genShapedRecipe(String modId, String name, String row1, String row2, String row3, String[] keys, String[] values, String result, int resultCount) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "recipes\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\ " + name + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();

            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("type").value("crafting_shaped");
            jw.name("pattern");
            jw.beginArray();
            jw.value(row1);
            jw.value(row2);
            jw.value(row3);
            jw.endArray();
            jw.name("key");
            jw.beginObject();

            for (int i = 0; i <= keys.length - 1; i++) {
                if (!values[i].equalsIgnoreCase("")) {
                    jw.name(keys[i]);
                    jw.beginObject();
                    jw.name("item").value(values[i]);
                    jw.endObject();
                }
            }

            jw.endObject();

            jw.name("result");
            jw.beginObject();

            jw.name("item").value(result);
            if (resultCount > 1) jw.name("count").value(resultCount);

            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genItemModel(String modId, String itemName, String textureName) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\item\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + itemName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value("item/generated");
            jw.name("textures");
            jw.beginObject();
            jw.name("layer0").value(modId + ":items/" + textureName);
            jw.endObject();
            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genToolModel(String modId, String itemName, String textureName, String path) {

        File fileDir = new File("src\\main\\resources\\assets\\" + modId + "\\models\\item\\");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileDir + "\\" + itemName + ".json"), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonWriter jw = gson.newJsonWriter(writer);

            jw.beginObject();
            jw.name("_comment").value("Generated using Husky's JSON Generator v1.");
            jw.name("parent").value("item/handheld");
            jw.name("textures");
            jw.beginObject();
            jw.name("layer0").value(modId + ":items/" + textureName);
            jw.endObject();
            jw.endObject();

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}