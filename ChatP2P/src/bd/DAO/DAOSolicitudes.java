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
public class DAOSolicitudes {
    
    private Connection con;

    public DAOSolicitudes(Connection con) {
        this.con = con;
    }

    
    public boolean insertarSolicitud(String emisor, String receptor){
        boolean existe=false;
        //Primero comprobamos si esa solicitud ya existía
        String consulta = "select * from solicitudes where emisor = '" + emisor +
                "' and receptor = '" + receptor + "'";
        try(Statement stmt = con.createStatement()){
            ResultSet rs = stmt.executeQuery(consulta);
            //Si existe alguna tupla indicamos que la solicitud ya existe
            if(rs.next()){
                existe=true;
            }
        } catch (SQLException e){
            System.out.println("Error al comprobar la existencia de la solicitud: " + e.getMessage());
        }
            
        if(!existe){
            //Insertamos la solicitud en la base de datos
            String insercion = "insert into solicitudes(emisor, receptor) values('" + emisor + 
                    "', '" + receptor + "')";

            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate(insercion);
            } catch (SQLException e) {
                System.out.println("Error al insertar solicitud en la BD: " + e.getMessage());
            }
        }
        
        return existe;
    }
    
    public ArrayList<String> obtenerSolicitudes(String usuarioReceptor){
        ArrayList<String> listaEmisores = new ArrayList<>();
        //Obtenemos la contraseña del usuario en concreto
        String consulta = "select emisor from solicitudes where receptor = '" + usuarioReceptor + "'";
        try(Statement stmt = con.createStatement()){
            ResultSet rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                listaEmisores.add(rs.getString("emisor"));
            }
        } catch (SQLException e){
            System.out.println("Error al consultar las solicitudes: " + e.getMessage());
        }
        return listaEmisores;
    }
    
    public void eliminarSolicitud(String emisor, String receptor){
        //Eliminamos las tuplas de la tabla de solicitudes
        String eliminacion = "delete from solicitudes where emisor = '" + emisor + 
                "' and receptor = '" + receptor + "' or emisor = '" + receptor + 
                "' and receptor = '" + emisor + "'";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(eliminacion);
        } catch (SQLException e) {
            System.out.println("Error al eliminar las solicitudes: " + e.getMessage());
        }
    }
    
}

