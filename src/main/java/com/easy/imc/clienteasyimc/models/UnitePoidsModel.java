package com.easy.imc.clienteasyimc.models;

import com.easy.imc.clienteasyimc.entities.UnitePoids;

public class UnitePoidsModel {
    public int id;
    public String name;
    public double value;

    public UnitePoidsModel() {
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }

    public UnitePoids toEntity(){
        return new UnitePoids(id, name, value);
    }
}
