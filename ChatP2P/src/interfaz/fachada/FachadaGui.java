package interfaz.fachada;

import aplicacion.fachada.FachadaAplicacion;
import interfaz.controladores.Controlador;
import interfaz.controladores.VAccederController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FachadaGui {

    FachadaAplicacion fa;

    public FachadaGui(FachadaAplicacion fa) {
        this.fa=fa;
    }

    public void iniciarVista(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(FachadaGui.class.getResource("/interfaz/ventanas/VAcceder.fxml"));
        Pane root = (Pane) loader.load();

        //Collemos o controlador de Acceder
        VAccederController contAcceder = loader.getController();
        contAcceder.setFgui(this);

        primaryStage.setTitle("ChatP2P");
        primaryStage.setScene(new Scene(root));

        contAcceder.setVenta(primaryStage);
        //Controlador.setStageIcon(primaryStage);
        primaryStage.show();

    }
    
    public void enviarMensaje(String mensaje, String receptor){
        fa.enviarMensaje(mensaje,receptor);
    }
    
    public void registrarCliente(String nombre){
        fa.registrarCliente(nombre);
    }

}
