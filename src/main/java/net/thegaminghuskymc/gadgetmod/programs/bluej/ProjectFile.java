package net.thegaminghuskymc.gadgetmod.programs.bluej;

import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.gadgetmod.programs.bluej.api.BlueJLanguageManager;
import net.thegaminghuskymc.gadgetmod.programs.bluej.api.SyntaxHighlighter;
import net.thegaminghuskymc.gadgetmod.programs.bluej.resources.BlueJResolvedResloc;
import net.thegaminghuskymc.gadgetmod.programs.bluej.resources.BlueJResourceLocation;

public class ProjectFile {

    private static final String FILENAME_TAG = "name";
    private static final String CODE_TAG = "code";
    private static final String LANGUAGE_TAG = "lang";

    private String name;
    private String code;
    private SyntaxHighlighter language;

    public ProjectFile(String name, String code, SyntaxHighlighter language) {
        this.name = name;
        this.code = code;
        this.language = language;
    }

    public static ProjectFile fromNBT(NBTTagCompound data) {
        String name = data.getString(FILENAME_TAG);
        String code = data.getString(CODE_TAG);
        String lang = data.getString(LANGUAGE_TAG);
        SyntaxHighlighter language;
        if (lang.equals("Text")) {
            language = null;
        } else {
            language = BlueJLanguageManager.getHighlighters().get(lang);
        }
        return new ProjectFile(name, code, language);
    }

    public void save(BlueJResourceLocation baseFolder) {
        BlueJResolvedResloc resloc = baseFolder.resolve();
        BlueJResolvedResloc file = resloc.getFile(name);
        if (!file.exists()) file.create();
        file.setData(toNBT());
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SyntaxHighlighter getLanguage() {
        return language;
    }

    public void setLanguage(SyntaxHighlighter language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "ProjectFile [name=" + name + ", code=" + code + ", language=" + language + "]";
    }

    public NBTTagCompound toNBT() {
        NBTTagCompound file = new NBTTagCompound();
        file.setString(FILENAME_TAG, name);
        file.setString(CODE_TAG, code);
        if (language != null) file.setString(LANGUAGE_TAG, language.getName());
        else file.setString(LANGUAGE_TAG, "Text");
        file.setString(Project.TYPE_TAG, Project.TYPE_CODE_FILE);
        return file;
    }

}
