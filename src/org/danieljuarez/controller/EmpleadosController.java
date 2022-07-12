package org.danieljuarez.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.danieljuarez.bean.Administracion;
import org.danieljuarez.bean.Cargos;
import org.danieljuarez.bean.Departamento;
import org.danieljuarez.bean.Empleados;
import org.danieljuarez.bean.Horarios;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.report.GenerarReporte;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class EmpleadosController implements Initializable{
    //1....
    private Principal escenarioPrincpal;
    
    //3.........
    private enum operaciones{NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    
    
    //4....
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Departamento> listaDepa;
    private ObservableList<Cargos> listaCargos;
    private ObservableList<Horarios> listaHorarios;
    private ObservableList<Administracion> listaAdmin;
    
    
    //5....
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodEmpleado;
    @FXML private TextField txtNomEmpleado;
    @FXML private TextField txtApelliEmpleado;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelEmpleado;
    @FXML private GridPane grpFechaContratacion; 
    @FXML private TextField txtSueldo;
    @FXML private ComboBox cmbCodDepa;
    @FXML private ComboBox cmbCodCargo;
    @FXML private ComboBox cmbCodHorarios;
    @FXML private ComboBox cmbCodAdmin;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodEmple;
    @FXML private TableColumn colNomEm;
    @FXML private TableColumn colApelliEm;
    @FXML private TableColumn colEmailEm;
    @FXML private TableColumn colTelEmple;
    @FXML private TableColumn colFechaContr;
    @FXML private TableColumn colSueldoEmpl;
    @FXML private TableColumn colCodDepa;
    @FXML private TableColumn colCodCargo;
    @FXML private TableColumn colCodH;
    @FXML private TableColumn colCodAdmin;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    //6...
    private DatePicker fechaContra;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //7...
        fechaContra=new DatePicker(Locale.ENGLISH);
        fechaContra.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaContra.getCalendarView().todayButtonTextProperty().set("Today");
        fechaContra.getCalendarView().setShowWeeks(false);
        grpFechaContratacion.add(fechaContra, 0, 0);
        fechaContra.getStylesheets().add("/org/danieljuarez/resorce/DatePicker.css");
        
        
        
        //8...
        cargarDatos();
        
    }
    
    //8...
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodEmple.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoEmpleado"));
        colNomEm.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombreEmpleado"));
        colApelliEm.setCellValueFactory(new PropertyValueFactory<Empleados,String>("apellidoEmpleado"));
        colEmailEm.setCellValueFactory(new PropertyValueFactory<Empleados,String>("correoElectronico"));
        colTelEmple.setCellValueFactory(new PropertyValueFactory<Empleados,String>("telefonoEmpleado"));
        colFechaContr.setCellValueFactory(new PropertyValueFactory<Empleados,Date>("fechaContratacion"));
        colSueldoEmpl.setCellValueFactory(new PropertyValueFactory<Empleados,Double>("sueldo"));
        colCodDepa.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoDepartamento"));
        colCodCargo.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoCargo"));
        colCodH.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoHorario"));
        colCodAdmin.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoAdministracion"));
        cmbCodDepa.setItems(getDepartamento());
        cmbCodCargo.setItems(getCargo());
        cmbCodHorarios.setItems(getHorario());
        cmbCodAdmin.setItems(getAdministracion());
        
    }
    
    //9...
    public ObservableList<Empleados> getEmpleados(){
        ArrayList<Empleados> lista=new ArrayList<Empleados>();
        try{
            PreparedStatement  procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleados(
                                        resultado.getInt("codigoEmpleado"),
                                        resultado.getString("nombreEmpleado"),
                                        resultado.getString("apellidoEmpleado"),
                                        resultado.getString("correoElectronico"),
                                        resultado.getString("telefonoEmpleado"),
                                        resultado.getDate("fechaContratacion"),
                                        resultado.getDouble("sueldo"),
                                        resultado.getInt("codigoDepartamento"),
                                        resultado.getInt("codigoCargo"),
                                        resultado.getInt("codigoHorario"),
                                        resultado.getInt("codigoAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        return listaEmpleados=FXCollections.observableArrayList(lista);
    }
    
    //10...
    public ObservableList<Departamento>getDepartamento(){
        ArrayList<Departamento> lista=new ArrayList<Departamento>();
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Departamento(resultado.getInt("codigoDepartamento"),resultado.getString("nombreDepartamento")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaDepa=FXCollections.observableArrayList(lista);
    }
    
    //11...
    public ObservableList<Cargos>getCargo(){
        ArrayList<Cargos> lista=new ArrayList<Cargos>();
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cargos(resultado.getInt("codigoCargo"),resultado.getString("nombreCargo")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCargos=FXCollections.observableArrayList(lista);
    }
    
    //12...
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
    
    //13...
    public ObservableList<Horarios>getHorario(){
        ArrayList<Horarios> lista=new ArrayList<Horarios>();
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorarios()}");
            ResultSet resultado=procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Horarios(resultado.getInt("codigoHorario"),resultado.getString("horaEntrada"),
                                                                        resultado.getString("horaSalida"),
                                                                        resultado.getBoolean("lunes"),
                                                                        resultado.getBoolean("martes"),
                                                                        resultado.getBoolean("miercoles"),
                                                                        resultado.getBoolean("jueves"),
                                                                        resultado.getBoolean("viernes")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaHorarios=FXCollections.observableArrayList(lista);
    }
    
    //14...
    public void seleccionarElemento(){
        if(tblEmpleados.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }else{
            txtCodEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
            txtNomEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
            txtApelliEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
            txtEmail.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCorreoElectronico());
            txtTelEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoEmpleado());
            fechaContra.selectedDateProperty().set(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getFechaContratacion());
            txtSueldo.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
            cmbCodDepa.getSelectionModel().select(buscarDepa(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            cmbCodCargo.getSelectionModel().select(buscarCargo(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            cmbCodHorarios.getSelectionModel().select(buscarH(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            cmbCodAdmin.getSelectionModel().select(buscarAdmin(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargo()));
    
        }
       
    }
    
    //15...
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
    
    //16...
    public Horarios buscarH(int codigoHorario){
        Horarios resultado=null;
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarHorario(?)}");
            procedimiento.setInt(1, codigoHorario);
            ResultSet registro=procedimiento.executeQuery();
            while(registro.next()){
                resultado=new Horarios(registro.getInt("codigoHorario"),registro.getString("horaEntrada"),
                                                                        registro.getString("horaSalida"),
                                                                        registro.getBoolean("lunes"),
                                                                        registro.getBoolean("martes"),
                                                                        registro.getBoolean("miercoles"),
                                                                        registro.getBoolean("jueves"),
                                                                        registro.getBoolean("viernes"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    
    }
    
    //17..
    public Cargos buscarCargo(int codigoCargo){
        Cargos resultado=null;
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargo(?)}");
            procedimiento.setInt(1, codigoCargo);
            ResultSet registro=procedimiento.executeQuery();
            while(registro.next()){
                resultado=new Cargos(registro.getInt("codigoCargo"),registro.getString("nombreCargo"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    
    }
    
    //18...
    public Departamento buscarDepa(int codigoDepartamento){
        Departamento resultado=null;
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDepartamento(?)}");
            procedimiento.setInt(1, codigoDepartamento);
            ResultSet registro=procedimiento.executeQuery();
            while(registro.next()){
                resultado=new Departamento(registro.getInt("codigoDepartamento"),registro.getString("nombreDepartamento"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    
    }
    
    //19...
    public void desactivarControles(){
        txtCodEmpleado.setEditable(false);
        txtNomEmpleado.setEditable(false);
        txtApelliEmpleado.setEditable(false);
        txtEmail.setEditable(false);
        txtTelEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        cmbCodAdmin.setEditable(true);
        cmbCodDepa.setEditable(true);
        cmbCodCargo.setEditable(true);
        cmbCodHorarios.setEditable(true);
        fechaContra.setDisable(true);
    }
    public void activarControles(){
        txtCodEmpleado.setEditable(false);
        txtNomEmpleado.setEditable(true);
        txtApelliEmpleado.setEditable(true);
        txtEmail.setEditable(true);
        txtTelEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        cmbCodAdmin.setEditable(false);
        cmbCodDepa.setEditable(false);
        cmbCodCargo.setEditable(false);
        cmbCodHorarios.setEditable(false);
        fechaContra.setDisable(false);
    }
    public void limpiarControles(){
        txtCodEmpleado.clear();
        txtNomEmpleado.clear();
        txtApelliEmpleado.clear();
        txtEmail.clear();
        txtTelEmpleado.clear();
        txtSueldo.clear();
        cmbCodAdmin.setValue(null);
        cmbCodDepa.setValue(null);
        cmbCodCargo.setValue(null);
        cmbCodHorarios.setValue(null);
        fechaContra.setPromptText("");
    }
    public void desactivarCMB(){
        cmbCodAdmin.setDisable(true);
        cmbCodDepa.setDisable(true);
        cmbCodCargo.setDisable(true);
        cmbCodHorarios.setDisable(true);
        //cmbCodDepa.setEditable(true);
        //cmbCodCargo.setEditable(true);
        //cmbCodHorarios.setEditable(true);
    }
    
    //20...
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
    
    //21....
    public void guardar(){
        Empleados registro=new Empleados();
        registro.setNombreEmpleado(txtNomEmpleado.getText());
        registro.setApellidoEmpleado(txtApelliEmpleado.getText());
        registro.setCorreoElectronico(txtEmail.getText());
        registro.setTelefonoEmpleado(txtTelEmpleado.getText());
        registro.setFechaContratacion(fechaContra.getSelectedDate());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setCodigoDepartamento((((Departamento)cmbCodDepa.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
        registro.setCodigoCargo((((Cargos)cmbCodCargo.getSelectionModel().getSelectedItem()).getCodigoCargo()));
        registro.setCodigoHorario((((Horarios)cmbCodHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario()));
        registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1,registro.getNombreEmpleado());
            procedimiento.setString(2,registro.getApellidoEmpleado());
            procedimiento.setString(3,registro.getCorreoElectronico());
            procedimiento.setString(4,registro.getTelefonoEmpleado());
            procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(6, registro.getSueldo());
            procedimiento.setInt(7,registro.getCodigoDepartamento());
            procedimiento.setInt(8, registro.getCodigoCargo());
            procedimiento.setInt(9, registro.getCodigoHorario());
            procedimiento.setInt(10, registro.getCodigoAdministracion());
            procedimiento.execute();
            listaEmpleados.add(registro);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    //22...
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
                if(tblEmpleados.getSelectionModel().getSelectedItem()!=null){
                   int respuesta;
                    respuesta=JOptionPane.showConfirmDialog(null, "¿Está seguro "
                            + " de eliminar este registro?","Eliminar Un Empleado",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }
    
    }
    
    
    //23...
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:{
                if(tblEmpleados.getSelectionModel().getSelectedItem()!=null){
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
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarEmpleado(?,?,?,?,?,?,?)}");
            Empleados registro=(Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNombreEmpleado(txtNomEmpleado.getText());
            registro.setNombreEmpleado(txtNomEmpleado.getText());
            registro.setApellidoEmpleado(txtApelliEmpleado.getText());
            registro.setCorreoElectronico(txtEmail.getText());
            registro.setTelefonoEmpleado(txtTelEmpleado.getText());
            registro.setFechaContratacion(fechaContra.getSelectedDate());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            //registro.setCodigoDepartamento((((Departamento)cmbCodDepa.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
            //registro.setCodigoCargo((((Cargos)cmbCodCargo.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            //registro.setCodigoHorario((((Horarios)cmbCodHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            //registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setString(4, registro.getCorreoElectronico());
            procedimiento.setString(5, registro.getTelefonoEmpleado());
            procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));             
            procedimiento.setDouble(7, registro.getSueldo());    
            //procedimiento.setInt(8, registro.getCodigoDepartamento());  
            //procedimiento.setInt(9, registro.getCodigoCargo());
            //procedimiento.setInt(10, registro.getCodigoHorario());
            //procedimiento.setInt(11, registro.getCodigoAdministracion());
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
                if(tblEmpleados.getSelectionModel().getSelectedItem()!=null){
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
        parametros.put("codigoEmpleado", null);
        GenerarReporte.mostrarReporte("ReporteEmpleados.jasper", "Reporte Datos Empleados", parametros);
       
    }
    
    public void reporteInduvidual(){
        Map parametros=new HashMap();
        int codE=((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado();
        parametros.put("codE", codE);
        GenerarReporte.mostrarReporte("ReporteEmpleadosIndividual.jasper", "Reporte de Empleado", parametros);
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
