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

    public void initialize(){
        cargarTabla();
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


    @FXML
    private void crearEvento(){
        Main.showDialog("dialogos/crear-evento-view","Crear evento");
        cargarTabla();
    }



}
