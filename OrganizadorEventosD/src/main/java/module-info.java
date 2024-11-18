module com.progra3.organizadord.organizadoreventos {
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires jakarta.mail;
//    requires java.sql;


//    opens com.progra3.organizadord.organizadoreventos to javafx.fxml;
//    opens com.progra3.organizadord.organizadoreventos.models to javafx.base;
//    opens com.progra3.organizadord.organizadoreventos.Conexion to javafx.base;
//    exports com.progra3.organizadord.organizadoreventos.models;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;
    requires java.sql;

    exports com.progra3.organizadord.organizadoreventos;
    exports com.progra3.organizadord.organizadoreventos.models;

    opens com.progra3.organizadord.organizadoreventos to javafx.fxml;
    opens com.progra3.organizadord.organizadoreventos.models to javafx.fxml;
}