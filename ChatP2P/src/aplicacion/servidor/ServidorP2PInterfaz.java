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
    
    //Método a ejecutar para registrar un usuario nuevo en la base de datos, o para comprobar la contraseña
    //de uno ya existente
    public String registrarUsuario(String nombre, String contrasena) throws java.rmi.RemoteException;
    
    //Método a ejecutar cada vez que se conecte un nuevo cliente (o uno ya registrado para iniciar sesion)
    public void registrarCliente(CallbackClienteP2PInterfaz cliente, String nombre) throws java.rmi.RemoteException ;
    
    //Método a ejecutar cada vez que se desconecte un cliente
    public void desconectar(String nombre) throws java.rmi.RemoteException;
 
}
