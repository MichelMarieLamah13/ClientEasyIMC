package com.easy.imc.clienteasyimc.controllers;

import com.easy.imc.clienteasyimc.Main;
import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.entities.User;
import com.easy.imc.clienteasyimc.models.CategoryCountModel;
import com.easy.imc.clienteasyimc.models.HistoryModel;
import com.easy.imc.clienteasyimc.models.UserModel;
import com.easy.imc.clienteasyimc.services.HistoryService;
import com.easy.imc.clienteasyimc.services.UserService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileController implements Initializable {

    @FXML
    private CheckBox afficher_mdp_cb;

    @FXML
    private Hyperlink afficher_mdp_hplk;

    @FXML
    private ImageView avatar_iv;

    @FXML
    private Hyperlink cacher_mdp_hplk;

    @FXML
    private Label categorie_txtfd;

    @FXML
    private AnchorPane container;

    @FXML
    private Button details_btn;

    @FXML
    private TextField imc_txtfd;

    @FXML
    private TextField login_txtfd;

    @FXML
    private PasswordField mdp_pwdfld;

    @FXML
    private Button modifier_btn;

    @FXML
    private TextField poids_txtfd;

    @FXML
    private TextField taille_txtfd;

    @FXML
    private TextField unite_imc_txtfd;

    @FXML
    private TextField unite_poids_txtf;

    @FXML
    private TextField unite_taille_txtfd;

    @FXML
    private TextField mdp_txtfd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public UserModel connectedUser;
    public HistoryModel lastAdded;
    public void setData(UserModel user){
        connectedUser = user;
        initLastAdded();
        initUserInfo();
    }
    public void initLastAdded(){
        IMCResponse<HistoryModel> res = HistoryService.getAll(connectedUser.toEntity(), true, 1);
        try{

            lastAdded = res.values.get(0);
            categorie_txtfd.setText(lastAdded.category.title);
            taille_txtfd.setText(String.valueOf(lastAdded.taille));
            poids_txtfd.setText(String.valueOf(lastAdded.poids));
            unite_poids_txtf.setText(lastAdded.unitePoids.name);
            unite_taille_txtfd.setText(lastAdded.uniteTaille.name);
            unite_imc_txtfd.setText("Kg/m^2");
            imc_txtfd.setText(String.valueOf(lastAdded.imc));
            if(lastAdded.category!=null && !lastAdded.category.avatar.isEmpty()){
                avatar_iv.setImage(new Image(String.valueOf(Main.class.getResource("images/categories/"+lastAdded.category.avatar))));
            }
        }catch (Exception e){
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
        }

    }

    public void initUserInfo(){
        login_txtfd.setText(connectedUser.login);
        mdp_pwdfld.setText(connectedUser.password);
        mdp_txtfd.setText(connectedUser.password);
    }

    public User getValues(){
        String login = login_txtfd.getText();
        String password = mdp_pwdfld.getText();
        return new User(login, password);
    }

    public void onEditBtnClicked(){
        User user = getValues();
        IMCResponse<UserModel> response = UserService.update(user);
    }


    @FXML
    public void onShowPasswordClicked(ActionEvent event){
        mdp_txtfd.setVisible(!mdp_txtfd.isVisible());
        mdp_pwdfld.setVisible(!mdp_pwdfld.isVisible());
        afficher_mdp_hplk.setVisible(!afficher_mdp_hplk.isVisible());
        cacher_mdp_hplk.setVisible(!cacher_mdp_hplk.isVisible());
        if(event.getSource() == afficher_mdp_hplk || event.getSource() == cacher_mdp_hplk){
            afficher_mdp_cb.setSelected(!afficher_mdp_cb.isSelected());
        }
    }

    public void onKeyReleasedInHiddenPwd(KeyEvent event){
        String pwd = mdp_txtfd.getText();
        mdp_pwdfld.setText(pwd);
    }

    public void onKeyReleasedInPwd(KeyEvent event){
        String pwd = mdp_pwdfld.getText();
        mdp_txtfd.setText(pwd);
    }

    public void onEnterKeyPressed(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            onEditBtnClicked();
        }
    }

    private final ReadOnlyObjectWrapper<HistoryModel> isDetailsBtnClicked = new ReadOnlyObjectWrapper<>();
    public ReadOnlyObjectProperty<HistoryModel> isDetailsBtnClickedProperty() {
        return isDetailsBtnClicked.getReadOnlyProperty();
    }

    public void onDetailsBtnClicked(){
        isDetailsBtnClicked.set(lastAdded);
    }

}
