package org.danieljuarez.bean;

import java.util.Date;
/**
 *
 * @author Daniel Juárez
 */
public class Empleados {
//    codigoEmpleado int not null auto_increment,
//    nombreEmpleado varchar(45) not null,
//    apellidoEmpleado varchar(45) not null,
//    correoElectronico varchar(45) not null,
//    telefonoEmpleado varchar(8) not null,
//    fechaContratacion date not null,
//    sueldo double(11,2) not null,
//    primary key PK_codigoEmpleado(codigoEmpleado),
//    codigoDepartamento int not null,
//    codigoCargo int not null,
//    codigoHorario int not null,
//    codigoAdministracion int not null,
    
    //Atributos
    private int codigoEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String correoElectronico;
    private String telefonoEmpleado;
    private Date fechaContratacion;
    private Double sueldo;
    private int codigoDepartamento;
    private int codigoCargo;
    private int codigoHorario;
    private int codigoAdministracion;

    
    //Cosntructores
    public Empleados() {
    }

    public Empleados(int codigoEmpleado, String nombreEmpleado, String apellidoEmpleado, String correoElectronico, String telefonoEmpleado, Date fechaContratacion, Double sueldo, int codigoDepartamento, int codigoCargo, int codigoHorario, int codigoAdministracion) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.correoElectronico = correoElectronico;
        this.telefonoEmpleado = telefonoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.sueldo = sueldo;
        this.codigoDepartamento = codigoDepartamento;
        this.codigoCargo = codigoCargo;
        this.codigoHorario = codigoHorario;
        this.codigoAdministracion = codigoAdministracion;
    }
    
    //Metodos de acceso

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public int getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(int codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public int getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(int codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public int getCodigoHorario() {
        return codigoHorario;
    }

    public void setCodigoHorario(int codigoHorario) {
        this.codigoHorario = codigoHorario;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }
    
    
    
    
}
