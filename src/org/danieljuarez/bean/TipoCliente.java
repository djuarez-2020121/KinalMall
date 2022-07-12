
package org.danieljuarez.bean;

/**
 *
 * @author Daniel Juárez
 */
public class TipoCliente {
    //Atributos
    private int codigoTipoCliente;
    private String descripcion;
    
    //Constructores
    public TipoCliente() {
    }

    public TipoCliente(int codigoTipoCliente, String descripcion) {
        this.codigoTipoCliente = codigoTipoCliente;
        this.descripcion = descripcion;
    }
    
    //Modos de Accesos

    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return getCodigoTipoCliente() + " / " +getDescripcion();
    }
    
    
    
}
