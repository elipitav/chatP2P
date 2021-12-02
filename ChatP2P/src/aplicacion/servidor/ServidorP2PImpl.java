/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import aplicacion.cliente.CallbackClienteP2PInterfaz;
import bd.fachada.FachadaBD;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author eliseopitavilarino
 */
public class ServidorP2PImpl extends UnicastRemoteObject implements ServidorP2PInterfaz {
    
    //HashMap con todos los clientes conectados
    private HashMap<String, CallbackClienteP2PInterfaz> clientesConectados;
    
    //Fachada de la base de datos de usuarios
    private FachadaBD fbd;
    
    public ServidorP2PImpl() throws RemoteException{
        super();
        clientesConectados = new HashMap<>();
        fbd = new FachadaBD(this);
    }
    
    @Override
    public String registrarUsuario(String nombre, String contrasena) throws java.rmi.RemoteException {
            
        String contra = fbd.obtenerContrasenaUsuario(nombre);

        //Si la contraseña del usuario es "" quiere decir que el usuario no existía
        if (contra.equals("")){
            
            //Añadimos el usuario nuevo a la base de datos
            fbd.insertarUsuario(nombre, contrasena);
            
            System.out.println("Nuevo cliente registrado: " + nombre + ". Su contraseña es: " + contrasena);
            
            return "Cliente registrado";
            
        } else { // En caso de que ya estuviese registrado, tratamos de iniciar la sesión
            
            if (contra.equals(contrasena)) {
                if(clientesConectados.containsKey(nombre)){
                    System.out.println("Usuario ya conectado");
                    return "Usuario ya conectado";
                } else {
                                                            
                    System.out.println("Sesion iniciada");
                    return "Sesion iniciada";
                }
            }
            else {
                System.out.println("Error en inicio de sesion: contrasena incorrecta");
                return "Contrasena incorrecta";
            }
        }
    }
    
    @Override
    public synchronized void registrarCliente(CallbackClienteP2PInterfaz cliente, String nombre) throws java.rmi.RemoteException {
        //Avisamos a todos los clientes conectados (Todos son amigos)
        for(Map.Entry<String, CallbackClienteP2PInterfaz> entry : this.clientesConectados.entrySet()) {
            entry.getValue().amigoConectado(cliente, nombre);
            cliente.amigoConectado(entry.getValue(), entry.getKey());
        }

        //Añadimos al cliente al hashmap de clientes conectados
        clientesConectados.put(nombre, cliente);
    }

    @Override
    public synchronized void desconectar(String nombre) throws java.rmi.RemoteException {
        
        //Eliminamos al cliente del hashmap de clientes conectados
        if (clientesConectados.remove(nombre) != null){
            System.out.println("Cliente desconectado: " + nombre);
            //Avisamos a todos los clientes conectados (Todos son amigos)
            for(Map.Entry<String, CallbackClienteP2PInterfaz> entry : this.clientesConectados.entrySet()) {
                entry.getValue().amigoDesconectado(nombre);
            }
        } else{
            System.out.println("Error en desconexion: el cliente " + nombre + "no estaba conectado");
        }        
        
    }   
    
}
