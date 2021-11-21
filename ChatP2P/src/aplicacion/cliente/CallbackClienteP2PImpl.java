/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion.cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 *
 * @author eliseopitavilarino
 */
public class CallbackClienteP2PImpl extends UnicastRemoteObject implements CallbackClienteP2PInterfaz{
    
    //Nombre del cliente
    private String nombre;
    //Amigos del cliente conectados
    private HashMap<String,CallbackClienteP2PInterfaz> amigos;
    
    
    public CallbackClienteP2PImpl(String nombre) throws RemoteException{
        super();
        this.nombre = nombre;
        this.amigos = new HashMap<>();
    }

    @Override
    public synchronized void amigoConectado(CallbackClienteP2PInterfaz amigo, String nombre) throws java.rmi.RemoteException {
        
        //Mostrar notificación de amigo conectado
        
        //Añadimos al cliente al hashmap de amigos conectados
        this.amigos.put(nombre,amigo);
    }

    @Override
    public synchronized void amigoDesconectado(String nombre) throws java.rmi.RemoteException {
        
       //Mostrar mensaje de amigo desconectado
       
       //Eleminamos al cliente del hashmap de amigos conectados
       this.amigos.remove(nombre);
    }
    
    
    
}
