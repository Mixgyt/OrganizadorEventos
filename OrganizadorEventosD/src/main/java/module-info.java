module com.progra3.organizadord.organizadoreventos {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;
    requires java.sql;


    opens com.progra3.organizadord.organizadoreventos to javafx.fxml;
    opens com.progra3.organizadord.organizadoreventos.Models to javafx.base;
    opens com.progra3.organizadord.organizadoreventos.ConexionDB to javafx.base;
    exports com.progra3.organizadord.organizadoreventos;
}