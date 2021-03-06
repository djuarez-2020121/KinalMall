
package org.danieljuarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danieljuarez.bean.Administracion;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.report.GenerarReporte;
import org.danieljuarez.system.Principal;

public class AdministracionController implements Initializable {
    private Principal escenarioPrincipal;
    
    private enum operaciones {NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    private ObservableList <Administracion> listaAdministracion;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoAdministracion;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TableView tblAdministracion;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }
    
    public void cargarDatos(){
        tblAdministracion.setItems(getAdministracion());
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Administracion,String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Administracion,String>("telefono"));    
    }

    
    public ObservableList<Administracion>getAdministracion(){
        
        ArrayList<Administracion> lista=new ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdinistracion()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                                                resultado.getString("direccion"),
                                                resultado.getString("telefono") ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
                
        return listaAdministracion=FXCollections.observableArrayList(lista);
    }

 
    
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Calcelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/danieljuarez/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/danieljuarez/images/cancelar.png"));
                tipoDeOperacion=operaciones.GUARDAR;
            }break;
            case GUARDAR:{
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/danieljuarez/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/danieljuarez/images/eliminar.png"));
                tipoDeOperacion=operaciones.NINGUNO;
                cargarDatos();
            }break;
        }
    }
    
    public void guardar(){
        Administracion registro=new Administracion();
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarAdministracion(?,?)}");
            procedimiento.setString(1,registro.getDireccion());
            procedimiento.setString(2,registro.getTelefono());
            procedimiento.execute();
            listaAdministracion.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void eliminar(){
           switch(tipoDeOperacion){
               case GUARDAR:{
                   desactivarControles();
                   limpiarControles();
                   btnNuevo.setText("Nuevo");
                   btnEliminar.setText("Eliminar");
                   imgNuevo.setImage(new Image("/org/danieljuarez/images/agregar.png"));
                   imgEliminar.setImage(new Image("/org/danieljuarez/images/eliminar.png"));
                   btnEditar.setDisable(false);
                   btnReporte.setDisable(false);
                   tipoDeOperacion=operaciones.NINGUNO;
                   
               }break;
               default: //Para seleccionar un dato en la tabla de Administracin la tabla de datos
                        //Confirmar si desea elimnar o no "showConfirmDialog"
                   if(tblAdministracion.getSelectionModel().getSelectedItem()!=null){
                       int respuesta=JOptionPane.showConfirmDialog(null,"??Esta seguro de eliminar el registro?","Eliminar Administraci??n",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                       if(respuesta==JOptionPane.YES_NO_OPTION){
                           try{
                               PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarAdministracion(?)}");
                               procedimiento.setInt(1,((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
                               procedimiento.execute();
                               listaAdministracion.remove(tblAdministracion.getSelectionModel().getSelectedIndex());
                               limpiarControles();
                           }catch(Exception e){
                               e.printStackTrace();
                           }
                       }
                    }else{
                       JOptionPane.showMessageDialog(null,"Debe de seleccionar un elemento de la tabla de contenido");
                    }
           }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
              if(tblAdministracion.getSelectionModel().getSelectedItem()!=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieljuarez/images/actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieljuarez/images/cancelar.png"));
                    activarControles();
                    tipoDeOperacion=operaciones.ACTUALIZAR;
                }else
                   JOptionPane.showMessageDialog(null,"Debe de seleccionar un elemento de la tabla de contenido");
                 
            }break;
            case ACTUALIZAR:{
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/danieljuarez/images/editar.png"));
                imgReporte.setImage(new Image("/org/danieljuarez/images/reporte.png"));
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion=operaciones.NINGUNO;
            }break;
           
           
        }
    
    }
    
    public void cancelar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblAdministracion.getSelectionModel().getSelectedItem()!=null){
                    tipoDeOperacion=operaciones.ACTUALIZAR;
                }
                imprimirReporte();
            }break;
            case ACTUALIZAR:{
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/danieljuarez/images/editar.png"));
                imgReporte.setImage(new Image("/org/danieljuarez/images/reporte.png"));
                limpiarControles();
                desactivarControles();
                //codAdministracion
                
            }
        }
    }
    
//    public void reporte(){
//        switch(tipoDeOperacion){
//            case ACTUALIZAR:{
//            
//            }break;
//            case NINGUNO:{
//                imprimirReporte();
//                
//            }break;
//        }
//    
//    }
    
    public void imprimirReporte(){
        Map parametros=new HashMap();
        parametros.put("codigoAdministracion", null);
        GenerarReporte.mostrarReporte("ReporteAdministracion.jasper", "Reporte Datos Administraci??n", parametros);
        
    
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarAdministracion(?,?,?)}");
            Administracion registro=(Administracion)tblAdministracion.getSelectionModel().getSelectedItem();
            registro.setDireccion(txtDireccion.getText());
            registro.setTelefono(txtTelefono.getText());
            procedimiento.setInt(1,registro.getCodigoAdministracion());
            procedimiento.setString(2,registro.getDireccion());
            procedimiento.setString(3,registro.getTelefono());
            procedimiento.execute();
            cargarDatos();
            tipoDeOperacion = operaciones.NINGUNO;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarElemento(){
        if(tblAdministracion.getSelectionModel().getSelectedItem()==null){
           JOptionPane.showMessageDialog(null,"Debe de seleccionar un elemento de la tabla de contenido.");
        }else{
           txtCodigoAdministracion.setText(String.valueOf(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
           txtDireccion.setText(String.valueOf(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getDireccion()));
           txtTelefono.setText(String.valueOf(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getTelefono()));   
        }
    }
 
    public void desactivarControles(){
        txtCodigoAdministracion.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
    }
    public void activarControles(){
        txtCodigoAdministracion.setEditable(false);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
    }
    public void limpiarControles(){
        txtCodigoAdministracion.clear();
        txtDireccion.clear();
        txtTelefono.clear();
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
