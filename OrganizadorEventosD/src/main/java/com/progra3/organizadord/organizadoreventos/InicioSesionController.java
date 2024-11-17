package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.Models.UsuarioModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class InicioSesionController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private TextField txtClave;

    @FXML
    private TextField txtUsuario;

    public void initialize(){
    }

    @FXML
    private void iniciarSesion(){
        String usuario = txtUsuario.getText().trim();
        String clave = txtClave.getText();
        if (usuario.isEmpty() || clave.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Uno a varios campos estan vacios.");
            alert.show();
        }
        else {
            UsuarioModel usuarioModel = new UsuarioModel(usuario,clave);
            if (usuarioModel.iniciarSesion()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Se ha iniciado sesión correctamente");
                alert.show();

                txtUsuario.clear();
                txtClave.clear();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("El usuario o la contrasña es incorrecta");
                alert.show();
            }
        }
    }

    @FXML
    private void salir(){
        Main.setRoot("main-view","Inicio");
    }
}

