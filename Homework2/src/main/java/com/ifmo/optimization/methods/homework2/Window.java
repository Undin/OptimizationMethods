package com.ifmo.optimization.methods.homework2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("window.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("f(x, y) = x^4 + y^4 - 5 * (x * y - x^2 * y^2)");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
