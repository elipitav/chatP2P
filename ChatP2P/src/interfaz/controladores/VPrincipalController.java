/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package interfaz.controladores;

import aplicacion.recursos.Amigo;
import interfaz.fachada.FachadaGui;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    @FXML
    private ListView<String> listaUsuarios;
    @FXML
    private TextField textFieldBuscar;
    @FXML
    private Button botonSolicitud;
    @FXML
    private ListView<String> listaSolicitudes;
    @FXML
    private Label labelSolicitudes;
    @FXML
    private Button botonAceptarSolicitud;
    @FXML
    private Button botonRechazarSolicitud;

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
        //Desactivamos todos los botones hasta que se introduzcan argumentos en los textfield
        this.botonModContra.setDisable(true);
        this.botonEnviar.setDisable(true);
        this.botonSolicitud.setDisable(true);
        //Lo desactivamos hasta que se hable con alguien
        this.textFieldMensaje.setEditable(false);
        //Iniciamos los valores de la tableview
        this.colEstado.setCellValueFactory(new PropertyValueFactory<Amigo, String>("estado"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory<Amigo, String>("nombre"));
        //this.textFieldModContra.set

    }

    //Método para tener activado el botón de enviar cuando haya texto en el textiField
    @FXML
    private void activarBoton(KeyEvent event) {
        if (textFieldMensaje.getText().equals("")) {
            botonEnviar.setDisable(true);
        } else {
            botonEnviar.setDisable(false);
        }
    }

    //Método para tener activado el botón de enviar solicitud
    @FXML
    private void activarBotonSolicitud(MouseEvent event) {
        if (this.listaUsuarios.getSelectionModel().getSelectedItem() != null) {
            this.botonSolicitud.setDisable(false);
            this.botonRechazarSolicitud.setDisable(false);
        }
    }

    //Método para tener activado el botón de modificar contraseña cuando haya texto en el textiField
    @FXML
    private void activarBotonModContra(KeyEvent event) {
        if (this.textFieldModContra.getText().equals("")) {
            this.botonModContra.setDisable(true);
        } else {
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
    //Método para limpiar la pestaña de cambio de contraseña
    @FXML
    private void limpiarPestanaContrasena(Event event) {
        this.textFieldModContra.clear();
        this.botonModContra.setDisable(true);
        this.labelContraModificada.setText("");
    }

    //Método para cambiar la contraseña
    private void modificarContrasena() {
        String nuevaContra = this.textFieldModContra.getText();
        //Pasamos la contraseña para que el servidor pueda comprobar que somos el cliente de verdad
        String confirmacion = this.fgui.modificarContrasena(usuario,
                nuevaContra, this.contrasena);
        this.labelContraModificada.setText(confirmacion);
        //Si todo va correctamente cambiamos la contraseña de la ventana
        if (confirmacion.equals("Contraseña modificada con éxito")) {
            this.contrasena = nuevaContra;
        }
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
        fgui.enviarMensaje(receptor.getNombre(), mensaje);
    }

    //Método para recibir mensaje
    public void recibirMensaje(String emisor, String mensaje) {
        //Solo si el chat actual se corresponde con el del emisor lo mostramos por pantalla
        //Además comprobamos null por si todavía no estamos hablando con nadie
        if (this.receptor != null) {
            if (this.receptor.getNombre().equals(emisor)) {
                //Añadimos el mensaje al textArea
                textAreaChat.appendText(emisor + ": " + mensaje + "\n");
            }
            else{
                this.anadirNotificacion("Mensaje nuevo de " + emisor);
            }
        }
        else{
                this.anadirNotificacion("Mensaje nuevo de " + emisor);
        }
    }

    //Método para añadir amigo a la tabla
    public void nuevoAmigo(Amigo amigo) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tablaAmigos.getItems().add(amigo);
            }

        });
    }

    //Método para indicar que un amigo se ha conectado
    public void amigoConectado(String amigo) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Actualizamos la tabla
                tablaAmigos.refresh();
                anadirNotificacion(amigo + " conectado");
                if (!(receptor == null)) {
                    if (amigo.equals(receptor.getNombre())) {
                        textFieldMensaje.setEditable(true);
                    }
                }
            }

        });
    }

    //Método para indicar que un amigo se ha desconectado
    public void amigoDesconectado(String nombre) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Actualizamos la tabla
                tablaAmigos.refresh();
                anadirNotificacion(nombre + " desconectado");
                if (!(receptor == null)) {
                    if (nombre.equals(receptor.getNombre())) {
                        textFieldMensaje.setEditable(false);
                    }
                }
            }
        });
    }

    //Método para añadir una solicitud de amistad
    public void nuevaSolicitud(String emisor) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                listaSolicitudes.getItems().add(emisor);
                anadirNotificacion(emisor + " quiere ser tu amigo");
                listaSolicitudes.refresh();
            }
        });
    }

    //Método para añadir notificaciones
    public void anadirNotificacion(String notificacion) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Añadimos la notificación
                textAreaNotificacion.appendText(notificacion + "\n");
            }
        });
    }

    @FXML

    private void cambiarAmigo(MouseEvent event) {
        //Cambiamos el receptor, actualizando el chat y la etiqueta con su nombre
        int indice = this.tablaAmigos.getSelectionModel().getFocusedIndex();
        if (indice != -1) {  //Si realmente hay algún amigo seleccionado
            this.receptor = this.tablaAmigos.getItems().get(indice);
            this.textAreaChat.setText(receptor.getChat());
            this.labelReceptor.setText(receptor.getNombre());

            //Permitimos enviar mensajes si el amigo está en línea
            if (this.receptor.conectado()) {
                this.textFieldMensaje.setEditable(true);
            } else {
                this.textFieldMensaje.setEditable(false);
            }
        }
    }

    //Método para limpiar la pestaña de envio de solicitudes
    @FXML
    private void limpiarPestanaSolicitar(Event event) {
        this.botonSolicitud.setDisable(true);
        this.labelSolicitudes.setText("");
        this.textFieldBuscar.clear();
        this.listaUsuarios.getItems().clear();
    }

    @FXML
    private void buscarUsuarios(KeyEvent event) {
        String cadena = this.textFieldBuscar.getText();
        ArrayList<String> listaUsuarios = this.fgui.buscarUsuarios(cadena);
        this.listaUsuarios.getItems().clear();
        this.botonSolicitud.setDisable(true);
        //buscarUsuarios devuelve null si la cadena introducida tiene menos de 4 caracteres
        if (listaUsuarios != null) {
            for (String u : listaUsuarios) {
                if (!this.usuario.equals(u)) { //Comprobamos que no es él mismo
                    //System.out.println(u);
                    this.listaUsuarios.getItems().add(u);
                    this.listaUsuarios.refresh();
                }
            }
        }
    }

    @FXML
    private void enviarSolicitud(ActionEvent event) {
        //enviarSolicitud devuelve true si ya existía la solicitud
        String solicitado = this.listaUsuarios.getSelectionModel().getSelectedItem();
        boolean existe = this.fgui.enviarSolicitud(this.usuario, solicitado, contrasena);
        if (existe) {
            this.labelSolicitudes.setText("Ya enviada previamente");
        } else {
            this.anadirNotificacion("Solicitud enviada a " + solicitado);
            this.labelSolicitudes.setText("Solicitud enviada");
        }
    }

    public void actualizarSolicitudes(ArrayList<String> emisores) {
        this.listaSolicitudes.getItems().clear();
        for (String emisor : emisores) {
            this.listaSolicitudes.getItems().add(emisor);
            this.listaSolicitudes.refresh();
        }
    }

    @FXML
    private void aceptarSolicitud(ActionEvent event) {
        if (this.listaSolicitudes.getSelectionModel().getSelectedItem() != null) {
            String emisor = this.listaSolicitudes.getSelectionModel().getSelectedItem();
            this.fgui.anadirAmistad(emisor, contrasena);
        }

    }

    @FXML
    private void rechazarSolicitud(ActionEvent event) {
        if (this.listaSolicitudes.getSelectionModel().getSelectedItem() != null) {
            String emisor = this.listaSolicitudes.getSelectionModel().getSelectedItem();
            this.fgui.rechazarAmistad(emisor, contrasena);
        }
    }

}
