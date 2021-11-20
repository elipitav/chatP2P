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
    
    //Método a ejecutar cada vez que se conecte un nuevo cliente
    public void registrarCliente(CallbackClienteP2PInterfaz cliente, String nombre);
    
    //Método a ejecutar cada vez que se desconecte un cliente
    public void eliminarCliente(String nombre);
}
