/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package aplicacion.cliente;

import java.util.ArrayList;

/**
 *
 * @author eliseopitavilarino
 */

public interface CallbackClienteP2PInterfaz extends java.rmi.Remote{
    
    //Método para recibir la lista de amigos
    public void recibirListaAmigos(ArrayList<String> amigos) throws java.rmi.RemoteException;
    
    //Método para informar a un cliente de que un amigo se ha conectado
    public void amigoConectado(CallbackClienteP2PInterfaz amigo, String nombre) throws java.rmi.RemoteException;
    
    //Método para informar a un cliente de que un amigo se ha desconectado
    public void amigoDesconectado(String nombre) throws java.rmi.RemoteException;
    
    //Método para recibir un mensaje
    public void recibirMensaje(String emisor, String mensaje) throws java.rmi.RemoteException;

    //Método para enviar una solicitud de amistad
    public void nuevaSolicitud(String emisor) throws java.rmi.RemoteException;
}
