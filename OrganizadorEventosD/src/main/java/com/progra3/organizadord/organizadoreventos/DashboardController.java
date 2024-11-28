package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;
import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class DashboardController {

    @FXML
    private BorderPane Pane;

    @FXML
    private Button btnInicio;

    @FXML
    private Hyperlink lnkCorreo;

    @FXML
    private Hyperlink lnkEventos;

    @FXML
    private Hyperlink lnkTipoEvento;

    @FXML
    private Hyperlink lnkTipoUsuario;

    @FXML
    private Hyperlink lnkInvitado;

    @FXML
    private Hyperlink lnkUsuario;

    @FXML
    public void initialize(){
        Pane.setCenter(Main.getView("dialogos/calendario"));
    }


    @FXML
    void onEvento(ActionEvent event) {
        Pane.setCenter(Main.getView("eventos-view"));
    }
    @FXML
    void onCerrar(ActionEvent event) {
        UserSession.setUsuario(null);
        Main.setRoot("inicio-sesion-view","Inicio sesion", 707,665);
    }

    @FXML
    void onInicio(ActionEvent event) {
        Pane.setCenter(Main.getView("dialogos/calendario"));
    }


    @FXML
    void onInvitado(ActionEvent event) {
        Pane.setCenter(Main.getView("gestion-invitado-view"));
    }

    @FXML
    void onSeguimineto(ActionEvent event) {
        Pane.setCenter(Main.getView("seguimiento-invitado-view"));
    }


}