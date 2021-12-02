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
        
    
    
}
