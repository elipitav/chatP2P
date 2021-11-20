/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package aplicacion.cliente;

/**
 *
 * @author eliseopitavilarino
 */

public interface CallbackClienteP2PInterfaz {
    
    //Método para informar a un cliente de que un amigo se ha conectado
    public void amigoConectado(CallbackClienteP2PInterfaz amigo, String nombre) throws java.rmi.RemoteException;
    
    //Métooo para informar a un cliente de que un amigo se ha desconectado
    public void amigoDesconectado(String nombre) throws java.rmi.RemoteException;
}
