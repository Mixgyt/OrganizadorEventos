package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.Models.CorreoModel;
import com.progra3.organizadord.organizadoreventos.Models.UsuarioModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CrearUsuarioController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCrear;

    @FXML
    private TextField txtClave;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtUsuario;

    @FXML
    private AnchorPane mainPane;
    public void initialize(){
        btnCrear.setOnAction(e ->{
            String correo = txtCorreo.getText().toLowerCase().replace(" ", "");
            String usuario = txtUsuario.getText().trim();
            String clave = txtClave.getText();
            CorreoModel correoModel = new CorreoModel(txtCorreo.getText());

            if (correo.isEmpty() || usuario.isEmpty() || clave.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Uno o varios campos estan vacios.");
                alert.show();
            }
            else {
                if (!correoModel.CorreoExistente()){
                    correoModel.AgregarCorreo();
                    UsuarioModel usuarioModel = new UsuarioModel(txtUsuario.getText(), correoModel.BuscarIdCorreo(),txtClave.getText());
                    if (usuarioModel.UsuarioExistente()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("El usuario ingresado ya existe, ingrese otro nombre de usuario");
                        alert.show();
                    }
                    else {
                        usuarioModel.AgregarUsuario();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Se ha registrado el usuario con exito");
                        alert.show();

                        try {
                            Node node = FXMLLoader.load(getClass().getResource("inicio-sesion-view.fxml"));
                            mainPane.getChildren().clear();
                            mainPane.getChildren().setAll(node);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El correo ingresado ya esta registrado");
                    alert.show();
                }
            }
        });
        btnCancelar.setOnAction(e->{
            Node node = null;
            try {
                node = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                mainPane.getChildren().clear();
                mainPane.getChildren().setAll(node);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

