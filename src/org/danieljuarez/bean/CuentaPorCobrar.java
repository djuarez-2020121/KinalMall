package org.danieljuarez.bean;


/**
 *
 * @author Daniel Juárez
 */
public class CuentaPorCobrar {
    /*
    codigoCuentaPorCobrar int not null auto_increment,
    numeroFactura varchar(45)  not null,
    anioo year(4)not null,
    mes int(2) not null,
    valorNetoPago double(11,2) not null,
    estadoPago varchar(45) not null,
    primary key PK_codigoCuentaPorCobrar(codigoCuentaPorCobrar),
    codigoAdministracion int not null,
    codigoCliente int not null,
    codigoLocal int not null,
    
    */
    
    //Atributos
    private int codigoCuentaPorCobrar;
    private String numeroFactura;
    private int anioo;
    private int mes;
    private Double valorNetoPago;
    private String estadoPago;
    private int codigoAdministracion;
    private int codigoCliente;
    private int codigoLocal;

    
    
    public CuentaPorCobrar() {
    }

    public CuentaPorCobrar(int codigoCuentaPorCobrar, String numeroFactura, int anioo, int mes, Double valorNetoPago, String estadoPago, int codigoAdministracion, int codigoCliente, int codigoLocal) {
        this.codigoCuentaPorCobrar = codigoCuentaPorCobrar;
        this.numeroFactura = numeroFactura;
        this.anioo = anioo;
        this.mes = mes;
        this.valorNetoPago = valorNetoPago;
        this.estadoPago = estadoPago;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoCliente = codigoCliente;
        this.codigoLocal = codigoLocal;
    }

    public int getCodigoCuentaPorCobrar() {
        return codigoCuentaPorCobrar;
    }

    public void setCodigoCuentaPorCobrar(int codigoCuentaPorCobrar) {
        this.codigoCuentaPorCobrar = codigoCuentaPorCobrar;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public int getAnioo() {
        return anioo;
    }

    public void setAnioo(int anioo) {
        this.anioo = anioo;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public Double getValorNetoPago() {
        return valorNetoPago;
    }

    public void setValorNetoPago(Double valorNetoPago) {
        this.valorNetoPago = valorNetoPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    
    
    
    
    
}
