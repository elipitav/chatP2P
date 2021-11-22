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
    private String usuario; //Usuario que hace uso de la aplicación
    private String receptor; //Persona con la que habla el usuario en cada momento

    public void setFgui(FachadaGui fgui) {
        this.fgui = fgui;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
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
        botonEnviar.setDisable(true);
        //fgui.registrarCliente(usuario);
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
        //Obtenemos el mensaje del textfield
        String mensaje = textFieldMensaje.getText();
        //Vacíamos el textfield
        textFieldMensaje.setText("");
        //Añadimos el mensaje al textArea
        textAreaChat.appendText("Yo: " + mensaje + "\n");
        //Desactivamos el botón
        botonEnviar.setDisable(true);
        //Enviamos el mensaje al receptor
        fgui.enviarMensaje(receptor,mensaje);
    }
    
    //Método para recibir mensaje
    public void recibirMensaje(String emisor, String mensaje){
        //Solo si el chat actual se corresponde con el del emisor lo mostramos por pantalla
        if(this.receptor.equals(emisor)){
            //Añadimos el mensaje al textArea
            textAreaChat.appendText(emisor+": " + mensaje + "\n");
        }
    }
    
}
