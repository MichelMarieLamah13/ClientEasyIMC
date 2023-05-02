package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.entities.IMC;
import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.entities.User;
import com.easy.imc.clienteasyimc.models.HistoryModel;
import com.easy.imc.clienteasyimc.models.OkDialogType;
import com.easy.imc.clienteasyimc.models.UnitePoidsModel;
import com.easy.imc.clienteasyimc.models.UniteTailleModel;
import com.easy.imc.clienteasyimc.services.CalculIMCService;
import com.easy.imc.clienteasyimc.services.HistoryService;
import com.easy.imc.clienteasyimc.services.UnitePoidsService;
import com.easy.imc.clienteasyimc.services.UniteTailleService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculIMCController implements Initializable {
    @FXML
    private AnchorPane container;

    @FXML
    private TextField imc_txtfd;

    @FXML
    private TextField poids_txtfd;

    @FXML
    private Pane recemment_calcule_pane;

    @FXML
    private TextField taille_txtfd;

    @FXML
    private TextField unite_imc_txtfd;

    @FXML
    private ImageView categorie_iv;

    @FXML
    private ChoiceBox<UnitePoidsModel> unite_poids_cb;

    @FXML
    private ChoiceBox<UniteTailleModel> unite_taille_cb;

    @FXML
    private Label categorie_title_txtfd;

    @FXML
    private VBox recentlyAdded_vbox;

    private final ReadOnlyObjectWrapper<HistoryModel> isDetailsBtnClicked = new ReadOnlyObjectWrapper<>();

    User connectedUser = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUnitePoids();
        initUniteTaille();
        initValues();
    }

    public ReadOnlyObjectProperty<HistoryModel> isDetailsBtnClickedProperty() {
        return isDetailsBtnClicked.getReadOnlyProperty();
    }

    public void setConnectedUser(User user){
        connectedUser = user;
        toggleRCPane();
        initRecentlyAdded();
    }

    public boolean isUserConnected(){
        return connectedUser != null;
    }
    public void toggleRCPane(){
        recemment_calcule_pane.setVisible(isUserConnected());
    }

    public void onClickCalculerIMC(){

        try{
            IMC imc = getValues();
            if(isNotEmpty(imc)){
                IMCResponse<HistoryModel> response = CalculIMCService.calculIMC(imc);
                if(response.status == HttpURLConnection.HTTP_OK){
                    if(noDialogShowing()){
                        initOkDialog();
                    }
                    if(!okDialogStage.isShowing()){
                        String title = "Calcul réussi";
                        String message = "Calcul effectué avec succès";
                        okDialogController.setData(OkDialogType.SUCCESS, title, message);
                        okDialogController.resultProperty().addListener((diaObs, diaOldResult, diaNewResult)->{
                            if(diaNewResult){
                                HistoryModel model = response.values.get(0);
                                setValues(model);
                            }
                            clearDialog();
                        });
                        okDialogStage.show();
                    }

                }else{
                    showWarningMessage(response.message);
                }
            }else{
               showWarningMessage("Veuillez saisir les valeurs");
            }
        }catch (Exception e){
            showErrorServer(e.getMessage());
        }

    }

    public boolean isNotEmpty(IMC imc){
        return imc.taille != 0 && imc.poids != 0;
    }

    public void showErrorServer(String message){
        if(noDialogShowing()){
            initOkDialog();
        }
        if(!okDialogStage.isShowing()){
            String title = "Erreur serveur";
            okDialogController.setData(OkDialogType.DANGER, title, message);
            okDialogController.resultProperty().addListener((diaObs, diaOldResult, diaNewResult)->{
                clearDialog();
            });
            okDialogStage.show();
        }
    }

    public void showWarningMessage(String message){
        if(noDialogShowing()){
            initOkDialog();
        }
        if(!okDialogStage.isShowing()){
            String title = "Erreur calcul";
            okDialogController.setData(OkDialogType.WARNING, title, message);
            okDialogController.resultProperty().addListener((diaObs, diaOldResult, diaNewResult)->{
                clearDialog();
            });
            okDialogStage.show();
        }
    }

    public HistoryModel model;
    public void setValues(HistoryModel model){
        this.model = model;
        imc_txtfd.setText(String.valueOf(model.imc));
        categorie_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/categories/"+model.category.avatar))));
        categorie_title_txtfd.setText(model.category.title);
        initRecentlyAdded();
    }

    public void onDetailsBtnClicked(){
        if(model!=null){
            isDetailsBtnClicked.set(model);
        }
    }

    public IMC getValues(){
        IMC imc = new IMC();
        imc.poids = Double.parseDouble(poids_txtfd.getText());
        imc.taille = Double.parseDouble(taille_txtfd.getText());
        imc.user = connectedUser;
        imc.unitePoids = unite_poids_cb.getValue().toEntity();
        imc.uniteTaille = unite_taille_cb.getValue().toEntity();
        return imc;
    }
    public void initUnitePoids(){
        IMCResponse<UnitePoidsModel> res = UnitePoidsService.getAll();

        ObservableList<UnitePoidsModel> listItems = FXCollections.observableArrayList();
        listItems.addAll(res.values);
        unite_poids_cb.setItems(listItems);
        unite_poids_cb.setValue(listItems.get(0));
    }

    public void initUniteTaille(){
        IMCResponse<UniteTailleModel> res = UniteTailleService.getAll();

        ObservableList<UniteTailleModel> listItems = FXCollections.observableArrayList();
        listItems.addAll(res.values);
        unite_taille_cb.setItems(listItems);
        unite_taille_cb.setValue(listItems.get(0));
    }

    public void initRecentlyAdded(){
        if(isUserConnected()){
            IMCResponse<HistoryModel> res = HistoryService.getAll(connectedUser, true, 5);
            recentlyAdded_vbox.getChildren().clear();
            try{
                for (HistoryModel model:res.values) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("history-table-row-view.fxml"));
                    HBox row = loader.load();
                    HistoryTableRowController rowController = loader.getController();
                    rowController.isDetailsBtnClickedProperty().addListener((obs, oldResult, newResult)->{
                        isDetailsBtnClicked.set(newResult);
                    });
                    rowController.setData(model);
                    recentlyAdded_vbox.getChildren().add(row);
                }
            }catch (Exception e){
                showErrorServer(e.getMessage());
            }

        }

    }

    public void initValues(){
        unite_imc_txtfd.setText("Kg/m^2");
        imc_txtfd.setText(Double.toString(0));
        poids_txtfd.setText(Double.toString(0));
        taille_txtfd.setText(Double.toString(0));
    }
    OkDialogController okDialogController = null;
    Stage okDialogStage = null;

    @FXML
    private Pane top_pane;

    double x, y = 0;
    public void initOkDialog(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ok-dialog.fxml"));
            Parent parent = loader.load();
            okDialogController = loader.getController();

            okDialogStage = new Stage();
            Scene scene = new Scene(parent);
            okDialogStage.setScene(scene);
            okDialogStage.setResizable(false);
            okDialogStage.setAlwaysOnTop(true);

            double topX = top_pane.getScene().getX();
            double topY = top_pane.getScene().getY();
            okDialogStage.setX(topX + 500);
            okDialogStage.setY(topY + 200);
            okDialogStage.initModality(Modality.WINDOW_MODAL);
            okDialogStage.initStyle(StageStyle.UNDECORATED);

            scene.setOnMousePressed(evt->{
                x = evt.getSceneX();
                y = evt.getSceneY();
            });

            scene.setOnMouseDragged(evt->{
                okDialogStage.setX(evt.getScreenX() - x);
                okDialogStage.setY(evt.getScreenY() - y);
            });


        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean noDialogShowing(){
        return okDialogStage == null && okDialogController == null;
    }

    public void clearDialog(){
        okDialogStage = null;
        okDialogController = null;
    }
}
