/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package aplicacion.servidor;

import java.rmi.Remote;
import aplicacion.cliente.CallbackClienteP2PInterfaz;
import java.util.ArrayList;
/**
 *
 * @author eliseopitavilarino
 */
public interface ServidorP2PInterfaz extends Remote {
    
    //Método a ejecutar para registrar un usuario nuevo en la base de datos
    public String registrarUsuario(String nombre, String contrasena) throws java.rmi.RemoteException;
    
    //Método para iniciarSeion
    public String iniciarSesion(String nombre, String contrasena) throws java.rmi.RemoteException;
    
    //Método a ejecutar cada vez que se conecte un nuevo cliente (o uno ya registrado para iniciar sesion)
    public void conectarCliente(CallbackClienteP2PInterfaz cliente, String nombre) throws java.rmi.RemoteException ;
            
    //Método a ejecutar cada vez que se desconecte un cliente
    public void desconectar(String nombre) throws java.rmi.RemoteException;
    
    //Método para modificar la contraseña de un usuario
    public String modificarContrasena(String usuario, String nuevaContrasena, 
            String antiguaContrasena) throws java.rmi.RemoteException;
    
    //Método para buscar usuarios que coincidan parcialmente con la cadena de caracteres introducida
    //Devuelve null cuando la cadena es de menos de 4 caracteres
    public  ArrayList<String> buscarUsuarios(String cadena) throws java.rmi.RemoteException;
    
    //Método para enviar una solicitud de amistad
    //Devuelve true si ya existía la solicitud
    public boolean enviarSolicitud(String emisor, String receptor) throws java.rmi.RemoteException;
    
    //Método para obtener las solicitudes pendientes de un usuario
    public ArrayList<String> obtenerSolicitudes(String usuarioReceptor) throws java.rmi.RemoteException;
 
    //Método para aceptar una solicitud de amistad
    //Devuelve la interfaz si está conectado
    public CallbackClienteP2PInterfaz anadirAmistad(String usuario1, String usuario2) throws java.rmi.RemoteException;
    
    //Método para rechazar una solicitud de amistad
    public void rechazarAmistad(String emisor, String receptor) throws java.rmi.RemoteException;
}
