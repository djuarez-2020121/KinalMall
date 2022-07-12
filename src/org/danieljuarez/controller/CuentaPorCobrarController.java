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
import org.danieljuarez.bean.Cliente;
import org.danieljuarez.bean.CuentaPorCobrar;
import org.danieljuarez.bean.Locales;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class CuentaPorCobrarController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    
    //1....
    private Principal escenarioPrincpal;
    
    //2......

    public Principal getEscenarioPrincpal() {
        return escenarioPrincpal;
    }

    public void setEscenarioPrincpal(Principal escenarioPrincpal) {
        this.escenarioPrincpal = escenarioPrincpal;
    }
    
    
    //3.........
    
    public void menuPrincipal(){
        escenarioPrincpal.menuPrincipal();
    }
    
    
    //3.........
    private enum operaciones{NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    //4....
    private ObservableList<CuentaPorCobrar> listaCuentaPorCobrar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Locales> listaLocal;
    
    //5...
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodCuentaC;
    @FXML private TextField txtNumFac;
    @FXML private TextField txtAnio;
    @FXML private TextField txtMes;
    @FXML private TextField txtValorNeto;
    @FXML private TextField txtEstadoP;
    @FXML private ComboBox cmbCodAdmin;
    @FXML private ComboBox cmbCodCliente;
    @FXML private ComboBox cmbCodLocal;
    @FXML private TableView tblCuentasC;
    @FXML private TableColumn colCodCuentaC;
    @FXML private TableColumn colNumFac;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colMes;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colEstadoP;
    @FXML private TableColumn colCodAdmin;
    @FXML private TableColumn colCodCliente;
    @FXML private TableColumn colCodLocal;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    //6...
    public void cargarDatos(){
        tblCuentasC.setItems(getCuentaPorC());
        colCodCuentaC.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,Integer>("codigoCuentaPorCobrar"));
        colNumFac.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,String>("numeroFactura"));
        colAnio.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,Integer>("anioo"));
        colMes.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,Integer>("mes"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,Double>("valorNetoPago"));
        colEstadoP.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,String>("estadoPago"));
        colCodAdmin.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,Integer>("codigoAdministracion"));
        colCodCliente.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,Integer>("codigoCliente"));
        colCodLocal.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,Integer>("codigoLocal"));
        cmbCodAdmin.setItems(getAdministracion());
        cmbCodCliente.setItems(getCliente());
        cmbCodLocal.setItems(getLocales());
        
    }
    
    //7...
    public ObservableList<CuentaPorCobrar> getCuentaPorC(){
        ArrayList<CuentaPorCobrar> lista=new ArrayList<CuentaPorCobrar>();
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasCobrar()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentaPorCobrar(
                                            resultado.getInt("codigoCuentaPorCobrar"),
                                            resultado.getString("numeroFactura"),
                                            resultado.getInt("anioo"),
                                            resultado.getInt("mes"),
                                            resultado.getDouble("valorNetoPago"),
                                            resultado.getString("estadoPago"),
                                            resultado.getInt("codigoAdministracion"),
                                            resultado.getInt("codigoCliente"),
                                            resultado.getInt("codigoLocal")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaCuentaPorCobrar=FXCollections.observableArrayList(lista);
    }
    
    //8...
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
    
    //9...
    public ObservableList<Cliente> getCliente(){
        ArrayList<Cliente> lista=new ArrayList<Cliente>();
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("codigoCliente"),
                                      resultado.getString("nombreCliente"),
                                      resultado.getString("apellidoCliente"),
                                      resultado.getString("telefonoCliente"),
                                      resultado.getString("direccionCliente"),
                                      resultado.getString("email"),
                                      resultado.getInt("codigoLocal"),
                                      resultado.getInt("codigoAdministracion"),
                                      resultado.getInt("codigoTipoCliente")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCliente=FXCollections.observableArrayList(lista);
    }
    
    //10...
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
     
    //11..
    public void seleccionarElemento(){
        if(tblCuentasC.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }else{
            txtCodCuentaC.setText(String.valueOf(((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar()));
            txtNumFac.setText(((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getNumeroFactura());
            txtAnio.setText(String.valueOf(((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getAnioo()));
            txtMes.setText(String.valueOf(((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getMes()));
            txtValorNeto.setText(String.valueOf(((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            txtEstadoP.setText(((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getEstadoPago());
            cmbCodAdmin.getSelectionModel().select(buscarAdmin(((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodCliente.getSelectionModel().select(buscarCliente(((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            cmbCodLocal.getSelectionModel().select(buscarLocal(((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getCodigoLocal()));
        }
        
    }
    
    //12...
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
    //13..
    public Cliente buscarCliente(int codigoCliente){
        Cliente resultado=null;
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCliente(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro=procedimiento.executeQuery();
            while(registro.next()){
               resultado=new Cliente(registro.getInt("codigoCliente"),
                                      registro.getString("nombreCliente"),
                                      registro.getString("apellidoCliente"),
                                      registro.getString("telefonoCliente"),
                                      registro.getString("direccionCliente"),
                                      registro.getString("email"),
                                      registro.getInt("codigoLocal"),
                                      registro.getInt("codigoAdministracion"),
                                      registro.getInt("codigoTipoCliente"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    //14...
    public Locales buscarLocal(int codigoLocal){
        Locales resultado=null;
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocal(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro=procedimiento.executeQuery();
            while(registro.next()){
                resultado=new Locales(registro.getInt("codigoLocal"),
                                      registro.getDouble("saldoFavor"),
                                      registro.getDouble("saldoContra"),
                                      registro.getInt("mesesPendientes"),
                                      registro.getBoolean("disponibilidad"),
                                      registro.getDouble("valorLocal"),
                                      registro.getDouble("valorAdministracion"));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    }
    
    //15........
    public void desactivarControles(){
        txtCodCuentaC.setEditable(false);
        txtNumFac.setEditable(false);
        txtAnio.setEditable(false);
        txtMes.setEditable(false);
        txtValorNeto.setEditable(false);
        txtEstadoP.setEditable(false);
        cmbCodAdmin.setEditable(true);
        cmbCodCliente.setEditable(true);
        cmbCodLocal.setEditable(true);
        
    }
    public void activarControles(){
        txtCodCuentaC.setEditable(false);
        txtNumFac.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNeto.setEditable(true);
        txtEstadoP.setEditable(true);
        cmbCodAdmin.setEditable(false);
        cmbCodCliente.setEditable(false);
        cmbCodLocal.setEditable(false);
    }
    public void limpiarControles(){
        txtCodCuentaC.clear();
        txtNumFac.clear();
        txtAnio.clear();
        txtMes.clear();
        txtValorNeto.clear();
        txtEstadoP.clear();
        cmbCodAdmin.setValue(null);
        cmbCodCliente.setValue(null);
        cmbCodLocal.setValue(null);
    }
    
    public void desactivarCMB(){
        cmbCodAdmin.setDisable(true);
        cmbCodCliente.setDisable(true);
        cmbCodLocal.setDisable(true);
    }
    
    //16..
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
     
    //17...
    public void guardar(){
        CuentaPorCobrar registro=new CuentaPorCobrar();
        registro.setNumeroFactura(txtNumFac.getText());
        registro.setAnioo(Integer.parseInt(txtAnio.getText()));
        registro.setMes(Integer.parseInt(txtMes.getText()));
        registro.setValorNetoPago(Double.parseDouble(txtValorNeto.getText()));
        registro.setEstadoPago(txtEstadoP.getText());
        registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoCliente(((Cliente)cmbCodCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoLocal(((Locales)cmbCodLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaCobrar(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1,registro.getNumeroFactura());
            procedimiento.setInt(2, registro.getAnioo());
            procedimiento.setInt(3,registro.getMes());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setString(5, registro.getEstadoPago());
            procedimiento.setInt(6, registro.getCodigoAdministracion());
            procedimiento.setInt(7, registro.getCodigoCliente());
            procedimiento.setInt(8, registro.getCodigoLocal());
            procedimiento.execute();
            listaCuentaPorCobrar.add(registro);          
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
                if(tblCuentasC.getSelectionModel().getSelectedItem()!=null){
                   int respuesta;
                    respuesta=JOptionPane.showConfirmDialog(null, "¿Está seguro "
                            + " de eliminar este registro?","Eliminar Cueta Por Cobrar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentaCobrar(?)}");
                            procedimiento.setInt(1, ((CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar());
                            procedimiento.execute();
                            listaCuentaPorCobrar.remove(tblCuentasC.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }
    
    }
    
    //19..
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblCuentasC.getSelectionModel().getSelectedItem()!=null){
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
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarCuentaCobrar(?,?,?,?,?,?)}");
            CuentaPorCobrar registro=(CuentaPorCobrar)tblCuentasC.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumFac.getText());
            registro.setAnioo(Integer.parseInt(txtAnio.getText()));
            registro.setMes(Integer.parseInt(txtMes.getText()));
            registro.setValorNetoPago(Double.parseDouble(txtValorNeto.getText()));
            registro.setEstadoPago(txtEstadoP.getText());
            /*registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            registro.setCodigoCliente(((Cliente)cmbCodCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoLocal(((Locales)cmbCodLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());*/
            procedimiento.setInt(1,registro.getCodigoCuentaPorCobrar());
            procedimiento.setString(2,registro.getNumeroFactura());
            procedimiento.setInt(3, registro.getAnioo());
            procedimiento.setInt(4,registro.getMes());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.setString(6, registro.getEstadoPago());
            /*procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.setInt(8, registro.getCodigoCliente());
            procedimiento.setInt(9, registro.getCodigoLocal());*/
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
                if(tblCuentasC.getSelectionModel().getSelectedItem()!=null){
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
    
}
