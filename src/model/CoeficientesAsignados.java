/*
 * CoeficientesAsignados.java
 *
 * Created on 12 de enero de 2008, 14:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Javier
 */
public class CoeficientesAsignados {
    
    /** Creates a new instance of CoeficientesAsignados */
    public CoeficientesAsignados() {
    }
    private long codigo;
    private Coeficiente coeficiente;
    private Double valor;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public Coeficiente getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(Coeficiente coeficiente) {
        this.coeficiente = coeficiente;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
}
