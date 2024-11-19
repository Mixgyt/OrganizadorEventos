package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioModel {
    private Integer idUsuario;
    private String nombre;
    private Integer idCorreo;
    private String pass;


    public UsuarioModel() {
    }

    public UsuarioModel(Integer idUsuario, String nombre, Integer idCorreo, String pass) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.idCorreo = idCorreo;
        this.pass = pass;
    }

    public UsuarioModel(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;
    }

    public UsuarioModel(String nombre, Integer idCorreo, String pass) {
        this.nombre = nombre;
        this.idCorreo = idCorreo;
        this.pass = pass;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(Integer idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void crearUsuario(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tbl_usuarios(nombre,pass,id_correo) " +
                    "VALUES (?,?,?)");
            statement.setString(1, this.nombre);
            statement.setString(2, this.pass);
            statement.setInt(3, this.idCorreo);
            System.out.println(statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existe(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT*FROM tbl_usuarios WHERE " +
                    "nombre = ?");
            statement.setString(1, this.nombre);
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

    public boolean iniciarSesion(){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT*FROM tbl_usuarios WHERE " +
                    "nombre = ? AND pass = ?");
            statement.setString(1, this.nombre);
            statement.setString(2, this.pass);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
