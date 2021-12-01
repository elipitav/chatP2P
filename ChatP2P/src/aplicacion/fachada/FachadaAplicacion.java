/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion.fachada;

import aplicacion.cliente.CallbackClienteP2PImpl;
import aplicacion.cliente.CallbackClienteP2PInterfaz;
import aplicacion.recursos.Amigo;
import aplicacion.servidor.ServidorP2PInterfaz;
import interfaz.fachada.FachadaGui;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 *
 * @author eliseopitavilarino
 */
public class FachadaAplicacion extends Application {
    
    private final static String registryURL = "rmi://localhost:1099/servidorChat";
    
    private FachadaGui fgui;
    private CallbackClienteP2PImpl cliente;

    public FachadaAplicacion() {
        fgui = new FachadaGui(this);
    }    

    public static void main(String[] args) {
        launch(args);
    }

    public void iniciaInterfazUsuario(Stage stage) throws Exception {
        fgui.iniciarVista(stage);
    }

    @Override
    public void start(Stage stage) {
         FachadaAplicacion fa = new FachadaAplicacion();
        try {
            fa.iniciaInterfazUsuario(stage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }
    }
    
    public void registrarCliente(String nombre){
        try{
            //Obtenemos la interfaz remota del servidor
            ServidorP2PInterfaz servidor = (ServidorP2PInterfaz) Naming.lookup(registryURL);
            System.out.println("Lookup completed");
            //Obtenemos una instancia de la implementación
            this.cliente = new CallbackClienteP2PImpl(nombre, servidor,this);
            //Y nos registramos
            this.cliente.registrarse();
        }
        catch (Exception e) {
            System.out.println("Excepcion en el cliente: " + e);
        }
    }
    
    public void enviarMensaje(String receptor, String mensaje) {
        //Invocamos al metodo del cliente para enviar el mensaje
        this.cliente.enviarMensaje(receptor, mensaje);
    }
    
    public void recibirMensaje(String emisor, String mensaje){
        //Llamamos a la interfaz para poner el mensaje
        this.fgui.recibirMensaje(emisor, mensaje);
    }
    
    //Método para añadir amigo a la tabla
    public void anadirAmigoTabla(Amigo amigo){
        //Llamamos a la interfaz
        this.fgui.anadirAmigoTabla(amigo);
    }
    
    //Método para añadir una notificacion
    public void anadirNotificacion(String notificacion){
        this.fgui.anadirNotificacion(notificacion);
    }
}
