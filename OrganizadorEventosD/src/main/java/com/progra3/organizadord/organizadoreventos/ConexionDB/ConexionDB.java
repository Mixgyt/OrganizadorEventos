package com.progra3.organizadord.organizadoreventos.ConexionDB;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static  final String url = "jdbc:postgresql://localhost:5432/db_eventos";
    private static  final String USER = "postgres";
    private static String PASS = "2004";

    public static Connection connection(){
        try {
            Connection conectar = DriverManager.getConnection(url,USER,PASS);
            System.out.println("Conexion exitosa");
            return conectar;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
