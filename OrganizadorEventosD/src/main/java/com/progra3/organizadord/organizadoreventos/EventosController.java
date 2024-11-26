package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.controllers.dialogos.DetalleEventoController;
import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import com.progra3.organizadord.organizadoreventos.models.TipoEventoModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

public class EventosController {
    @FXML
    private DatePicker dpFechaFinal;
    @FXML
    private DatePicker dpFechaInicial;

    @FXML
    private ComboBox<TipoEventoModel> cbTipoEvento;

    @FXML
    private TableColumn<EventosModel, String > clEvento;

    @FXML
    private TableColumn<EventosModel, String> clFechaFinal;

    @FXML
    private TableColumn<EventosModel, String> clFechaInicio;

    @FXML
    private TableColumn<EventosModel, String> clTipoEvento;

    @FXML
    private TableView<EventosModel> tbEventos;
    @FXML
    private TableColumn<EventosModel, Button> clDetalles;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrearEvento;
    @FXML
    private Button btnLimpiar;

    public void initialize(){
        cargarTabla();
        cargarTipoEvento();
    }

    public void cargarTabla(){
        this.clEvento.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.clFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicial"));
        this.clFechaFinal.setCellValueFactory(new PropertyValueFactory<>("fechaFinal"));
        this.clTipoEvento.setCellValueFactory(new PropertyValueFactory<>("tipoEventoCadena"));
        this.clDetalles.setCellFactory(ts-> new TableCell<>(){
            @Override
            protected void updateItem(Button button, boolean b) {
                super.updateItem(button, b);
                Button btnDetalles = new Button("Detalles");
                if(!b){
                    btnDetalles.setOnAction(x->{
                        //Abrir ventana de dialogo
                        System.out.println("Ventana");
                        Main.showDialogDetalle("dialogos/detalle-evento-view", "Detalles de Evento", tbEventos.getItems().get(getIndex()));
                    });
                    setGraphic(btnDetalles);
                    return;
                }
                setText(null);
            }
        });
        this.tbEventos.getItems().clear();
        this.tbEventos.setItems(EventosModel.getEventos());
    }

    private void cargarTipoEvento(){
        TipoEventoModel tipoEventoModel = new TipoEventoModel();
        this.cbTipoEvento.setItems(tipoEventoModel.getTiposEvento());

    }

    private void cargarEventosRango(){
        if((String.valueOf(dpFechaInicial.getValue())) != null){
            this.tbEventos.getItems().clear();

        }
    }
    @FXML
    private void crearEvento(){
        Main.showDialog("dialogos/crear-evento-view","Crear evento");
        cargarTabla();
    }


    @FXML
    private void eventosBuscar(ActionEvent event) {
        int idTipoEvento;
        LocalDate fechaInicial;
        LocalDate fechaFinal;

        if (this.dpFechaInicial.getValue() == null || this.dpFechaFinal.getValue() == null || this.cbTipoEvento.getValue() == null) {
            System.out.println("HAY QUE VALIDAR");
        }else {
            this.tbEventos.getItems().clear();
            this.tbEventos.refresh();
            if (this.dpFechaInicial.getValue() != null && this.dpFechaFinal.getValue() != null && this.cbTipoEvento.getValue() != null){
                fechaInicial = this.dpFechaInicial.getValue();
                fechaFinal = this.dpFechaFinal.getValue();
                idTipoEvento = this.cbTipoEvento.getValue().getIdTipoEvento();
                this.tbEventos.setItems(EventosModel.getEventosRangoTipo(fechaInicial,fechaFinal,idTipoEvento));

            }else if(this.dpFechaInicial.getValue() != null && this.dpFechaFinal.getValue() != null && this.cbTipoEvento.getValue() == null){
                fechaInicial = this.dpFechaInicial.getValue();
                fechaFinal = this.dpFechaFinal.getValue();
                this.tbEventos.setItems(EventosModel.getEventosRango(fechaInicial,fechaFinal));
            }
        }
    }

    @FXML
    void eventoLimpiar(ActionEvent event) {
        this.dpFechaInicial.setValue(null);
        this.dpFechaFinal.setValue(null);
        cbTipoEvento.getSelectionModel().clearSelection();
        cargarTabla();
    }
}
