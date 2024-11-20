package com.progra3.organizadord.organizadoreventos.models;

import java.util.HashMap;
import java.util.Map;

public enum EstadosModel {
    Pendiente("Pendiente",0),
    Confirmado("Confirmado",1),
    Rechazado("Rechazado",2);

    private static final Map<String, EstadosModel> POR_TEXTOS = new HashMap<>();
    private static final Map<Integer, EstadosModel> POR_VALORES = new HashMap<>();

    static {
        for(EstadosModel estado : EstadosModel.values()) {
            POR_TEXTOS.put(estado.texto,estado);
            POR_VALORES.put(estado.valor,estado);
        }
    }

    public final String texto;
    public final int valor;

    EstadosModel(String texto, int valor) {
        this.texto = texto;
        this.valor = valor;
    }

    public static EstadosModel valueOfText(String texto) {
        return POR_TEXTOS.get(texto);
    }

    public static EstadosModel valueOfValor(int valor) {
        return POR_VALORES.get(valor);
    }
}
