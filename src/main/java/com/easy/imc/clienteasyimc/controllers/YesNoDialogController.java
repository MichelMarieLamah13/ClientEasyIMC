package com.easy.imc.clienteasyimc.controllers;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class YesNoDialogController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private ImageView avatar_lbl;

    @FXML
    private AnchorPane fermeture;

    @FXML
    private HBox header;

    @FXML
    private Label message_lbl;

    @FXML
    private Button no_btn;

    @FXML
    private Label titre_lbl;

    @FXML
    private Button yes_btn;

    @FXML
    void onClick(ActionEvent event) {
        Button source = (Button) event.getSource();
        if(source == yes_btn){
            result.set(true);
        } else if (source == no_btn) {
            result.set(false);
        }
        Stage stage = (Stage) header.getScene().getWindow();
        stage.close();
    }

    public void setData(String title, String message){
        titre_lbl.setText("EasyIMC - "+title);
        message_lbl.setText(message);

    }

    private final ReadOnlyObjectWrapper<Boolean> result = new ReadOnlyObjectWrapper<>();

    public ReadOnlyObjectProperty<Boolean> resultProperty() {
        return result.getReadOnlyProperty() ;
    }

    public Boolean getResult() {
        return result.get();
    }

}
