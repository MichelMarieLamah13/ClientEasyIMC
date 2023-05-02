package com.easy.imc.clienteasyimc.entities;


import java.util.List;

public class IMCResponse<T> {
    public String message;
    public List<T> values;
    public int status;
    public String name;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "\"message\"=:\"" + message + '"' +
                ", \"values\":" + values +
                ", \"status\":" + status +
                ", \"name\":\"" + name + '"' +
                '}';
    }
}
