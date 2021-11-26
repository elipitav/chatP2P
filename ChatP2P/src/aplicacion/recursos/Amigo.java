/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion.recursos;

import aplicacion.cliente.CallbackClienteP2PInterfaz;

/**
 *
 * @author eliseopitavilarino
 */
public class Amigo {
    private String nombre;
    private String estado;
    private CallbackClienteP2PInterfaz interfaz;
    private String chat;

    public Amigo(String nombre, String estado, CallbackClienteP2PInterfaz interfaz) {
        this.nombre = nombre;
        this.estado = estado;
        this.interfaz = interfaz;
        this.chat="";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CallbackClienteP2PInterfaz getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(CallbackClienteP2PInterfaz interfaz) {
        this.interfaz = interfaz;
    }

    public void anadirMensaje(String emisor, String mensaje){
        //Añadimos el mensaje al textArea
        this.chat.concat(emisor+": "+mensaje+"\n");
    }
    
    public void enviarMensaje(String receptor, String mensaje){
        try{
            //Obtenemos la interfaz remota del amigo y la usamos para enviar el mensaje
            this.interfaz.recibirMensaje(this.nombre, mensaje);
        }
        catch(Exception e){
            System.out.println("Excepcion en el cliente: " + e);
        }
    }
    
}
