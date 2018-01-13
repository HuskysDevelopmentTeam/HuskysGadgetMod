package net.thegaminghuskymc.gadgetmod.programs.email.object;

public class Contact {
    private String nickname;
    private String email;

    public Contact(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return nickname;
    }
}