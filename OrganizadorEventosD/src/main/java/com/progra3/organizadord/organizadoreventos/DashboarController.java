package com.progra3.organizadord.organizadoreventos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DashboarController {

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
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seguimiento-invitado-view.fxml"));
        Pane.setCenter(loader.load());
    }

    @FXML
    void onCorreo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("eventos-view.fxml"));
        Pane.setCenter(loader.load());
    }

    @FXML
    void onEvento(ActionEvent event) {

    }

    @FXML
    void onInicio(ActionEvent event) {

    }

    @FXML
    void onTipoEvento(ActionEvent event) {

    }

    @FXML
    void onTipoUsuario(ActionEvent event) {

    }

    @FXML
    void onUsuario(ActionEvent event) {

    }

}