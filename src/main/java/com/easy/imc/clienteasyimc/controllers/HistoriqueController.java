package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.entities.History;
import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.entities.User;
import com.easy.imc.clienteasyimc.models.*;
import com.easy.imc.clienteasyimc.services.CategoryService;
import com.easy.imc.clienteasyimc.services.HistoryService;
import com.easy.imc.clienteasyimc.services.UnitePoidsService;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoriqueController implements Initializable {

    @FXML
    private ChoiceBox<CategoryModel> categorie_cb;

    @FXML
    private AnchorPane container;

    @FXML
    private ChoiceBox<String> hh_cb;

    @FXML
    private VBox history_vbox;

    @FXML
    private ChoiceBox<String> mm_cb;

    @FXML
    private TextField x_txtfd;

    @FXML
    private DatePicker date_dp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private final ReadOnlyObjectWrapper<HistoryModel> isDetailsBtnClicked = new ReadOnlyObjectWrapper<>();
    public ReadOnlyObjectProperty<HistoryModel> isDetailsBtnClickedProperty() {
        return isDetailsBtnClicked.getReadOnlyProperty();
    }

    UserModel connectedUser = null;
    public void setConnectedUser(UserModel user){
        connectedUser = user;
        initHistoriesTab();
        initHHChoiceBox();
        initMMChoiceBox();
        initCategoryChoiceBox();
    }
    public void initHistoriesTab(){
        IMCResponse<HistoryModel> res = HistoryService.getAll(connectedUser.toEntity(), false, 0);
        history_vbox.getChildren().clear();
        addValuesToTab(res.values);

    }

    public void addValuesToTab(List<HistoryModel> values){
        try{
            history_vbox.getChildren().clear();
            for (HistoryModel model:values) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("history-table-row-view.fxml"));
                HBox row = loader.load();
                HistoryTableRowController rowController = loader.getController();
                rowController.isDetailsBtnClickedProperty().addListener((obs, oldResult, newResult)->{
                    isDetailsBtnClicked.set(newResult);
                });
                rowController.setData(model);
                history_vbox.getChildren().add(row);
            }
        }catch (Exception e){
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
        }
    }

    public void initHHChoiceBox(){
        for (int i = 0; i < 24; i++) {
            String hour = String.format("%02d", i);
            hh_cb.getItems().add(hour);
        }
    }

    public void initMMChoiceBox(){
        for (int i = 0; i < 60; i++) {
            String minute = String.format("%02d", i);
            mm_cb.getItems().add(minute);
        }
    }

    public void initCategoryChoiceBox(){
        IMCResponse<CategoryModel> res = CategoryService.getAll();

        ObservableList<CategoryModel> listItems = FXCollections.observableArrayList();
        listItems.addAll(res.values);
        categorie_cb.setItems(listItems);
    }

    @FXML
    public void onMultiCriteriaSearchClicked(){
        History history = new History();
        history.idUser = connectedUser.id;
        String x = x_txtfd.getText();
        if(!x.isEmpty()){
            try{
                history.poids = Double.parseDouble(x);
                history.taille = Double.parseDouble(x);
                history.imc = Double.parseDouble(x);
            }catch (Exception e){
                showErrorServer(e.getMessage());
                Logger.getAnonymousLogger().log(
                        Level.INFO,
                        LocalDateTime.now() + ":" +e.getMessage());
            }
        }
        CategoryModel categorie = categorie_cb.getValue();
        if(categorie!=null){
            history.idCategory = categorie.id;
        }
        LocalDate date = date_dp.getValue();
        if(date != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            history.date = date_dp.getValue().format(formatter);
        }

        String hh = hh_cb.getValue();
        if(hh!=null && !hh.isEmpty()){
            history.heure=hh+":";
        }

        String mm = mm_cb.getValue();
        if(mm!=null && !mm.isEmpty()){
            history.heure+=mm;
        }

        if(hh!=null && !hh.isEmpty() && mm!=null && !mm.isEmpty()){
            history.heure = hh+":"+mm;
        }

        IMCResponse<HistoryModel> response = HistoryService.getHistoryByMultiCriteria(history);
        addValuesToTab(response.values);
    }

    @FXML
    private Pane top_pane;
    @FXML
    public void onClearBtnClicked(){
        History history = new History();
        history.idUser = connectedUser.id;
        x_txtfd.setText("");
        date_dp.setValue(null);
        mm_cb.setValue("");
        hh_cb.setValue("");
        categorie_cb.setValue(null);
        IMCResponse<HistoryModel> response = HistoryService.getHistoryByMultiCriteria(history);
        addValuesToTab(response.values);
    }

    OkDialogController okDialogController = null;
    Stage okDialogStage = null;

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

    public void clearDialog(){
        okDialogStage = null;
        okDialogController = null;
    }

    public boolean noDialogShowing(){
        return okDialogStage == null && okDialogController == null;
    }
}
