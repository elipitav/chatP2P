/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package interfaz.controladores;

import static interfaz.controladores.Controlador.loadWindow;
import interfaz.fachada.FachadaGui;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author eliseopitavilarino
 */
public class VAccederController extends Controlador implements Initializable {
    
    private FachadaGui fgui;

    @FXML
    private TextField textFieldNombreUsuario;
    @FXML
    private TextField textFieldContrasenha;
    @FXML
    private Button botonAcceder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void acceder(ActionEvent event) {
        //Creamos una ventana
        Stage stage = new Stage(StageStyle.DECORATED);
        
        
        //Método para cerrar la ventana, momento en el que nos desconectaremos
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                fgui.desconectar();
                System.exit(0);
            }
        });
        
        VPrincipalController controlador = ((VPrincipalController) loadWindow(getClass().getResource("/interfaz/ventanas/VPrincipal.fxml"), "Chat P2P", stage));

        //Inicializamos la ventana principal
        controlador.setUsuario(textFieldNombreUsuario.getText());
        controlador.setVentana(stage);
        controlador.setFgui(fgui);
        controlador.getVentana().setTitle(textFieldNombreUsuario.getText());
        fgui.setVp(controlador);
        
        //Registramos al cliente
        fgui.registrarCliente(textFieldNombreUsuario.getText());
        
        //Cerramos la ventana de inicio de sesión
        getVentana().close();
    }

    public FachadaGui getFgui() {
        return fgui;
    }

    public void setFgui(FachadaGui fgui) {
        this.fgui = fgui;
    }
    
    
    
}
