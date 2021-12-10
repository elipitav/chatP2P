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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private Label labelContraIncorrecta;
    @FXML
    private TextField textFieldNombreRegistro;
    @FXML
    private TextField textFieldContrasenaRegistro;
    @FXML
    private Button botonRegistro;
    @FXML
    private Label labelRegistroIncorrecto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.botonAcceder.setDisable(true);
    }    
    
    //Método para iniciar sesión
    @FXML
    private void acceder(ActionEvent event) {
        
        String nombre = textFieldNombreUsuario.getText();
        String contrasena = textFieldContrasenha.getText();
        
        //Tratamos de iniciar sesión con los datos introducidos
        String mensaje = fgui.iniciarSesion(nombre, contrasena);
        
        if (mensaje.equals("Sesión iniciada")) {
           
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
            controlador.setUsuario(nombre);
            controlador.setContrasena(contrasena);
            controlador.setVentana(stage);
            controlador.setFgui(fgui);
            controlador.getVentana().setTitle(nombre);
            fgui.setVp(controlador);

            //Conectamos el ciente
            fgui.conectarCliente(nombre, contrasena);

            //Cerramos la ventana de inicio de sesión
            getVentana().close();
            
        } else { //Si la contraseña es incorrecta, el usuario ya está conectado o no existe
            System.out.println(mensaje);
            this.labelContraIncorrecta.setText(mensaje);
        }
        
        
    }
    
    //Método para registrar un usuario
    @FXML
    private void registrar(ActionEvent event) {
        String nombre = textFieldNombreRegistro.getText();
        String contrasena = textFieldContrasenaRegistro.getText();
        
        //Tratamos de registrar el usuario
        String mensaje = fgui.registrarUsuario(nombre, contrasena);
        
        if(mensaje.equals("El usuario ya existe")){
            this.labelRegistroIncorrecto.setText(mensaje);
        }
        else{
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
            controlador.setUsuario(nombre);
            controlador.setContrasena(contrasena);
            controlador.setVentana(stage);
            controlador.setFgui(fgui);
            controlador.getVentana().setTitle(nombre);
            fgui.setVp(controlador);

            //Conectamos el ciente
            fgui.conectarCliente(nombre, contrasena);

            //Cerramos la ventana de inicio de sesión
            getVentana().close();
        }
    }
    
    //Método para tener activado el botón cuando haya texto en el textiField
    @FXML
    private void activarBoton(KeyEvent event) {
        if (this.textFieldContrasenha.getText().equals("") || this.textFieldNombreUsuario.getText().equals("")){
            this.botonAcceder.setDisable(true);
        }
        else{
            this.botonAcceder.setDisable(false);
        }
    }

    public FachadaGui getFgui() {
        return fgui;
    }

    public void setFgui(FachadaGui fgui) {
        this.fgui = fgui;
    }
    
    
    
}
