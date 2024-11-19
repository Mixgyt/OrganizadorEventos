package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;
import com.progra3.organizadord.organizadoreventos.models.CorreosEventosModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SeguimientoInvitadoController {
    @FXML
    private Button btnActualizar;

    @FXML
    private TableColumn<CorreosEventosModel, Integer> clE;

    @FXML
    private TableColumn<CorreosEventosModel, String> clidC;

    @FXML
    private TableColumn<CorreosEventosModel, String> clidCE;

    @FXML
    private TableColumn<CorreosEventosModel, String> clidE;

    @FXML
    private TableColumn<CorreosEventosModel, Integer> clidTI;

    @FXML
    private TableView<CorreosEventosModel> tbInv;
    @FXML
    private ComboBox<String> cmbInvitados;

    public void initialize(){
        clidCE.setCellValueFactory(new PropertyValueFactory<>("idCorreosEvento"));
        clidE.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        clidC.setCellValueFactory(new PropertyValueFactory<>("idCorreo"));
        clidTI.setCellValueFactory(new PropertyValueFactory<>("idTipoInvitado"));
        clE.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarTablaTodo();

        ObservableList<String> datoInvitadosMostrar = FXCollections.observableArrayList();
        datoInvitadosMostrar.add("Ver todos");
        datoInvitadosMostrar.add("Ver pendientes");
        datoInvitadosMostrar.add("Ver confirmados");
        datoInvitadosMostrar.add("Ver rechazados");

        cmbInvitados.setItems(datoInvitadosMostrar);
        cmbInvitados.getSelectionModel().selectFirst();
        cmbInvitados.setOnAction(e->{
            if (cmbInvitados.getSelectionModel().getSelectedIndex() == 0){
                cargarTablaTodo();
            }
            else if (cmbInvitados.getSelectionModel().getSelectedIndex() == 1){
                cargarPendiente();
            }
            else if (cmbInvitados.getSelectionModel().getSelectedIndex() == 2) {
                cargarConfirmado();
            }
            else if (cmbInvitados.getSelectionModel().getSelectedIndex() == 3) {
                cargarRechazado();
            }
        });

        btnActualizar.setOnAction(e->{
            if (cmbInvitados.getSelectionModel().getSelectedIndex() == 0){
                cargarTablaTodo();
            }
            else if (cmbInvitados.getSelectionModel().getSelectedIndex() == 1){
                cargarPendiente();
            }
            else if (cmbInvitados.getSelectionModel().getSelectedIndex() == 2) {
                cargarConfirmado();
            }
            else if (cmbInvitados.getSelectionModel().getSelectedIndex() == 3) {
                cargarRechazado();
            }
        });

    }
    public void cargarTablaTodo(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInv.setItems(correosEventosModel.mostrarCorreosEvento());
    }

    public void cargarConfirmado(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInv.setItems(correosEventosModel.mostrarInvitadosPorEstado(1));
    }
    public void cargarPendiente(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInv.setItems(correosEventosModel.mostrarInvitadosPorEstado(0));
    }
    public void cargarRechazado(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInv.setItems(correosEventosModel.mostrarInvitadosPorEstado(2));
    }
}
