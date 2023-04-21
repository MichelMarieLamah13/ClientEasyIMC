module com.easy.imc.clienteasyimc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.easy.imc.clienteasyimc to javafx.fxml;
    exports com.easy.imc.clienteasyimc;
}