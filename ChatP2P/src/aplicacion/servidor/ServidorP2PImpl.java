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

    public ServidorP2PImpl() throws RemoteException {
        super();
        clientesConectados = new HashMap<>();
        fbd = new FachadaBD(this);
    }

    @Override
    public String registrarUsuario(String nombre, String contrasena) throws java.rmi.RemoteException {

        //Comprobamos si el usuario ya existe antes de insertarlo
        if (!fbd.usuarioExiste(nombre)) {
            //Si no existe, lo añadimos el usuario nuevo a la base de datos
            fbd.insertarUsuario(nombre, contrasena);

            System.out.println("Nuevo cliente registrado: " + nombre);

            return "Cliente registrado";
        } else {
            return "El usuario ya existe";
        }
    }

    @Override
    public String iniciarSesion(String nombre, String contrasena) {
        //Comprobamos si el usuario existe
        if (!fbd.usuarioExiste(nombre)) {
            return "El usuario no existe";
        }
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(nombre);
        //Comprobamos si coincide
        if (contra.equals(contrasena)) {
            synchronized (clientesConectados) {
                //Y comprobamos si el usuario está ya conectado
                if (clientesConectados.containsKey(nombre)) {
                    System.out.println("Usuario ya conectado");
                    return "Usuario ya conectado";
                } else {
                    System.out.println("Sesión iniciada");
                    return "Sesión iniciada";
                }
            }
        } else {
            System.out.println("Error en inicio de sesión: contraseña incorrecta");
            return "Contraseña incorrecta";
        }
    }

    @Override
    public void conectarCliente(CallbackClienteP2PInterfaz cliente, String nombre, String contrasena) throws java.rmi.RemoteException {
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(nombre);
        //Comprobamos si coincide
        if (contra.equals(contrasena)) {
            //Obtenemos los amigos del usuario
            ArrayList<String> amigos = fbd.obtenerAmigos(nombre);
            cliente.recibirListaAmigos(amigos);

            //Avisamos a los amigos conectados que el cliente se ha conectado
            //Y avisamos al nuevo cliente conectado con los amigos conectados
            synchronized (this.clientesConectados) {
                for (Map.Entry<String, CallbackClienteP2PInterfaz> entry : this.clientesConectados.entrySet()) {
                    if (amigos.contains(entry.getKey())) {
                        entry.getValue().amigoConectado(cliente, nombre);
                        cliente.amigoConectado(entry.getValue(), entry.getKey());
                    }
                }

                //Añadimos al cliente al hashmap de clientes conectados
                clientesConectados.put(nombre, cliente);
            }
        }
    }

    @Override
    public void desconectar(String nombre, String contrasena) throws java.rmi.RemoteException {
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(nombre);
        //Comprobamos si coincide
        if (contra.equals(contrasena)) {
            //Eliminamos al cliente del hashmap de clientes conectados
            synchronized (this.clientesConectados) {
                if (clientesConectados.remove(nombre) != null) {
                    System.out.println("Cliente desconectado: " + nombre);
                    //Avisamos a todos los amigos conectados
                    ArrayList<String> amigos = fbd.obtenerAmigos(nombre);
                    for (String amigo : amigos) {
                        if (this.clientesConectados.containsKey(amigo)) {
                            this.clientesConectados.get(amigo).amigoDesconectado(nombre);
                        }
                    }
                } else {
                    System.out.println("Error en desconexión: el cliente " + nombre + "no estaba conectado");
                }
            }
        }
    }

    @Override
    public String modificarContrasena(String usuario, String nuevaContrasena,
            String antiguaContrasena) throws java.rmi.RemoteException {
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(usuario);
        //Y comprobamos que es correcta
        if (contra.equals(antiguaContrasena)) {
            this.fbd.modificarContrasena(usuario, nuevaContrasena);
            return "Contraseña modificada con éxito";
        } else {
            return "Contraseña antigua incorrecta";
        }
    }

    @Override
    public synchronized ArrayList<String> buscarUsuarios(String cadena) throws java.rmi.RemoteException {
        return this.fbd.buscarUsuarios(cadena);
    }

    @Override
    public synchronized ArrayList<String> obtenerSolicitudes(String usuarioReceptor, String contrasena) throws java.rmi.RemoteException {
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(usuarioReceptor);
        //Comprobamos si coincide
        if (contra.equals(contrasena)) {
            return this.fbd.obtenerSolicitudes(usuarioReceptor);
        }
        return null;
    }

    @Override
    public boolean enviarSolicitud(String emisor, String receptor, String contrasena) throws java.rmi.RemoteException {
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(emisor);
        //Comprobamos si coincide
        if (contra.equals(contrasena)) {
            boolean existe = this.fbd.insertarSolicitud(emisor, receptor);

            if (!existe) {
                synchronized (clientesConectados) {
                    //Si el receptor está conectado, le notificamos la solicitud y actualizamos su tabla de solicitudes
                    if (this.clientesConectados.keySet().contains(receptor)) {
                        this.clientesConectados.get(receptor).nuevaSolicitud(emisor);
                    }
                }
            }

            return existe;
        }
        return false;
    }

    @Override
    public CallbackClienteP2PInterfaz anadirAmistad(String emisor, String receptor, String contrasena) throws java.rmi.RemoteException {
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(receptor);
        //Comprobamos si coincide
        if (contra.equals(contrasena)) {
            this.fbd.anadirAmistad(emisor, receptor);
            synchronized (clientesConectados) {
                if (this.clientesConectados.containsKey(emisor)) {
                    return this.clientesConectados.get(emisor);
                }
            }
            
        }
        return null;
    }

    @Override
    public void rechazarAmistad(String emisor, String receptor, String contrasena) throws RemoteException {
        //Obtenemos la contraseña
        String contra = fbd.obtenerContrasenaUsuario(receptor);
        //Comprobamos si coincide
        if (contra.equals(contrasena)) {
            this.fbd.eliminarSolicitud(emisor, receptor);
            synchronized (clientesConectados) {
                if (this.clientesConectados.containsKey(emisor)) {
                    this.clientesConectados.get(emisor).respuestaSolicitud(false, receptor);
                }
            }
        }
    }
}
