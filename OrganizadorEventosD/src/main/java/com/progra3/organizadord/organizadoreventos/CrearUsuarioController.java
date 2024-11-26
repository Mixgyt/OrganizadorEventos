package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.models.CorreoModel;
import com.progra3.organizadord.organizadoreventos.models.UsuarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class CrearUsuarioController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnShow;

    @FXML
    private PasswordField txtClave;

    @FXML
    private TextField txtClaveText;
    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtUsuario;

    @FXML
    private Hyperlink link;

    @FXML
    private AnchorPane mainPane;

    public void initialize(){
        txtClaveText.setVisible(false);
        btnShow.setOnAction(event -> {
            if (btnShow.getText().equals("X")){
                btnShow.setText("O");
                txtClave.setVisible(true);
                txtClaveText.setVisible(false);
            }
            else {
                btnShow.setText("X");
                txtClave.setVisible(false);
                txtClaveText.setVisible(true);
            }
        });
    }

    @FXML
    void EscribirPWD(KeyEvent event) {
        txtClaveText.setText(txtClave.getText());
    }

    @FXML
    void EscribirTXT(KeyEvent event) {
        txtClave.setText(txtClaveText.getText());
    }


    @FXML
    private void crearUsuario(){
        String correo = txtCorreo.getText().toLowerCase().replace(" ", "");
        String usuario = txtUsuario.getText().trim();
        String clave = txtClave.getText();
        CorreoModel correoModel = new CorreoModel(correo);

        if (correo.isEmpty() || usuario.isEmpty() || clave.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Uno o varios campos estan vacios.");
            alert.show();
        }
        else {
            if (!correoModel.existe()){
                correoModel.crearCorreo();
                UsuarioModel usuarioModel = new UsuarioModel(usuario, correoModel.buscarId(),clave);
                if (usuarioModel.existe()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El usuario ingresado ya existe, ingrese otro nombre de usuario");
                    alert.show();
                }
                else {
                    usuarioModel.crearUsuario();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Se ha registrado el usuario con exito");
                    alert.show();
                    Main.setRoot("inicio-sesion-view","Inicio de Sesion");
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("El correo ingresado ya esta registrado");
                alert.show();
            }
        }
    }

    @FXML
    private void cancelar(){
        Main.setRoot("main-view","Inicio");
    }

    @FXML
    void regresarLogin(ActionEvent event) {
        link.setStyle("-fx-background-color: #9290C3");
        Main.setRoot("inicio-sesion-view","Inicio de sesion");
    }
}