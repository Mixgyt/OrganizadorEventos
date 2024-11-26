package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.models.TipoEventoModel;
import com.progra3.organizadord.organizadoreventos.models.TipoInvitadoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TipoInvitadoController {

    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<TipoInvitadoModel> tbTipoInvitado;

    @FXML
    private TableColumn<TipoInvitadoModel, String> clDescripcion;

    @FXML
    private TableColumn<TipoInvitadoModel, Integer> clId;

    @FXML
    private TableColumn<TipoInvitadoModel, Button> clEditar;

    @FXML
    private TableColumn<TipoInvitadoModel, Button> clEliminar;


    @FXML
    private Label lbId;

    @FXML
    private TextField txtDescripcion;

    @FXML
    public void initialize(){
        clId.setCellValueFactory(new PropertyValueFactory<>("idTipoInvitado"));
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
                        TipoInvitadoModel eventoTemp = tbTipoInvitado.getItems().get(getIndex());
                        lbId.setText(String.valueOf(eventoTemp.getIdTipoInvitado()));
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
                            TipoInvitadoModel eventoTemp = tbTipoInvitado.getItems().get(getIndex());
                            try {
                                eventoTemp.eliminarTipoInvitado(eventoTemp.getIdTipoInvitado());
                                cargarTipoInvitado();
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
        cargarTipoInvitado();
    }

    public void cargarTipoInvitado(){
        tbTipoInvitado.setItems(TipoInvitadoModel.getTiposInvitado());
    }

    @FXML
    void Insertar(ActionEvent event) {
        if(validarCamposVacios()){
            if(btnInsertar.getText().equals("Agregar")) {
                agregar();
            }
            else{
                actualizar();
            }
            cargarTipoInvitado();
        }
    }

    public void agregar(){
        TipoInvitadoModel tipoInvitadoModel = new TipoInvitadoModel();
        tipoInvitadoModel.setDescripcion(txtDescripcion.getText());
        if(tipoInvitadoModel.guardarTipoInvitado() > 0){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText("Tipo de invitado guardado");
            alerta.show();
            cargarTipoInvitado();
            limpiarCampos();
        }
        else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Error al guardar");
            alerta.show();
        }
    }
    public void actualizar(){
        TipoInvitadoModel tipoInvitadoModel = new TipoInvitadoModel();
        tipoInvitadoModel.setIdTipoInvitado(Integer.parseInt(lbId.getText()));
        tipoInvitadoModel.setDescripcion(txtDescripcion.getText());
        if(tipoInvitadoModel.actualizarTipoInvitado(tipoInvitadoModel.getIdTipoInvitado()) > 0){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText("Tipo de invitado actualizado");
            alerta.show();
            cargarTipoInvitado();
            limpiarCampos();
        }
        else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Error al actualizar");
            alerta.show();
        }
    }
    @FXML
    void Limpiar(ActionEvent event) {
        limpiarCampos();
    }

    public void limpiarCampos(){
        lbId.setText("");
        txtDescripcion.setText("");
        btnInsertar.setText("Insertar");
        btnLimpiar.setText("Limpiar");
    }

    public boolean  validarCamposVacios(){
        if(txtDescripcion.getText().isEmpty()){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Error, campos vacios");
            alerta.show();
            return false;
        }
        return true;
    }

}
