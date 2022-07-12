package org.danieljuarez.controller;


import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.danieljuarez.bean.Administracion;
import org.danieljuarez.bean.CuentaPorPagar;
import org.danieljuarez.bean.Proveedor;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class CuentaPorPagarController implements Initializable{

    //1....
    private Principal escenarioPrincpal;
    
    //3.........
    private enum operaciones{NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    //4....
    private ObservableList<CuentaPorPagar> listaCuentaPorPagar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Proveedor> listaProveedor;
    
    
    //5....
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodCuentaPagar;
    @FXML private TextField txtNumFac;
    @FXML private TextField txtEstadoPago;
    @FXML private TextField txtVarloNetoPago;
    @FXML private ComboBox cmbCodAdmin;
    @FXML private ComboBox cmbCodProveedor;
    @FXML private TableView tblCuentasPorPagar;
    @FXML private TableColumn colCodCuentaPagar;
    @FXML private TableColumn colNumFac;
    @FXML private TableColumn colFechaLimiteP;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colCodAdmin;
    @FXML private TableColumn colCodProveedor;
    @FXML private GridPane grpFechaLimite;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    //6...
    private DatePicker fechaLimite;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //7....
        fechaLimite=new DatePicker(Locale.ENGLISH);
        fechaLimite.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaLimite.getCalendarView().todayButtonTextProperty().set("Today");
        fechaLimite.getCalendarView().setShowWeeks(false);
        grpFechaLimite.add(fechaLimite, 0, 0); 
        fechaLimite.getStylesheets().add("/org/danieljuarez/resorce/DatePicker.css");
        
        
        //8...
        cargarDatos();
        
    }
    
    //8..
    public void cargarDatos(){
        tblCuentasPorPagar.setItems(getCuentaPorPagar());
        colCodCuentaPagar.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Integer>("codigoCuentaPorPagar"));
        colNumFac.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,String>("numeroFactura"));
        colFechaLimiteP.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Date>("fechaLimitePago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,String>("estadoPago"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Double>("valorNetoPago"));
        colCodAdmin.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Integer>("codigoAdministracion"));
        colCodProveedor.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Integer>("codigoProveedor"));
        cmbCodAdmin.setItems(getAdministracion());
        cmbCodProveedor.setItems(getProveedor());
    }
    
    //9......
    public ObservableList<CuentaPorPagar> getCuentaPorPagar(){
        ArrayList<CuentaPorPagar> lista=new ArrayList<CuentaPorPagar>();
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasP()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentaPorPagar(
                                            resultado.getInt("codigoCuentaPorPagar"),
                                            resultado.getString("numeroFactura"),
                                            resultado.getDate("fechaLimitePago"),
                                            resultado.getString("estadoPago"),
                                            resultado.getDouble("valorNetoPago"),
                                            resultado.getInt("codigoAdministracion"),
                                            resultado.getInt("codigoProveedor")));
            } 
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaCuentaPorPagar=FXCollections.observableArrayList(lista);
    }
    
    //10...
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
                
        return listaAdministracion=FXCollections.observableArrayList(lista);
    } 
     
     
    //12.....
    public void seleccionarElemento(){
        if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }else{
            txtCodCuentaPagar.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar()));
            txtNumFac.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            fechaLimite.selectedDateProperty().set(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
            txtEstadoPago.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
            txtVarloNetoPago.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            cmbCodAdmin.getSelectionModel().select(buscarAdmin(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodProveedor.getSelectionModel().select(buscarProveedor(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        
        }
   
    }
    
    //13...
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
    
    //14.........
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
    
    
    //15........
    public void desactivarControles(){
        txtCodCuentaPagar.setEditable(false);
        txtNumFac.setEditable(false);
        txtEstadoPago.setEditable(false);
        txtVarloNetoPago.setEditable(false);
        cmbCodAdmin.setEditable(true);
        cmbCodProveedor.setEditable(true);
        fechaLimite.setDisable(true);
    }
    public void activarControles(){
        txtCodCuentaPagar.setEditable(false);
        txtNumFac.setEditable(true);
        txtEstadoPago.setEditable(true);
        txtVarloNetoPago.setEditable(true);
        cmbCodAdmin.setEditable(false);
        cmbCodProveedor.setEditable(false);
        fechaLimite.setDisable(false);
    }
    public void limpiarControles(){
        txtCodCuentaPagar.clear();
        txtNumFac.clear();
        txtEstadoPago.clear();
        txtVarloNetoPago.clear();
        cmbCodAdmin.setValue(null);
        cmbCodProveedor.setValue(null);
        fechaLimite.setPromptText("");
    }
    
    public void desactivarCMB(){
        cmbCodAdmin.setDisable(true);
        cmbCodProveedor.setDisable(true);
    }
    
    //16...
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
    
    //17....
    public void guardar(){
        CuentaPorPagar registro=new CuentaPorPagar();
        registro.setNumeroFactura(txtNumFac.getText());
        registro.setFechaLimitePago(fechaLimite.getSelectedDate());
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setValorNetoPago(Double.parseDouble(txtVarloNetoPago.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoProveedor(((Proveedor)cmbCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaPagar(?,?,?,?,?,?)}");
            procedimiento.setString(1,registro.getNumeroFactura());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(3, registro.getEstadoPago());
            procedimiento.setDouble(4,registro.getValorNetoPago());
            procedimiento.setInt(5, registro.getCodigoAdministracion());
            procedimiento.setInt(6, registro.getCodigoProveedor());
            procedimiento.execute();
            listaCuentaPorPagar.add(registro);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
           
    }
    
    //18...
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
                if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()!=null){
                   int respuesta;
                    respuesta=JOptionPane.showConfirmDialog(null, "¿Está seguro "
                            + " de eliminar este registro?","Eliminar Cuenta Por Pagar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentaP(?)}");
                            procedimiento.setInt(1, ((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar());
                            procedimiento.execute();
                            listaCuentaPorPagar.remove(tblCuentasPorPagar.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }
    
    }
    
    //19....
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()!=null){
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
                cargarDatos();
                tipoDeOperacion=operaciones.NINGUNO;
            
            
            }
        
        }
    }
    
    //20..
    public void actualizar(){
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarCuentaPagar(?,?,?,?,?)}");
            CuentaPorPagar registro=(CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumFac.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoPago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtVarloNetoPago.getText()));
            /*registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            registro.setCodigoProveedor(((Proveedor)cmbCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());*/
            procedimiento.setInt(1, registro.getCodigoCuentaPorPagar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));               
            procedimiento.setString(4, registro.getEstadoPago());    
            procedimiento.setDouble(5, registro.getValorNetoPago());
            /*procedimiento.setInt(6, registro.getCodigoAdministracion());
            procedimiento.setInt(7, registro.getCodigoProveedor());*/
            procedimiento.execute();
            cargarDatos();
            tipoDeOperacion = operaciones.NINGUNO;
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    //21...
    public void cancelar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()!=null){
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
    
    
}
