package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.models.OkDialogType;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OkDialogController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private ImageView avatar_iv;

    @FXML
    private AnchorPane fermeture;

    @FXML
    private HBox header;

    @FXML
    private Label message_lbl;
    

    @FXML
    private Label titre_lbl;

    @FXML
    private Button ok_btn;

    @FXML
    void onClick(ActionEvent event) {
        result.set(true);        
        Stage stage = (Stage) header.getScene().getWindow();
        stage.close();
    }

    public void setData(OkDialogType type, String title, String message){
        titre_lbl.setText("EasyIMC - "+title);
        message_lbl.setText(message);
        if(type == OkDialogType.DANGER){
            avatar_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/icons/danger.png"))));
        } else if (type == OkDialogType.INFO) {
            avatar_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/icons/info.png"))));
        } else if (type == OkDialogType.WARNING) {
            avatar_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/icons/warning.png"))));
        } else if (type == OkDialogType.SUCCESS) {
            avatar_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/icons/success.png"))));
        }

    }

    private final ReadOnlyObjectWrapper<Boolean> result = new ReadOnlyObjectWrapper<>();

    public ReadOnlyObjectProperty<Boolean> resultProperty() {
        return result.getReadOnlyProperty() ;
    }

    public Boolean getResult() {
        return result.get();
    }

}
