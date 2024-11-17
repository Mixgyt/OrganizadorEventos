module com.progra3.organizadord.organizadoreventos {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;


    opens com.progra3.organizadord.organizadoreventos to javafx.fxml;
    exports com.progra3.organizadord.organizadoreventos;
}