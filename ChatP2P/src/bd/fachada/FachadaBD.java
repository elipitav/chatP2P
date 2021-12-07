package bd.fachada;

import aplicacion.fachada.FachadaAplicacion;
import aplicacion.servidor.ServidorP2PImpl;
import bd.DAO.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author manut
 */
public class FachadaBD {
    
    private ServidorP2PImpl servidor;
    private DAOUsuarios du;
    private DAOAmistades da;
    private DAOSolicitudes ds;
    private Connection con;

    public FachadaBD(ServidorP2PImpl servidor) {
        
        conectarBD();
        
        this.servidor = servidor;
        this.du = new DAOUsuarios(con);
        this.da = new DAOAmistades(con);
        this.ds = new DAOSolicitudes(con);
    }
    
    public void conectarBD(){
        
        try {
            // Registramos el driver de PostgresSQL
            try { 
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            // Conectamos con la base de datos
            this.con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/servidor_chat",
                    "postgres", "timiraos");
            

        } catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }
    
    public void insertarUsuario(String nombre, String contrasena){
        this.du.insertarUsuario(nombre, contrasena);
    }

    public String obtenerContrasenaUsuario(String usuario){
        return this.du.obtenerContrasenaUsuario(usuario);
    }
    
    public boolean usuarioExiste(String nombre){
        return this.du.usuarioExiste(nombre);
    }
    
    public ArrayList<String> obtenerAmigos(String nombre){
        return this.da.obtenerAmigos(nombre);
    }
    
    public void modificarContrasena(String usuario, String nuevaContrasena){
        this.du.modificarContrasena(usuario, nuevaContrasena);
    }
    
    public ArrayList<String> buscarUsuarios(String cadena){
        return this.du.buscarUsuarios(cadena);
    }
    
    public boolean insertarSolicitud(String emisor, String receptor){
        return this.ds.insertarSolicitud(emisor, receptor);
    }
    
    public ArrayList<String> obtenerSolicitudes(String usuarioReceptor){
        return this.ds.obtenerSolicitudes(usuarioReceptor);
    }
    
    public void anadirAmistad(String usuario1, String usuario2){
        this.da.anadirAmistad(usuario1, usuario2);
    }
    
    public void eliminarSolicitud(String emisor, String receptor){
        this.ds.eliminarSolicitud(emisor, receptor);
    }
    
    
}
