/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.danieljuarez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
//import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.danieljuarez.bean.CuentaPorCobrar;
import org.danieljuarez.controller.AdministracionController;
import org.danieljuarez.controller.CargosController;
import org.danieljuarez.controller.ClienteController;
import org.danieljuarez.controller.CuentaPorCobrarController;
import org.danieljuarez.controller.CuentaPorPagarController;
import org.danieljuarez.controller.DepartamentoController;
import org.danieljuarez.controller.EmpleadosController;
import org.danieljuarez.controller.HorariosController;
import org.danieljuarez.controller.LocalesController;
import org.danieljuarez.controller.LoginController;
import org.danieljuarez.controller.MenuPrincipalController;
import org.danieljuarez.controller.ProgramadorController;
import org.danieljuarez.controller.ProveedorController;
import org.danieljuarez.controller.TipoClienteController;
import org.danieljuarez.controller.UsuarioController;

/**
 *
 * @author Daniel Juárez
 */
public class Principal extends Application {
    private final String PAQUETE_VISTA="/org/danieljuarez/view/"; //ruta de las vistas.
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
       this.escenarioPrincipal=escenarioPrincipal;
       this.escenarioPrincipal.setTitle("Kinal Mall 2021");
       //Parent root=FXMLLoader.load(getClass().getResource("/org/danieljuarez/view/MenuPrincipalView.fxml"));
       //Scene escena=new Scene(root);
       escenarioPrincipal.getIcons().add(new Image("/org/danieljuarez/images/portada.png"));
       //escenarioPrincipal.setScene(escena);
       ventanaLogin();
       //ventanaCuentasPorPagar();
       //menuPrincipal();
       escenarioPrincipal.show();
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal=(MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",575,401);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaProgramador(){
        try{
            ProgramadorController programador=(ProgramadorController)cambiarEscena("ProgramadorView.fxml",649,436);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ventanaTipoCliente(){
        try{
            TipoClienteController tipoCliente=(TipoClienteController) cambiarEscena("TipoClienteView.fxml",886,471);
            tipoCliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCliente(){
        try{
            ClienteController cliente=(ClienteController)cambiarEscena("ClientesView.fxml",1200,477);
            cliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ventanaLocales(){
        try{
            LocalesController local=(LocalesController)cambiarEscena("LocalesView.fxml",1039,494);
            local.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void vantanaHorarios(){
        try{
            HorariosController hora=(HorariosController)cambiarEscena("HorariosView.fxml",929,476);
            hora.setEscenarioPrincpal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDepartamento(){
        try{
            DepartamentoController depa=(DepartamentoController)cambiarEscena("DepartamentosView.fxml",805,452);
            depa.setEscenarioPrincpal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCargo(){
        try{
            CargosController cargos=(CargosController)cambiarEscena("CargosView.fxml",755,463);
            cargos.setEscenarioPrincpal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaAdministracion(){
        try{
            AdministracionController admin=(AdministracionController)cambiarEscena("AdministracionView.fxml",990,415);
            admin.setEscenarioPrincipal(this);
        }catch(Exception e){
             e.printStackTrace();
        }
    }
    
    public void ventanProveedor(){
        try{
            ProveedorController proveedor=(ProveedorController) cambiarEscena("ProveedorView.fxml",1000,445);
            proveedor.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }   
    
    public void ventanaUsuario(){
        try{
            UsuarioController usuario=(UsuarioController) cambiarEscena("UsuarioView.fxml",600,400);
            usuario.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ventanaLogin(){
        try{
        LoginController login=(LoginController) cambiarEscena("LoginView.fxml",600,400);
        login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
//    public void ventanaEmpleados(){
//        try{
//            EmpleadosController empleado=(EmpleadosController) cambiarEscena("EmpleadosView.fxml",1350,400);
//            empleado.setEscenarioPrincipal(this);   
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    
//    }
    
    public void ventanaEmpleadoss(){
        try{
            EmpleadosController empleado2=(EmpleadosController) cambiarEscena("Empleados2View.fxml",1318,400);
            empleado2.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
            
    }
    
    public void ventanaCuentasPorCobrar(){
        try{
            CuentaPorCobrarController cobra=(CuentaPorCobrarController) cambiarEscena("CuentasPorCobrarView.fxml",1350,400);
            cobra.setEscenarioPrincpal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ventanaCuentasPorPagar(){
        try{
            CuentaPorPagarController cuentaP=(CuentaPorPagarController)cambiarEscena("CuentasPorPagarView.fxml",1009,460);
            cuentaP.setEscenarioPrincipal(this); 
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    public Initializable cambiarEscena(String fxml,int ancho, int alto) throws IOException{
        Initializable resul=null;
        FXMLLoader cargadorFXML=new FXMLLoader(); //  =  org/danieljuarez/view/MenuPrincipalView.fxm
        InputStream archivo=Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena=new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resul=(Initializable)cargadorFXML.getController();
        return resul;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    
    
}
