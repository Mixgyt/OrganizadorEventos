package com.progra3.organizadord.organizadoreventos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public Stage mainStage;
    @FXML
    private Button btnCrearCuenta;

    @FXML
    private Button btnInicioSesion;

    @FXML
    private Button btnSalir;

    @FXML
    private AnchorPane mainPane;
    public void initialize(){

        btnInicioSesion.setOnMouseEntered(mouseEvent -> {
            btnInicioSesion.setStyle("-fx-background-color: #535C91");
        });
        btnCrearCuenta.setOnMouseEntered(mouseEvent -> {
            btnCrearCuenta.setStyle("-fx-background-color: #535C91");
        });
        btnSalir.setOnMouseEntered(mouseEvent -> {
            btnSalir.setStyle("-fx-background-color: #535C91");
        });
        btnInicioSesion.setOnMouseExited(mouseEvent -> {
            btnInicioSesion.setStyle("-fx-background-color: #1B1A55");
        });
        btnCrearCuenta.setOnMouseExited(mouseEvent -> {
            btnCrearCuenta.setStyle("-fx-background-color: #1B1A55");
        });
        btnSalir.setOnMouseExited(mouseEvent -> {
            btnSalir.setStyle("-fx-background-color: #1B1A55");
        });
        btnInicioSesion.setOnAction(actionEvent -> {
            btnInicioSesion.setStyle("-fx-background-color: #9290C3");
            try {
                Node node = FXMLLoader.load(getClass().getResource("inicio-sesion-view.fxml"));
                mainPane.getChildren().clear();
                mainPane.getChildren().setAll(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnCrearCuenta.setOnAction(actionEvent -> {
            btnCrearCuenta.setStyle("-fx-background-color: #9290C3");
            try {
                Node node = FXMLLoader.load(getClass().getResource("crear-usuario-view.fxml"));
                mainPane.getChildren().clear();
                mainPane.getChildren().setAll(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}