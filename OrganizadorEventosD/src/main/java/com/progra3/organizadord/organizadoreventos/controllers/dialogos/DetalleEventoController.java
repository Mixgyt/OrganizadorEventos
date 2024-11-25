package com.progra3.organizadord.organizadoreventos.controllers.dialogos;

import com.progra3.organizadord.organizadoreventos.models.CorreosEventosModel;
import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DetalleEventoController {

    @FXML
    private TableColumn<CorreosEventosModel, String> clCorreos;

    @FXML
    private TableColumn<CorreosEventosModel, String> clEstado;

    @FXML
    private Label lbDetalle;

    @FXML
    private Label lbFechaFinalizacion;

    @FXML
    private Label lbFechaInicio;

    @FXML
    private Label lbHoraFinalizacion;

    @FXML
    private Label lbHoraInicio;

    @FXML
    private Label lbInformacion;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbUbicacion;

    @FXML
    private TableView<CorreosEventosModel> tbInvitados;

    @FXML
    public void initialize(){
        clCorreos.setCellValueFactory( new PropertyValueFactory<>("idCorreo"));
        clEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    public void cargarEvento(EventosModel evento){
        lbNombre.setText(evento.getNombre());
        lbFechaInicio.setText(evento.getFechaInicial());
        lbFechaFinalizacion.setText(evento.getFechaFinal());
        lbUbicacion.setText(evento.getUbicacion());
        lbInformacion.setText(evento.getDescripcion());
        lbDetalle.setText(evento.getDetalles());
    }

    public void cargarTabla(EventosModel evento){
        tbInvitados.setItems(CorreosEventosModel.mostrarInvitadosEvento(evento.getIdEvento()));
    }

}
