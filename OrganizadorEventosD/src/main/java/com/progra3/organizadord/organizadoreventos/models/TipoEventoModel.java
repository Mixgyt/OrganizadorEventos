package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TipoEventoModel {
    private int idTipoEvento;
    private String descripcion;

    public TipoEventoModel() {
    }

    public TipoEventoModel(int idTipoEvento, String descripcion) {
        this.idTipoEvento = idTipoEvento;
        this.descripcion = descripcion;
    }

    public int getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(int idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static ObservableList<TipoEventoModel> getTiposEvento(){
        ObservableList<TipoEventoModel> tiposEvento = FXCollections.observableArrayList();
        Connection connection = ConexionDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_tipo_evento, descripcion" +
                    " FROM tbl_tipo_evento;");

            while (resultSet.next()){
                TipoEventoModel tipoEventoModel= new TipoEventoModel();
                tipoEventoModel.setIdTipoEvento(resultSet.getInt("id_tipo_evento"));
                tipoEventoModel.setDescripcion(resultSet.getString("descripcion"));
                tiposEvento.add(tipoEventoModel);
            }
            return tiposEvento;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int guardarTipoEvento(){
        Connection connection = ConexionDB.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tbl_tipo_evento" +
                    " (descripcion) VALUES (?);");
            preparedStatement.setString(1,this.descripcion);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int actualizarTipoEvento(int idTipoEvento){
        Connection connection = ConexionDB.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tbl_tipo_evento" +
                    " SET descripcion=? " +
                    " WHERE id_tipo_evenyo =" +idTipoEvento);
            preparedStatement.setString(1,this.descripcion);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminarTipoEvento(int idTipoEvento){
        Connection conecction = ConexionDB.getConnection();

        try {
            PreparedStatement statement = conecction.prepareStatement("DELETE FROM tbl_tipo_evento" +
                    " WHERE id_tipo_evento = "+idTipoEvento);
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
