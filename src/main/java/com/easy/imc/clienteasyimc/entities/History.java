package com.easy.imc.clienteasyimc.entities;

public class History {
    public int id;
    public int idUser;
    public double poids;
    public double taille;
    public double imc;

    public int idUnitePoids;

    public int idUniteTaille;

    public String date;

    public String heure;

    public int idCategory;

    public History() {
        this.id = 0;
        this.idUser = 0;
        this.poids = 0;
        this.taille = 0;
        this.imc = 0;
        this.idUnitePoids = 0;
        this.idUniteTaille = 0;
        this.date = "";
        this.heure = "";
        this.idCategory = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdUnitePoids() {
        return idUnitePoids;
    }

    public void setIdUnitePoids(int idUnitePoids) {
        this.idUnitePoids = idUnitePoids;
    }

    public int getIdUniteTaille() {
        return idUniteTaille;
    }

    public void setIdUniteTaille(int idUniteTaille) {
        this.idUniteTaille = idUniteTaille;
    }


    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"idUser\":" + idUser +
                ", \"poids\":" + poids +
                ", \"taille\":" + taille +
                ", \"imc\":" + imc +
                ", \"idUnitePoids\":" + idUnitePoids +
                ", \"idUniteTaille\":" + idUniteTaille +
                ", \"date\":\"" + date + '"' +
                ", \"heure\":\"" + heure + '"' +
                ", \"idCategory\":" + idCategory +
                "}";
    }
}
