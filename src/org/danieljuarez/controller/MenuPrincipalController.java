
package org.danieljuarez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.danieljuarez.system.Principal;

public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    
    public void ventanaAdministracion(){
        escenarioPrincipal.ventanaAdministracion();
    }
    
    public void ventanaTipoCliente(){
        escenarioPrincipal.ventanaTipoCliente();
    }
    
    public void ventanaDepartamento(){
        escenarioPrincipal.ventanaDepartamento();
    }
    
    public void ventanaLocales(){
        escenarioPrincipal.ventanaLocales();
    }
    
    public void ventanaCargos(){
        escenarioPrincipal.ventanaCargo();
    }
    
    public void ventanaHorarios(){
        escenarioPrincipal.vantanaHorarios();
    }
    
    public void ventanaCliente(){
        escenarioPrincipal.ventanaCliente();
    }
    
    public void ventanaProveedor(){
        escenarioPrincipal.ventanProveedor();
    
    }
    
//    public void ventanaCuentasPorPagar(){
//        escenarioPrincipal.ventanaCuentasPorPagar();
//    }
    
//    public void ventanaEmpleado(){
//        escenarioPrincipal.ventanaEmpleados();
//    }
    
//    public void ventanaEmpleados(){
//        escenarioPrincipal.ventanaEmpleadoss();
//    }
    
//    public void ventanaCuentaPorCobrar(){
//        escenarioPrincipal.ventanaCuentasPorCobrar();
//    }
    
    public void ventanaUsuario(){
        escenarioPrincipal.ventanaUsuario();
    }
    
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
   
}
