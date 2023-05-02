package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.entities.User;
import com.easy.imc.clienteasyimc.models.UserModel;
import com.easy.imc.clienteasyimc.services.UserService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
        User user = getValues();
        IMCResponse<UserModel> response = UserService.create(user);
        isSignUpBtnClicked.set(response);
    }

    public User getValues(){
        String login = login_txtfd.getText();
        String password = mdp_pwdfld.getText();
        return new User(login, password);
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
