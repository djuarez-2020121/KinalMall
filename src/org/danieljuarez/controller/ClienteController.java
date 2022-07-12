
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
import org.danieljuarez.bean.Locales;
import org.danieljuarez.bean.TipoCliente;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.report.GenerarReporte;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class ClienteController implements Initializable{
    
    //1....
    private Principal escenarioPrincpal;
    
    //4.........
    private enum operaciones{NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    
    //5.........
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Locales> listaLocal;
    private ObservableList<Administracion> listaAdmin;
    private ObservableList<TipoCliente> listaTipoCliente;
    
    //6......
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodCliente;
    @FXML private TextField txtNomCliente;
    @FXML private TextField txtApellCliente;
    @FXML private TextField txtDireccCliente;
    @FXML private TextField txtEmailCliente;
    @FXML private TextField txtTelCliente;
    @FXML private TableView tblClientes;
    @FXML private TableColumn colCodCliente;
    @FXML private TableColumn colNomCliente;
    @FXML private TableColumn colApelliCliente;
    @FXML private TableColumn colDireccCliente;
    @FXML private TableColumn colEmalCliente;
    @FXML private TableColumn colTelCliente;
    @FXML private TableColumn colCodLocal;
    @FXML private TableColumn colCodAdmin;
    @FXML private TableColumn colCodTipoCliente;
    @FXML private ComboBox cmbCodLocal;
    @FXML private ComboBox cmbCodAdmin;
    @FXML private ComboBox cmbCodTipoCliente;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //8...
        cargarDatos();
    }
    
    //8...
    public void cargarDatos(){
        tblClientes.setItems(getCliente());
        colCodCliente.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("codigoCliente"));
        colNomCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombreCliente"));
        colApelliCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("apellidoCliente"));
        colDireccCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("direccionCliente"));
        colEmalCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("email"));
        colTelCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("telefonoCliente"));
        colCodLocal.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("codigoLocal"));
        colCodAdmin.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        colCodTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoCliente,Integer>("codigoTipoCliente"));
        cmbCodLocal.setItems(getLocales());
        cmbCodAdmin.setItems(getAdministracion());
        cmbCodTipoCliente.setItems(getTipoCliente());
    }
    
    //10
    public void seleccionarElemento(){
        if(tblClientes.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }else{
            txtCodCliente.setText(String.valueOf(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            txtNomCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
            txtApellCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
            txtDireccCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
            txtEmailCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getEmail());
            txtTelCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
            //11.0
            cmbCodLocal.getSelectionModel().select(buscarLocal(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoLocal()));
            cmbCodAdmin.getSelectionModel().select(buscarAdmin(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodTipoCliente.getSelectionModel().select(buscarTipoCliente(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
        
        }
        
    } 
    
    
    
    //9....
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
    
    public ObservableList<Locales> getLocales(){
        ArrayList<Locales> lista= new ArrayList<Locales>();
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocal()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Locales(resultado.getInt("codigoLocal"),
                                      resultado.getDouble("saldoFavor"),
                                      resultado.getDouble("saldoContra"),
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
    
    //11.1
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
    
    public TipoCliente buscarTipoCliente(int codigoTipoCliente){
        TipoCliente resultado=null;
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("call sp_BuscarTipoCliente(?)");
            procedimiento.setInt(1, codigoTipoCliente);
            ResultSet registro=procedimiento.executeQuery();
            while(registro.next()){
                resultado=new TipoCliente(registro.getInt("codigoTipoCliente"),registro.getString("descripcion"));
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
    
    //13....
    public void guardar(){
        Cliente registro=new Cliente();
        
        registro.setNombreCliente(txtNomCliente.getText());
        registro.setApellidoCliente(txtApellCliente.getText());
        registro.setTelefonoCliente(txtTelCliente.getText());
        registro.setDireccionCliente(txtDireccCliente.getText());
        registro.setEmail(txtEmailCliente.getText());
        registro.setCodigoLocal(((Locales)cmbCodLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoTipoCliente(((TipoCliente)cmbCodTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
        
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCliente(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1,registro.getNombreCliente());
            procedimiento.setString(2, registro.getApellidoCliente());
            procedimiento.setString(3, registro.getTelefonoCliente());
            procedimiento.setString(4, registro.getDireccionCliente());
            procedimiento.setString(5, registro.getEmail());
            procedimiento.setInt(6, registro.getCodigoLocal());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.setInt(8, registro.getCodigoTipoCliente());
            procedimiento.execute();
            listaCliente.add(registro);
            
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
                if(tblClientes.getSelectionModel().getSelectedItem()!=null){
                    int respuesta=JOptionPane.showConfirmDialog(null,"¿Esta seguro de eliminar el registro?","Eliminar un Cliente",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==JOptionPane.YES_NO_OPTION){
                        
                        try{
                            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCliente(?)}");
                            procedimiento.setInt(1,((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()); 
                            procedimiento.execute();
                            listaCliente.remove(tblClientes.getSelectionModel().getSelectedIndex());
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
                if(tblClientes.getSelectionModel().getSelectedItem()!=null){
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
    
    //16........
    public void actualizar(){
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarCliente(?,?,?,?,?,?)}");
            Cliente registro=(Cliente)tblClientes.getSelectionModel().getSelectedItem();
            registro.setNombreCliente(txtNomCliente.getText());
            registro.setApellidoCliente(txtApellCliente.getText());
            registro.setTelefonoCliente(txtTelCliente.getText());
            registro.setDireccionCliente(txtDireccCliente.getText());
            registro.setEmail(txtEmailCliente.getText());
            //registro.setCodigoLocal(((Locales)cmbCodLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
            //registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            //registro.setCodigoTipoCliente(((TipoCliente)cmbCodTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(4, registro.getTelefonoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getEmail());
            //procedimiento.setString(6, registro.getDireccionCliente());
            //procedimiento.setInt(7, registro.getCodigoLocal());
            //procedimiento.setInt(8, registro.getCodigoAdministracion());
            //procedimiento.setInt(9, registro.getCodigoTipoCliente());
            procedimiento.execute();
            cargarDatos();
            tipoDeOperacion = operaciones.NINGUNO;
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    //17....
    public void cancelar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblClientes.getSelectionModel().getSelectedItem()!=null){
                    tipoDeOperacion=operaciones.ACTUALIZAR;
                    reporteInduvidual();
                }
                //imprimirReporte();
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
        parametros.put("codigoCliente", null);
        GenerarReporte.mostrarReporte("ReporteClientes.jasper", "Reporte Datos De Clientes", parametros);
        
    
    }
    
    public void reporteInduvidual(){
        Map parametros=new HashMap();
        int codCl=((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente();
        parametros.put("codCl", codCl);
        GenerarReporte.mostrarReporte("ReporteClienteIndividual.jasper", "Reporte de Cliente", parametros);
    }
    
    //7.....
    public void desactivarControles(){
        txtCodCliente.setEditable(false);
        txtNomCliente.setEditable(false);
        txtApellCliente.setEditable(false);
        txtDireccCliente.setEditable(false);
        txtEmailCliente.setEditable(false);
        txtTelCliente.setEditable(false);
        cmbCodLocal.setDisable(true);
        cmbCodAdmin.setDisable(true);
        cmbCodTipoCliente.setDisable(true);
    }
    public void activarControles(){
        txtCodCliente.setEditable(false);
        txtNomCliente.setEditable(true);
        txtApellCliente.setEditable(true);
        txtDireccCliente.setEditable(true);
        txtEmailCliente.setEditable(true);
        txtTelCliente.setEditable(true);
        cmbCodLocal.setDisable(false);
        cmbCodAdmin.setDisable(false);
        cmbCodTipoCliente.setDisable(false);
    }
    public void limpiarControles(){
        txtCodCliente.clear();
        txtNomCliente.clear();
        txtApellCliente.clear();
        txtDireccCliente.clear();
        txtEmailCliente.clear();
        txtTelCliente.clear();
        cmbCodLocal.getSelectionModel().clearSelection();
        cmbCodAdmin.getSelectionModel().clearSelection();
        cmbCodTipoCliente.getSelectionModel().clearSelection();
    }
    
    public void desactivarCMB(){
        cmbCodLocal.setDisable(true);
        cmbCodAdmin.setDisable(true);
        cmbCodTipoCliente.setDisable(true);
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
    
    
//    horarios, cuentasPagar, Proveedores POR HACER CRUS.......
    
    
}
