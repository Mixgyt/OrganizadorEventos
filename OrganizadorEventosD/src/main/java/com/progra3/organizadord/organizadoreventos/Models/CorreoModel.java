package com.progra3.organizadord.organizadoreventos.Models;

import com.progra3.organizadord.organizadoreventos.ConexionDB.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

    public void AgregarCorreo(){
        try {
            Connection connection = ConexionDB.connection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tbl_correos(correo) " +
                    "VALUES (?)");
            statement.setString(1, this.correo);
            System.out.println(statement.executeUpdate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Integer BuscarIdCorreo(){
        try {
            Connection connection = ConexionDB.connection();
            PreparedStatement statement = connection.prepareStatement("SELECT*FROM tbl_correos WHERE correo = ?");
            statement.setString(1, this.correo);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                System.out.println(resultSet.getInt("id_correo"));
                return resultSet.getInt("id_correo");
            }
            else {
                return 0;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean CorreoExistente(){
        try {
            Connection connection = ConexionDB.connection();
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