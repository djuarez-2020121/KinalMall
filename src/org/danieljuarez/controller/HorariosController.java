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
import org.danieljuarez.bean.Horarios;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.report.GenerarReporte;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class HorariosController implements Initializable{

    //1....
    private Principal escenarioPrincpal;
    
    
    //6.....
    private enum operaciones {NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    //9.....
    private ObservableList<Horarios> listaH;
    
    //4......
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodH;
    @FXML private TextField txtHorarioEntrada;
    @FXML private TextField txtHorarioSalida;
    @FXML private TextField txtLu;
    @FXML private TextField txtMa;
    @FXML private TextField txtMi;
    @FXML private TextField txtJu;
    @FXML private TextField txtVi;
    @FXML private TableView tblHorarios;
    @FXML private TableColumn colCodH;
    @FXML private TableColumn colHoraEntrada;
    @FXML private TableColumn colHoraSalida;
    @FXML private TableColumn colLu;
    @FXML private TableColumn colMa;
    @FXML private TableColumn colMi;
    @FXML private TableColumn colJu;
    @FXML private TableColumn colVi;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }
    
    //8...
    public void cargarDatos(){
        tblHorarios.setItems(getHorario());
        colCodH.setCellValueFactory(new PropertyValueFactory<Horarios,Integer>("codigoHorario"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Horarios,String>("horaEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<Horarios,String>("horaSalida"));
        colLu.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("lunes"));
        colMa.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("martes"));
        colMi.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("miercoles"));
        colJu.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("jueves"));
        colVi.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("viernes"));
    }
    
    //10....
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
        
        return listaH=FXCollections.observableArrayList(lista);
    }
    
    
    
    
    
    
    //7.....
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
                imgNuevo.setImage(new Image("/org/danieljuarez/images/agregarH.png"));
                imgEliminar.setImage(new Image("/org/danieljuarez/images/eliminar.png"));
                tipoDeOperacion=operaciones.NINGUNO;
                cargarDatos();
            }break;
        }
    }
    
   //11......
    public void guardar(){
        Horarios registro=new Horarios();
        registro.setHoraEntrada(txtHorarioEntrada.getText());
        registro.setHoraSalida(txtHorarioSalida.getText());
        registro.setLunes(Boolean.parseBoolean(txtLu.getText()));
        registro.setMartes(Boolean.parseBoolean(txtMa.getText()));
        registro.setMiercoles(Boolean.parseBoolean(txtMi.getText()));
        registro.setJueves(Boolean.parseBoolean(txtJu.getText()));
        registro.setViernes(Boolean.parseBoolean(txtVi.getText()));
        
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarHorario(?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getHoraEntrada());
            procedimiento.setString(2, registro.getHoraSalida());
            procedimiento.setBoolean(3, registro.isLunes());
            procedimiento.setBoolean(4, registro.isMartes());
            procedimiento.setBoolean(5, registro.isMiercoles());
            procedimiento.setBoolean(6, registro.isJueves());
            procedimiento.setBoolean(7, registro.isViernes());
            procedimiento.execute();
            listaH.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    
//    //12.....
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:{
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/danieljuarez/images/agregarDepa.png"));
                imgEliminar.setImage(new Image("/org/danieljuarez/images/eliminar.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion=operaciones.NINGUNO;
            }break;
            default:
               if(tblHorarios.getSelectionModel().getSelectedItem()!=null){
                   int respuesta=JOptionPane.showConfirmDialog(null,"¿Esta seguro de eliminar el registro?","Eliminar Un Horario",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==JOptionPane.YES_NO_OPTION){
                      try{
                          PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarHorario(?)}");
                          procedimiento.setInt(1,((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario());
                          procedimiento.execute();
                          listaH.remove(tblHorarios.getSelectionModel().getSelectedIndex());
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
    
//    //13.....
    public void seleccionarElemento(){
        if(tblHorarios.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }else{
            txtCodH.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            txtHorarioEntrada.setText(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHoraEntrada());
            txtHorarioSalida.setText(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHoraSalida());
            txtLu.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isLunes()));
            txtMa.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMartes()));
            txtMi.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles()));
            txtJu.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isJueves()));
            txtVi.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isViernes()));
    
        }
        
    }
//    
    //14....editar
    public void editar(){
       switch(tipoDeOperacion){
           case NINGUNO:{
               if(tblHorarios.getSelectionModel().getSelectedItem()!=null){
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
         PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarHorario(?,?,?,?,?,?,?,?)}");
         Horarios registro=(Horarios)tblHorarios.getSelectionModel().getSelectedItem();
         registro.setHoraEntrada(txtHorarioEntrada.getText());
         registro.setHoraSalida(txtHorarioSalida.getText());
         registro.setLunes(Boolean.parseBoolean(txtLu.getText()));
         registro.setMartes(Boolean.parseBoolean(txtMa.getText()));
         registro.setMiercoles(Boolean.parseBoolean(txtMi.getText()));
         registro.setJueves(Boolean.parseBoolean(txtJu.getText()));
         registro.setViernes(Boolean.parseBoolean(txtVi.getText()));
         procedimiento.setInt(1,registro.getCodigoHorario());
         procedimiento.setString(2, registro.getHoraEntrada());
         procedimiento.setString(3, registro.getHoraSalida());
         procedimiento.setBoolean(4, registro.isLunes());
         procedimiento.setBoolean(5, registro.isMartes());
         procedimiento.setBoolean(6, registro.isMiercoles());
         procedimiento.setBoolean(7, registro.isJueves());
         procedimiento.setBoolean(8, registro.isViernes());
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
                if(tblHorarios.getSelectionModel().getSelectedItem()!=null){
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
        parametros.put("codigoHorario", null);
        GenerarReporte.mostrarReporte("ReporteHorarios.jasper", "Reporte Datos Horario", parametros);
        
    
    }
     
    
    //5.....
    public void desactivarControles(){
        txtCodH.setEditable(false);
        txtHorarioEntrada.setEditable(false);
        txtHorarioSalida.setEditable(false);
        txtHorarioEntrada.setEditable(false);
        txtLu.setEditable(false);
        txtMa.setEditable(false);
        txtMi.setEditable(false);
        txtJu.setEditable(false);
        txtVi.setEditable(false);
    }
    
    public void activarControles(){
        txtCodH.setEditable(false);
        txtHorarioEntrada.setEditable(true);
        txtHorarioSalida.setEditable(true);
        txtHorarioEntrada.setEditable(true);
        txtLu.setEditable(true);
        txtMa.setEditable(true);
        txtMi.setEditable(true);
        txtJu.setEditable(true);
        txtVi.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodH.clear();
        txtHorarioEntrada.clear();
        txtHorarioSalida.clear();
        txtHorarioEntrada.clear();
        txtLu.clear();
        txtMa.clear();
        txtMi.clear();
        txtJu.clear();
        txtVi.clear();
    }
    
    
    //2.....
    public Principal getEscenarioPrincpal() {
        return escenarioPrincpal;
    }

    public void setEscenarioPrincpal(Principal escenarioPrincpal) {
        this.escenarioPrincpal = escenarioPrincpal;
    }
    
    //3.......
    
    public void menuPrincipal(){
        escenarioPrincpal.menuPrincipal();
    }
}
