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
    public synchronized void conectarCliente(CallbackClienteP2PInterfaz cliente, String nombre) throws java.rmi.RemoteException {
        //Obtenemos los amigos del usuario
        ArrayList<String> amigos = fbd.obtenerAmigos(nombre);
        cliente.recibirListaAmigos(amigos);
        
        //Avisamos a los amigos conectados que el cliente se ha conectado
        //Y avisamos al nuevo cliente conectado con los amigos conectados
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
            //Avisamos a todos los amigos conectados
            ArrayList<String> amigos = fbd.obtenerAmigos(nombre);
            for(String amigo: amigos){
                if(this.clientesConectados.containsKey(amigo)){
                    this.clientesConectados.get(amigo).amigoDesconectado(nombre);
                }
            }
        } else{
            System.out.println("Error en desconexión: el cliente " + nombre + "no estaba conectado");
        }        
        
    }   
    
    @Override
    public synchronized String modificarContrasena(String usuario, String nuevaContrasena, 
            String antiguaContrasena) throws java.rmi.RemoteException{
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(usuario);
        //Y comprobamos que es correcta
        if(contra.equals(antiguaContrasena)){
            this.fbd.modificarContrasena(usuario, nuevaContrasena);
            return "Contraseña modificada con éxito";
        }
        else{
            return "Contraseña antigua incorrecta";
        }
    }
    
    @Override
    public synchronized ArrayList<String> buscarUsuarios(String cadena) throws java.rmi.RemoteException{
        return this.fbd.buscarUsuarios(cadena);
    }
    
    @Override
    public synchronized ArrayList<String> obtenerSolicitudes(String usuarioReceptor) throws java.rmi.RemoteException{
        return this.fbd.obtenerSolicitudes(usuarioReceptor);
    }
   
    @Override
    public synchronized boolean enviarSolicitud(String emisor, String receptor) throws java.rmi.RemoteException{
        boolean existe = this.fbd.insertarSolicitud(emisor, receptor);
        
        if(!existe){
            //Si el receptor está conectado, le notificamos la solicitud y actualizamos su tabla de solicitudes
            if (this.clientesConectados.keySet().contains(receptor)){
                this.clientesConectados.get(receptor).nuevaSolicitud(emisor);
                //this.clientesConectados.get(receptor).notificar(emisor + " quiere ser tu amigo");
            }
        }
        
        return existe;
    }
    
    @Override
    public synchronized CallbackClienteP2PInterfaz anadirAmistad(String usuario1, String usuario2) throws java.rmi.RemoteException{
        this.fbd.anadirAmistad(usuario1, usuario2);
        if(this.clientesConectados.containsKey(usuario1)){
            return this.clientesConectados.get(usuario1);
        }
        return null;
    }
    
}
