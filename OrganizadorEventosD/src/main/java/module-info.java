module com.progra3.organizadord.organizadoreventos {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;
    requires java.sql;
    requires com.opencsv;

    exports com.progra3.organizadord.organizadoreventos;
    exports com.progra3.organizadord.organizadoreventos.models;
    exports com.progra3.organizadord.organizadoreventos.controllers.dialogos;

    opens com.progra3.organizadord.organizadoreventos.controllers.dialogos to javafx.fxml;
    opens com.progra3.organizadord.organizadoreventos to javafx.fxml;
    opens com.progra3.organizadord.organizadoreventos.models to javafx.fxml;
    exports com.progra3.organizadord.organizadoreventos.Conexion;
    opens com.progra3.organizadord.organizadoreventos.Conexion to javafx.fxml;
}