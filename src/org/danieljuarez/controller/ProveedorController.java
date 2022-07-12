package org.danieljuarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danieljuarez.bean.Administracion;
import org.danieljuarez.bean.Proveedor;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class ProveedorController implements Initializable{
    
    //1....
    private Principal escenarioPrincpal;
    
    //4.........
    private enum operaciones{NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    //5......
    private ObservableList<Proveedor> listaProveedor;
    private ObservableList<Administracion> listaAdmin;
    
    
    //6....
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoProveedor;
    @FXML private TextField txtNITProveedor;
    @FXML private TextField txtServicioPrestado;
    @FXML private TextField txtTelProveedor;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtSaldoFavor;
    @FXML private TextField txtSaldoContra;
    @FXML private ComboBox cmbCodAdmin;
    @FXML private TableView tblProveedor;
    @FXML private TableColumn colCodProveedor;
    @FXML private TableColumn colNITPro;
    @FXML private TableColumn colServicioPrestado;
    @FXML private TableColumn colTelPro;
    @FXML private TableColumn colDire;
    @FXML private TableColumn colSalF;
    @FXML private TableColumn colSalC;
    @FXML private TableColumn colCodAdmin;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //7....
        cargarDatos();
    }
    
    
    //7....
    public void cargarDatos(){
        tblProveedor.setItems(getProveedor());
        colCodProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("codigoProveedor"));
        colNITPro.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("NITProveedor"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("servicioPrestado"));
        colTelPro.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("telefonoProveedor"));
        colDire.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("direccionProveedor"));
        colSalF.setCellValueFactory(new PropertyValueFactory<Proveedor,Double>("saldoFavor"));
        colSalC.setCellValueFactory(new PropertyValueFactory<Proveedor,Double>("saldoContra"));
        colCodAdmin.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        cmbCodAdmin.setItems(getAdministracion());
        
        
    }
    
    //8...
    public ObservableList<Proveedor> getProveedor(){
        ArrayList<Proveedor> lista=new ArrayList<Proveedor>();
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedor(
                                        resultado.getInt("codigoProveedor"),
                                        resultado.getString("NITProveedor"),
                                        resultado.getString("servicioPrestado"),
                                        resultado.getString("telefonoProveedor"),
                                        resultado.getString("direccionProveedor"),
                                        resultado.getDouble("saldoFavor"),
                                        resultado.getDouble("saldoContra"),
                                        resultado.getInt("codigoAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaProveedor=FXCollections.observableArrayList(lista);
    }
    
    //9.....
    public Proveedor buscarProveedor(int codigoProveedor){
        Proveedor resultado=null;
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro=procedimiento.executeQuery();
            while(registro.next()){
                resultado=new Proveedor(registro.getInt("codigoProveedor"),
                                        registro.getString("NITProveedor"),
                                        registro.getString("servicioPrestado"),
                                        registro.getString("telefonoProveedor"),
                                        registro.getString("direccionProveedor"),
                                        registro.getDouble("saldoFavor"),
                                        registro.getDouble("saldoContra"),
                                        registro.getInt("codigoAdministracion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    //10...
    public void seleccionarElemento(){
        if(tblProveedor.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }else{
        
            txtCodigoProveedor.setText(String.valueOf(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtNITProveedor.setText(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getNITProveedor());
            txtServicioPrestado.setText(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getServicioPrestado());
            txtTelProveedor.setText(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getTelefonoProveedor());
            txtDireccion.setText(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getDireccionProveedor());
            txtSaldoFavor.setText(String.valueOf(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtSaldoContra.setText(String.valueOf(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getSaldoContra()));
            cmbCodAdmin.getSelectionModel().select(buscarAdmin(((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        }   
    }
    
    //11...
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
                
        return listaAdmin=FXCollections.observableArrayList(lista);
    }
    
    public Administracion buscarAdmin(int codigoAdministracion){
        Administracion resultado=null;
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdministracion(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro=procedimiento.executeQuery();
            while(registro.next()){
                resultado=new Administracion(registro.getInt("codigoAdministracion"),
                                             registro.getString("direccion"),
                                             registro.getString("telefono"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    
    }
    
    
    //12....
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                limpiarControles();
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
    
    //13...
    public void guardar(){
        Proveedor registro=new Proveedor();
        registro.setNITProveedor(txtNITProveedor.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setTelefonoProveedor(txtTelProveedor.getText());
        registro.setDireccionProveedor(txtDireccion.getText());
        registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
        registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedor(?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNITProveedor());
            procedimiento.setString(2, registro.getServicioPrestado());
            procedimiento.setString(3, registro.getTelefonoProveedor());
            procedimiento.setString(4, registro.getDireccionProveedor());
            procedimiento.setDouble(5, registro.getSaldoFavor());
            procedimiento.setDouble(6, registro.getSaldoContra());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.execute();
            listaProveedor.add(registro);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    //14...
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
                if(tblProveedor.getSelectionModel().getSelectedItem()!=null){
                    int respuesta;
                    respuesta=JOptionPane.showConfirmDialog(null, "¿Está seguro "
                            + " de eliminar este registro?","Eliminar Un Proveedor",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedor)tblProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedor.remove(tblProveedor.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null,"Debe de seleccionar un elemento de la tabla de contenido");
        }
    
    }
    
    //15...
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblProveedor.getSelectionModel().getSelectedItem()!=null){
                    
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danieljuarez/images/actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieljuarez/images/cancelar.png"));
                    activarControles();
                    desactivarCMB();
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
                //activarControles();
                cargarDatos();
                tipoDeOperacion=operaciones.NINGUNO;
            
            
            }
        }
    
    }
    
    //16...
    public void actualizar(){
           try{
               PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarProveedor(?,?,?,?,?,?,?)}");
               Proveedor registro = (Proveedor)tblProveedor.getSelectionModel().getSelectedItem();
               registro.setNITProveedor(txtNITProveedor.getText());
               registro.setServicioPrestado(txtServicioPrestado.getText());
               registro.setTelefonoProveedor(txtTelProveedor.getText());
               registro.setDireccionProveedor(txtDireccion.getText());
               registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
               registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
               //registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
               procedimiento.setInt(1, registro.getCodigoProveedor());
               procedimiento.setString(2, registro.getNITProveedor());
               procedimiento.setString(3, registro.getServicioPrestado());
               procedimiento.setString(4, registro.getTelefonoProveedor());
               procedimiento.setString(5, registro.getDireccionProveedor());
               procedimiento.setDouble(6, registro.getSaldoFavor());
               procedimiento.setDouble(7, registro.getSaldoContra());
               //procedimiento.setInt(8, registro.getCodigoAdministracion());
               procedimiento.execute();
               cargarDatos();
               tipoDeOperacion = operaciones.NINGUNO;
               
            }catch(Exception e){
               e.printStackTrace();
            }             
    }
    
    //17...
    public void cancelar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblProveedor.getSelectionModel().getSelectedItem()!=null){
                    tipoDeOperacion=operaciones.ACTUALIZAR;
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
            }
        }
    }
    
    
    //10...
    public void desactivarControles(){
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(false);
        txtServicioPrestado.setEditable(false);
        txtTelProveedor.setEditable(false);
        txtDireccion.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        cmbCodAdmin.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelProveedor.setEditable(true);
        txtDireccion.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        cmbCodAdmin.setDisable(false);
    
    }
    
    public void limpiarControles(){
        txtCodigoProveedor.clear();
        txtNITProveedor.clear();
        txtServicioPrestado.clear();
        txtTelProveedor.clear();
        txtDireccion.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        cmbCodAdmin.getSelectionModel().clearSelection();
    
    }
    
    public void desactivarCMB(){
        cmbCodAdmin.setDisable(true);
    }
    
    
    //2......
    public Principal getEscenarioPrincipal() {
        return escenarioPrincpal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincpal = escenarioPrincipal;
    }
    
    //3.........
    
    public void menuPrincipal(){
        escenarioPrincpal.menuPrincipal();
    }
    
    public void ventanaCuentasPorPagar(){
        escenarioPrincpal.ventanaCuentasPorPagar();
    }
    
}
