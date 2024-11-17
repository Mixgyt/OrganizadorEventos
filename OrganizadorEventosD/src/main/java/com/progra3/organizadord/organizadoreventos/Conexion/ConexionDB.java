package com.progra3.organizadord.organizadoreventos.Conexion;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {
    private static final Properties properties;
    private static final InputStream stream = ClassLoader.getSystemResourceAsStream("application.properties");

    static {
        try{
            properties = new Properties();
            properties.load(stream);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection(){
        try {
            Connection conexion = DriverManager.getConnection(properties.getProperty("url"),properties.getProperty("user"),properties.getProperty("pass"));
            System.out.println("Conexion exitosa");
            return conexion;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
