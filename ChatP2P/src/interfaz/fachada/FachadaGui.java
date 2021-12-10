package interfaz.fachada;

import aplicacion.fachada.FachadaAplicacion;
import aplicacion.recursos.Amigo;
import interfaz.controladores.Controlador;
import interfaz.controladores.VAccederController;
import interfaz.controladores.VPrincipalController;
import java.util.ArrayList;
import java.util.Collection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FachadaGui {

    FachadaAplicacion fa;
    VPrincipalController vp;

    public FachadaGui(FachadaAplicacion fa) {
        this.fa=fa;
    }

    public void iniciarVista(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(FachadaGui.class.getResource("/interfaz/ventanas/VAcceder.fxml"));
        Pane root = (Pane) loader.load();

        //Collemos o controlador de Acceder
        VAccederController contAcceder = loader.getController();
        contAcceder.setFgui(this);

        primaryStage.setTitle("ChatP2P");
        primaryStage.setScene(new Scene(root));

        contAcceder.setVentana(primaryStage);
        //Controlador.setStageIcon(primaryStage);
        primaryStage.show();

    }
    
    public String registrarUsuario(String nombre, String contrasena){
        return fa.registrarUsuario(nombre, contrasena);
    }
    
    public String iniciarSesion(String nombre, String contrasena){
        return fa.iniciarSesion(nombre, contrasena);
    }
    
    public void conectarCliente(String nombre, String contrasena){
        fa.conectarCliente(nombre, contrasena);
    }
    
    public void enviarMensaje(String receptor, String mensaje){
        fa.enviarMensaje(receptor,mensaje);
    }
    
    public void recibirMensaje(String emisor, String mensaje){
        vp.recibirMensaje(emisor, mensaje);
    }

    public VPrincipalController getVp() {
        return vp;
    }

    public void setVp(VPrincipalController vp) {
        this.vp = vp;
    }
    
    //Método para añadir amigo a la tabla
    public void nuevoAmigo(Amigo amigo){
        this.vp.nuevoAmigo(amigo);
    }
    
    //Método para indicar que un amigo se ha conectado
    public void amigoConectado(String amigo){
        this.vp.amigoConectado(amigo);
    }
    
    //Método para indicar que un amigo se ha desconectado
    public void amigoDesconectado(String nombre){
        //Llamamos a la interfaz para indicar que un amigo se ha desconectado
        this.vp.amigoDesconectado(nombre);
    }
    
    //Método para añadir una solicitud de amistad
    public void nuevaSolicitud(String emisor){
        this.vp.nuevaSolicitud(emisor);
    }
    
    //Método para añadir una notificación
    public void anadirNotificacion(String notificacion){
        this.vp.anadirNotificacion(notificacion);
    }
    
    //Método para desconectarse
    public void desconectar(){
        this.fa.desconectar(this.vp.getContrasena());
    }
    
    //Método para modificar la contraseña de un usuario
    public String modificarContrasena(String usuario, String nuevaContrasena, String antiguaContrasena){
        return this.fa.modificarContrasena(usuario, nuevaContrasena, antiguaContrasena);
    }
    
    //Método para buscar usuarios
    public ArrayList<String> buscarUsuarios(String cadena){
        return this.fa.buscarUsuarios(cadena);
    }
    
    //Método para enviar solicitudes de amistad
    public boolean enviarSolicitud(String emisor, String receptor, String contrasena){
        return this.fa.enviarSolicitud(emisor, receptor, contrasena);
    }
    
    //Método para actualizar la lista de solicitudes pendientes de un usuario
    public void actualizarSolicitudes(ArrayList<String> emisores){
        this.vp.actualizarSolicitudes(emisores);
    }
    
    //Método para aceptar una solicitud de amistad
    public void anadirAmistad(String emisor, String contrasena){
        this.fa.anadirAmistad(emisor, contrasena);
    }
    
    //Método para rechazar una solicitud de amistad
    public void rechazarAmistad(String emisor, String contrasena){
        this.fa.rechazarAmistad(emisor, contrasena);
    }

}
