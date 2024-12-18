package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;
import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarFX {
    private String nombre;
    private LocalDateTime completoFechaIncio;
    private LocalDateTime completoFechaFinal;
    private String ubicacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private LocalTime tiempoInicio;
    private LocalTime tiempoFinal;


    private int idTipoEvento;
    private String nombreTipoEvento;

    public CalendarFX() {
    }

    public LocalTime getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(LocalTime tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }

    public LocalTime getTiempoInicio() {
        return tiempoInicio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setTiempoInicio(LocalTime tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public int getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(int idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public String getNombreTipoEvento() {
        return nombreTipoEvento;
    }

    public void setNombreTipoEvento(String nombreTipoEvento) {
        this.nombreTipoEvento = nombreTipoEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getCompletoFechaIncio() {
        return completoFechaIncio;
    }

    public void setCompletoFechaIncio(LocalDateTime completoFechaIncio) {
        this.completoFechaIncio = completoFechaIncio;
    }

    public LocalDateTime getCompletoFechaFinal() {
        return completoFechaFinal;
    }

    public void setCompletoFechaFinal(LocalDateTime completoFechaFinal) {
        this.completoFechaFinal = completoFechaFinal;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public static List<CalendarFX> obtenerEventos(int idTipoEvento){
        List<CalendarFX> eventos = new ArrayList<>();
        Connection connection = ConexionDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_evento, usuarios.nombre" +
                    " AS usuario, eventos.nombre" +
                    " AS evento, TO_CHAR(fecha_inicio,'YYYY-MM-DD HH24:MI:SS')" +
                    " AS fecha_inicio, TO_CHAR(fecha_final,'YYYY-MM-DD HH24:MI:SS')" +
                    " AS fecha_final, ubicacion, eventos.descripcion, detalles, tipo_evento.descripcion" +
                    " AS tipo_evento" +
                    " FROM public.tbl_eventos AS eventos" +
                    " INNER JOIN tbl_usuarios AS usuarios ON eventos.id_usuario = usuarios.id_usuario" +
                    " INNER JOIN tbl_tipo_evento AS tipo_evento ON eventos.id_tipo_evento = tipo_evento.id_tipo_evento" +
                    " WHERE eventos.id_usuario = "+ UserSession.getUsuario().getIdUsuario() +" AND eventos.id_tipo_evento = "+idTipoEvento);

            while (resultSet.next()){
                CalendarFX calendarFX = new CalendarFX();
                calendarFX.setNombre(resultSet.getString("evento"));
                calendarFX.setCompletoFechaIncio(resultSet.getTimestamp("fecha_inicio").toLocalDateTime());
                calendarFX.setCompletoFechaFinal(resultSet.getTimestamp("fecha_final").toLocalDateTime());
                calendarFX.setTiempoInicio(resultSet.getTimestamp("fecha_inicio").toLocalDateTime().toLocalTime());
                calendarFX.setTiempoFinal(resultSet.getTimestamp("fecha_final").toLocalDateTime().toLocalTime());
                calendarFX.setFechaInicio(resultSet.getTimestamp("fecha_inicio").toLocalDateTime().toLocalDate());
                calendarFX.setFechaFinal(resultSet.getTimestamp("fecha_final").toLocalDateTime().toLocalDate());
                calendarFX.setUbicacion(resultSet.getString("ubicacion"));
                eventos.add(calendarFX);
            }
            return eventos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CalendarFX> obtenerTipoEventos(){
        List<CalendarFX> listaEventos = new ArrayList<>();
        Connection connection = ConexionDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_tipo_evento, descripcion" +
                    " FROM tbl_tipo_evento;");

            while (resultSet.next()){
                CalendarFX calendarFXTipoEvento = new CalendarFX();
                calendarFXTipoEvento.setIdTipoEvento(resultSet.getInt("id_tipo_evento"));
                calendarFXTipoEvento.setNombreTipoEvento(resultSet.getString("descripcion"));
                listaEventos.add(calendarFXTipoEvento);
            }
            return listaEventos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
