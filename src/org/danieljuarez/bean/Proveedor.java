package org.danieljuarez.bean;
/**
 *
 * @author Daniel Juárez
 */
public class Proveedor {
//    codigoProveedor int not null auto_increment,
//    NITProveedor varchar (45) not null,
//    servicioPrestado varchar (45) not null,
//    telefonoProveedor varchar (45) not null,
//   -- proveedorescol varchar (45) not null,
//    direccionProveedor varchar (60),
//    saldoFavor double(11,2) not null,
//    saldoContra double(11,2)not null,
//    primary key PK_codigoProveedor (codigoProveedor),
//    codigoAdministracion int not null,
    
    //Atributos
    
    private int codigoProveedor;
    private String NITProveedor;
    private String servicioPrestado;
    private String telefonoProveedor;
    private String direccionProveedor;
    private double saldoFavor;
    private double saldoContra;
    private int codigoAdministracion;

    
    //Constructores
    
    public Proveedor() {
    }

    public Proveedor(int codigoProveedor, String NITProveedor, String servicioPrestado, String telefonoProveedor, String direccionProveedor, double saldoFavor, double saldoContra, int codigoAdministracion) {
        this.codigoProveedor = codigoProveedor;
        this.NITProveedor = NITProveedor;
        this.servicioPrestado = servicioPrestado;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
        this.codigoAdministracion = codigoAdministracion;
    }
    
    //Metodos de acceso 

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITProveedor() {
        return NITProveedor;
    }

    public void setNITProveedor(String NITProveedor) {
        this.NITProveedor = NITProveedor;
    }

    public String getServicioPrestado() {
        return servicioPrestado;
    }

    public void setServicioPrestado(String servicioPrestado) {
        this.servicioPrestado = servicioPrestado;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public double getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(double saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public double getSaldoContra() {
        return saldoContra;
    }

    public void setSaldoContra(double saldoContra) {
        this.saldoContra = saldoContra;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    @Override
    public String toString() {
        return getCodigoProveedor() +" / "+ getServicioPrestado();
    }
    
    
    
}
