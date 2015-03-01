package com.ifmo.optimization.methods.homework2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Whiplash on 01.03.2015.
 */
public class Controller implements Initializable {

    @FXML
    private Pane plot;
    @FXML
    private ChoiceBox<String> methodSelector;
    @FXML
    private Button run;
    @FXML
    private Label result;
    @FXML
    private TextField scale;

    @FXML
    private TextField x0;
    @FXML
    private TextField xl;
    @FXML
    private TextField xr;
    @FXML
    private TextField y0;
    @FXML
    private TextField yl;
    @FXML
    private TextField yr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
