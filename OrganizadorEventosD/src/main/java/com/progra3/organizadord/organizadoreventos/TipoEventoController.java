package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import com.progra3.organizadord.organizadoreventos.models.TipoEventoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TipoEventoController {

    @FXML
    private Label lbId;

    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<TipoEventoModel> tbTipoEvento;

    @FXML
    private TableColumn<TipoEventoModel, Integer> clId;

    @FXML
    private TableColumn<TipoEventoModel, String> clDescripcion;

    @FXML
    private TableColumn<TipoEventoModel, Button> clEditar;

    @FXML
    private TableColumn<TipoEventoModel, Button> clEliminar;

    @FXML
    private TextField txtDescripcion;

    @FXML
    public void initialize(){
        clId.setCellValueFactory(new PropertyValueFactory<>("idTipoEvento"));
        clDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clEditar.setCellFactory(tc -> new TableCell<>(){
            @Override
            protected void updateItem(Button button, boolean b) {
                super.updateItem(button, b);
                if (b){
                    setGraphic(null);
                }
                else{
                    Button btnEditar = new Button("Editar");
                    setGraphic(btnEditar);
                    btnEditar.setOnAction(e ->{
                        TipoEventoModel eventoTemp = tbTipoEvento.getItems().get(getIndex());
                        lbId.setText(String.valueOf(eventoTemp.getIdTipoEvento()));
                        txtDescripcion.setText(eventoTemp.getDescripcion());
                        btnInsertar.setText("Actualizar");
                        btnLimpiar.setText("Cancelar");

                    });
                }
            }
        });

        clEliminar.setCellFactory(tc -> new TableCell<>(){
            @Override
            protected void updateItem(Button button, boolean b) {
                super.updateItem(button, b);
                if (b){
                    setGraphic(null);
                }
                else{
                    Button btnEliminar = new Button("Eliminar");
                    setGraphic(btnEliminar);
                    btnEliminar.setOnAction(e ->{
                        System.out.println("ELimina");
                        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setContentText("Desea eliminar el tipo de evento");
                        var respuesta = alerta.showAndWait();

                        if (respuesta.get() == ButtonType.OK){
                            TipoEventoModel eventoTemp = tbTipoEvento.getItems().get(getIndex());
                            try {
                                eventoTemp.eliminarTipoEvento(eventoTemp.getIdTipoEvento());
                                cargarTipoEvento();
                            } catch (RuntimeException ex) {
                                Alert alertEx = new Alert(Alert.AlertType.ERROR);
                                alertEx.setContentText("Error, no se puedo eliminar el tipo de evento porque esta asociado algun campo");
                                alertEx.show();
                            }
                        }
                    });
                }
            }
        });
        cargarTipoEvento();
    }

    public void cargarTipoEvento(){
        tbTipoEvento.setItems(TipoEventoModel.getTiposEvento());
    }

    @FXML
    void Insertar(ActionEvent event) {
        if(validarCamposVacios()) {
            if (btnInsertar.getText().equals("Agregar")) {
                agregarTipoEvento();
                limpiar();
            } else {
                System.out.println("Actualizar");
                actualizarTipoEvento();
                limpiar();
            }
            cargarTipoEvento();
        }
    }

    @FXML
    void Limpiar(ActionEvent event) {
        limpiar();
    }

    public void agregarTipoEvento(){
        TipoEventoModel tipoEventoModel = new TipoEventoModel();
        tipoEventoModel.setDescripcion(txtDescripcion.getText());
        if(tipoEventoModel.guardarTipoEvento()   > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Se agrego un nuevo de tipo evento a la base de datos");
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error al guardar el tipo de evento");
            alert.show();
        }
    }

    public void actualizarTipoEvento(){
        TipoEventoModel tipoEventoModel = new TipoEventoModel();
        tipoEventoModel.setDescripcion(txtDescripcion.getText());
        tipoEventoModel.setIdTipoEvento(Integer.parseInt(lbId.getText()));
        if(tipoEventoModel.actualizarTipoEvento(tipoEventoModel.getIdTipoEvento()) > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Se ha actualizado de tipo evento en la base de datos");
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error al actualizar el tipo de evento");
            alert.show();
        }

    }

    public void limpiar(){
        txtDescripcion.setText(null);
        lbId.setText(null);
        btnInsertar.setText("Agregar");
        btnLimpiar.setText("Limpiar");
    }

    public boolean validarCamposVacios(){
        if(txtDescripcion.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("El campo descripcion no puede estar vacio");
            alert.show();
            return false;
        }
        return true;
    }
}
