package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.controllers.dialogos.DetalleEventoController;
import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import com.progra3.organizadord.organizadoreventos.models.TipoEventoModel;

import javafx.collections.ObservableList;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

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
    private TableColumn<EventosModel, Button> clEditar;

    @FXML
    private TableColumn<EventosModel, Button> clEliminar;

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

    //carga todos los eventos de el usuario que ha iniciado sesion
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
        this.clEditar.setCellFactory(tc -> new TableCell<>(){
            @Override
            protected void updateItem(Button button, boolean b) {
                super.updateItem(button, b);
                Button btnEditar = new Button("Editar");
                if (!b){
                    btnEditar.setOnAction(e ->{
                        //Abrir ventana de dialogo
                        Main.showDialog("dialogos/crear-evento-view", "Editar Evento", tbEventos.getItems().get(getIndex()));

                    });
                    setGraphic(btnEditar);
                    return;
                }
                setText(null);
            }
        });
        this.clEliminar.setCellFactory(tc -> new TableCell<>(){
            @Override
            protected void updateItem(Button button, boolean b) {
                super.updateItem(button, b);
                Button btnEditar = new Button("Eliminar");
                if (!b){
                    btnEditar.setOnAction(e ->{
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Eliminar");
                        alert.setContentText("Está seguro de eliminar registro?");

                        //admite 2 posibles resultados, respuesta de un obj o null
                        Optional<ButtonType> respuesta = alert.showAndWait();
                        if (respuesta.get() == ButtonType.OK){

                            //obtiene la fila que seleccionamos
                            EventosModel eventosModel = getTableView().getItems().get(getIndex());
                            eventosModel.eliminarEvento(eventosModel.getIdEvento());
                            cargarTabla();
                        }

                    });
                    setGraphic(btnEditar);
                    return;
                }
                setText(null);
            }
        });

        this.tbEventos.getItems().clear();
        this.tbEventos.setItems(EventosModel.getEventos());
    }

    //carga los tipos de evento en el combobox
    private void cargarTipoEvento(){
        TipoEventoModel tipoEventoModel = new TipoEventoModel();
        this.cbTipoEvento.setItems(tipoEventoModel.getTiposEvento());

    }

    //abre dialogo para crear un evento
    @FXML
    private void crearEvento(){
        Main.showDialog("dialogos/crear-evento-view","Crear evento");
        cargarTabla();
    }

    //busca eventos segun un rango de fecha y un tipo si es seleccionado en combobox
    @FXML
    private void eventosBuscar(ActionEvent event) {
        int idTipoEvento;
        LocalDate fechaInicial;
        LocalDate fechaFinal;

        //alerta de datos no existentes
        Alert alertaDatos = new Alert(Alert.AlertType.WARNING);
        alertaDatos.setTitle("No hay datos");
        alertaDatos.setContentText("No existen eventos en ese rango de fecha");

        ObservableList<EventosModel> eventosModels;
        if (this.dpFechaInicial.getValue() == null || this.dpFechaFinal.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Conflicto de fechas");
            alert.setContentText("Ingrese ambas fechas para buscar");
            alert.showAndWait();
        }else {

            if (this.dpFechaInicial.getValue() != null && this.dpFechaFinal.getValue() != null && this.cbTipoEvento.getValue() != null){
                fechaInicial = this.dpFechaInicial.getValue();
                fechaFinal = this.dpFechaFinal.getValue();
                idTipoEvento = this.cbTipoEvento.getValue().getIdTipoEvento();
                if (validacionFechas(fechaInicial,fechaFinal)){
                    eventosModels = EventosModel.getEventosRangoTipo(fechaInicial,fechaFinal,idTipoEvento);
                    if (eventosModels.size() > 0){
                        this.tbEventos.getItems().clear();
                        this.tbEventos.refresh();
                        this.tbEventos.setItems(eventosModels);
                    }else {
                        alertaDatos.showAndWait();
                    }
                }
            }else if(this.dpFechaInicial.getValue() != null && this.dpFechaFinal.getValue() != null && this.cbTipoEvento.getValue() == null){
                fechaInicial = this.dpFechaInicial.getValue();
                fechaFinal = this.dpFechaFinal.getValue();
                if (validacionFechas(fechaInicial,fechaFinal)){
                    eventosModels = EventosModel.getEventosRango(fechaInicial,fechaFinal);
                    if (eventosModels.size() > 0){
                        this.tbEventos.getItems().clear();
                        this.tbEventos.refresh();
                        this.tbEventos.setItems(eventosModels);
                    }else {
                        alertaDatos.showAndWait();
                    }
                }
            }
        }
    }

    //devuelve un booleano al comparar fechaInicial > fechaFinal
    private boolean validacionFechas(LocalDate fechaInicial, LocalDate fechaFinal){
        if (fechaInicial.isAfter(fechaFinal)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Conflicto de fechas");
            alert.setContentText("No puedes ingresar una fecha final menor que la de fecha inicial");
            alert.showAndWait();
            return false;
        }else{
            return true;
        }
    }

    //Limpia inputs del usuario para buscar eventos
    @FXML
    void eventoLimpiar(ActionEvent event) {
        this.dpFechaInicial.setValue(null);
        this.dpFechaFinal.setValue(null);
        cbTipoEvento.getSelectionModel().clearSelection();
        cargarTabla();
    }
}
