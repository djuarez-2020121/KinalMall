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
import org.danieljuarez.bean.Departamento;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.report.GenerarReporte;
import org.danieljuarez.system.Principal;

/*
 *
 * @author Daniel Juárez
 */
public class DepartamentoController implements Initializable{
    //1....
    private Principal escenarioPrincpal;
    
    //6.....
    private enum operaciones {NUEVO,ELIMINAR,EDITAR,REPORTE,ACTUALIZAR,CANCELAR,GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion=operaciones.NINGUNO;
    
    //9.....
    private ObservableList<Departamento> listaDepartamento;
    
    //4.....
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoDepartamento;
    @FXML private TextField txtNombreDepartamento;
    @FXML private TableView tblDepartamentos;
    @FXML private TableColumn colCodgoDepartamento;
    @FXML private TableColumn colNombreDepartamento;
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
        tblDepartamentos.setItems(getDepartamento());
        colCodgoDepartamento.setCellValueFactory(new PropertyValueFactory<Departamento,Integer>("codigoDepartamento"));
        colNombreDepartamento.setCellValueFactory(new PropertyValueFactory<Departamento,String>("nombreDepartamento"));
    }
    
    //10....
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
        
        return listaDepartamento=FXCollections.observableArrayList(lista);
    }
    
    //7.....
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
                imgNuevo.setImage(new Image("/org/danieljuarez/images/agregarDepa.png"));
                imgEliminar.setImage(new Image("/org/danieljuarez/images/eliminar.png"));
                tipoDeOperacion=operaciones.NINGUNO;
                cargarDatos();
            }break;
        }
    }
    
   //11......
    public void guardar(){
        Departamento registro=new Departamento();
        registro.setNombreDepartamento(txtNombreDepartamento.getText());
        try{
            PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDepartamento(?)}");
            procedimiento.setString(1, registro.getNombreDepartamento());
            procedimiento.execute();
            listaDepartamento.add(registro);
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
                imgNuevo.setImage(new Image("/org/danieljuarez/images/agregarDepa.png"));
                imgEliminar.setImage(new Image("/org/danieljuarez/images/eliminar.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion=operaciones.NINGUNO;
            }break;
            default:
               if(tblDepartamentos.getSelectionModel().getSelectedItem()!=null){
                   int respuesta=JOptionPane.showConfirmDialog(null,"¿Esta seguro de eliminar el registro?","Eliminar Un Departamento",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==JOptionPane.YES_NO_OPTION){
                      try{
                          PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDepartamento(?)}");
                          procedimiento.setInt(1,((Departamento)tblDepartamentos.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
                          procedimiento.execute();
                          listaDepartamento.remove(tblDepartamentos.getSelectionModel().getSelectedIndex());
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
        if(tblDepartamentos.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento de la tabla de contenido");
        }else{
            txtCodigoDepartamento.setText(String.valueOf(((Departamento)tblDepartamentos.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
            txtNombreDepartamento.setText(((Departamento)tblDepartamentos.getSelectionModel().getSelectedItem()).getNombreDepartamento());
    
        }
    }
    
    //14....editar
    public void editar(){
       switch(tipoDeOperacion){
           case NINGUNO:{
               if(tblDepartamentos.getSelectionModel().getSelectedItem()!=null){
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
         PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_ModificarDepartamento(?,?)}");
         Departamento registro=(Departamento)tblDepartamentos.getSelectionModel().getSelectedItem();
         registro.setNombreDepartamento(txtNombreDepartamento.getText());
         procedimiento.setInt(1,registro.getCodigoDepartamento());
         procedimiento.setString(2, registro.getNombreDepartamento());
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
                if(tblDepartamentos.getSelectionModel().getSelectedItem()!=null){
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
        parametros.put("codigoDepartamento", null);
        GenerarReporte.mostrarReporte("ReporteDepa.jasper", "Reporte Datos Departamentos", parametros);
        
    
    }
    
    //5.....
    public void desactivarControles(){
        txtCodigoDepartamento.setEditable(false);
        txtNombreDepartamento.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoDepartamento.setEditable(false);
        txtNombreDepartamento.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoDepartamento.clear();
        txtNombreDepartamento.clear();
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
