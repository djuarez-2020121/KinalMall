
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
import org.danieljuarez.bean.TipoCliente;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.report.GenerarReporte;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class TipoClienteController implements Initializable {
    //1....
    private Principal escenarioPrincipal;
    
    //6....
    private enum operaciones {NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    //9........
    private ObservableList<TipoCliente> listaTipoCliente;
    
    //4..........
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoTipoCliente;
    @FXML private TextField txtDireccion;
    @FXML private TableView tblTipoCliente;
    @FXML private TableColumn colCodigoTipoCliente;
    @FXML private TableColumn colDescripcion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       //8....
        cargarDatos();
    }
    
    //8....
    public void cargarDatos(){
        tblTipoCliente.setItems(getTipoCliente());
        colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoCliente,Integer>("codigoTipoCliente"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoCliente,String>("descripcion"));
        
    }
    
    //10.......
    public ObservableList<TipoCliente>getTipoCliente(){
        ArrayList<TipoCliente>lista=new ArrayList<TipoCliente>();
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoCliente()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoCliente(resultado.getInt("codigoTipoCliente"),resultado.getString("descripcion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaTipoCliente=FXCollections.observableArrayList(lista);
    }
    
    //7......
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Calcenlar");
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
    
    //11.......
    public void guardar(){
        TipoCliente registro=new TipoCliente();
        registro.setDescripcion(txtDireccion.getText());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoCliente(?)}");
            procedimiento.setString(1,registro.getDescripcion());
            procedimiento.execute();
            listaTipoCliente.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //12......
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
           default:
               if(tblTipoCliente.getSelectionModel().getSelectedItem()!=null){
                   int respuesta=JOptionPane.showConfirmDialog(null,"¿Esta seguro de eliminar el registro?","Eliminar Un Tipo Cliente",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                  if(respuesta==JOptionPane.YES_NO_OPTION){
                      try{
                          PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoCliente(?)}");
                          procedimiento.setInt(1,((TipoCliente)tblTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
                          procedimiento.execute();
                          listaTipoCliente.remove(tblTipoCliente.getSelectionModel().getSelectedIndex());
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
   
   //13.....
   public void seleccionarElemento(){
       if(tblTipoCliente.getSelectionModel().getSelectedItem()==null){
           JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
       }else{
           txtCodigoTipoCliente.setText(String.valueOf(((TipoCliente)tblTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
         txtDireccion.setText(((TipoCliente)tblTipoCliente.getSelectionModel().getSelectedItem()).getDescripcion());
       }
    }
    
   
   public void editar(){
       switch(tipoDeOperacion){
           case NINGUNO:{
               if(tblTipoCliente.getSelectionModel().getSelectedItem()!=null){
                   btnEditar.setText("Actualizar");
                   btnReporte.setText("Cancelar");
                   btnNuevo.setDisable(true);
                   btnEliminar.setDisable(true);
                   imgEditar.setImage(new Image("/org/danieljuarez/images/actualizar.png"));
                   imgReporte.setImage(new Image("/org/danieljuarez/images/cancelar.png"));
                   activarControles();
                   tipoDeOperacion=operaciones.ACTUALIZAR;
               }else{
                   JOptionPane.showMessageDialog(null,"Debe de seleccionar un elemento de la tabla de contenido");
               }
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
   
   public void actualizar(){
       try{
         PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarTipoCliente(?,?)}");
         TipoCliente registro=(TipoCliente)tblTipoCliente.getSelectionModel().getSelectedItem();
         registro.setDescripcion(txtDireccion.getText());
         procedimiento.setInt(1,registro.getCodigoTipoCliente());
         procedimiento.setString(2, registro.getDescripcion());
         procedimiento.execute();
         cargarDatos();
         tipoDeOperacion = operaciones.NINGUNO;
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void cancelar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblTipoCliente.getSelectionModel().getSelectedItem()!=null){
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
            }
        }
    }
   
    public void imprimirReporte(){
        Map parametros=new HashMap();
        parametros.put("codigoTipoCliente", null);
        GenerarReporte.mostrarReporte("ReporteTipoCliente.jasper", "Reporte Datos Tipo Cliente", parametros);
    }
    
    
    // Por probar, falta en IReport 
    //select * from TipoCliente where codigoTipoCliente = codTC(Parametro)
    public void reporteIndividual(){
        Map parametros=new HashMap();
        int codTC=((TipoCliente)tblTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente();
        parametros.put("codTC", codTC);
        GenerarReporte.mostrarReporte("ReporteTipoCliente.jasper", "Reporte Datos Tipo Cliente", parametros);
    
    }
    
    
    //5.....
    public void desactivarControles(){
        txtCodigoTipoCliente.setEditable(false);
        txtDireccion.setEditable(false);
    
    }
    
    public void activarControles(){
        txtCodigoTipoCliente.setEditable(false);
        txtDireccion.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoTipoCliente.clear();
        txtDireccion.clear();
    }
    
    
    
    
    
    //2......

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    //3.....
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaCliente(){
        escenarioPrincipal.ventanaCliente();
    }
}
