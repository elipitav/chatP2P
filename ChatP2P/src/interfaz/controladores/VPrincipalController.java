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

/**
 * FXML Controller class
 *
 * @author eliseopitavilarino
 */
public class VPrincipalController extends Controlador implements Initializable {
    
    private FachadaGui fgui;
    private String usuario; //Usuario que hace uso de la aplicación
    private Amigo receptor; //Persona con la que habla el usuario en cada momento
    @FXML
    private TableColumn<Amigo, String> colNombre;
    @FXML
    private TableColumn<Amigo, String> colEstado;
    @FXML
    private TableView<Amigo> tablaAmigos;
    @FXML
    private TextArea textAreaNotificacion;

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
    @FXML
    private Label labelReceptor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.botonEnviar.setDisable(true);
        //Lo desactivamos hasta que se hable con alguien
        this.textFieldMensaje.setDisable(true);
        
        this.colEstado.setCellValueFactory(new PropertyValueFactory<Amigo,String>("estado"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory<Amigo,String>("nombre"));
        ObservableList<Amigo> listAmigos = FXCollections.observableArrayList();
        this.tablaAmigos.setItems(listAmigos);
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
    public void anadirAmigoTabla(Amigo amigo){
        this.tablaAmigos.getItems().add(amigo);
    }
    
    //Método para añadir notificaciones
    public void anadirNotificacion(String notificacion){
        this.textAreaNotificacion.appendText(notificacion+"\n");
    }

    @FXML
    private void cambiarAmigo(MouseEvent event) {
        this.tablaAmigos.refresh();
        this.receptor = this.tablaAmigos.getItems().get(this.tablaAmigos.getSelectionModel().getFocusedIndex());
        this.textAreaChat.setText(receptor.getChat());
        this.labelReceptor.setText(receptor.getNombre());
        
        //Lo activamos ya que pasaremos a hablar con alguiebn
        this.textFieldMensaje.setDisable(false);
    }
}
