package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;
import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tbl_usuarios(nombre,pass, id_correo) " +
                    "VALUES (?,?,?)");
            this.pass = Encripter.hash(pass);
            statement.setString(1, this.nombre);
            statement.setString(2, this.pass);
            statement.setInt(3, this.idCorreo);
            System.out.println(statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int actualizarUsuario(int id){
        try {
            Connection connection = ConexionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE tbl_usuarios SET nombre=?, pass=? WHERE id_usuario = ?;");
            this.pass = Encripter.hash(pass);
            statement.setString(1, this.nombre);
            statement.setString(2, this.pass);
            statement.setInt(3, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminarUsuario(int id){
        Connection connection = ConexionDB.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tbl_usuarios WHERE id_usuario = ?;");
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<UsuarioModel> getusuario(){
        Connection connection = ConexionDB.getConnection();
        ObservableList<UsuarioModel> usuarios = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tbl_usuarios");
            while(rs.next()){
                UsuarioModel usuario = new UsuarioModel();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPass(rs.getString("pass"));
                usuario.setIdCorreo(rs.getInt("id_correo"));
                usuarios.add(usuario);
            }
            return usuarios;
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
                    "nombre = ?");
            statement.setString(1, this.nombre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                if(Encripter.verify(this.pass, resultSet.getString(3))){
                    //Usuario obtenido para guardar su sesion
                    UsuarioModel usuarioModel = new UsuarioModel();
                    usuarioModel.setIdUsuario(resultSet.getInt(1));
                    usuarioModel.setNombre(resultSet.getString(2));
                    usuarioModel.setIdCorreo(resultSet.getInt(4));
                    UserSession.setUsuario(usuarioModel);
                    return true;
                }
                return false;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
