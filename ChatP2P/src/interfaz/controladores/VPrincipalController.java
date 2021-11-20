/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package interfaz.controladores;

import interfaz.fachada.FachadaGui;
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
    private String usuario;

    public void setFgui(FachadaGui fgui) {
        this.fgui = fgui;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
    
    //Método para tener activado el botón cuando haya texto en el textiField
    @FXML
    private void activarBoton(KeyEvent event) {
        if (textFieldMensaje.getText().equals("")){
            botonEnviar.setDisable(true);
        }
        else{
            botonEnviar.setDisable(false);
        }
    }
    
    //Método para enviar mensaje pulsando enter
    @FXML
    private void onEnter(ActionEvent event) {
        this.enviarMensaje();
    }
    
    //Método para enviar mensaje pulsando el botón
    @FXML
    private void enviarMensajeClick(ActionEvent event) {
        this.enviarMensaje();
    }
    
    //Método para enviar un mensaje
    private void enviarMensaje() {
        String mensaje = "Yo: " + textFieldMensaje.getText() + "\n";
        textAreaChat.appendText(mensaje);
        textFieldMensaje.setText("");
        botonEnviar.setDisable(true);
        //fgui.enviarMensaje(mensaje);
    }
    
    //Método para añadir mensaje al textArea cuando llega o se envía un mensaje
    public void anhadirMensaje(String mensaje) {
        textAreaChat.appendText(mensaje);
    }
    
}
