package interfaz.controladores;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class Controlador {

    private Stage ventana;

    //Método para cargar una ventana
    public static Controlador loadWindow(URL loc, String title, Stage stageCreado) {
        Controlador controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage;
            if (stageCreado != null) {
                stage = stageCreado;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            
            //Asignamos la ventana al controlador
            controller.setVentana(stage);

        } catch (IOException ex) {
            System.out.println("Error en la apertura de la ventana");
        }
        return controller;
    }
    
    public static Controlador loadWindowConfirm(URL loc, String title, Stage stageCreado) {
        Controlador controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage;
            if (stageCreado != null) {
                stage = stageCreado;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));

            //Asignamos la ventana al controlador
            controller.setVentana(stage);

        } catch (IOException ex) {
            System.out.println("Error en la apertura de la ventana");
        }
        return controller;
    }
    

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }
}
