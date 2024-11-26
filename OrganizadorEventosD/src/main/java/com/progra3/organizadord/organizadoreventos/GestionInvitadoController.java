package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;
import com.progra3.organizadord.organizadoreventos.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class GestionInvitadoController {

    @FXML
    private Button btnAgregarInvitado;

    @FXML
    private Button btnImportarInvitado;

    @FXML
    private TableColumn<CorreosEventosModel, String> clCorreo;

    @FXML
    private TableColumn<CorreosEventosModel, String> clEstado;

    @FXML
    private TableColumn<CorreosEventosModel, String> clEvento;

    @FXML
    private TableColumn<CorreosEventosModel, String> clTipoInvitado;

    @FXML
    private ComboBox<EventosModel> cmbEvento;

    @FXML
    private ComboBox<TipoInvitadoModel> cmbTipoInvitado;

    @FXML
    private ComboBox<EventosModel> cmbVerEvento;

    @FXML
    private TableView<CorreosEventosModel> tbInvitados;

    @FXML
    private TextField txtCorreo;

    public void initialize(){
        clEvento.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        clCorreo.setCellValueFactory(new PropertyValueFactory<>("idCorreo"));
        clTipoInvitado.setCellValueFactory(new PropertyValueFactory<>("idTipoInvitado"));
        clEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarTablaTodo();

        cmbEvento.setItems(EventosModel.getEventos());
        cmbTipoInvitado.setItems(TipoInvitadoModel.getTiposInvitado());
        EventosModel eventosModel = new EventosModel();
        eventosModel.setNombre("Ver Todos");
        cmbVerEvento.getItems().add(eventosModel);

        for (EventosModel item: EventosModel.getEventos()){
            cmbVerEvento.getItems().add(item);
        }

        cmbVerEvento.getSelectionModel().select(0);
        cmbVerEvento.setOnAction(e->{
            if (cmbVerEvento.getSelectionModel().getSelectedIndex() == 0){
                cargarTablaTodo();
            }
            else {
                cargarTablaPorEvento(cmbVerEvento.getSelectionModel().getSelectedItem().getIdEvento());
            }
        });

        btnAgregarInvitado.setOnAction(e->{
            String correo = txtCorreo.getText().toLowerCase().replace(" ","");

            //valida si los combo box estan seleccionados
            if (cmbEvento.getSelectionModel().getSelectedIndex() > -1 && cmbTipoInvitado.getSelectionModel().getSelectedIndex() > -1){
                //Valida si el campo de correo esta vacio
                if (!correo.isEmpty()){
                    CorreoModel correoModel = new CorreoModel(correo);
                    //Valida si el correo existe, en caso de que no exista la cree
                    if (correoModel.existe()){
                        CorreosEventosModel correosEventosModel = new CorreosEventosModel(
                                String.valueOf(cmbEvento.getSelectionModel().getSelectedItem().getIdEvento()),
                                String.valueOf(correoModel.buscarId()),
                                String.valueOf(cmbTipoInvitado.getSelectionModel().getSelectedItem().getIdTipoInvitado()),
                                EstadosModel.valueOfValor(0)
                        );

                        correosEventosModel.insertarCorreoEvento();

                        cargarTablaTodo();

                    }
                    else{
                        correoModel.crearCorreo();
                        correoModel.setCorreo(correo);
                        CorreosEventosModel correosEventosModel = new CorreosEventosModel(
                                String.valueOf(cmbEvento.getSelectionModel().getSelectedItem().getIdEvento()),
                                String.valueOf(correoModel.buscarId()),
                                String.valueOf(cmbTipoInvitado.getSelectionModel().getSelectedItem().getIdTipoInvitado()),
                                EstadosModel.valueOfValor(0)
                        );

                        correosEventosModel.insertarCorreoEvento();

                        cargarTablaTodo();
                    }
                }
                else {
                    txtCorreo.setText(correo);

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Campo vacio");
                    alert.setContentText("El campo de correo se encuentra vacio, por favor ingrese el correo");
                    alert.show();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lista desplegable si seleccionar");
                alert.setContentText("Un o varias listas desplecables no están seleccionadas");
                alert.show();
            }
        });

    }
    public void limpiar(){
        cmbEvento.getSelectionModel().select(-1);
        cmbTipoInvitado.getSelectionModel().select(-1);
        txtCorreo.clear();
    }
    public void cargarTablaTodo(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInvitados.setItems(correosEventosModel.mostrarCorreosEvento());
    }
    public void cargarTablaPorEvento(int evento){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInvitados.setItems(correosEventosModel.mostrarCorreosEventoEspecifico(evento));
    }
}
