package com.easy.imc.clienteasyimc.entities;

public class User {
    public int id;
    public String login;
    public String password;
    public String avatar;

    public int role;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"login\":\"" + login + '"' +
                ", \"password\":\"" + password + '"' +
                ", \"avatar\":\"" + avatar + '"' +
                ", \"role\":" + role +
                "}";
    }

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, String avatar, int role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
    }

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
