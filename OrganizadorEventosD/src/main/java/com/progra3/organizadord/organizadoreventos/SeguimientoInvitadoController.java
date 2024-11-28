package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;
import com.progra3.organizadord.organizadoreventos.models.CorreoModel;
import com.progra3.organizadord.organizadoreventos.models.CorreosEventosModel;
import com.progra3.organizadord.organizadoreventos.models.EstadosModel;
import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Properties;

public class SeguimientoInvitadoController {
    @FXML
    private Button btnActualizarTabla;

    @FXML
    private Button btnEnviarRecordatorio;
    @FXML
    private TextField txtClaveApp;

    @FXML
    private TableColumn<CorreosEventosModel, EstadosModel> clE;

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

    @FXML
    private ComboBox<EventosModel> cmbVerEventos;

    private static String emailFrom = CorreoModel.buscarCorreo(UserSession.getUsuario().getIdCorreo());
    private Properties mProperties = new Properties();
    private Session mSession;
    private MimeMessage mCorreo;

    public void initialize(){

        clidCE.setCellValueFactory(new PropertyValueFactory<>("idCorreosEvento"));
        clidE.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        clidC.setCellValueFactory(new PropertyValueFactory<>("idCorreo"));
        clidTI.setCellValueFactory(new PropertyValueFactory<>("idTipoInvitado"));
        clE.setCellValueFactory(new PropertyValueFactory<>("estado"));

        ObservableList<String> datoInvitadosMostrar = FXCollections.observableArrayList();
        datoInvitadosMostrar.add("Ver todos");
        datoInvitadosMostrar.add("Ver pendientes");
        datoInvitadosMostrar.add("Ver confirmados");
        datoInvitadosMostrar.add("Ver rechazados");

        EventosModel eventosModel = new EventosModel();
        if (eventosModel.existenciaEventoUsuario()){
            cargarEventos();
            cargarTablaTodo();

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

            cmbVerEventos.setOnAction(e->{
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

            btnActualizarTabla.setOnAction(e->{
                cargarTablaTodo();
                restaurar();
            });

            btnEnviarRecordatorio.setOnAction(e->{
                if (!txtClaveApp.getText().replace(" ","").isEmpty()){
                    try {
                        Integer idEvento = Integer.parseInt(String.valueOf(cmbVerEventos.getValue().getIdEvento()));
                        CorreosEventosModel correosEventos = new CorreosEventosModel();
                        ObservableList<CorreosEventosModel> datoInvitados = correosEventos.invitadosPendientesPorEvento(idEvento);

                        mProperties.put("mail.smtp.host", "smtp.gmail.com");
                        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                        mProperties.setProperty("mail.smtp.starttls.enable", "true");
                        mProperties.setProperty("mail.smtp.port", "587");
                        mProperties.setProperty("mail.smtp.user", emailFrom);
                        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
                        mProperties.setProperty("mail.smtp.auth", "true");

                        Integer correosEnviados = 0;

                        mSession = Session.getInstance(mProperties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(emailFrom, txtClaveApp.getText());
                            }
                        });

                        for (CorreosEventosModel item : datoInvitados) {
                            CorreoModel correoTemp = new CorreoModel();
                            correoTemp.setCorreo(item.getIdCorreo());


                            String correoInvitado = item.getIdCorreo();
                            String asunto = "Recordatorio de evento: " + cmbVerEventos.getValue().getNombre();
                            String contenido = "<div>Te recordamos que has sido invitado a " + cmbVerEventos.getValue().getNombre()
                                    + " que se realizará el " + cmbVerEventos.getValue().getFechaInicial() + ".</div> <div>¡TE ESPERAMOS!</div> " +
                                    "<div><a href=\"http://localhost:8080/invitaciones/evento?id="+ cmbVerEventos.getValue().getIdEvento() + "&usuario=" + correoTemp.buscarId() + "\">Haz clic aquí para más información</a></div>";

                            if (correoInvitado == null || correoInvitado.isEmpty()) continue;

                            try {
                                mCorreo = new MimeMessage(mSession);
                                mCorreo.setFrom(new InternetAddress(emailFrom));
                                mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(correoInvitado));
                                mCorreo.setSubject(asunto);
                                mCorreo.setContent(contenido, "text/html; charset=UTF-8");

                                Transport.send(mCorreo);

                                correosEnviados++;
                            } catch (Exception ex) {
                                System.err.println("Error al enviar a: " + correoInvitado + " - " + ex.getMessage());
                            }
                        }

                        if (correosEnviados > 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Correo enviado");
                            alert.setContentText("Se han enviado los recordatorios.");
                            alert.show();
                        }

                        txtClaveApp.clear();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("No se ha ingresado la contraseña de aplicación.");
                    alert.show();
                }
            });

        }
    }

    public void restaurar(){
        cmbVerEventos.getSelectionModel().select(0);
        cmbInvitados.getSelectionModel().select(0);
    }

    public void cargarEventos(){
        cmbVerEventos.setItems(EventosModel.getEventos());
        cmbVerEventos.setValue(EventosModel.getEventos().get(0));
    }

    public void cargarTablaTodo(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInv.setItems(correosEventosModel.mostrarCorreosEvento(cmbVerEventos.getSelectionModel().getSelectedItem().getIdEvento()));
    }

    public void cargarConfirmado(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInv.setItems(correosEventosModel.mostrarInvitadosPorEstado(1, cmbVerEventos.getSelectionModel().getSelectedItem().getIdEvento()));
    }
    public void cargarPendiente(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInv.setItems(correosEventosModel.mostrarInvitadosPorEstado(0, cmbVerEventos.getSelectionModel().getSelectedItem().getIdEvento()));
    }
    public void cargarRechazado(){
        CorreosEventosModel correosEventosModel = new CorreosEventosModel();
        correosEventosModel.setIdAnfitrion(UserSession.getUsuario().getIdCorreo());
        tbInv.setItems(correosEventosModel.mostrarInvitadosPorEstado(2, cmbVerEventos.getSelectionModel().getSelectedItem().getIdEvento()));
    }
}