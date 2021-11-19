/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import gui.FachadaGui;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author eliseopitavilarino
 */
public class VPrincipalController extends Controlador implements Initializable {
    
    private FachadaGui fgui;

    public void setFgui(FachadaGui fgui) {
        this.fgui = fgui;
    }
    
    @FXML
    private Button botonEnviar;
    @FXML
    private TextField textFieldMensaje;
    @FXML
    private TextArea textAreaChat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    //Método para tener activado el botón cuando haya texto
    @FXML
    private void activarBoton(KeyEvent event) {
        if (textFieldMensaje.getText().equals("")){
            botonEnviar.setDisable(true);
        }
        else{
            botonEnviar.setDisable(false);
        }
    }
    
    //Método para enviar un mensaje
    @FXML
    private void enviarMensaje(ActionEvent event) {
        String mensaje = "Yo: " + textFieldMensaje.getText() + "\n";
        textAreaChat.appendText(mensaje);
        textFieldMensaje.setText("");
        //fgui.enviarMensaje(mensaje);
    }
    
    //Método para añadir mensaje al textArea cuando llega o se envía un mensaje
    public void anhadirMensaje(String mensaje) {
        textAreaChat.appendText(mensaje);
    }
    
}
