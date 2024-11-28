package com.progra3.organizadord.organizadoreventos.controllers.dialogos;

import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;
import com.progra3.organizadord.organizadoreventos.Main;
import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import com.progra3.organizadord.organizadoreventos.models.TipoEventoModel;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.time.LocalDateTime;


public class CrearEventoController
{
    @FXML
    private ComboBox<String> cmbHoraFinal;
    @FXML
    private ComboBox<String> cmbFranjaHorariaInicio;
    @FXML
    private TextField txtNombreEvento;
    @FXML
    private TextField txtUbicacionEvento;
    @FXML
    private DatePicker dtpFechaFinal;
    @FXML
    private ComboBox<String> cmbFranjaHorariaFinal;
    @FXML
    private ComboBox<TipoEventoModel> cmbTipoEvento;
    @FXML
    private Button btnCrearEvento;
    @FXML
    private TextField txtDetallesEvento;
    @FXML
    private ComboBox<String> cmbHoraInicio;
    @FXML
    private TextField txtDescripcionEvento;
    @FXML
    private DatePicker dtpFechaInicio;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<String> cmbMinutosInicio;
    @FXML
    private ComboBox<String> cmbMinutosFinal;

    @FXML
    public void initialize() {
        cargarHoras();
        cargandoFechaHoraActual();
        cargarTipoDeEvento();
    }

    //Cargando valores a los comboBox
    private void cargarHoras(){
        for(int i=1;i<=12;i++){
            cmbHoraInicio.getItems().add(String.format("%d", i));
            cmbHoraFinal.getItems().add(String.format("%d", i));
        }

        for(int i=0;i<=59;i++){
            if(i%5==0){
                cmbMinutosInicio.getItems().add(String.format("%02d", i));
                cmbMinutosFinal.getItems().add(String.format("%02d", i));
            }
        }

        cmbFranjaHorariaInicio.getItems().addAll("AM","PM");
        cmbFranjaHorariaFinal.getItems().addAll("AM","PM");
    }

    //Obteniendo la hora y fecha actuales para mostrar
    private void cargandoFechaHoraActual(){
        cmbFranjaHorariaInicio.setValue("AM");
        cmbFranjaHorariaFinal.setValue("AM");
        LocalDateTime localDate = LocalDateTime.now();
        String minutos = String.format("%02d",localDate.getMinute());
        int horas = localDate.getHour();
        if(horas>12){
            horas=horas-12;
            cmbFranjaHorariaInicio.setValue("PM");
            cmbFranjaHorariaFinal.setValue("PM");
        }
        cmbHoraInicio.setValue(String.valueOf(horas));
        cmbMinutosInicio.setValue(minutos);
        cmbHoraFinal.setValue(String.valueOf(horas));
        cmbMinutosFinal.setValue(minutos);

        dtpFechaInicio.setValue(localDate.toLocalDate());
        dtpFechaFinal.setValue(localDate.toLocalDate());
    }

    private void cargarTipoDeEvento(){
        cmbTipoEvento.setItems(TipoEventoModel.getTiposEvento());
    }

    @FXML
    private void crearEvento(){
        if(camposVacios()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Faltan datos");
            alert.setContentText("Hay campos importante que se dejaron vacios");
            alert.showAndWait();
           return;
        }

        String nombreEvento = txtNombreEvento.getText();
        String ubicacionEvento = txtUbicacionEvento.getText();
        String descripcionEvento = txtDescripcionEvento.getText();
        String detallesEvento = txtDetallesEvento.getText().isEmpty() ? "" : txtDetallesEvento.getText();
        int idTipoEvento = cmbTipoEvento.getValue().getIdTipoEvento();

        //0-Inicio 1-Final
        String[] fechas = getFechas();
        if(fechas==null){
            return;
        }
        String fechaInicial = fechas[0];
        String fechaFinal = fechas[1];

        EventosModel nuevoEvento = new EventosModel(
                UserSession.getUsuario().getIdUsuario(),
                nombreEvento,
                fechaInicial,
                fechaFinal,
                ubicacionEvento,
                descripcionEvento,
                detallesEvento,
                idTipoEvento
        );
        nuevoEvento.guardarEvento();
        Main.close();
    }

    private String[] getFechas(){
        String[] fechas = new String[2];

        //Fecha Inicio indice = 0
        String fechaInicio = String.valueOf(dtpFechaInicio.getValue());
        int horaInicioInt = Integer.parseInt(cmbHoraInicio.getValue());
        String horaInicio;
        String minutosInicio = cmbMinutosInicio.getValue();

        if(cmbFranjaHorariaInicio.getValue().equals("PM")){
            if(horaInicioInt != 12){
                horaInicioInt = horaInicioInt+12;
            }
        }

        if(horaInicioInt==24){
            horaInicioInt = 0;
        }

        horaInicio = String.format("%02d",horaInicioInt);

        String tiempoInicio = horaInicio+":"+minutosInicio+":00";
        fechaInicio+="T"+tiempoInicio;

        //Fecha Final indice = 0
        String fechaFinal = String.valueOf(dtpFechaFinal.getValue());
        int horaFinalInt = Integer.parseInt(cmbHoraFinal.getValue());
        String horaFinal;
        String minutosFinal = cmbMinutosFinal.getValue();

        if(cmbFranjaHorariaFinal.getValue().equals("PM")){
            if(horaFinalInt != 12){
                horaFinalInt = horaFinalInt+12;
            }
        }

        if(horaFinalInt==24){
            horaFinalInt = 0;
        }

        horaFinal = String.format("%02d",horaFinalInt);

        String tiempoFinal = horaFinal+":"+minutosFinal+":00";
        fechaFinal+="T"+tiempoFinal;
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
        LocalDateTime fin = LocalDateTime.parse(fechaFinal);
        if(inicio.isAfter(fin) || inicio.equals(fin)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Conflicto de fechas");
            alert.setContentText("No puedes ingresar una final menor que la de inicial");
            alert.showAndWait();
            return null;
        }

        fechas[0] = fechaInicio;
        fechas[1] = fechaFinal;
        return fechas;
    }

    private boolean camposVacios(){
        boolean textFields = txtNombreEvento.getText().isEmpty() || txtUbicacionEvento.getText().isEmpty() || txtDescripcionEvento.getText().isEmpty();
        boolean comboBox = cmbTipoEvento.getSelectionModel().isEmpty();
        return textFields || comboBox;
    }

    @FXML
    private void onCancelar(){
        Main.close();
    }
}