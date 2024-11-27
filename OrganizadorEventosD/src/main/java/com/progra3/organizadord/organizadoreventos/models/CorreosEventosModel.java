package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//Este es el model de la tabla pivote Correos_Evento
public class CorreosEventosModel {
    private Integer idCorreosEvento;
    private String idEvento;
    private String idCorreo;
    private String idTipoInvitado;
    private EstadosModel estado;
    private Integer idAnfitrion;

    public CorreosEventosModel(Integer idCorreosEvento, String idEvento, String idCorreo, String idTipoInvitado, Integer estado, Integer idAnfitrion) {
        this.idCorreosEvento = idCorreosEvento;
        this.idEvento = idEvento;
        this.idCorreo = idCorreo;
        this.idTipoInvitado = idTipoInvitado;
        this.estado = EstadosModel.valueOfValor(estado);
        this.idAnfitrion = idAnfitrion;
    }

    public CorreosEventosModel(String idEvento, String idCorreo, String idTipoInvitado, Integer estado, Integer idAnfitrion) {
        this.idEvento = idEvento;
        this.idCorreo = idCorreo;
        this.idTipoInvitado = idTipoInvitado;
        this.estado = EstadosModel.valueOfValor(estado);
        this.idAnfitrion = idAnfitrion;
    }

    public CorreosEventosModel(String idEvento, String idCorreo, String idTipoInvitado, EstadosModel estado) {
        this.idEvento = idEvento;
        this.idCorreo = idCorreo;
        this.idTipoInvitado = idTipoInvitado;
        this.estado = estado;
    }

    public CorreosEventosModel(Integer idCorreosEvento, String idEvento, String idCorreo, String idTipoInvitado, Integer estado) {
        this.idCorreosEvento = idCorreosEvento;
        this.idEvento = idEvento;
        this.idCorreo = idCorreo;
        this.idTipoInvitado = idTipoInvitado;
        this.estado = EstadosModel.valueOfValor(estado);
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

    public EstadosModel getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = EstadosModel.valueOfValor(estado);
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
                    "WHERE e.id_evento = ? AND e.id_usuario = ?");

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

    //Retorna una observable con los invitados según el estado especificado

    public ObservableList<CorreosEventosModel> mostrarInvitadosPorEstado(int estado, int idEvento){

        try {
            ObservableList<CorreosEventosModel> datoCorreoEvento = FXCollections.observableArrayList();

            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT ce.id_correos_evento AS id_correos_evento, e.nombre AS id_evento, \n" +
                    "c.correo AS id_correo, ti.descripcion AS id_tipo_invitado, estado FROM tbl_correos_evento AS ce \n" +
                    "INNER JOIN tbl_eventos AS e ON ce.id_evento = e.id_evento\n" +
                    "INNER JOIN tbl_correos AS c ON ce.id_correo = c.id_correo \n" +
                    "INNER JOIN tbl_tipo_invitado AS ti ON ce.id_tipo_invitado = ti.id_tipo_invitado " +
                    "WHERE ce.id_evento = ? AND estado = ? AND e.id_usuario = ?");
            statement.setInt(1, idEvento);
            statement.setInt(2, estado);
            statement.setInt(3, this.idAnfitrion);

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

    //Retorna un observable con todos los estados de los invitados
    public ObservableList<CorreosEventosModel> mostrarCorreosEvento(int idEvento){
        try {
            ObservableList<CorreosEventosModel> datoCorreoEvento = FXCollections.observableArrayList();

            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT ce.id_correos_evento AS id_correos_evento, e.nombre AS id_evento, \n" +
                    "c.correo AS id_correo, ti.descripcion AS id_tipo_invitado, estado FROM tbl_correos_evento AS ce \n" +
                    "INNER JOIN tbl_eventos AS e ON ce.id_evento = e.id_evento\n" +
                    "INNER JOIN tbl_correos AS c ON ce.id_correo = c.id_correo \n" +
                    "INNER JOIN tbl_tipo_invitado AS ti ON ce.id_tipo_invitado = ti.id_tipo_invitado " +
                    "WHERE ce.id_evento = ? AND e.id_usuario = ?");
            statement.setInt(1, idEvento);
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

    public static ObservableList<CorreosEventosModel> mostrarInvitadosEvento(Integer idEvento) {
        try {
            ObservableList<CorreosEventosModel> invitados = FXCollections.observableArrayList();

            Connection connection = ConexionDB.getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT ce.id_correos_evento AS id_correos_evento, e.nombre AS evento, " +
                    "c.correo AS correo, ti.descripcion AS tipo_invitado, ce.estado AS estado " +
                    "FROM tbl_correos_evento AS ce " +
                    "INNER JOIN tbl_eventos AS e ON ce.id_evento = e.id_evento " +
                    "INNER JOIN tbl_correos AS c ON ce.id_correo = c.id_correo " +
                    "INNER JOIN tbl_tipo_invitado AS ti ON ce.id_tipo_invitado = ti.id_tipo_invitado " +
                    "WHERE ce.id_evento = ?");
            statement.setInt(1, idEvento);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                invitados.add(new CorreosEventosModel(
                        resultSet.getInt("id_correos_evento"),
                        resultSet.getString("evento"),
                        resultSet.getString("correo"),
                        resultSet.getString("tipo_invitado"),
                        resultSet.getInt("estado")
                ));
            }

            return invitados;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para actualizar un registro de la tabla correo Evento
    public void actualizarCorreoEvento(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE tbl_correos_evento SET " +
                    "id_evento = ?, id_correo = ?, id_tipo_invitado = ?, estado = ? WHERE id_correos_evento = ?");
            statement.setInt(1, Integer.parseInt(this.getIdEvento()));
            statement.setInt(2, Integer.parseInt(this.getIdCorreo()));
            statement.setInt(3, Integer.parseInt(this.getIdTipoInvitado()));
            statement.setInt(4, this.getEstado().valor);
            statement.setInt(5, this.getIdCorreosEvento());
            System.out.println("Actualizaciones = " + statement.executeUpdate());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    //Permite eliminar un registro de la tabla Correo Evento
    public void eliminarCoreoEvento(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tbl_correos_evento WHERE id_correo = ?");

            statement.setInt(1, Integer.parseInt(this.idCorreo));
            System.out.println("Eliminaciones = " + statement.executeUpdate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarCorreoEvento(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tbl_correos_evento (id_evento, id_correo, id_tipo_invitado, estado) " +
                    "VALUES(?,?,?,?)");
            statement.setInt(1,Integer.parseInt(this.idEvento));
            statement.setInt(2,Integer.parseInt(this.idCorreo));
            statement.setInt(3,Integer.parseInt(this.idTipoInvitado));
            statement.setInt(4,this.getEstado().valor);

            System.out.printf("" + statement.executeUpdate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean invitadoExistente(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT*FROM tbl_correos_evento WHERE id_correo = ? AND id_evento = ?");
            statement.setInt(1,Integer.parseInt(this.idCorreo));
            statement.setInt(2,Integer.parseInt(this.idEvento));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
