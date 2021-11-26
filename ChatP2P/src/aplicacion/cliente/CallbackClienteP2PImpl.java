/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion.cliente;

import aplicacion.fachada.FachadaAplicacion;
import aplicacion.recursos.Amigo;
import aplicacion.servidor.ServidorP2PInterfaz;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eliseopitavilarino
 */
public class CallbackClienteP2PImpl extends UnicastRemoteObject implements CallbackClienteP2PInterfaz{
    
    //Nombre del cliente
    private String nombre;
    //Interfaz del servidor
    private ServidorP2PInterfaz servidor;
    //Amigos del cliente
    private HashMap<String,Amigo> amigos;
    //Fachada aplicación
    private FachadaAplicacion fa;
    
    
    public CallbackClienteP2PImpl(String nombre, ServidorP2PInterfaz servidor, FachadaAplicacion fa) throws RemoteException{
        super();
        this.nombre = nombre;
        this.fa = fa;
        this.servidor = servidor;
        this.amigos = new HashMap<>();
    }

    @Override
    public synchronized void amigoConectado(CallbackClienteP2PInterfaz interfaz, String nombre) throws java.rmi.RemoteException {
        
        //Mostrar notificación de amigo conectado
        System.out.println(nombre+" conectado");
        
        //Añadimos al cliente al hashmap de amigos conectados
        Amigo amigo = new Amigo(nombre,"En linea",interfaz);
        this.amigos.put(nombre, amigo);
        
        fa.anadirAmigoTabla(amigo);
    }

    @Override
    public synchronized void amigoDesconectado(String nombre) throws java.rmi.RemoteException {
        
       //Mostrar mensaje de amigo desconectado
        System.out.println(nombre+" desconectado");
        
       //Eliminamos al cliente del hashmap de amigos conectados
       this.amigos.remove(nombre);
    }

    @Override
    public void recibirMensaje(String emisor, String mensaje) throws java.rmi.RemoteException{
        //Obtener el mensaje y mostrarlo por pantalla
        fa.recibirMensaje(emisor, mensaje);
        //Añadimos el mensaje al chat con ese amigo
        this.amigos.get(emisor).anadirMensaje(emisor, mensaje);
    }
    
    public void enviarMensaje(String receptor, String mensaje){
        //Enviamos un mensaje al amigo indicado
        this.amigos.get(receptor).enviarMensaje(receptor, mensaje);
    }
    
    public void registrarse(){
        try {
            //Llamamos al servidor para registrar al cliente
            this.servidor.registrarCliente(this, nombre);
            System.out.println("Registered for callback.");
        } catch (Exception e) {
            System.out.println("Excepcion en el cliente: " + e);
        }
    }
     
}
