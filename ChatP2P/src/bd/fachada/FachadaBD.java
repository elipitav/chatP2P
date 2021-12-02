package bd.fachada;

import aplicacion.fachada.FachadaAplicacion;
import aplicacion.servidor.ServidorP2PImpl;
import bd.DAO.*;
import java.sql.*;

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
    
    
    
}
