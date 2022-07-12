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
import org.danieljuarez.bean.Cargos;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.report.GenerarReporte;
import org.danieljuarez.system.Principal;

/**
 *
 * @author Daniel Juárez
 */
public class CargosController implements Initializable{
    
    //1.....
    private Principal escenarioPrincpal;
    
    //6......
    private enum operaciones {NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    //9.....
    private ObservableList<Cargos> listaCargo;
    
    //4.....
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCargo;
    @FXML private TextField txtNombreCargo;
    @FXML private TableView tblCargos;
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colNombreCargo;
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
        tblCargos.setItems(getCargo());
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Cargos,Integer>("codigoCargo"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos,String>("nombreCargo"));
    }
    
    //10....
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
        
        return listaCargo=FXCollections.observableArrayList(lista);
    }
    
    
    //7......
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
    
    //11........
    public void guardar(){
        Cargos registro=new Cargos();
        registro.setNombreCargo(txtNombreCargo.getText());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargo(?)}");
            procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.execute();
            listaCargo.add(registro);
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
               if(tblCargos.getSelectionModel().getSelectedItem()!=null){
                   int respuesta=JOptionPane.showConfirmDialog(null,"¿Esta seguro de eliminar el registro?","Eliminar Cargo",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==JOptionPane.YES_NO_OPTION){
                        try{
                          PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargo(?)}");
                          procedimiento.setInt(1,((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargo());
                          procedimiento.execute();
                          listaCargo.remove(tblCargos.getSelectionModel().getSelectedIndex());
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
        if(tblCargos.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contendido");
        }else{
            txtCodigoCargo.setText(String.valueOf(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargo()));
        txtNombreCargo.setText(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo());
        }
        
       
    }
    
    //14....editar
    public void editar(){
       switch(tipoDeOperacion){
           case NINGUNO:{
                if(tblCargos.getSelectionModel().getSelectedItem()!=null){
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
         PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarCargo(?,?)}");
         Cargos registro=(Cargos)tblCargos.getSelectionModel().getSelectedItem();
         registro.setNombreCargo(txtNombreCargo.getText());
         procedimiento.setInt(1,registro.getCodigoCargo());
         procedimiento.setString(2, registro.getNombreCargo());
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
                if(tblCargos.getSelectionModel().getSelectedItem()!=null){
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
        parametros.put("codigoCargo", null);
        GenerarReporte.mostrarReporte("ReporteCargos.jasper", "Reporte Datos De Cargos", parametros);
        
    }
    
    
    //5.....
    public void desactivarControles(){
        txtCodigoCargo.setEditable(false);
        txtNombreCargo.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoCargo.setEditable(false);
        txtNombreCargo.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoCargo.clear();
        txtNombreCargo.clear();
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
    
    public void ventanaEmpleados(){
        escenarioPrincpal.ventanaEmpleadoss();
    }
}
