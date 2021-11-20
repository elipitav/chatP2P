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
    public void registrarCliente(CallbackClienteP2PInterfaz cliente, String nombre) throws java.rmi.RemoteException {
        
        //Añadimos al cliente al hashmap de clientes conectados
        clientes.put(nombre, cliente);
        
        //Avisamos a todos los clientes conectados (Todos son amigos)
        for(Map.Entry<String, CallbackClienteP2PInterfaz> entry : this.clientes.entrySet()) {
            entry.getValue().amigoConectado(cliente, nombre);
        }
    }

    @Override
    public void eliminarCliente(String nombre) throws java.rmi.RemoteException {
        
        //Eliminamos al cliente del hashmap de clientes conectados
        clientes.remove(nombre);
        
        //Avisamos a todos los clientes conectados (Todos son amigos)
        for(Map.Entry<String, CallbackClienteP2PInterfaz> entry : this.clientes.entrySet()) {
            entry.getValue().amigoDesconectado(nombre);
        }
    }
    
}
