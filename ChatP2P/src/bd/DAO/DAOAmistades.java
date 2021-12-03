/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bd.DAO;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author manut
 */
public class DAOAmistades {
    
    private Connection con;

    public DAOAmistades(Connection con) {
        this.con = con;
    }
    
    public ArrayList<String> obtenerAmigos(String nombre){
        ArrayList<String> amigos = new ArrayList<>();
        //Obtenemos la contraseña del usuario en concreto
        String consulta = "select usuario2 from amistades where usuario1 = '" + nombre + "'";
        try(Statement stmt = con.createStatement()){
            ResultSet rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                amigos.add(rs.getString("usuario2"));
            }
        } catch (SQLException e){
            System.out.println("Error al consultar la contrasena del usuario: " + e.getMessage());
        }
        return amigos;
    }
    

    
}
