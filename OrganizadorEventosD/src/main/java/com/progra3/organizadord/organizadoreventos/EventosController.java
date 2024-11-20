package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

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
    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrearEvento;

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
