package com.easy.imc.clienteasyimc.entities;

import com.easy.imc.clienteasyimc.models.AgeCategorieModel;

public class AgeCategorie {
    public int id;
    public String name;
    public int minAge;
    public int maxAge;
    public double minVar;
    public double maxVar;

    public AgeCategorie(int id, String name, int minAge, int maxAge, double minVar, double maxVar) {
        this.id = id;
        this.name = name;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.minVar = minVar;
        this.maxVar = maxVar;
    }

    public AgeCategorie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public double getMinVar() {
        return minVar;
    }

    public void setMinVar(double minVar) {
        this.minVar = minVar;
    }

    public double getMaxVar() {
        return maxVar;
    }

    public void setMaxVar(double maxVar) {
        this.maxVar = maxVar;
    }

    public AgeCategorieModel toModel(){
        return new AgeCategorieModel(id, name, minAge, maxAge,  minVar, maxVar);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '"' +
                ", \"minAge\":" + minAge +
                ", \"maxAge\":" + maxAge +
                ", \"minVar\":" + minVar +
                ", \"maxVar\":" + maxVar +
                '}';
    }
}
