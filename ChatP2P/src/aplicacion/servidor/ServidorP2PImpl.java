/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import aplicacion.cliente.CallbackClienteP2PInterfaz;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author eliseopitavilarino
 */
public class ServidorP2PImpl extends UnicastRemoteObject implements ServidorP2PInterfaz {
    
    //HashMap con todos los clientes conectados
    private HashMap<String, CallbackClienteP2PInterfaz> clientes;
    
    public ServidorP2PImpl() throws RemoteException{
        super();
        clientes = new HashMap<>();
    }
    
    @Override
    public synchronized HashMap<String, CallbackClienteP2PInterfaz> registrarCliente(CallbackClienteP2PInterfaz cliente, String nombre) throws java.rmi.RemoteException {
        
        if (!clientes.keySet().contains(cliente)){
            //Añadimos al cliente al hashmap de clientes conectados
            clientes.put(nombre, cliente);
            System.out.println("Nuevo cliente conectado: " + cliente);

            //Avisamos a todos los clientes conectados (Todos son amigos)
            for(Map.Entry<String, CallbackClienteP2PInterfaz> entry : this.clientes.entrySet()) {
                entry.getValue().amigoConectado(cliente, nombre);
            }
            
            //Enviamos al cliente la lista completa de clientes conectados (todos son amigos)
            return clientes;
        }
        
        return null;
    }

    @Override
    public synchronized void eliminarCliente(String nombre) throws java.rmi.RemoteException {
        
        //Eliminamos al cliente del hashmap de clientes conectados
        if (clientes.remove(nombre) != null){
            System.out.println("Cliente desconectado: " + nombre);
            //Avisamos a todos los clientes conectados (Todos son amigos)
            for(Map.Entry<String, CallbackClienteP2PInterfaz> entry : this.clientes.entrySet()) {
                entry.getValue().amigoDesconectado(nombre);
            }
        } else{
            System.out.println("Error en desconexion: el cliente " + nombre + "no estaba conectado");
        }        
        
    }   
    
}
