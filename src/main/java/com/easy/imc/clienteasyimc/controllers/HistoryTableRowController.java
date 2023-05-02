package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.models.HistoryModel;
import com.easy.imc.clienteasyimc.models.UserModel;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryTableRowController implements Initializable {

    @FXML
    private Button category_btn;

    @FXML
    private Label date_lbl;

    @FXML
    private Label heure_lbl;

    @FXML
    private Label imc_lbl;

    @FXML
    private Label poids_lbl;

    @FXML
    private Label taille_lbl;

    @FXML
    private ImageView category_avatar_iv;

    HistoryModel model;

    private final ReadOnlyObjectWrapper<HistoryModel> isDetailsBtnClicked = new ReadOnlyObjectWrapper<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public ReadOnlyObjectProperty<HistoryModel> isDetailsBtnClickedProperty() {
        return isDetailsBtnClicked.getReadOnlyProperty();
    }

    public void onDetailsBtnClicked(){
        isDetailsBtnClicked.set(model);
    }

    public void setData(HistoryModel historyModel){
        model = historyModel;
        poids_lbl.setText(String.valueOf(historyModel.poids));
        taille_lbl.setText(String.valueOf(historyModel.taille));
        imc_lbl.setText(String.valueOf(historyModel.imc));
        date_lbl.setText(historyModel.date);
        heure_lbl.setText(historyModel.heure);
        String avatar = historyModel.category.avatar;
        if(avatar!=null && !avatar.isEmpty()){
            category_avatar_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/categories/"+avatar))));
        }
    }
}
