package net.thegaminghuskymc.gadgetmod.programs.bluej.api;

public class Token {

    private String token;
    private int color;

    public Token(String token, int color) {
        this.token = token;
        this.color = color;
    }

    public String getToken() {
        return token;
    }

    public int getColor() {
        return color;
    }

}
