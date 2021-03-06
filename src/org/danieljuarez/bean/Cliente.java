package org.danieljuarez.bean;
/**
 *
 * @author Daniel Juárez
 */
public class Cliente {
//    codigoCliente int not null auto_increment,
//    nombreCliente varchar(45) not null,
//    apellidoCliente varchar(45) not null,
//    telefonoCliente varchar(8) not null,
//    direccionCliente varchar(60) not null,
//    email varchar(45) not null,
//    primary key PK_codigoCliente(codigoCliente),
//    codigoLocal int not null,
//    codigoAdministracion int not null,
//    codigoTipoCliente int not null, 
    //Laboratorio #3proveedores,horarios,cuentasporpagar
    
    //Atributos
    private int codigoCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;
    private String direccionCliente;
    private String email;
    private int codigoLocal;
    private int codigoAdministracion;
    private int codigoTipoCliente;

    
    //Constructores
    public Cliente() {
    }

    public Cliente(int codigoCliente, String nombreCliente, String apellidoCliente, String telefonoCliente, String direccionCliente, String email, int codigoLocal, int codigoAdministracion, int codigoTipoCliente) {
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.email = email;
        this.codigoLocal = codigoLocal;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoTipoCliente = codigoTipoCliente;
    }
    
    
    //Metodos de acceso

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }
    
    @Override
    public String toString() {
        return getCodigoCliente() +  " / "+getApellidoCliente() + " / "+getEmail();
    }
    
    
}
