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
import javafx.stage.Stage;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eliseopitavilarino
 */
public class FachadaAplicacion extends Application {
    
    private final static String registryURL = "rmi://localhost:1099/servidorChat";
    
    private FachadaGui fgui;
    private CallbackClienteP2PImpl cliente;
    private ServidorP2PInterfaz servidor;

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
    
    public String registrarUsuario(String nombre, String contrasena){
        String mensaje = "";
        try{
            //Obtenemos la interfaz remota del servidor
            servidor = (ServidorP2PInterfaz) Naming.lookup(registryURL);
            System.out.println("Lookup completed");
            
            //Llamamos al servidor para registrar al usuario
            mensaje = servidor.registrarUsuario(nombre, contrasena);
        }
        catch (Exception e) {
            System.out.println("Excepcion en el cliente: " + e);
        }
        return mensaje;
    }
    
    public String iniciarSesion(String nombre, String contrasena){
        String mensaje = "";
        try{
            //Obtenemos la interfaz remota del servidor
            servidor = (ServidorP2PInterfaz) Naming.lookup(registryURL);
            System.out.println("Lookup completed");
            
            //Llamamos al servidor para registrar al usuario
            mensaje = servidor.iniciarSesion(nombre, contrasena);
        }
        catch (Exception e) {
            System.out.println("Excepcion en el cliente: " + e);
        }
        return mensaje;
    }
    
    public void conectarCliente(String nombre){
        try{
            //Obtenemos una instancia de la implementación
            this.cliente = new CallbackClienteP2PImpl(nombre, this.servidor,this);
            //Llamamos al servidor para registrar al cliente
            servidor.conectarCliente(this.cliente, nombre);
            ArrayList<String> listaSolicitudes = servidor.obtenerSolicitudes(nombre);
            if(!listaSolicitudes.isEmpty()){
                this.actualizarSolicitudes(listaSolicitudes);
                this.cliente.notificar("Tienes solicitudes pendientes");
            }
            
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
        //Llamamos a la interfaz para mostrar el mensaje
        this.fgui.recibirMensaje(emisor, mensaje);
    }
    
    //Método para añadir amigo a la tabla
    public void nuevoAmigo(Amigo amigo){
        this.fgui.nuevoAmigo(amigo);
    }
    
    //Método para indicar que un amigo se ha conectado
    public void amigoConectado(String amigo){
        //Llamamos a la interfaz para indicar que un amigo se ha conectado
        this.fgui.amigoConectado(amigo);
    }
    
    //Método para indicar que un amigo se ha desconectado
    public void amigoDesconectado(String nombre){
        //Llamamos a la interfaz para indicar que un amigo se ha desconectado
        this.fgui.amigoDesconectado(nombre);
    }
    
    //Método para añadir una solicitud de amistad
    public void nuevaSolicitud(String emisor){
        this.fgui.nuevaSolicitud(emisor);
    }
    
    //Método para añadir una notificacion
    public void anadirNotificacion(String notificacion){
        this.fgui.anadirNotificacion(notificacion);
    }
    
    //Método para desconectarse
    public void desconectar(){
        this.cliente.desconectar();
    }
    
    //Método para modificar la contraseña de un usuario
    public String modificarContrasena(String usuario, String nuevaContrasena, String antiguaContrasena){
        try {
            return this.servidor.modificarContrasena(usuario, nuevaContrasena, antiguaContrasena);
        } catch (RemoteException ex) {
            System.out.println("Error al modificar la contraseña: " + ex.getMessage());
            return "Error al modificar la contraseña";
        }
    }
    
    //Método para buscar usuarios que coincidan parcialmente con la cadena de caracteres introducida
    //Devuelve null cuando la cadena es de menos de 4 caracteres
    public ArrayList<String> buscarUsuarios(String cadena){
        ArrayList<String> lista = null;
        ArrayList<String> listaEliminar = new ArrayList<>();
        //Solo buscaremos cuando la cadena tenga un tamaño suficientemente grande
        if(cadena.length() >= 4){
            try {
                lista = this.servidor.buscarUsuarios(cadena);
                
                //Eliminaremos de la búsqueda los que ya son amigos del usuario
                for (String usuario : lista){
                    if (this.cliente.getAmigos().keySet().contains(usuario)){
                        listaEliminar.add(usuario);
                    }
                }
                for (String usuario : listaEliminar){
                    lista.remove(usuario);
                }
                
            } catch (RemoteException ex) {
                System.out.println("Error al buscar usuarios: " + ex.getMessage());
            }
        }
        
        return lista;
    }
    
    //Método para enviar solicitudes de amistad
    //Devuelve true si ya existía la solicitud
    public boolean enviarSolicitud(String emisor, String receptor){
        boolean existe = false;
        try{
            existe = this.servidor.enviarSolicitud(emisor, receptor);
        } catch (RemoteException ex){
            System.out.println("Error al enviar la solicitud: " + ex.getMessage());
        }
        return existe;
    }
    
    //Método para actualizar la lista de solicitudes pendientes de un usuario
    public void actualizarSolicitudes(ArrayList<String> emisores){
        this.fgui.actualizarSolicitudes(emisores);
    }
    
    //Método para añadir una amistad
    public void anadirAmistad(String emisor){
        try{
            this.servidor.anadirAmistad(emisor, this.cliente.getNombre());
            this.fgui.actualizarSolicitudes(this.servidor.obtenerSolicitudes(this.cliente.getNombre()));
                  
            Amigo amigo = new Amigo(emisor);
            
            if (this.servidor.clienteConectado(emisor)){
                amigo.setEstado("En linea");
                CallbackClienteP2PInterfaz clienteAmigo = this.servidor.obtenerCliente(emisor);
                amigo.setInterfaz(clienteAmigo);
                
                clienteAmigo.amigoConectado(this.cliente, this.cliente.getNombre());
                
            }
            this.cliente.getAmigos().put(emisor, amigo);
            this.nuevoAmigo(amigo);
            this.amigoConectado(emisor);
            
            
        } catch (RemoteException ex){
            System.out.println("Error al aceptar la amistad: " + ex.getMessage());
        }
    }
    
}
