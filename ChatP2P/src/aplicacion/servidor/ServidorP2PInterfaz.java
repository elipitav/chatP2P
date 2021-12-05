/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package aplicacion.servidor;

import java.rmi.Remote;
import aplicacion.cliente.CallbackClienteP2PInterfaz;
/**
 *
 * @author eliseopitavilarino
 */
public interface ServidorP2PInterfaz extends Remote {
    
    //Método a ejecutar para registrar un usuario nuevo en la base de datos
    public String registrarUsuario(String nombre, String contrasena) throws java.rmi.RemoteException;
    
    //Método para iniciarSeion
    public String iniciarSesion(String nombre, String contrasena) throws java.rmi.RemoteException;
    
    //Método para saber si un cliente está conectado
    public boolean clienteConectado(String nombre) throws java.rmi.RemoteException;
    
    //Método a ejecutar cada vez que se conecte un nuevo cliente (o uno ya registrado para iniciar sesion)
    public void conectarCliente(CallbackClienteP2PInterfaz cliente, String nombre) throws java.rmi.RemoteException ;
            
    //Método a ejecutar cada vez que se desconecte un cliente
    public void desconectar(String nombre) throws java.rmi.RemoteException;
    
    //Método para modificar la contraseña de un usuario
    public void modificarContrasena(String usuario, String nuevaContrasena) throws java.rmi.RemoteException;
 
}
