package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.controllers.dialogos.DetalleEventoController;
import com.progra3.organizadord.organizadoreventos.models.EventosModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import java.util.ArrayList;


public class Main extends Application {
    private static ArrayList<Stage> windows = new ArrayList<>();
    private static Stage mainStage;
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("inicio-sesion-view.fxml"));
        Pane root = fxmlLoader.load();
        mainScene = new Scene(root);
        mainScene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());

        primaryStage.setTitle("Inicio");
        primaryStage.setScene(mainScene);

        mainStage = primaryStage;
        windows.add(mainStage);
        mainStage.show();
    }

    public static void setRoot(String fxml){
        setRoot(fxml,fxml);
    }

    public static void setRoot(String fxml, String title) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml+".fxml"));
            mainScene.setRoot(fxmlLoader.load());
            mainStage.setTitle(title);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    //setRoot pero para abrirlo con un tamaño en especifico
    public static void setRoot(String fxml, String title, Integer width, Integer height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
            mainScene.setRoot(fxmlLoader.load());
            mainScene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());
            mainStage.setTitle(title);
            mainStage.setWidth(width);
            mainStage.setHeight(height);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void showDialog(String fxml) {
        showDialog(fxml, fxml);
    }

    public static void showDialog(String fxml, String title) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml+".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(windows.getLast());
            windows.add(newStage);
            mainStage = newStage;
            mainStage.setOnCloseRequest(Main::onCloseAction);
            mainStage.showAndWait();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void showDialogDetalle(String fxml, String title, EventosModel evento) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml+".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            DetalleEventoController dt = fxmlLoader.getController();
            dt.cargarEvento(evento);
            dt.cargarTabla(evento);
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(windows.getLast());
            windows.add(newStage);
            mainStage = newStage;
            mainStage.setOnCloseRequest(Main::onCloseAction);
            mainStage.showAndWait();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Node getView(String fxml){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml+".fxml"));
            return fxmlLoader.load();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void close(){
        mainStage.close();
        windows.remove(mainStage);
        mainStage = windows.getLast();
    }

    private static void onCloseAction(WindowEvent event){
        windows.remove(mainStage);
        if(!windows.isEmpty()){
            mainStage = windows.getLast();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}