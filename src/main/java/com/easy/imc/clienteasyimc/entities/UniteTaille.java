package com.easy.imc.clienteasyimc.entities;

public class UniteTaille {
    public int id;
    public String name;
    public double value;

    public UniteTaille(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public UniteTaille() {
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

    public UniteTaille(int id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '"' +
                ", \"value\":" + value +
                '}';
    }
}
