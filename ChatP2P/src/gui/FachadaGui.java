package gui;

import clases.FachadaAplicacion;
import controladores.Controlador;
import controladores.VPrincipalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FachadaGui {

    FachadaAplicacion fa;

    public FachadaGui(FachadaAplicacion fa) {
    }

    public void iniciarVista(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(FachadaGui.class.getResource("/ventanas/vPrincipal.fxml"));
        Pane root = (Pane) loader.load();

        //Collemos o controlador de Acceder
        VPrincipalController contPrincipal = loader.getController();

        primaryStage.setTitle("ChatP2P");
        primaryStage.setScene(new Scene(root));

        contPrincipal.setVenta(primaryStage);
        //Controlador.setStageIcon(primaryStage);
        primaryStage.show();

    }

}
