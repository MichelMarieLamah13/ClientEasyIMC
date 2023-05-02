package com.easy.imc.clienteasyimc.models;

public class DescriptionModel {
    public int id;
    public CategoryModel category;
    public String description;

    public DescriptionModel() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
