package com.easy.imc.clienteasyimc.models;

import com.easy.imc.clienteasyimc.entities.User;

public class UserModel {
    public int id;
    public String login;
    public String password;
    public String avatar;
    public int role;

    public UserModel() {
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

    public User toEntity(){
        return new User(id, login, password, avatar, role);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"login\":\"" + login + '"' +
                ", \"password\":\"" + password + '"' +
                ", \"avatar\":" + avatar + '"' +
                ", \"role\":" + role +
                '}';
    }
}
