
package org.danieljuarez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.danieljuarez.system.Principal;

public class ProgramadorController  implements Initializable{
    private Principal escenarioPrincipal;
    @FXML Button btnProgramador;
    @FXML Button btnAdmin;
    @FXML Label lblNombres;
    @FXML Label lblApellidos;
    @FXML Label lblTitulo;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }
    
    public void opcioness(ActionEvent event ){
        if(event.getSource()==btnProgramador){
            lblNombres.setText("Daniel Alejandro");
            lblApellidos.setText("Juárez García");
            lblTitulo.setText("Kinal Mall");
        }
        if(event.getSource()==btnAdmin){
            lblNombres.setText("Fundación");
            lblApellidos.setText("Kinal");
            lblTitulo.setText("2021");
        }
    }
   
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    
}
