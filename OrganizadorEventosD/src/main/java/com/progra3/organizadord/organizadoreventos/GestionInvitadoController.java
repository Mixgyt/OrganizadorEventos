package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;
import com.progra3.organizadord.organizadoreventos.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class GestionInvitadoController {

    @FXML
    private Button btnAgregarInvitado;

    @FXML
    private Button btnCancelar;

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
    private TableColumn<CorreosEventosModel, Button> clEditar;

    @FXML
    private TableColumn<CorreosEventosModel, Button> clEliminar;

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

    FileChooser fileChooser = new FileChooser();

    Integer idCorreoEvento = 0;

    public void initialize(){
        clEvento.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        clCorreo.setCellValueFactory(new PropertyValueFactory<>("idCorreo"));
        clTipoInvitado.setCellValueFactory(new PropertyValueFactory<>("idTipoInvitado"));
        clEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
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
                        CorreosEventosModel correosEventosModel = tbInvitados.getItems().get(getIndex());
                        idCorreoEvento = correosEventosModel.getIdCorreosEvento();
                        EventosModel eventos = new EventosModel();
                        eventos.setNombre(correosEventosModel.getIdEvento());
                        for (int i = 0; i < cmbEvento.getItems().size(); i++) {
                            if (cmbEvento.getItems().get(i).getIdEvento() == eventos.buscarId()){
                                cmbEvento.getSelectionModel().select(i);
                                break;
                            }
                        }

                        txtCorreo.setText(correosEventosModel.getIdCorreo());

                        TipoInvitadoModel tipoInvitadoModel = new TipoInvitadoModel();
                        tipoInvitadoModel.setDescripcion(correosEventosModel.getIdTipoInvitado());
                        for (int i = 0; i < cmbTipoInvitado.getItems().size(); i++) {
                            if (cmbTipoInvitado.getItems().get(i).getIdTipoInvitado() == tipoInvitadoModel.buscarId()){
                                cmbTipoInvitado.getSelectionModel().select(i);
                                break;
                            }
                        }


                        btnAgregarInvitado.setText("Actualizar");
                        btnCancelar.setText("Cancelar");
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
                        alerta.setContentText("Desea eliminar el invitado");
                        var respuesta = alerta.showAndWait();

                        if (respuesta.get() == ButtonType.OK){
                            try {
                                CorreosEventosModel correosEventosModel = tbInvitados.getItems().get(getIndex());
                                CorreoModel correoModel = new CorreoModel();
                                correoModel.setCorreo(correosEventosModel.getIdCorreo());
                                correosEventosModel.setIdCorreo(String.valueOf(correoModel.buscarId()));
                                correosEventosModel.eliminarCoreoEvento();
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

                        if (btnAgregarInvitado.getText().equals("Actualizar")){
                            correosEventosModel.setIdCorreosEvento(idCorreoEvento);
                            correosEventosModel.actualizarCorreoEvento();
                            limpiar();
                            cargarTablaTodo();
                            idCorreoEvento = 0;
                        }
                        else {
                            correosEventosModel.insertarCorreoEvento();
                            limpiar();
                            cargarTablaTodo();
                        }

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

        btnCancelar.setOnAction(e->{
            limpiar();
        });

        btnImportarInvitado.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setContentText("Asegurate de que la estructura de las columnas debe ser Evento, Correo, Tipo Invitado");
            alert.show();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo CSV");

            // Filtro de extensión para archivos CSV
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Archivos CSV", "*.csv")
            );

            // Mostrar el diálogo de selección de archivo
            File selectedFile = fileChooser.showOpenDialog(new Stage());

            // Verificar si se seleccionó un archivo
            if (selectedFile != null) {
                try {
                    ObservableList<CorreosEventosModel> datosInvitados = FXCollections.observableArrayList();
                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                    String linea="";
                    while ((linea= reader.readLine())!=null){
                        CorreosEventosModel correosEventosTemp = new CorreosEventosModel();

                        String[] row = linea.split(";");
                        System.out.println(row[0] + "++++" + row[1] + "+++" + row[2]);
                        correosEventosTemp.setIdEvento(row[0]);
                        correosEventosTemp.setIdCorreo(row[1]);
                        correosEventosTemp.setIdTipoInvitado(row[2]);
                        datosInvitados.add(correosEventosTemp);
                    }

                    importarNuevosInvitados(datosInvitados);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.println("No se seleccionó ningún archivo.");
            }
        });

    }
    public void limpiar(){
        btnCancelar.setText("Limpiar");
        btnAgregarInvitado.setText("Agregar Invitado");
        cmbEvento.getSelectionModel().select(-1);
        cmbTipoInvitado.getSelectionModel().select(-1);
        txtCorreo.clear();
    }

    public void cargarTablaTodo(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInvitados.setItems(correosEventosModel.mostrarCorreosEvento());
    }

    public void cargarTablaTodo(int evento){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInvitados.setItems(correosEventosModel.mostrarCorreosEventoEspecifico(evento));
    }
    public void cargarTablaPorEvento(int evento){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInvitados.setItems(correosEventosModel.mostrarCorreosEventoEspecifico(evento));
    }
    public void importarNuevosInvitados(ObservableList<CorreosEventosModel> datos){
        try {
            for (CorreosEventosModel item: datos){

                TipoInvitadoModel tipoInvitadoTemp = new TipoInvitadoModel();
                EventosModel eventosTemp = new EventosModel();

                tipoInvitadoTemp.setDescripcion(item.getIdTipoInvitado());
                eventosTemp.setNombre(item.getIdEvento());

                //Verifica si el tipo de usuario ingresado existe para ver si los datos son correctos
                if (tipoInvitadoTemp.tipoInvitadoExistente()){

                    if (eventosTemp.eventoExistente()){

                        CorreoModel correoTemp= new CorreoModel();
                        correoTemp.setCorreo(item.getIdCorreo());
                        if (correoTemp.existe()){

                            Integer idEvento = eventosTemp.buscarId();
                            Integer idCorreo = correoTemp.buscarId();
                            Integer idTipoInvitado = tipoInvitadoTemp.buscarId();

                            CorreosEventosModel validarInvitado = new CorreosEventosModel();
                            validarInvitado.setIdCorreo(String.valueOf(idCorreo));
                            validarInvitado.setIdEvento(String.valueOf(idEvento));
                            if (validarInvitado.invitadoExistente()){
                                CorreosEventosModel correosEventosModel = new CorreosEventosModel(
                                        String.valueOf(idEvento),
                                        String.valueOf(idCorreo),
                                        String.valueOf(idTipoInvitado),
                                        EstadosModel.valueOfValor(0)
                                );
                                correosEventosModel.insertarCorreoEvento();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Información");
                                alert.setContentText("Se han importado los invitados");
                                alert.show();

                                cargarTablaTodo();
                            }

                        }
                        else{
                            correoTemp.crearCorreo();
                            Integer idEvento = eventosTemp.buscarId();
                            Integer idCorreo = correoTemp.buscarId();
                            Integer idTipoInvitado = tipoInvitadoTemp.buscarId();
                            CorreosEventosModel validarInvitado = new CorreosEventosModel();
                            validarInvitado.setIdCorreo(String.valueOf(idCorreo));
                            validarInvitado.setIdEvento(String.valueOf(idEvento));
                            if (validarInvitado.invitadoExistente()){
                                CorreosEventosModel correosEventosModel = new CorreosEventosModel(
                                        String.valueOf(idEvento),
                                        String.valueOf(idCorreo),
                                        String.valueOf(idTipoInvitado),
                                        EstadosModel.valueOfValor(0)
                                );
                                correosEventosModel.insertarCorreoEvento();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Información");
                                alert.setContentText("Se han importado los invitados");
                                alert.show();

                                cargarTablaTodo();
                            }
                        }
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error de foramto");
                        alert.setContentText("No se ha encontrado el evento, el eveto esta mal ingresado o error en la estructura de la columna,la estructura de las columnas debe " +
                                "ser Evento, Correo, Tipo Invitado");
                        alert.show();
                        break;
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de foramto");
                    alert.setContentText("No se ha encontrado el tipo de invitado, el tipo esta mal ingresado o error en la estructura de la columna,la estructura de las columnas debe " +
                            "ser Evento, Correo, Tipo Invitado");
                    alert.show();
                    break;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}