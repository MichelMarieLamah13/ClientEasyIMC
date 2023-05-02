package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.models.CategoryCountModel;
import com.easy.imc.clienteasyimc.models.CategoryModel;
import com.easy.imc.clienteasyimc.models.HistoryModel;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CategorieCardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private ImageView avatar_iv;

    @FXML
    private Label nombre_lbl;

    @FXML
    private Label subtitle_lbl;

    @FXML
    private Label title_lbl;

    CategoryCountModel model;
    private final ReadOnlyObjectWrapper<CategoryCountModel> isDetailsBtnClicked = new ReadOnlyObjectWrapper<>();
    public ReadOnlyObjectProperty<CategoryCountModel> isDetailsBtnClickedProperty() {
        return isDetailsBtnClicked.getReadOnlyProperty();
    }

    public void onDetailsBtnClicked(){
        isDetailsBtnClicked.set(model);
    }

    public void setData(CategoryCountModel model){
        this.model = model;
        nombre_lbl.setText(String.valueOf(model.count));
        title_lbl.setText(model.title);
        subtitle_lbl.setText(model.subtitle);
        if(model.avatar!=null && !model.avatar.isEmpty()){
            avatar_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/categories/"+model.avatar))));
        }
    }
}
