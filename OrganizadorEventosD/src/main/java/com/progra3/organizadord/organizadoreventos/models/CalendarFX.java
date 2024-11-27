package com.progra3.organizadord.organizadoreventos.models;

import com.progra3.organizadord.organizadoreventos.Conexion.ConexionDB;
import com.progra3.organizadord.organizadoreventos.Conexion.UserSession;
import jakarta.ejb.Local;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarFX {
    private String nombre;
    private LocalDate fechaIncio;
    private LocalDate fechaFinal;
    private String ubicacion;

    public CalendarFX() {
    }

    public CalendarFX(String nombre, LocalDate fechaIncio, LocalDate fechaFinal, String ubiacion) {
        this.nombre = nombre;
        this.fechaIncio = fechaIncio;
        this.fechaFinal = fechaFinal;
        this.ubicacion = ubiacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaIncio() {
        return fechaIncio;
    }

    public void setFechaIncio(LocalDate fechaIncio) {
        this.fechaIncio = fechaIncio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public static List<CalendarFX> obtenerEventos(){
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
                    " WHERE eventos.id_usuario = "+ 1);

            while (resultSet.next()){
                CalendarFX calendarFX = new CalendarFX();
                calendarFX.setNombre(resultSet.getString("evento"));
                calendarFX.setFechaIncio(resultSet.getTimestamp("fecha_inicio").toLocalDateTime().toLocalDate());
                calendarFX.setFechaFinal(resultSet.getTimestamp("fecha_final").toLocalDateTime().toLocalDate());
                calendarFX.setUbicacion(resultSet.getString("ubicacion"));
                eventos.add(calendarFX);
            }
            return eventos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
