/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bd.DAO;
import java.sql.*;

/**
 *
 * @author manut
 */
public class DAOUsuarios {
    
    private Connection con;

    public DAOUsuarios(Connection con) {
        this.con = con;
    }
    
    public void insertarUsuario(String nombre, String contrasena){
        //Insertamos el usuario en la base de datos
        String insercion = "INSERT INTO usuarios(nombre, contrasena) values('" + 
                nombre + "','" + contrasena + "')";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(insercion);
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario en la BD: " + e.getMessage());
        }
    }
    
    public String obtenerContrasenaUsuario(String usuario){
        String contrasena = "";
        //Obtenemos la contraseña del usuario en concreto
        String consulta = "select contrasena from usuarios where nombre = '" + usuario + "'";
        try(Statement stmt = con.createStatement()){
            ResultSet rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                contrasena = rs.getString("contrasena");
            }
        } catch (SQLException e){
            System.out.println("Error al consultar la contrasena del usuario: " + e.getMessage());
        }
        return contrasena;
    }
    
    public boolean usuarioExiste(String nombre){
        boolean existe=false;
        //Intentamos obtener el usuario con el nombre introducido
        String consulta = "select nombre from usuarios where nombre = '" + nombre + "'";
        try(Statement stmt = con.createStatement()){
            ResultSet rs = stmt.executeQuery(consulta);
            //Si existe alguna tupla indicamos que el usuario ya existe
            if(rs.next()){
                existe=true;
            }
        } catch (SQLException e){
            System.out.println("Error al comprobar la existencia del usuario: " + e.getMessage());
        }
        return existe;
    }
    
    public void modificarContrasena(String usuario, String nuevaContrasena){
        String actualizacion = "update usuarios set contrasena = '" + nuevaContrasena + 
                "' where nombre = '" + usuario + "'";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(actualizacion);
        } catch (SQLException e) {
            System.out.println("Error al modificar la contraseña: " + e.getMessage());
        }
    }
        
    
    
}
