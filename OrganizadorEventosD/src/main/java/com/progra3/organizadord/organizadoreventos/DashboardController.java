package com.progra3.organizadord.organizadoreventos;

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
    private Hyperlink lnkUsuario;

    @FXML
    public void initialize(){
        Pane.setCenter(Main.getView("seguimiento-invitado-view"));
    }

    @FXML
    void onCorreo(){
        Pane.setCenter(Main.getView("eventos-view"));
    }

    @FXML
    void onEvento(ActionEvent event) {

    }

    @FXML
    void onInicio(ActionEvent event) {
        Pane.setCenter(Main.getView("seguimiento-invitado-view"));
    }

    @FXML
    void onTipoEvento(ActionEvent event) {
        Pane.setCenter(Main.getView("tipo-evento-view"));
    }

    @FXML
    void onTipoUsuario(ActionEvent event) {

    }

    @FXML
    void onUsuario(ActionEvent event) {

    }

}