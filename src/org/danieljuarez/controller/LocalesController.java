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
import org.danieljuarez.bean.Locales;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.report.GenerarReporte;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class LocalesController implements Initializable {
    //1...
    private Principal escenarioPrincipal;
    
    //6.....
    private enum operaciones {NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    //9.....
    private ObservableList<Locales> listaLocal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    //4...
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoLocal;
    @FXML private TextField txtMesesP;
    @FXML private TextField txtValorAdmin;
    @FXML private TextField txtSaldoFavor;
    @FXML private TextField txtDisponibilidad;
    @FXML private TextField txtSaldoContra;
    @FXML private TextField txtValorL;
    @FXML private TableView tblLocales;
    @FXML private TableColumn colCodigoLocal;
    @FXML private TableColumn colSaldoFavor;
    @FXML private TableColumn colSaldoContra;
    @FXML private TableColumn colMesesP;
    @FXML private TableColumn colDisponibilidad;
    @FXML private TableColumn colValorL;
    @FXML private TableColumn colValorAdmin;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    

    //8...true
    public void cargarDatos(){
        tblLocales.setItems(getLocales());
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("codigoLocal"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Locales,Double>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Locales,Double>("saldoContra"));
        colMesesP.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("mesesPendientes"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Locales,Boolean>("disponibilidad"));
        colValorL.setCellValueFactory(new PropertyValueFactory<Locales,Double>("valorLocal"));
        colValorAdmin.setCellValueFactory(new PropertyValueFactory<Locales,Double>("valorAdministracion"));
    }
    
    //10....
    public ObservableList<Locales>getLocales(){
        ArrayList<Locales> lista=new ArrayList<Locales>();
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocal()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Locales(resultado.getInt("codigoLocal"),resultado.getDouble("saldoFavor"),resultado.getDouble("saldoContra"),
                                      resultado.getInt("mesesPendientes"),
                                      resultado.getBoolean("disponibilidad"),
                                      resultado.getDouble("valorLocal"),
                                      resultado.getDouble("valorAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaLocal=FXCollections.observableArrayList(lista);
    }
    
    //7...
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                activarControles();
                limpiarControles();
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
    
    //11......
    public void guardar(){
        Locales registro=new Locales();
        registro.setMesesPendientes(Integer.parseInt(txtMesesP.getText()));
        registro.setValorAdministracion(Double.parseDouble(txtValorAdmin.getText()));
        registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
        registro.setDisponibilidad(Boolean.parseBoolean(txtDisponibilidad.getText()));
        registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
        registro.setValorLocal(Double.parseDouble(txtValorL.getText()));
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgreagarLocal(?,?,?,?,?,?)}");
            procedimiento.setDouble(1, registro.getSaldoFavor());
            procedimiento.setDouble(2, registro.getSaldoContra());
            procedimiento.setInt(3, registro.getMesesPendientes());
            procedimiento.setBoolean(4, registro.isDisponibilidad());
            procedimiento.setDouble(5, registro.getValorLocal());
            procedimiento.setDouble(6, registro.getValorAdministracion());
            procedimiento.execute();
            listaLocal.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    //12.....
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
               if(tblLocales.getSelectionModel().getSelectedItem()!=null){
                   int respuesta=JOptionPane.showConfirmDialog(null,"¿Esta seguro de eliminar el registro?","Eliminar Un Local",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==JOptionPane.YES_NO_OPTION){
                      try{
                          PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarLocal(?)}");
                          procedimiento.setInt(1,((Locales)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
                          procedimiento.execute();
                          listaLocal.remove(tblLocales.getSelectionModel().getSelectedIndex());
                          limpiarControles();
                      }catch(Exception e){
                          e.printStackTrace();
                      }
                  }
               }else{
                   JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
               }
        }
    }
    
    
    //13.....
    public void seleccionarElemento(){
        if(tblLocales.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }else{
        
            txtCodigoLocal.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal()));
            txtMesesP.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getMesesPendientes()));
            txtValorAdmin.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getValorAdministracion()));
            txtSaldoFavor.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtDisponibilidad.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).isDisponibilidad()));
            txtSaldoContra.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getSaldoContra()));
            txtValorL.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getValorLocal()));
        }
    }
    
    //14....editar
    public void editar(){
       switch(tipoDeOperacion){
           case NINGUNO:{
               if(tblLocales.getSelectionModel().getSelectedItem()!=null){
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
    
   //15.......actualizar
    public void actualizar(){
       try{
         PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarLocal(?,?,?,?,?,?,?)}");
         Locales registro=(Locales)tblLocales.getSelectionModel().getSelectedItem();
         registro.setMesesPendientes(Integer.parseInt(txtMesesP.getText()));
         registro.setValorAdministracion(Double.parseDouble(txtValorAdmin.getText()));
         registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
         registro.setDisponibilidad(Boolean.parseBoolean(txtDisponibilidad.getText()));
         registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
         registro.setValorLocal(Double.parseDouble(txtValorL.getText()));
         procedimiento.setInt(1,registro.getCodigoLocal());
         procedimiento.setDouble(2, registro.getSaldoFavor());
         procedimiento.setDouble(3, registro.getSaldoContra());
         procedimiento.setInt(4, registro.getMesesPendientes());
         procedimiento.setBoolean(5, registro.isDisponibilidad());
         procedimiento.setDouble(6, registro.getValorLocal());
         procedimiento.setDouble(7, registro.getValorAdministracion());
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
                if(tblLocales.getSelectionModel().getSelectedItem()!=null){
                    tipoDeOperacion=operaciones.ACTUALIZAR;
                }
                imprimirReporte();
            }break;
            case ACTUALIZAR:{
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
        parametros.put("codigoLocal", null);
        GenerarReporte.mostrarReporte("ReporteLocal.jasper", "Reporte Datos Locales", parametros);
        
    
    }
    
    
    
    
    
    //5....
     public void desactivarControles(){
        txtCodigoLocal.setEditable(false);
        txtMesesP.setEditable(false);
        txtValorAdmin.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtDisponibilidad.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtValorL.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoLocal.setEditable(false);
        txtMesesP.setEditable(true);
        txtValorAdmin.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtDisponibilidad.setEditable(true);
        txtSaldoContra.setEditable(true);
        txtValorL.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoLocal.clear();
        txtMesesP.clear();
        txtValorAdmin.clear();
        txtSaldoFavor.clear();
        txtDisponibilidad.clear();
        txtSaldoContra.clear();
        txtValorL.clear();
    }
    //2...
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    //3....
    public void menuPrincipal(){
       escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaCuentaPorCobrar(){
        escenarioPrincipal.ventanaCuentasPorCobrar();
    }
    
    
}
