package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TipoInvitadoModel {
    private int idTipoInvitado;
    private String descripcion;

    public TipoInvitadoModel() {
    }

    public TipoInvitadoModel(int idTipoInvitado, String descripcion) {
        this.idTipoInvitado = idTipoInvitado;
        this.descripcion = descripcion;
    }

    public int getIdTipoInvitado() {
        return idTipoInvitado;
    }

    public void setIdTipoInvitado(int idTipoInvitado) {
        this.idTipoInvitado = idTipoInvitado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ObservableList<TipoInvitadoModel> getTiposInvitado(){
        ObservableList<TipoInvitadoModel> tiposInvitado = FXCollections.observableArrayList();
        Connection connection = ConexionDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_tipo_invitado, descripcion" +
                    " FROM tbl_tipo_invitado;");

            while (resultSet.next()){
                TipoInvitadoModel tipoInvitadoModel = new TipoInvitadoModel();
                tipoInvitadoModel.setIdTipoInvitado(resultSet.getInt("id_tipo_invitado"));
                tipoInvitadoModel.setDescripcion(resultSet.getString("descripcion"));
                tiposInvitado.add(tipoInvitadoModel);
            }
            return tiposInvitado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int guardarTipoInvitado(){
        Connection connection = ConexionDB.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tbl_tipo_invitado" +
                    "(descripcion) VALUES (?);");
            preparedStatement.setString(1,this.descripcion);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int actualizarTipoInvitado(int idTipoInvitado){
        Connection connection = ConexionDB.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tbl_tipo_invitado" +
                    " SET descripcion=?" +idTipoInvitado);
            preparedStatement.setString(1,this.descripcion);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminarTipoInvitado(int idTipoInvitado){
        Connection conecction = ConexionDB.getConnection();
        try {
            PreparedStatement statement = conecction.prepareStatement("DELETE FROM tbl_tipo_invitado" +
                    " WHERE id_tipo_invitado = "+idTipoInvitado);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
