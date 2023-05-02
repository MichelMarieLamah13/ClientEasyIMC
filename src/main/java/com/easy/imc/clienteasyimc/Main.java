package com.easy.imc.clienteasyimc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    double x, y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);

        scene.setOnMousePressed(event->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}