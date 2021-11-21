/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion.fachada;

import aplicacion.cliente.CallbackClienteP2PImpl;
import aplicacion.cliente.CallbackClienteP2PInterfaz;
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


/**
 *
 * @author eliseopitavilarino
 */
public class FachadaAplicacion extends Application{
    private FachadaGui fgui;
    
    public FachadaAplicacion() {
        fgui = new interfaz.fachada.FachadaGui(this);
    }
    
    public static void main(String[] args) {
        
        try {
            int RMIPort;         
            String hostName;

            String registryURL = "rmi://localhost:1099/servidorChat";  
            // find the remote object and cast it to an 
            //   interface object
            ServidorP2PInterfaz servidor =
              (ServidorP2PInterfaz)Naming.lookup(registryURL);
            System.out.println("Lookup completed " );

            CallbackClienteP2PInterfaz callbackObj = 
              new CallbackClienteP2PImpl("manolo");
            // register for callback
            System.out.println("holas");
            servidor.registrarCliente(callbackObj, "manolo");
            System.out.println("Registered for callback.");
            
            //Poner esto en el código de cerrar la ventana
            //servidor.eliminarCliente("manolo");
            //System.out.println("Unregistered for callback.");
        }
        catch (Exception e) {
          System.out.println(
            "Excepcion en el cliente: " + e);
        }
        
        launch(args);
    }
    
    public void iniciaInterfazUsuario(Stage stage) throws Exception {
        fgui.iniciarVista(stage);
    }

    @Override
    public void start(Stage stage) {
        FachadaAplicacion fa;
        fa = new FachadaAplicacion();
        try {
            fa.iniciaInterfazUsuario(stage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }
    }
    
    public void enviarMensaje(String mensaje){
        //this.emisor.enviarMensaje(mensaje);
    }
}
