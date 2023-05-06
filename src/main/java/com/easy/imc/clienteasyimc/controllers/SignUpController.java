package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.entities.User;
import com.easy.imc.clienteasyimc.models.OkDialogType;
import com.easy.imc.clienteasyimc.models.UserModel;
import com.easy.imc.clienteasyimc.services.UserService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private CheckBox afficher_mdp_cb;

    @FXML
    private Hyperlink afficher_mdp_hplk;

    @FXML
    private Hyperlink cacher_mdp_hplk;

    @FXML
    private TextField login_txtfd;

    @FXML
    private PasswordField mdp_pwdfld;

    @FXML
    private TextField mdp_txtfld;

    @FXML
    private Button modifier_btn;

    @FXML
    private Hyperlink se_connecter_hplk;


    private final ReadOnlyObjectWrapper<Boolean> isSignInClicked = new ReadOnlyObjectWrapper<>();
    private final ReadOnlyObjectWrapper<IMCResponse<UserModel>> isSignUpBtnClicked = new ReadOnlyObjectWrapper<>();



    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public ReadOnlyObjectProperty<Boolean> isSignInClickedProperty() {
        return isSignInClicked.getReadOnlyProperty() ;
    }

    public void onSignInClicked(){
        isSignInClicked.set(true);
    }

    public ReadOnlyObjectProperty<IMCResponse<UserModel>> isSignUpBtnClickedProperty() {
        return isSignUpBtnClicked.getReadOnlyProperty();
    }

    public void onSignUpClicked(){
        try{
            User user = getValues();
            if(isUserValid(user)){
                IMCResponse<UserModel> response = UserService.create(user);
                isSignUpBtnClicked.set(response);
            }else{
                showWarningMessage("Valeurs valides!");
            }

        }catch (Exception e){
            showErrorServer(e.getMessage());
        }

    }

    public boolean isUserValid(User user){
        if(user != null){
            if(user.age > 0 && !user.login.isEmpty() && !user.password.isEmpty()){
                return true;
            }
        }
        return false;
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
    OkDialogController okDialogController = null;
    Stage okDialogStage = null;
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
    double x, y = 0;
    @FXML
    private VBox container_vbox;
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

            double topX = container_vbox.getScene().getX();
            double topY = container_vbox.getScene().getY();
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

    @FXML
    private TextField age_txtfd;
    public User getValues(){
        String login = login_txtfd.getText();
        String password = mdp_pwdfld.getText();
        int age = Integer.parseInt(age_txtfd.getText());
        return new User(login, password, age);
    }

    @FXML
    public void onShowPasswordClicked(ActionEvent event){
        mdp_txtfld.setVisible(!mdp_txtfld.isVisible());
        mdp_pwdfld.setVisible(!mdp_pwdfld.isVisible());
        afficher_mdp_hplk.setVisible(!afficher_mdp_hplk.isVisible());
        cacher_mdp_hplk.setVisible(!cacher_mdp_hplk.isVisible());
        if(event.getSource() == afficher_mdp_hplk || event.getSource() == cacher_mdp_hplk){
            afficher_mdp_cb.setSelected(!afficher_mdp_cb.isSelected());
        }
    }

    public void onKeyReleasedInHiddenPwd(KeyEvent event){
        String pwd = mdp_txtfld.getText();
        mdp_pwdfld.setText(pwd);
    }

    public void onKeyReleasedInPwd(KeyEvent event){
        String pwd = mdp_pwdfld.getText();
        mdp_txtfld.setText(pwd);
    }

    public void onEnterKeyPressed(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            onSignUpClicked();
        }
    }
}
