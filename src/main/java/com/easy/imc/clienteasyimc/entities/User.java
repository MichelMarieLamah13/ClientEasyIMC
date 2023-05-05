package com.easy.imc.clienteasyimc.entities;

public class User {
    public int id;
    public String login;
    public String password;
    public String avatar;

    public int role;

    public int age;

    public int idAgeCategorie;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"login\":\"" + login + '"' +
                ", \"password\":\"" + password + '"' +
                ", \"avatar\":\"" + avatar + '"' +
                ", \"role\":" + role +
                ", \"age\":" + age +
                ", \"idAgeCategorie\":" + idAgeCategorie +
                "}";
    }

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, int age) {
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public User(int id, String login, String password, String avatar, int role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
    }

    public User(int id, String login, String password, String avatar, int role, int age, int idAgeCategorie) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.age = age;
        this.idAgeCategorie = idAgeCategorie;
    }

    public User(int id, String login, String password, String avatar, int role, int age) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIdAgeCategorie() {
        return idAgeCategorie;
    }

    public void setIdAgeCategorie(int idAgeCategorie) {
        this.idAgeCategorie = idAgeCategorie;
    }
}
