package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

public class EventosController {
    @FXML
    private TableColumn<EventosModel, String> clDescripcion;

    @FXML
    private TableColumn<EventosModel, String> clDetalles;

    @FXML
    private TableColumn<EventosModel, String > clEvento;

    @FXML
    private TableColumn<EventosModel, String> clFechaFinal;

    @FXML
    private TableColumn<EventosModel, String> clFechaInicio;

    @FXML
    private TableColumn<EventosModel, Integer> clIdEvento;

    @FXML
    private TableColumn<EventosModel, String> clTipoEvento;

    @FXML
    private TableColumn<EventosModel, String> clUbicacion;

    @FXML
    private TableColumn<EventosModel, String> clUsuario;

    @FXML
    private TableView<EventosModel> tbEventos;

    public void initialize(){
        cargarTablaEventos();
    }

    public void cargarTablaEventos(){
        this.clIdEvento.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        this.clUsuario.setCellValueFactory(new PropertyValueFactory<>("usuarioCadena"));
        this.clEvento.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.clFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicial"));
        this.clFechaFinal.setCellValueFactory(new PropertyValueFactory<>("fechaFinal"));
        this.clUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        this.clDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        this.clDetalles.setCellValueFactory(new PropertyValueFactory<>("detalles"));
        this.clTipoEvento.setCellValueFactory(new PropertyValueFactory<>("tipoEventoCadena"));

        EventosModel eventosModel = new EventosModel();
        this.tbEventos.setItems(eventosModel.getEventos());
    }
}
