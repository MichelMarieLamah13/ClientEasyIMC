package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.entities.IMC;
import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.entities.User;
import com.easy.imc.clienteasyimc.models.CategoryCountModel;
import com.easy.imc.clienteasyimc.models.CategoryModel;
import com.easy.imc.clienteasyimc.models.HistoryModel;
import com.easy.imc.clienteasyimc.models.UserModel;
import com.easy.imc.clienteasyimc.services.CategoryService;
import com.easy.imc.clienteasyimc.services.HistoryService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @FXML
    private GridPane card_gp;


    @FXML
    private LineChart<String, Number> imc_lc;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private final ReadOnlyObjectWrapper<CategoryCountModel> isDetailsBtnClicked = new ReadOnlyObjectWrapper<>();
    public ReadOnlyObjectProperty<CategoryCountModel> isDetailsBtnClickedProperty() {
        return isDetailsBtnClicked.getReadOnlyProperty();
    }

    public UserModel connectedUser;
    public void setData(UserModel user){
        connectedUser = user;
        initCards();
        initChart();
    }

    public void initCards(){
        try{
            int column = 1;
            int row = 1;
            card_gp.getChildren().clear();
            IMCResponse<CategoryCountModel> response = CategoryService.getAllCounts(connectedUser.toEntity());
            int size = response.values.size();
            for (int i = 1; i <= size; i++) {
                CategoryCountModel model = response.values.get(i-1);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("categorie-card-view.fxml"));
                AnchorPane categoryCard = loader.load();
                CategorieCardController categoryCardController = loader.getController();
                // Remove old CSS class
                categoryCard.getStyleClass().remove("categorie01");

                String cssClass = model.avatar.split("\\.")[0];
                categoryCard.getStyleClass().add(cssClass);
                categoryCardController.isDetailsBtnClickedProperty().addListener((obs, oldResult, newResult)->{
                    isDetailsBtnClicked.set(newResult);
                });
                categoryCardController.setData(model);
                if (column == 4){
                    column = 0;
                    row++;
                }
                card_gp.add(categoryCard, column++, row);
                GridPane.setMargin(categoryCard, new Insets(3));
            }
        }catch (Exception e){
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
        }

    }

    public void initChart(){

        IMCResponse<HistoryModel> response = HistoryService.getAll(connectedUser.toEntity(), false, -1);
        int nbHistories = response.values.size();

        // Create a data series for the line chart
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("IMC"); // Set the name of the data series
        double maxIMC = 0;
        for (int i = 0; i < nbHistories; i++) {
            HistoryModel model = response.values.get(i);
            dataSeries.getData().add(new XYChart.Data<>(String.valueOf(i+1), model.imc));
            if(model.imc > maxIMC){
                maxIMC = model.imc;
            }
        }
        // Add the data series to the line chart
        imc_lc.getData().add(dataSeries);
        imc_lc.getXAxis().setLabel("Itérations");
        imc_lc.getYAxis().setLabel("IMC");


        // Categories plot
        IMCResponse<CategoryModel> categoryResponse = CategoryService.getAll();
        int nbCategories = categoryResponse.values.size();
        for (int i = 0; i < nbCategories; i++) {


            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            CategoryModel c = categoryResponse.values.get(i);
            if(c.id == 6){
                XYChart.Data<String, Number> start = new XYChart.Data<>(String.valueOf(1), 60);
                serie.getData().add(start);

                XYChart.Data<String, Number> end = new XYChart.Data<>(String.valueOf(nbHistories), 60);
                serie.getData().add(end);
            }else{
                XYChart.Data<String, Number> start = new XYChart.Data<>(String.valueOf(1), c.max);
                serie.getData().add(start);

                XYChart.Data<String, Number> end = new XYChart.Data<>(String.valueOf(nbHistories), c.max);
                serie.getData().add(end);
            }

            serie.setName(c.title);
            imc_lc.getData().add(serie);

            for (XYChart.Data<String, Number> data : serie.getData()) {
                data.getNode().setVisible(false);
                data.getNode().toBack();
            }
        }

        dataSeries.getNode().toFront();

        // Add values
        /*for (int i = 0; i < categoryResponse.values.size(); i++) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            CategoryModel c = categoryResponse.values.get(i);
            if(i==6){
                serie.getData().add(new XYChart.Data<>(String.valueOf(1), 60));
                serie.getData().add(new XYChart.Data<>(String.valueOf(nbHistories), 60));
            }else{
                serie.getData().add(new XYChart.Data<>(String.valueOf(1), c.max));
                serie.getData().add(new XYChart.Data<>(String.valueOf(nbHistories), c.max));
            }
            serie.setName(c.title);
            imc_lc.getData().add(serie);
        }*/

    }
}
