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

        /*
            Estructura como funcionan las vistas de eventos
         */
//        CalendarView calendarView = new CalendarView();
//        ArrayList<CalendarSource> tipoEventos= new ArrayList<CalendarSource>();
//        CalendarSource calendarSource1 = new CalendarSource("Viaje");
//        CalendarSource calendarSource2 = new CalendarSource("Comer");
//
//        Calendar calendar1 = new Calendar("Viajar a españa");
//        Calendar calendar2 = new Calendar("Comer en españa");
//
//
//        Entry<String> evento1 = new Entry<>("Viajar a españa");
//        evento1.setInterval(
//                LocalDate.of(2024, 11, 26), // Fecha de inicio
//                LocalDate.of(2024, 11, 30)  // Fecha de fin
//        );
//        calendar1.addEntry(evento1);
//        calendar1.setShortName("V");
//
//        Entry<String> evento2 = new Entry<>("Comer en españa");
//        evento2.setInterval(
//                LocalDate.of(2024, 11, 20), // Fecha de inicio
//                LocalDate.of(2024, 11, 22)  // Fecha de fin
//        );
//        calendar2.addEntry(evento2);
//        calendar2.setShortName("C");
//
//        calendarSource1.getCalendars().add(calendar1);
//        calendarSource2.getCalendars().add(calendar2);
//
//        tipoEventos.add(calendarSource1);
//        tipoEventos.add(calendarSource2);
//
//        calendarView.getCalendarSources().setAll(tipoEventos);
//        contenedor.getChildren().add(calendarView);

        //bucle que agrega los eventos al CalendarFx
        CalendarView calendarView = new CalendarView();
        ArrayList<CalendarSource> tipoEventos= new ArrayList<CalendarSource>();

        List<CalendarFX> tipos = CalendarFX.obtenerTipoEventos();
        for (CalendarFX itemTipos: tipos){
            CalendarSource calendarSource = new CalendarSource(itemTipos.getNombreTipoEvento());

            List<CalendarFX> eventos = CalendarFX.obtenerEventos(itemTipos.getIdTipoEvento());
            for (CalendarFX itemEvento: eventos){
                Calendar calendar = new Calendar(itemEvento.getNombre());
                        Entry<String> evento = new Entry<>(itemEvento.getNombre());
                            evento.setInterval(
                            LocalDate.of(2024, 11, 20), // Fecha de inicio
                            LocalDate.of(2024, 11, 22)  // Fecha de fin
                        );
                        calendar.addEntry(evento);
                        calendarSource.getCalendars().add(calendar);
            }
            tipoEventos.add(calendarSource);
        }

        calendarView.getCalendarSources().setAll(tipoEventos);
        contenedor.getChildren().add(calendarView);


        //prueba random
        List<CalendarFX> tipoEvento = CalendarFX.obtenerTipoEventos();
        for (CalendarFX xd: tipoEvento){
            System.out.println(xd.getNombreTipoEvento());
        }
    }

    public List<Entry<String>> cargarEventoCalendario(int idTipoEvento){
        ArrayList<Entry<String>> listEntry = new ArrayList<>();

        List<CalendarFX> evento = CalendarFX.obtenerEventos(idTipoEvento);

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
