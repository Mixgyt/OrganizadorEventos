package com.progra3.organizadord.organizadoreventos.models;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import com.calendarfx.view.CalendarView;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarioController {

    @FXML
    private VBox contenedor;

    @FXML
    public void initialize(){

        Calendar calendar = new Calendar("Calendario");
        Entry<String> evento = new Entry<>("Entrega de paiz");
        evento.setInterval(
                LocalDate.of(2024, 11, 26), // Fecha de inicio
                LocalDate.of(2024, 11, 30)  // Fecha de fin
        );

        calendar.addEntry(evento);
        List<Entry<String>> listaEvento = cargarEventoCalendario();



        for(int i = 0; i < listaEvento.size(); i++){
            calendar.addEntry(listaEvento.get(i));
        }

        CalendarSource calendarSource = new CalendarSource("Mis Calendarios");
        calendarSource.getCalendars().add(calendar);
        CalendarView calendarView = new CalendarView();
        calendarView.getCalendarSources().add(calendarSource);
        contenedor.getChildren().add(calendarView);
    }

    public List<Entry<String>> cargarEventoCalendario(){
        List<Entry<String>> listEntry = new ArrayList<>();

        List<CalendarFX> evento = CalendarFX.obtenerEventos();

        System.out.println(evento.get(1).getFechaIncio());
        for (int i = 0; i < evento.size(); i++){
            Entry<String> event = new Entry<>(evento.get(i).getNombre());
            event.setInterval(evento.get(i).getFechaIncio(),
                    evento.get(i).getFechaFinal()

            );
            event.setLocation(evento.get(i).getUbicacion());
            listEntry.add(event);
        }
        return listEntry;
    }
}
