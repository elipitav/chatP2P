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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        //Creamos unha venta para asignarlla ao controlador
        Stage stage = new Stage(StageStyle.DECORATED);

        VPrincipalController controlador = ((VPrincipalController) loadWindow(getClass().getResource("/interfaz/ventanas/VPrincipal.fxml"), "Chat P2P", stage));

        //Asignamoslle o usuario e a venta
        controlador.setUsuario(textFieldNombreUsuario.getText());
        controlador.setVenta(stage);
        controlador.setFgui(fgui);
        controlador.setReceptor("Eliseo");
        fgui.registrarCliente(textFieldNombreUsuario.getText());
        
        //Pechase a venta de rexistro
        getVenta().close();
    }

    public FachadaGui getFgui() {
        return fgui;
    }

    public void setFgui(FachadaGui fgui) {
        this.fgui = fgui;
    }
    
    
    
}
