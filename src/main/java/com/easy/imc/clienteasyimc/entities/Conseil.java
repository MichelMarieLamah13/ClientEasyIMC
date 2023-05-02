package com.easy.imc.clienteasyimc.entities;

public class Conseil {
    public int id;
    public int idCategory;
    public String conseil;

    public Conseil() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getConseil() {
        return conseil;
    }

    public void setConseil(String conseil) {
        this.conseil = conseil;
    }
}
