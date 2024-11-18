package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CorreosEventosModel {
    private Integer idCorreosEvento;
    private String idEvento;
    private String idCorreo;
    private String idTipoInvitado;
    private Integer estado;
    private Integer idAnfitrion;

    public CorreosEventosModel(Integer idCorreosEvento, String idEvento, String idCorreo, String idTipoInvitado, Integer estado, Integer idAnfitrion) {
        this.idCorreosEvento = idCorreosEvento;
        this.idEvento = idEvento;
        this.idCorreo = idCorreo;
        this.idTipoInvitado = idTipoInvitado;
        this.estado = estado;
        this.idAnfitrion = idAnfitrion;
    }

    public CorreosEventosModel(String idEvento, String idCorreo, String idTipoInvitado, Integer estado, Integer idAnfitrion) {
        this.idEvento = idEvento;
        this.idCorreo = idCorreo;
        this.idTipoInvitado = idTipoInvitado;
        this.estado = estado;
        this.idAnfitrion = idAnfitrion;
    }

    public CorreosEventosModel(Integer idCorreosEvento, String idEvento, String idCorreo, String idTipoInvitado, Integer estado) {
        this.idCorreosEvento = idCorreosEvento;
        this.idEvento = idEvento;
        this.idCorreo = idCorreo;
        this.idTipoInvitado = idTipoInvitado;
        this.estado = estado;
    }

    public CorreosEventosModel() {
    }

    public Integer getIdAnfitrion() {
        return idAnfitrion;
    }

    public void setIdAnfitrion(Integer idAnfitrion) {
        this.idAnfitrion = idAnfitrion;
    }

    public Integer getIdCorreosEvento() {
        return idCorreosEvento;
    }

    public void setIdCorreosEvento(Integer idCorreosEvento) {
        this.idCorreosEvento = idCorreosEvento;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(String idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getIdTipoInvitado() {
        return idTipoInvitado;
    }

    public void setIdTipoInvitado(String idTipoInvitado) {
        this.idTipoInvitado = idTipoInvitado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    //Muestra los correos de un evento especifico
    public ObservableList<CorreosEventosModel> mostrarCorreosEventoEspecifico(Integer evento){
        try {
            ObservableList<CorreosEventosModel> datoCorreoEvento = FXCollections.observableArrayList();

            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT ce.id_correos_evento AS id_correos_evento, e.nombre AS id_evento, \n" +
                    "c.correo AS id_correo, ti.descripcion AS id_tipo_invitado, estado FROM tbl_correos_evento AS ce \n" +
                    "INNER JOIN tbl_eventos AS e ON ce.id_evento = e.id_evento\n" +
                    "INNER JOIN tbl_correos AS c ON ce.id_correo = c.id_correo \n" +
                    "INNER JOIN tbl_tipo_invitado AS ti ON ce.id_tipo_invitado = ti.id_tipo_invitado " +
                    "WHERE id_evento = ? AND e.id_usuario = ?");
            statement.setInt(1, evento);
            statement.setInt(2,this.idAnfitrion);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                datoCorreoEvento.add(new CorreosEventosModel(
                        resultSet.getInt("id_correos_evento"),
                        resultSet.getString("id_evento"),
                        resultSet.getString("id_correo"),
                        resultSet.getString("id_tipo_invitado"),
                        resultSet.getInt("estado")
                ));
            }

            return datoCorreoEvento;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Retorna una observable con todos los invitados que estan pedientes de confirmación
    public ObservableList<CorreosEventosModel> mostrarInvitadoPendiente(){
        try {
            ObservableList<CorreosEventosModel> datoCorreoEvento = FXCollections.observableArrayList();

            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT ce.id_correos_evento AS id_correos_evento, e.nombre AS id_evento, \n" +
                    "c.correo AS id_correo, ti.descripcion AS id_tipo_invitado, estado FROM tbl_correos_evento AS ce \n" +
                    "INNER JOIN tbl_eventos AS e ON ce.id_evento = e.id_evento\n" +
                    "INNER JOIN tbl_correos AS c ON ce.id_correo = c.id_correo \n" +
                    "INNER JOIN tbl_tipo_invitado AS ti ON ce.id_tipo_invitado = ti.id_tipo_invitado " +
                    "WHERE estado = 0 AND e.id_usuario = ?");
            statement.setInt(1,this.idAnfitrion);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                datoCorreoEvento.add(new CorreosEventosModel(
                        resultSet.getInt("id_correos_evento"),
                        resultSet.getString("id_evento"),
                        resultSet.getString("id_correo"),
                        resultSet.getString("id_tipo_invitado"),
                        resultSet.getInt("estado")
                ));
            }

            return datoCorreoEvento;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Retorna un observable con todos los invitados que han confirmado su asistencia al evento
    public ObservableList<CorreosEventosModel> mostrarInvitadoConfirmado(){
        try {
            ObservableList<CorreosEventosModel> datoCorreoEvento = FXCollections.observableArrayList();

            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT ce.id_correos_evento AS id_correos_evento, e.nombre AS id_evento, \n" +
                    "c.correo AS id_correo, ti.descripcion AS id_tipo_invitado, estado FROM tbl_correos_evento AS ce \n" +
                    "INNER JOIN tbl_eventos AS e ON ce.id_evento = e.id_evento\n" +
                    "INNER JOIN tbl_correos AS c ON ce.id_correo = c.id_correo \n" +
                    "INNER JOIN tbl_tipo_invitado AS ti ON ce.id_tipo_invitado = ti.id_tipo_invitado " +
                    "WHERE estado = 1 AND e.id_usuario = ?");
            statement.setInt(1,this.idAnfitrion);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                datoCorreoEvento.add(new CorreosEventosModel(
                        resultSet.getInt("id_correos_evento"),
                        resultSet.getString("id_evento"),
                        resultSet.getString("id_correo"),
                        resultSet.getString("id_tipo_invitado"),
                        resultSet.getInt("estado")
                ));
            }

            return datoCorreoEvento;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Retorna un observable con todos los invitados que hay rechazado la asistencia a dicho evento
    public ObservableList<CorreosEventosModel> mostrarInvitadoRechazado(){
        try {
            ObservableList<CorreosEventosModel> datoCorreoEvento = FXCollections.observableArrayList();

            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT ce.id_correos_evento AS id_correos_evento, e.nombre AS id_evento, \n" +
                    "c.correo AS id_correo, ti.descripcion AS id_tipo_invitado, estado FROM tbl_correos_evento AS ce \n" +
                    "INNER JOIN tbl_eventos AS e ON ce.id_evento = e.id_evento\n" +
                    "INNER JOIN tbl_correos AS c ON ce.id_correo = c.id_correo \n" +
                    "INNER JOIN tbl_tipo_invitado AS ti ON ce.id_tipo_invitado = ti.id_tipo_invitado " +
                    "WHERE estado = 2 AND e.id_usuario = ?");
            statement.setInt(1,this.idAnfitrion);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                datoCorreoEvento.add(new CorreosEventosModel(
                        resultSet.getInt("id_correos_evento"),
                        resultSet.getString("id_evento"),
                        resultSet.getString("id_correo"),
                        resultSet.getString("id_tipo_invitado"),
                        resultSet.getInt("estado")
                ));


            }

            return datoCorreoEvento;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Retorna un observable con todos los estados de los invitados
    public ObservableList<CorreosEventosModel> mostrarCorreosEvento(){
        try {
            ObservableList<CorreosEventosModel> datoCorreoEvento = FXCollections.observableArrayList();

            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT ce.id_correos_evento AS id_correos_evento, e.nombre AS id_evento, \n" +
                    "c.correo AS id_correo, ti.descripcion AS id_tipo_invitado, estado FROM tbl_correos_evento AS ce \n" +
                    "INNER JOIN tbl_eventos AS e ON ce.id_evento = e.id_evento\n" +
                    "INNER JOIN tbl_correos AS c ON ce.id_correo = c.id_correo \n" +
                    "INNER JOIN tbl_tipo_invitado AS ti ON ce.id_tipo_invitado = ti.id_tipo_invitado " +
                    "WHERE e.id_usuario = ?");
            statement.setInt(1,this.idAnfitrion);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                datoCorreoEvento.add(new CorreosEventosModel(
                        resultSet.getInt("id_correos_evento"),
                        resultSet.getString("id_evento"),
                        resultSet.getString("id_correo"),
                        resultSet.getString("id_tipo_invitado"),
                        resultSet.getInt("estado")
                ));
            }

            return datoCorreoEvento;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para actualizar un registro de la tabla correo Evento
    public void actualizarCorreoEvento(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE tbl_correos_evento SET " +
                    "id_evento = ?, id_correo = ?, id_tipo_invitado = ?, estado = ?, WHERE id_correos_evento = ?");
            statement.setInt(1, Integer.parseInt(this.idEvento));
            statement.setInt(2, Integer.parseInt(this.idCorreo));
            statement.setInt(3, Integer.parseInt(this.idTipoInvitado));
            statement.setInt(4, this.estado);
            statement.setInt(5, this.idCorreosEvento);
            System.out.println("Actualizaciones = " + statement.executeUpdate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Permite eliminar un registro de la tabla Correo Evento
    public void eliminarCoreoEvento(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tbl_correos_evento WHERE id_correos_evento = ?");
            statement.setInt(1, this.idCorreosEvento);
            System.out.println("Eliminaciones = " + statement.executeUpdate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
