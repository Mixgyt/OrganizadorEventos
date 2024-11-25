package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.models.UsuarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuarioController {

    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Label lbId;

    @FXML
    private TableColumn<UsuarioModel, Button> clEliminar;

    @FXML
    private TableColumn<UsuarioModel, Integer> clId;

    @FXML
    private TableColumn<UsuarioModel, String> clNombre;

    @FXML
    private TableColumn<UsuarioModel, Button> clEditar;

    @FXML
    private TableView<UsuarioModel> tbUsuario;

    @FXML
    private TextField txtClave;

    @FXML
    private TextField txtUsuario;

    @FXML
    public void initialize(){
        clId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
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
                        UsuarioModel eventoTemp = tbUsuario.getItems().get(getIndex());
                        txtUsuario.setText(eventoTemp.getNombre());
                        txtClave.setText(eventoTemp.getPass());
                        lbId.setText(String.valueOf(eventoTemp.getIdUsuario()));
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
                            UsuarioModel usuarioTemp = tbUsuario.getItems().get(getIndex());
                            try {
                                usuarioTemp.eliminarUsuario(usuarioTemp.getIdUsuario());
                                cargarUsuario();
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
        cargarUsuario();
    }

    public void cargarUsuario(){
        tbUsuario.setItems(UsuarioModel.getusuario());
    }

    @FXML
    void Insertar(ActionEvent event) {
        if(validarCamposVacios()){
            if(btnInsertar.getText().equals("Agregar")){
                agregar();
            }else{
                actualizar();
            }
        }
    }

    public void agregar(){
        System.out.println("Agrega");
    }

    public void actualizar(){
       System.out.println("Actualizar");
    }

    public boolean validarCamposVacios(){
        if(txtUsuario.getText().isEmpty() || txtClave.getText().isEmpty()){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Error, campos vacios");
            alerta.show();
            return false;
        }
        return true;
    }

    @FXML
    void Limpiar(ActionEvent event) {
        limpiar();
    }

    public void limpiar(){
        txtUsuario.setText("");
        txtClave.setText("");
        lbId.setText("");
        btnInsertar.setText("Agregar");
        btnLimpiar.setText("Limpiar");
    }

}
