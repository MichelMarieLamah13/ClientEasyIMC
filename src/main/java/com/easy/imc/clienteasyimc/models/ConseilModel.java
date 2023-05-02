package com.easy.imc.clienteasyimc.models;

public class ConseilModel {
    public int id;
    public CategoryModel category;
    public String conseil;

    public ConseilModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public String getConseil() {
        return conseil;
    }

    public void setConseil(String conseil) {
        this.conseil = conseil;
    }
}
