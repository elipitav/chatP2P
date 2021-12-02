package aplicacion.servidor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;
/**
 *
 * @author manut
 */
public class Servidor {

    public static void main(String[] args) {
        
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        
        String portNum, registryURL;
        
        try{     
            
            //Leemos el numero de puerto
            //System.out.println("Introduzca el puerto del registro RMI:");
            portNum = "1099";
            int RMIPortNum = Integer.parseInt(portNum);
         
            //Iniciamos el registro
            startRegistry(RMIPortNum);
            
            //Instanciamos el objeto
            ServidorP2PImpl exportedObj = new ServidorP2PImpl();
            
            //Registramos el objeto bajo el nombre "servidorChat"
            registryURL = "rmi://localhost:" + portNum + "/servidorChat";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Servidor registrado.");
            //Lista de nombres actualmente en el registro
            listRegistry(registryURL); 
            
            System.out.println("Servidor listo.");
            
        }// end try
        catch (Exception re) {
           System.out.println("Excepcion en Servidor.main: " + re);
        } // end catch
            

    }

    // Este metodo inicia un registro RMI en el localhost, si no existe
    //ya en el puerto indicado.
    private static void startRegistry(int RMIPortNum)
      throws RemoteException{
      try {
         Registry registry = LocateRegistry.getRegistry(RMIPortNum);
         registry.list( );  // Esta llamada lanzara una excepcion
                            // si el registro no existe ya
        }
        catch (RemoteException e) { 
            //Creamos el registro   
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("Registro RMI creado en el puerto " + RMIPortNum);
        }
    }
    
    // Este metodo lista los nombres registrados
    private static void listRegistry(String registryURL)
       throws RemoteException, MalformedURLException {
         System.out.println("El registro " + registryURL + " contiene: ");
         String [ ] names = Naming.list(registryURL);
         for (int i=0; i < names.length; i++)
            System.out.println(names[i]);
    }
    
}
