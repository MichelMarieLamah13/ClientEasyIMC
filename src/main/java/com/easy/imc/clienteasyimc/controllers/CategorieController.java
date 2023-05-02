package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.models.CategoryModel;
import com.easy.imc.clienteasyimc.models.ConseilModel;
import com.easy.imc.clienteasyimc.models.DescriptionModel;
import com.easy.imc.clienteasyimc.services.ConseilService;
import com.easy.imc.clienteasyimc.services.DescriptionService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CategorieController implements Initializable {
    @FXML
    private ImageView avatar_iv;

    @FXML
    private Hyperlink calculer_imc_hplk;

    @FXML
    private TextArea conseils_txtarea;

    @FXML
    private AnchorPane container;

    @FXML
    private TextArea description_txtarea;

    @FXML
    private Label subtitle_lbl;

    @FXML
    private Label subtitle_lbl1;

    @FXML
    private Label subtitle_lbl11;

    @FXML
    private Label title_lbl;

    @FXML
    private TextField maximum_txtfd;

    @FXML
    private TextField minimum_txtfd;
    private final ReadOnlyObjectWrapper<Boolean> isGoToCalculIMCClicked = new ReadOnlyObjectWrapper<>();
    CategoryModel model;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public ReadOnlyObjectProperty<Boolean> isGoToCalculIMCClickedProperty() {
        return isGoToCalculIMCClicked.getReadOnlyProperty() ;
    }

    public void onGoToCalculIMCClicked(){
        isGoToCalculIMCClicked.set(true);
    }

    public void setData(CategoryModel model){
        this.model =model;
        title_lbl.setText(model.title);
        subtitle_lbl.setText(model.subtitle);
        maximum_txtfd.setText(String.valueOf(model.max));
        minimum_txtfd.setText(String.valueOf(model.min));
        avatar_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/categories/"+model.avatar))));

        IMCResponse<DescriptionModel> desReq = DescriptionService.findByIdCategory(model.id);
        if(!desReq.values.isEmpty()){
            String description = getFormattedData(desReq.values.get(0).description);
            description_txtarea.setText(description);
        }

        IMCResponse<ConseilModel> conseilReq = ConseilService.findByIdCategory(model.id);
        if(!conseilReq.values.isEmpty()){
            String conseil = getFormattedData(conseilReq.values.get(0).conseil);
            conseils_txtarea.setText(conseil);
        }
    }

    public String  getFormattedData(String value){
        String[] values = value.split("\\.");
        return String.join(".\n", values)+".\n";
    }

}
