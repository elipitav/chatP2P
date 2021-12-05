/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package interfaz.controladores;

import aplicacion.recursos.Amigo;
import interfaz.fachada.FachadaGui;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author eliseopitavilarino
 */
public class VPrincipalController extends Controlador implements Initializable {
    
    private FachadaGui fgui;
    private String usuario;     //Usuario que hace uso de la aplicación
    private String contrasena;  //Contraseña del usuario
    private Amigo receptor;     //Persona con la que habla el usuario en cada momento
    @FXML
    private TableColumn<Amigo, String> colNombre;
    @FXML
    private TableColumn<Amigo, String> colEstado;
    @FXML
    private TableView<Amigo> tablaAmigos;
    @FXML
    private TextArea textAreaNotificacion;
    @FXML
    private TextField textFieldModContra;
    @FXML
    private Button botonModContra;
    @FXML
    private Label labelContraModificada;

    public void setFgui(FachadaGui fgui) {
        this.fgui = fgui;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
    
    @FXML
    private Button botonEnviar;
    @FXML
    private TextField textFieldMensaje;
    @FXML
    private TextArea textAreaChat;
    @FXML
    private Label labelReceptor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.botonModContra.setDisable(true);
        this.botonEnviar.setDisable(true);
        //Lo desactivamos hasta que se hable con alguien
        this.textFieldMensaje.setEditable(false);
        
        this.colEstado.setCellValueFactory(new PropertyValueFactory<Amigo,String>("estado"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory<Amigo,String>("nombre"));
        
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
    
    //Método para tener activado el botón cuando haya texto en el textiField
    @FXML
    private void activarBotonModContra(KeyEvent event) {
        if (this.textFieldModContra.getText().equals("")){
            this.botonModContra.setDisable(true);
        }
        else{
            this.botonModContra.setDisable(false);
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
    
    //Método para cambiar la contraseña pulsando el botón
    @FXML
    private void modContraClick(ActionEvent event) {
        this.modificarContrasena();
    }
    
    //Método para cambiar la contraseña pulsando enter
    /*@FXML
    private void onEnterModContra(ActionEvent event) {
        this.modificarContrasena();
    }*/
    
    //Método para cambiar la contraseña
    private void modificarContrasena(){
        
        
        
        this.textFieldModContra.setText("");
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
        fgui.enviarMensaje(receptor.getNombre(),mensaje);
    }
    
    //Método para recibir mensaje
    public void recibirMensaje(String emisor, String mensaje){
        //Solo si el chat actual se corresponde con el del emisor lo mostramos por pantalla
        //Además comprobamos null por si todavía no estamos hablando con nadie
        if(this.receptor != null){
            if(this.receptor.getNombre().equals(emisor)){
                //Añadimos el mensaje al textArea
                textAreaChat.appendText(emisor+": " + mensaje + "\n");
            }
        }
    }
    
    //Método para añadir amigo a la tabla
    public void nuevoAmigo(Amigo amigo){
        this.tablaAmigos.getItems().add(amigo);
        //this.anadirNotificacion(amigo.getNombre()+" conectado"); //Esto sobra creo
    }
    
    //Método para indicar que un amigo se ha conectado
    public void amigoConectado(String amigo){
        //Actualizamos la tabla
        this.tablaAmigos.refresh();
        this.anadirNotificacion(amigo+" conectado");
        if(!(this.receptor == null)){
            if(amigo.equals(this.receptor.getNombre())){
                this.textFieldMensaje.setEditable(true);
            }
        }
        
    }
    
    
    //Método para indicar que un amigo se ha desconectado
    public void amigoDesconectado(String nombre){
        //Actualizamos la tabla
        this.tablaAmigos.refresh();
        this.anadirNotificacion(nombre+" desconectado");
        if(nombre.equals(this.receptor.getNombre())){
            this.textFieldMensaje.setEditable(false);
        }
    }
    
    //Método para añadir notificaciones
    public void anadirNotificacion(String notificacion){
        //Añadimos la notificación
        this.textAreaNotificacion.appendText(notificacion+"\n");
    }

    @FXML
    private void cambiarAmigo(MouseEvent event) {
        //Cambiamos el receptor, actualizando el chat y la etiqueta con su nombre
        this.receptor = this.tablaAmigos.getItems().get(this.tablaAmigos.getSelectionModel().getFocusedIndex());
        this.textAreaChat.setText(receptor.getChat());
        this.labelReceptor.setText(receptor.getNombre());
        
        //Permitimos enviar mensajes si el amigo está en línea
        if(this.receptor.conectado()){
            this.textFieldMensaje.setEditable(true);
        }
        else{
            this.textFieldMensaje.setEditable(false);
        }
    }
}
