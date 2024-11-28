package com.progra3.organizadord.organizadoreventos.models;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarFXControl;
import com.calendarfx.view.DateControl;
import javafx.collections.ObservableSet;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import com.calendarfx.view.CalendarView;

import javax.swing.*;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;




public class CalendarioController {


    @FXML
    private VBox contenedor;



    @FXML
    public void initialize() {

        //bucle que agrega los eventos al CalendarFx
        CalendarView calendarView = new CalendarView();
        calendarView.setPrefSize(900, 600);
        calendarView.setMaxSize(900, 600);
        calendarView.setMinSize(900, 600);
        ArrayList<CalendarSource> tipoEventos = new ArrayList<CalendarSource>();

        List<CalendarFX> tipos = CalendarFX.obtenerTipoEventos();
        for (int i = 0; i < tipos.size(); i++) {
            CalendarSource calendarSource = new CalendarSource(tipos.get(i).getNombreTipoEvento());

            List<CalendarFX> eventos = CalendarFX.obtenerEventos(tipos.get(i).getIdTipoEvento());
            for (CalendarFX itemEvento : eventos) {
                Calendar calendar = new Calendar(itemEvento.getNombre());
                Entry<String> evento = new Entry<>(itemEvento.getNombre());
                //agregando intervalo de fechas para el evento
                evento.setInterval(
                        itemEvento.getFechaIncio()
                );
                evento.setLocation(itemEvento.getUbicacion());
                calendar.setStyle(Calendar.Style.getStyle(i));
                calendar.addEntry(evento);
                calendarSource.getCalendars().add(calendar);
            }
            tipoEventos.add(calendarSource);
        }

        //evento para mostrar informacion del evento seleccionado
        try {
            calendarView.setEntryDetailsPopOverContentCallback(entry -> {
                Label lblEvento = new Label();

                if (entry.getEntry().getCalendar() != null) {
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
                    LocalDateTime fechaInicio = entry.getEntry().getStartAsLocalDateTime();
                    String cadenaFechaInicio = fechaInicio.format(formato);
                    String ubicacion = entry.getEntry().getLocation();

                    String f = "\t" + entry.getEntry().getCalendar().getName() +
                            "\t\n\tUbicación : " + ubicacion +
                            "\t\n\tFecha de inicio : " + cadenaFechaInicio ;
                    lblEvento.setText(f);
                } else {
                    lblEvento.setText("No hay calendario seleccionado");
                }
                return lblEvento;
            });


            //quitando controles por defecto de el CalendarFx
            calendarView.setShowPageToolBarControls(false);
            calendarView.setShowDetailsUponEntryCreation(false);
            calendarView.setShowAddCalendarButton(false);
            calendarView.setShowPrintButton(true);
            calendarView.setDefaultCalendarProvider(param -> null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //agregando calendarios al CalendarioView y mostrandolo en contenedor
        calendarView.getCalendarSources().setAll(tipoEventos);
        contenedor.getChildren().add(calendarView);
    }
}