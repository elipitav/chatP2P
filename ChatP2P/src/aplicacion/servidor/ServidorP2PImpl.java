/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import aplicacion.cliente.CallbackClienteP2PInterfaz;
import bd.fachada.FachadaBD;
import java.util.ArrayList;
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

        //Comprobamos si el usuario ya existe antes de insertarlo
        if (!fbd.usuarioExiste(nombre)){
            //Si no existe, lo añadimos el usuario nuevo a la base de datos
            fbd.insertarUsuario(nombre, contrasena);
            
            System.out.println("Nuevo cliente registrado: " + nombre + ". Su contraseña es: " + contrasena);
            
            return "Cliente registrado";
        } else {
            return "El usuario ya existe";
        }
    }
    
    @Override
    public String iniciarSesion(String nombre, String contrasena) {
        //Comprobamos si el usuario existe
        if(!fbd.usuarioExiste(nombre)){
            return "El usuario no existe";
        }
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(nombre);
        //Comprobamos si coincide
        if (contra.equals(contrasena)) {
            //Y comprobamos si el usuario está ya conectado
            if (clientesConectados.containsKey(nombre)) {
                System.out.println("Usuario ya conectado");
                return "Usuario ya conectado";
            } else {
                System.out.println("Sesión iniciada");
                return "Sesión iniciada";
            }
        } else {
            System.out.println("Error en inicio de sesión: contraseña incorrecta");
            return "Contraseña incorrecta";
        }
    }
    
    @Override 
    public synchronized boolean clienteConectado(String nombre) throws java.rmi.RemoteException {
        return clientesConectados.containsKey(nombre);
    }
    
    @Override
    public synchronized void conectarCliente(CallbackClienteP2PInterfaz cliente, String nombre) throws java.rmi.RemoteException {
        //Obtenemos los amigos del usuario
        ArrayList<String> amigos = fbd.obtenerAmigos(nombre);
        cliente.recibirListaAmigos(amigos);
        
        for(Map.Entry<String, CallbackClienteP2PInterfaz> entry : this.clientesConectados.entrySet()) {
            if (amigos.contains(entry.getKey())){
                entry.getValue().amigoConectado(cliente, nombre);
                //System.out.println(entry.getKey() + " avisado de que se conecto " + nombre);
                cliente.amigoConectado(entry.getValue(), entry.getKey());
                //System.out.println(nombre + " avisado de que estaba conectado " + entry.getKey());
            }
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
            System.out.println("Error en desconexión: el cliente " + nombre + "no estaba conectado");
        }        
        
    }   
    
}
