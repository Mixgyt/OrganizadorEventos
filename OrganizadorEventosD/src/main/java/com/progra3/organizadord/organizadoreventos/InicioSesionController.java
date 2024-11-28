package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.models.UsuarioModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;


public class InicioSesionController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnCancelar;

    @FXML
    private Hyperlink link;

    @FXML
    private Button btnCrearCuenta;

    @FXML
    private Button btnShow;

    @FXML
    private TextField txtClaveText;

    @FXML
    private PasswordField txtClave;

    @FXML
    private TextField txtUsuario;

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
    private void iniciarSesion(){
        String usuario = txtUsuario.getText().trim();
        String clave = txtClave.getText();
        if (usuario.isEmpty() || clave.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Uno a varios campos estan vacios.");
            alert.show();
        }
        else {
            try {
                UsuarioModel usuarioModel = new UsuarioModel(usuario,clave);
                if (usuarioModel.iniciarSesion()){
                    txtUsuario.clear();
                    txtClave.clear();
                    Main.setRoot("dashboard-view", "Organizador de Eventos", 1100, 720);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El usuario o la contraseña es incorrecta");
                    alert.show();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void salir(){
        Main.setRoot("main-view","Inicio");
    }

    @FXML
    void crearUsuario(ActionEvent event) {
        link.setStyle("-fx-background-color: #3A6B8C");
        Main.setRoot("crear-usuario-view","Crear usuario");
    }
}