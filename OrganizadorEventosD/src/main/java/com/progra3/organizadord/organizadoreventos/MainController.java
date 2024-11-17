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
    }

    @FXML
    private void crearUsuario(){
        btnCrearCuenta.setStyle("-fx-background-color: #9290C3");
        Main.setRoot("crear-usuario-view","Crear usuario");
    }

    @FXML
    private void iniciarSesion(){
        btnInicioSesion.setStyle("-fx-background-color: #9290C3");
        Main.setRoot("inicio-sesion-view","Inicio de sesion");
    }

    @FXML
    private void salir(){
        btnSalir.setStyle("-fx-background-color: #1B1A55");
        Main.close();
    }
}