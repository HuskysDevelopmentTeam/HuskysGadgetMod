package io.github.vampirestudios.gadget.object;

import net.minecraft.util.ResourceLocation;

public class ImageEntry {
    private Type type;
    private ResourceLocation resource;
    private String url;

    public ImageEntry(ResourceLocation resource) {
        this.type = Type.LOCAL;
        this.resource = resource;
    }

    public ImageEntry(String url) {
        this.type = Type.REMOTE;
        this.url = url;
    }

    public Type getType() {
        return type;
    }

    public ResourceLocation getResource() {
        return resource;
    }

    public String getUrl() {
        return url;
    }

    public enum Type {
        LOCAL, REMOTE;
    }
}