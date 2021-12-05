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
public class DAOSolicitudes {
    
    private Connection con;

    public DAOSolicitudes(Connection con) {
        this.con = con;
    }

    
    public void insertarSolicitud(String emisor, String receptor){
        //Insertamos la solicitud en la base de datos
        String insercion = "insert into solicitudes(emisor, receptor) values('" + emisor + 
                "', '" + receptor + "')";

        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(insercion);
        } catch (SQLException e) {
            System.out.println("Error al insertar solicitud en la BD: " + e.getMessage());
        }
    }
    
}

