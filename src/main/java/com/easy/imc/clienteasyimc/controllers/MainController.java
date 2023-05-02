package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.entities.User;
import com.easy.imc.clienteasyimc.models.CategoryModel;
import com.easy.imc.clienteasyimc.models.OkDialogType;
import com.easy.imc.clienteasyimc.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    @FXML
    private HBox top_anchor;

    @FXML
    private Button calcul_imc_btn;

    @FXML
    private Button profile_btn;

    @FXML
    private Button close_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button historique_btn;

    @FXML
    private Button login_btn;

    @FXML
    private Hyperlink login_hyperlink;

    @FXML
    private Pane login_pane;

    @FXML
    private Button logout_btn;

    @FXML
    private Hyperlink logout_hyperlink;

    @FXML
    private Pane logout_pane;


    @FXML
    private AnchorPane center_anchorPane;

    @FXML
    private VBox user_info_vbox;

    @FXML
    private ImageView image_iv;

    @FXML
    private Label username_txtfd;

    DashboardController dashboardController;
    CalculIMCController calculIMCController;
    HistoriqueController historiqueController;
    ProfileController profileController;
    CategorieController categorieController;

    SignInController signInController;
    SignUpController signUpController;

    User connectedUser = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultMenu();
    }

    @FXML
    public void close(){
        if(noDialogShowing()){
            initYesNoDialog();
        }
        if(!yesNodialogStage.isShowing()){
            String title = "Fermeture Application";
            String message = "Voulez-vous vraiment fermer l'application?";
            yesNoDialogController.setData(title, message);
            yesNoDialogController.resultProperty().addListener((obs, oldResult, newResult)->{
                if (newResult){
                    Stage stage = (Stage) top_anchor.getScene().getWindow();
                    stage.close();
                }
                clearDialog();
            });
            yesNodialogStage.show();
        }
    }

    public boolean noDialogShowing(){
        return yesNoDialogController == null && yesNodialogStage==null
                && okDialogStage == null && okDialogController == null;
    }

    public void clearDialog(){
        yesNodialogStage = null;
        yesNoDialogController = null;
        okDialogStage = null;
        okDialogController = null;
    }

    YesNoDialogController yesNoDialogController = null;
    OkDialogController okDialogController = null;
    Stage yesNodialogStage = null;
    Stage okDialogStage = null;
    double x, y = 0;
    public void initYesNoDialog(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("yes-no-dialog.fxml"));
            Parent parent = loader.load();
            yesNoDialogController = loader.getController();

            yesNodialogStage = new Stage();
            Scene scene = new Scene(parent);
            yesNodialogStage.setScene(scene);
            yesNodialogStage.setResizable(false);
            yesNodialogStage.setAlwaysOnTop(true);

            double topX = top_anchor.getScene().getX();
            double topY = top_anchor.getScene().getY();
            yesNodialogStage.setX(topX + 500);
            yesNodialogStage.setY(topY + 200);
            yesNodialogStage.initModality(Modality.WINDOW_MODAL);
            yesNodialogStage.initStyle(StageStyle.UNDECORATED);

            scene.setOnMousePressed(evt->{
                x = evt.getSceneX();
                y = evt.getSceneY();
            });

            scene.setOnMouseDragged(evt->{
                yesNodialogStage.setX(evt.getScreenX() - x);
                yesNodialogStage.setY(evt.getScreenY() - y);
            });


        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

            double topX = top_anchor.getScene().getX();
            double topY = top_anchor.getScene().getY();
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

    @FXML
    public void minimize(){
        Stage stage = (Stage) top_anchor.getScene().getWindow();
        stage.setIconified(true);
    }

    private boolean isUserConnected(){
        return connectedUser!=null;
    }
    public void onMenuItemClicked(ActionEvent event){
        Button source = (Button) event.getSource();
        center_anchorPane.getChildren().clear();
        if(source == dashboard_btn){
            initDashboard();
        }else if(source == calcul_imc_btn){
            initCalculIMC();
        } else if (source == historique_btn) {
            initHistorique();
        } else if (source == profile_btn) {
            initProfile();
        }
        setStylesOnClick(source);

    }

    public void onEditProfileClicked(){
        center_anchorPane.getChildren().clear();
        initProfile();
    }

    private void setStylesOnClick(Button source){
        getStyleOnClick(source, profile_btn);
        getStyleOnClick(source, dashboard_btn);
        getStyleOnClick(source, calcul_imc_btn);
        getStyleOnClick(source, historique_btn);
    }
    public void getStyleOnClick(Button source, Button btn){
        if(source == btn){
            btn.getStyleClass().add("selected-border");
        }else{
            btn.getStyleClass().remove("selected-border");
        }
    }

    public void onLoginBtnClicked(){
        center_anchorPane.getChildren().clear();
        login_pane.setVisible(false);
        initSignIn();
        setStylesOnClick(null);
    }

    public void onRegisterBtnClicked(){
        center_anchorPane.getChildren().clear();
        login_pane.setVisible(true);
        initSignUp();
        setStylesOnClick(null);
    }

    public void onLogoutBtnClicked(){
        center_anchorPane.getChildren().clear();
        login_pane.setVisible(true);
        logout_pane.setVisible(false);
        setStylesOnClick(calcul_imc_btn);
        setConnectedUser(null);
        initCalculIMC();
    }




    public void initDashboard(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("dashboard-view.fxml"));
            AnchorPane anchorPane = loader.load();
            dashboardController = loader.getController();
            dashboardController.setData(connectedUser);
            dashboardController.isDetailsBtnClickedProperty().addListener((obs, oldResult, newResult)->{
                if(newResult!=null){
                    initCategorie(newResult);
                }
            });
            center_anchorPane.getChildren().add(anchorPane);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initCalculIMC(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("calcul-imc-view.fxml"));
            AnchorPane anchorPane = loader.load();
            calculIMCController = loader.getController();
            calculIMCController.setConnectedUser(connectedUser);
            calculIMCController.isDetailsBtnClickedProperty().addListener((obs, oldResult, newResult)->{
                if(newResult!=null){
                    if(isUserConnected()){
                        initCategorie(newResult.category);
                    }
                }
            });
            center_anchorPane.getChildren().add(anchorPane);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initProfile(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("profile-view.fxml"));
            AnchorPane anchorPane = loader.load();
            profileController = loader.getController();
            profileController.setData(connectedUser);
            profileController.isDetailsBtnClickedProperty().addListener((obs, oldResult, newResult)->{
                if(newResult!=null){
                    initCategorie(newResult.category);
                }
            });
            center_anchorPane.getChildren().add(anchorPane);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void initCategorie(CategoryModel model){
        try {
            setStylesOnClick(null);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("categorie-view.fxml"));
            AnchorPane anchorPane = loader.load();
            categorieController = loader.getController();
            categorieController.setData(model);
            categorieController.isGoToCalculIMCClickedProperty().addListener((obs, oldResult, newResult)->{
                if(newResult){
                    setStylesOnClick(calcul_imc_btn);
                    initCalculIMC();
                }
            });
            center_anchorPane.getChildren().add(anchorPane);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void initHistorique(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("historique-view.fxml"));
            AnchorPane anchorPane = loader.load();
            historiqueController = loader.getController();
            historiqueController.setConnectedUser(connectedUser);
            historiqueController.isDetailsBtnClickedProperty().addListener((obs, oldResult, newResult)->{
                if(newResult!=null){
                    if(isUserConnected()){
                        initCategorie(newResult.category);
                    }
                }
            });
            center_anchorPane.getChildren().add(anchorPane);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initSignIn(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("signin-view.fxml"));
            AnchorPane anchorPane = loader.load();
            signInController = loader.getController();
            signInController.isCreateAccountClickedProperty().addListener((obs, oldResult, newResult)->{
                if (newResult){
                    onRegisterBtnClicked();

                }
            });
            signInController.isLoggedInBtnClickedProperty().addListener((obs, oldResult, newResult)->{
                if (newResult.status == HttpURLConnection.HTTP_OK){
                    if(!newResult.values.isEmpty()){
                        UserModel model = newResult.values.get(0);
                        if(noDialogShowing()){
                            initOkDialog();
                        }
                        if(!okDialogStage.isShowing()){
                            String title = "Connexion réussie";
                            String message = "Bienvenu dans l'application EasyIMC";
                            okDialogController.setData(OkDialogType.SUCCESS, title, message);
                            okDialogController.resultProperty().addListener((diaObs, diaOldResult, diaNewResult)->{
                                if (diaNewResult){
                                    setConnectedUser(model);
                                }
                                clearDialog();
                            });
                            okDialogStage.show();
                        }
                    }else{
                        if(noDialogShowing()){
                            initOkDialog();
                        }
                        if(!okDialogStage.isShowing()){
                            String title = "Echec connexion";
                            String message = "Pas d'utilisateur avec ces identifiants";
                            okDialogController.setData(OkDialogType.WARNING, title, message);
                            okDialogController.resultProperty().addListener((diaObs, diaOldResult, diaNewResult)->{
                                clearDialog();
                            });
                            okDialogStage.show();
                        }
                    }
                }else{
                    if(noDialogShowing()){
                        initOkDialog();
                    }
                    if(!okDialogStage.isShowing()){
                        String title = "Erreur serveur";
                        String message = newResult.message;
                        okDialogController.setData(OkDialogType.DANGER, title, message);
                        okDialogController.resultProperty().addListener((diaObs, diaOldResult, diaNewResult)->{
                            clearDialog();
                        });
                        okDialogStage.show();
                    }
                }
            });
            center_anchorPane.getChildren().add(anchorPane);
        }catch (Exception e) {
            if(noDialogShowing()){
                initOkDialog();
            }
            if(!okDialogStage.isShowing()){
                String title = "Erreur serveur";
                String message = e.getMessage();
                okDialogController.setData(OkDialogType.DANGER, title, message);
                okDialogController.resultProperty().addListener((diaObs, diaOldResult, diaNewResult)->{
                    clearDialog();
                });
                okDialogStage.show();
            }

        }
    }

    public void setConnectedUser(UserModel model){
        if(model!=null){
            connectedUser = model.toEntity();
            username_txtfd.setText(model.login);
            logout_pane.setVisible(true);
            login_pane.setVisible(false);
            image_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/users/"+model.avatar))));
            setStylesOnClick(calcul_imc_btn);
            initCalculIMC();
        }else{
            connectedUser = null;
            logout_pane.setVisible(false);
        }
        initOnLaunch();
    }

    public void initSignUp(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("signup-view.fxml"));
            AnchorPane anchorPane = loader.load();
            signUpController = loader.getController();
            signUpController.isSignInClickedProperty().addListener((obs, oldResult, newResult)->{
                if (newResult){
                    onLoginBtnClicked();
                }
            });
            signUpController.isSignUpBtnClickedProperty().addListener((obs, oldResult, newResult)->{
                if (newResult.status == HttpURLConnection.HTTP_OK){
                    onLoginBtnClicked();
                }
            });
            center_anchorPane.getChildren().add(anchorPane);
        }catch (Exception e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + " : " +e.getMessage());
        }
    }

    public void initOnLaunch(){
        boolean show = isUserConnected();
        profile_btn.setVisible(show);
        dashboard_btn.setVisible(show);
        calcul_imc_btn.setVisible(true);
        historique_btn.setVisible(show);
        user_info_vbox.setVisible(show);
    }

    public void setDefaultMenu(){
        initOnLaunch();
        initCalculIMC();
        setStylesOnClick(calcul_imc_btn);
    }
}