/*
 * CtaCteItem.java
 *
 * Created on 11 de marzo de 2008, 6:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import java.util.Date;

/**
 *
 * @author Javier
 */
public class CtaCteItem implements Comparable{
    
    /** Creates a new instance of CtaCteItem */
    public CtaCteItem() {
    }
    private Date fechaCta;
    private Double debeCta;
    private Double haberCta;
    private Double saldoCta;
    private Double composicionCta;
    private Unidad unidadItem;

    public Date getFechaCta() {
        return fechaCta;
    }

    public void setFechaCta(Date fecha) {
        this.fechaCta = fecha;
    }

    public Double getDebeCta() {
        return debeCta;
    }

    public void setDebeCta(Double debe) {
        this.debeCta = debe;
    }

    public Double getHaberCta() {
        return haberCta;
    }

    public void setHaberCta(Double haber) {
        this.haberCta = haber;
    }

    public Double getSaldoCta() {
        return saldoCta;
    }

    public void setSaldoCta(Double saldo) {
        this.saldoCta = saldo;
    }

    public Double getComposicionCta() {
        return composicionCta;
    }

    public void setComposicionCta(Double composicion) {
        this.composicionCta = composicion;
    }
    
    public int compareTo(Object o) {
        CtaCteItem item = (CtaCteItem)o;       
        return this.fechaCta.compareTo(item.fechaCta);
    }

    public Unidad getUnidadItem() {
        return unidadItem;
    }

    public void setUnidadItem(Unidad unidad) {
        this.unidadItem = unidad;
    }
}
