package org.danieljuarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.danieljuarez.bean.Usuario;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class UsuarioController implements Initializable{

    private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    private enum operaciones {NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    @FXML private TextField txtCodUsuario;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtApellidoUsuario;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtContra;
    @FXML private Button btnNuevo;
    @FXML private Button btnCancelar;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
    public void ativarControles(){
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuario.setEditable(true);
        txtContra.setEditable(true);
    
    }
    
    public void desactivarControles(){
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuario.setEditable(false);
        txtContra.setEditable(false);
        
    
    }
    
    public void limpiarControles(){
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuario.clear();
        txtContra.clear();
    
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                ativarControles();
                btnNuevo.setText("Guardar");
                btnCancelar.setText("Calcelar");
                tipoDeOperacion=operaciones.GUARDAR;
                
            }break;
            case GUARDAR:{
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnCancelar.setText("Calcelar");
                tipoDeOperacion=operaciones.NINGUNO;
            
            }break;
        }
    
    }
    
    public void guardar(){
        Usuario registro=new Usuario();
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidoUsuario(txtApellidoUsuario.getText());
        registro.setUsuarioLogin(txtUsuario.getText());
        registro.setContrasena(txtContra.getText());
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarUsuario(?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreUsuario());
            procedimiento.setString(2, registro.getApellidoUsuario());
            procedimiento.setString(3, registro.getUsuarioLogin());
            procedimiento.setString(4, registro.getContrasena());
            procedimiento.execute();
            ventanaLogin();
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
    
    
}
