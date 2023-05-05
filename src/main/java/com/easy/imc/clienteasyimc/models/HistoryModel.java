package com.easy.imc.clienteasyimc.models;

import com.easy.imc.clienteasyimc.entities.User;

public class HistoryModel {
    public int id;
    public UserModel user;
    public UniteTailleModel uniteTaille;
    public UnitePoidsModel unitePoids;
    public double poids;
    public double taille;
    public double imc;
    public String date;
    public String heure;
    public CategoryModel category;

    public boolean save;

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public UniteTailleModel getUniteTaille() {
        return uniteTaille;
    }

    public void setUniteTaille(UniteTailleModel uniteTaille) {
        this.uniteTaille = uniteTaille;
    }

    public UnitePoidsModel getUnitePoids() {
        return unitePoids;
    }

    public void setUnitePoids(UnitePoidsModel unitePoids) {
        this.unitePoids = unitePoids;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"user\":" + user +
                ", \"uniteTaille\":" + uniteTaille +
                ", \"unitePoids\":" + unitePoids +
                ", \"poids\":" + poids +
                ", \"taille\":" + taille +
                ", \"imc\":" + imc +
                ", \"date\":\"" + date + '"' +
                ", \"heure\":\"" + heure + '"' +
                ", \"category\":" + category +
                '}';
    }
}
