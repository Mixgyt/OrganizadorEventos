package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;
import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EventosModel {
    private int idEvento;
    private int idUsuario;
    private String nombre;
    private String fechaInicial;
    private String fechaFinal;
    private String ubicacion;
    private String descripcion;
    private String detalles;
    private int idTipoEvento;
    private String usuarioCadena;
    private String tipoEventoCadena;

    public EventosModel() {
    }

    public EventosModel(int idEvento, int idUsuario, String nombre, String fechaInicial, String fechaFinal, String ubicacion, String descripcion, String detalles, int idTipoEvento) {
        this.idEvento = idEvento;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.detalles = detalles;
        this.idTipoEvento = idTipoEvento;
    }

    public EventosModel(int idUsuario, String nombre, String fechaInicial, String fechaFinal, String ubicacion, String descripcion, String detalles, int idTipoEvento) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.detalles = detalles;
        this.idTipoEvento = idTipoEvento;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public int getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(int idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public String getUsuarioCadena() {
        return usuarioCadena;
    }

    public void setUsuarioCadena(String usuarioCadena) {
        this.usuarioCadena = usuarioCadena;
    }

    public String getTipoEventoCadena() {
        return tipoEventoCadena;
    }

    public void setTipoEventoCadena(String tipoEventoCadena) {
        this.tipoEventoCadena = tipoEventoCadena;
    }

    //Se filtra el dueño del evento en la propia consulta para mostrar los eventos propios del usuario
    public static ObservableList<EventosModel> getEventos(){
        ObservableList<EventosModel> eventos = FXCollections.observableArrayList();
        Connection connection = ConexionDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_evento, usuarios.nombre" +
                    " AS usuario, eventos.nombre" +
                    " AS evento, TO_CHAR(fecha_inicio,'DD-MM-YYYY HH12:MI AM')" +
                    " AS fecha_inicio,TO_CHAR(fecha_final,'DD-MM-YYYY HH12:MI AM')" +
                    " AS fecha_final, ubicacion, eventos.descripcion, detalles, tipo_evento.descripcion" +
                    " AS tipo_evento" +
                    " FROM public.tbl_eventos AS eventos" +
                    " INNER JOIN tbl_usuarios AS usuarios ON eventos.id_usuario = usuarios.id_usuario" +
                    " INNER JOIN tbl_tipo_evento AS tipo_evento ON eventos.id_tipo_evento = tipo_evento.id_tipo_evento" +
                    " WHERE eventos.id_usuario = "+ UserSession.getUsuario().getIdUsuario());

            while (resultSet.next()){
                EventosModel eventosModel = new EventosModel();
                eventosModel.setIdEvento(resultSet.getInt("id_evento"));
                eventosModel.setUsuarioCadena(resultSet.getString("usuario"));
                eventosModel.setNombre(resultSet.getString("evento"));
                eventosModel.setFechaInicial(resultSet.getString("fecha_inicio"));
                eventosModel.setFechaFinal(resultSet.getString("fecha_final"));
                eventosModel.setUbicacion(resultSet.getString("ubicacion"));
                eventosModel.setDescripcion(resultSet.getString("descripcion"));
                eventosModel.setDetalles(resultSet.getString("detalles"));
                eventosModel.setTipoEventoCadena(resultSet.getString("tipo_evento"));

                eventos.add(eventosModel);
            }
            return eventos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean EventoExistente(){
        Connection connection = ConexionDB.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT*FROM tbl_eventos WHERE nombre = ? AND id_usuario = ?");
            statement.setString(1, this.nombre);
            statement.setInt(2, UserSession.getUsuario().getIdUsuario());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Integer buscarId(){
        Connection connection = ConexionDB.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT*FROM tbl_eventos WHERE nombre = ? AND id_usuario = ?");
            statement.setString(1, this.nombre);
            statement.setInt(2, UserSession.getUsuario().getIdUsuario());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                return resultSet.getInt("id_evento");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //retorna los eventos que incluyan el dia de hoy en adelante
    public static ObservableList<EventosModel> getEventosProximos(){
        ObservableList<EventosModel> eventos = FXCollections.observableArrayList();
        Connection connection = ConexionDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_evento, usuarios.nombre" +
                    " AS usuario, eventos.nombre" +
                    " AS evento, TO_CHAR(fecha_inicio,'DD-MM-YYYY HH12:MI AM')" +
                    " AS fecha_inicio,TO_CHAR(fecha_final,'DD-MM-YYYY HH12:MI AM')" +
                    " AS fecha_final, ubicacion, eventos.descripcion, detalles, tipo_evento.descripcion" +
                    " AS tipo_evento" +
                    " FROM public.tbl_eventos AS eventos" +
                    " INNER JOIN tbl_usuarios AS usuarios ON eventos.id_usuario = usuarios.id_usuario" +
                    " INNER JOIN tbl_tipo_evento AS tipo_evento ON eventos.id_tipo_evento = tipo_evento.id_tipo_evento" +
                    " WHERE eventos.fecha_inicio >= CURRENT_DATE;");

            while (resultSet.next()){
                EventosModel eventosModel = new EventosModel();
                eventosModel.setIdEvento(resultSet.getInt("id_evento"));
                eventosModel.setUsuarioCadena(resultSet.getString("usuario"));
                eventosModel.setNombre(resultSet.getString("evento"));
                eventosModel.setFechaInicial(resultSet.getString("fecha_inicio"));
                eventosModel.setFechaFinal(resultSet.getString("fecha_final"));
                eventosModel.setUbicacion(resultSet.getString("ubicacion"));
                eventosModel.setDescripcion(resultSet.getString("descripcion"));
                eventosModel.setDetalles(resultSet.getString("detalles"));
                eventosModel.setTipoEventoCadena(resultSet.getString("tipo_evento"));

                eventos.add(eventosModel);
            }
            return eventos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int guardarEvento(){
        Connection connection = ConexionDB.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tbl_eventos" +
                    "(id_usuario, nombre, fecha_inicio, fecha_final, ubicacion, descripcion, detalles, id_tipo_evento)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1,this.idUsuario);
            preparedStatement.setString(2,this.nombre);
            Timestamp fechaInicio = Timestamp.valueOf(this.fechaInicial.replace("T"," "));
            preparedStatement.setTimestamp(3,fechaInicio);
            Timestamp fechaFinal = Timestamp.valueOf(this.fechaFinal.replace("T"," "));
            preparedStatement.setTimestamp(4,fechaFinal);
            preparedStatement.setString(5,this.ubicacion);
            preparedStatement.setString(6,this.descripcion);
            preparedStatement.setString(7,this.detalles);
            preparedStatement.setInt(8,this.idTipoEvento);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int actualizarEvento(int idEvento){
        Connection connection = ConexionDB.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tbl_eventos" +
                    " SET id_usuario=?, nombre=?, fecha_inicio=?, fecha_final=?, ubicacion=?, descripcion=?, detalles=?, id_tipo_evento=?" +
                    " WHERE id_evento = " +idEvento);
            preparedStatement.setInt(1,this.idUsuario);
            preparedStatement.setString(2,this.nombre);
            preparedStatement.setString(3,this.fechaInicial);
            preparedStatement.setString(4,this.fechaFinal);
            preparedStatement.setString(5,this.ubicacion);
            preparedStatement.setString(6,this.descripcion);
            preparedStatement.setInt(7,this.idTipoEvento);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminarEvento(int idEvento){
        Connection conecction = ConexionDB.getConnection();
        try {
            PreparedStatement statement = conecction.prepareStatement("DELETE FROM tbl_eventos" +
                    " WHERE id_evento = "+idEvento);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return nombre;

    }
}
