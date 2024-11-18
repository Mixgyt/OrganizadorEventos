package com.progra3.organizadord.organizadoreventos;

import com.progra3.organizadord.organizadoreventos.Models.EstadosModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    private static Stage mainStage;
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("inicio-sesion-view.fxml"));
        Pane root = fxmlLoader.load();
        mainScene = new Scene(root);

        primaryStage.setTitle("Inicio");
        primaryStage.setScene(mainScene);

        mainStage = primaryStage;
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

    public static void showDialog(String fxml) {
        showDialog(fxml, fxml);
    }

    public static void showDialog(String fxml, String title) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml+".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            dialogStage.show();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void close(){
        mainStage.close();
    }

    public static void main(String[] args) {
        launch();
    }
}