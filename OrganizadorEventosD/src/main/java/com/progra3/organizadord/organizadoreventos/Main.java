package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.models.EstadosModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Main extends Application {
    private static Stage mainStage;
    private static Scene mainScene;
    private static Stage mainDialog;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("inicio-sesion-view.fxml"));
        Pane root = fxmlLoader.load();
        mainScene = new Scene(root);
        mainScene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());

        primaryStage.setTitle("Inicio");
        primaryStage.setScene(mainScene);

        mainStage = primaryStage;
        mainStage.show();
        LocalDateTime dtp = LocalDateTime.now();
        System.out.println(dtp.toString());
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
            mainDialog = new Stage();
            mainDialog.setTitle(title);
            mainDialog.setScene(scene);
            mainDialog.initModality(Modality.WINDOW_MODAL);
            mainDialog.initOwner(mainStage);
            mainDialog.showAndWait();
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

    public static void closeDialog(){
        mainDialog.close();
    }

    public static void exit(){
        mainStage.close();
    }

    public static void main(String[] args) {
        launch();
    }
}