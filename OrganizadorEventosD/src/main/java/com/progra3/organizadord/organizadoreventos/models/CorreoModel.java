package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CorreoModel {
    private Integer idCorreo;
    private String correo;

    public CorreoModel() {
    }

    public CorreoModel(Integer idCorreo, String correo) {
        this.idCorreo = idCorreo;
        this.correo = correo;
    }

    public CorreoModel(String correo) {
        this.correo = correo;
    }

    public Integer getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(Integer idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void crearCorreo() {
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tbl_correos(correo) VALUES (?)");
            statement.setString(1, this.correo);
            System.out.println(statement.executeUpdate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer buscarId() {
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT*FROM tbl_correos WHERE correo = ?");
            statement.setString(1, this.correo);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println(resultSet.getInt("id_correo"));
                return resultSet.getInt("id_correo");
            } else {
                Connection connection1 = ConexionDB.getConnection();
                PreparedStatement statement1 = connection1.prepareStatement("INSERT INTO tbl_correos(correo) VALUES (?)");
                statement1.setString(1, this.correo);
                statement1.executeUpdate();

                PreparedStatement statement2 = connection.prepareStatement("SELECT*FROM tbl_correos WHERE correo = ?");
                statement.setString(1, this.correo);
                ResultSet resultSet1 = statement2.executeQuery();
                if (resultSet1.next()) {
                    return resultSet1.getInt("id_correo");
                }
                return 0;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existe(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT*FROM tbl_correos WHERE correo = ?");
            statement.setString(1, this.correo);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return true;
            }
            else {
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
