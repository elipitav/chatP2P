/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion.cliente;

import aplicacion.fachada.FachadaAplicacion;
import aplicacion.recursos.Amigo;
import aplicacion.servidor.ServidorP2PInterfaz;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author eliseopitavilarino
 */
public class CallbackClienteP2PImpl extends UnicastRemoteObject implements CallbackClienteP2PInterfaz{
    
    //Nombre del cliente
    private String nombre;
    //Interfaz del servidor
    private ServidorP2PInterfaz servidor;
    //Amigos del cliente
    private HashMap<String,Amigo> amigos;
    //Fachada aplicación
    private FachadaAplicacion fa;
    
    
    public CallbackClienteP2PImpl(String nombre, ServidorP2PInterfaz servidor, FachadaAplicacion fa) throws RemoteException{
        super();
        this.nombre = nombre;
        this.fa = fa;
        this.servidor = servidor;
        this.amigos = new HashMap<>();
    }

    @Override
    public void recibirListaAmigos(ArrayList<String> amigos) throws RemoteException {
        try{
            for(String nombre: amigos){
                Amigo amigo = new Amigo(nombre);
                this.amigos.put(nombre, amigo);
                this.fa.nuevoAmigo(amigo);
            }
        } catch(Exception ex){
            System.out.println("Excepción en el cliente: "+ex.getMessage());
        }
    }
    
    @Override
    public synchronized void amigoConectado(CallbackClienteP2PInterfaz interfaz, String nombre) throws java.rmi.RemoteException {
        //Añadimos al cliente al hashmap de amigos si no estaba ya. Si lo estaba cambiamos su estado
        if(this.amigos.containsKey(nombre)){
            this.amigos.get(nombre).setEstado("En linea");
            this.amigos.get(nombre).setInterfaz(interfaz);
            //Indicamos que un amigo se ha conectado para que se informe a la interfaz
            this.fa.amigoConectado(nombre);
        }
        else{
            Amigo amigo = new Amigo(nombre,"En linea",interfaz);
            this.amigos.put(nombre, amigo);
            //Indicamos que un nuevo amigo se ha conectado para que se informe a la interfaz
            this.fa.nuevoAmigo(amigo);
            this.fa.amigoConectado(nombre);
        }
    }

    @Override
    public synchronized void amigoDesconectado(String nombre) throws java.rmi.RemoteException {
       //Indicamos que el usuario está desconectado
       this.amigos.get(nombre).setEstado("Desconectado");
       
       //Indicamos que un amigo se ha desconectado para que se informe a la interfaz
       this.fa.amigoDesconectado(nombre);
    }

    @Override
    public void recibirMensaje(String emisor, String mensaje) throws java.rmi.RemoteException{
        //Obtener el mensaje y mostrarlo por pantalla
        fa.recibirMensaje(emisor, mensaje);
        //Añadimos el mensaje al chat con ese amigo
        this.amigos.get(emisor).anadirMensaje(emisor, mensaje);
    }
    
    public void enviarMensaje(String receptor, String mensaje){
        //Enviamos un mensaje al amigo indicado
        this.amigos.get(receptor).enviarMensaje(this.nombre,mensaje);
    }
    
    //Método para desconectarse
    public void desconectar(){
        try {
            //Informamos al servidor que nos hemos desconectado
            this.servidor.desconectar(this.nombre);
        } catch (Exception e) {
            System.out.println("Excepcion en el cliente: " + e);
        }
    }
     
}
