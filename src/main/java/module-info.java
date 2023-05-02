module com.easy.imc.clienteasyimc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.easy.imc.clienteasyimc to javafx.fxml;
    exports com.easy.imc.clienteasyimc;
    exports com.easy.imc.clienteasyimc.controllers;
    opens com.easy.imc.clienteasyimc.controllers to javafx.fxml;

    exports com.easy.imc.clienteasyimc.models;
    opens com.easy.imc.clienteasyimc.models to com.fasterxml.jackson.databind, com.fasterxml.jackson.core, com.fasterxml.jackson.annotation;

    exports com.easy.imc.clienteasyimc.entities;
    opens com.easy.imc.clienteasyimc.entities to com.fasterxml.jackson.databind, com.fasterxml.jackson.core, com.fasterxml.jackson.annotation;


}