package com.easy.imc.clienteasyimc.entities;

public class IMC {
    public double taille;
    public double poids;
    public double value;
    public UnitePoids unitePoids;
    public UniteTaille uniteTaille;
    public User user;
    public boolean forMe = false;

    public IMC(double taille, double poids) {
        this.taille = taille;
        this.poids = poids;
    }

    public IMC() {
    }

    @Override
    public String toString() {
        return "{" +
                "\"taille\":" + taille +
                ", \"poids\":" + poids +
                ", \"value\":" + value +
                ", \"unitePoids\":" + unitePoids +
                ", \"uniteTaille\":" + uniteTaille +
                ", \"user\":" + user +
                ", \"forMe\":" + forMe +
                '}';
    }
}
