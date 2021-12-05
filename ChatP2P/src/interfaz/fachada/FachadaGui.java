package interfaz.fachada;

import aplicacion.fachada.FachadaAplicacion;
import aplicacion.recursos.Amigo;
import interfaz.controladores.Controlador;
import interfaz.controladores.VAccederController;
import interfaz.controladores.VPrincipalController;
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
    
    public void conectarCliente(String nombre){
        fa.conectarCliente(nombre);
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
    
    //Método para añadir una notificación
    public void anadirNotificacion(String notificacion){
        this.vp.anadirNotificacion(notificacion);
    }
    
    //Método para desconectarse
    public void desconectar(){
        this.fa.desconectar();
    }
    
    //Método para modificar la contraseña de un usuario
    public void modificarContrasena(String usuario, String nuevaContrasena){
        this.fa.modificarContrasena(usuario, nuevaContrasena);
    }

}
